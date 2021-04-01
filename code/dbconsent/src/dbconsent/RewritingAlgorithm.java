package dbconsent;

import dbconsent.DatalogParser.ParseDatalog;

import java.util.*;

/**
 * The main rewriting algorithm that combines DatalogStatements that represents Queries and Negative Constraints,
 * and combines them, producing a DatalogStatement representing a consent abiding rewriting.
 *
 */
public class RewritingAlgorithm {

    /** A dummy main method that just checks some stuff*/
    public static void main(String[] args) {
        String[] queries = {"Q(a) :- A(a,b,c)", "Q(a,b,c) :- A(a,b,c)", "Q(a) :- A(a,b,c)", "Q(a) :- A(a,b,c)",
                "Q(x,z) :- A(x,y), A(y,z)", "Q(x,y) :- A(x,y,5)", "Q(x,y) :- A(x,y,z)",
                "Q(x,y,z,w,v) :- P(x,y,z), P(y,w,v)", "Q(x) :- P(x,y,z), P(y,w,v)",
                "Q(x,y,z,w,v) :- P(x,y,z), P(y,w,v)", "Q(x) :- P(x,y,z), P(y,w,v)",
                "Q(x,y,z,w) :- A(x,y), A(y,z), B(z,v), B(v,w)",
                "Q(x,y,z,w) :- A(x,y), A(y,z), B(z,v), B(v,w)",
                "Q(x,w) :- P(x,y,z), A(w)"
                };
        String[] constraints = {"N(a) :- A(a,b,c)", "N(a) :- A(a,b,c)", "N(b) :- A(a,b,c)", "N(a,b) :- A(a,b,c)",
                "N(a,b) :- A(a,b)",           "N(a,c) :- A(a,b,c)", "N(a) :- A(a,b,5)",
                "N(a,b,d) :- P(a,b,0), P(b,d,1)", "N(a) :- P(a,b,0), P(b,d,1)",
                "N(a,b,c,d) :- P(a,b,0), P(c,d,1)", "N(a,b,d) :- P(a,b,0), P(b,d,1)",
                "N(a,c) :- A(a,b), B(b,c)",
                "N(a,c) :- A(b,a), A(b,0), B(b,d), B(d,c)",
                "N(a,e) :- P(a,b,c), P(b,d,1), A(e)"
                };

        ParseDatalog parseDatalog = new ParseDatalog();
        for (int i = 0; i < queries.length; i++){
            String query = queries[i];
            String constraint = constraints[i];
            DatalogStatement queryDS = parseDatalog.safeParseString(query);
            DatalogStatement constraintDS = parseDatalog.safeParseString(constraint);
            DatalogStatement rewritingDS = RewritingAlgorithm.rewriteQuery(queryDS, constraintDS);
            System.out.println(rewritingDS.toString());
        }
    }

    /**
     * A single constraint can produce many RewrittenConstraints for it's different unification options.
     * This method gets the set of applicable ECsets for each constraint and rewrites the constraint into a set of RewrittenConstraints,
     * and then associates them with the Query
     * @param query the Query
     * @param constraints the set of constraints
     * @return the Query with all of the RewrittenConstraints for a set of Constraints applied
     */
    public static DatalogStatement rewriteQuery(DatalogStatement query, Set<DatalogStatement> constraints) {
        // Rename all constraints to ensure there is not clash with a previous usage (during propagation).
        try {
            for(DatalogStatement constraint : constraints) {
                PoolOfNames.safeNamespace(constraint);
            }
        } catch (HeadVariableNotInQueryException e) {
            e.printStackTrace();
        }

        DatalogStatement rewriting = new DatalogStatement(query);
        rewriting.setPredicate(query.getPredicate());
        rewriting.applyConstraints(getRewrittenConstraints(query, constraints));
        return rewriting;
    }

    /**
     * A single constraint can produce many RewrittenConstraints for its different unification options.
     * This method gets the set of applicable ECsets and rewrites the constraint into a set of RewrittenConstraints,
     * and then associates them with the Query
     * @param query the Query
     * @param constraint the Constraint
     * @return the Query with all of the RewrittenConstraints for for a single Constraint applied
     */
    public static DatalogStatement rewriteQuery(DatalogStatement query, DatalogStatement constraint) {
        Set<DatalogStatement> constraintSet = new HashSet<>();
        constraintSet.add(constraint);
        return rewriteQuery(query, constraintSet);
    }


    static Set<RewrittenConstraint> getRewrittenConstraints(DatalogStatement query, Set<DatalogStatement> constraints) {

        // Rewrite every constraint to use only query terms
        // A single constraint can produce many RewrittenConstraints for its different unification options.
        // For example, for query `Q(x,z) :- A(x,y), A(y,z)` with constraint `N(a) :- A(a,a)`,
        // there are 2 Rewritten Constraints - `A(x,y)` unified with `A(a,a)` produces `A(x,x), x = y`
        // while `A(y,z)` unified with `A(a,a)` produces `A(z,z), z = y`.
        Set<RewrittenConstraint> rewrittenConstraints = new HashSet<>();
        for (DatalogStatement constraint : constraints) {

            // Find all possible substitutions for a constraint,
            // then apply each substitution to produce a RewrittenConstraint.
            Set<ECset> substitutions = getApplicableECsets(query, constraint);
            for (ECset substitution : substitutions) {
                RewrittenConstraint rewrittenConstraint = new RewrittenConstraint("N'");
                for (Atom atom : constraint.getAtoms()) {
                    // We can optimise our query by removing any predicates which were used in the unionECsets
                    if (!substitution.getUnifiedConstraintAtoms().contains(atom)) {

                        // Apply the ECset as a substitution. Replace terms in atom with representative of EC they are in.
                        Atom newAtom = new Atom(atom.predicate);
                        for (Term term : atom.getTerms()) {
                            newAtom.addTerm(applyECsetToTerm(term, substitution));
                        }
                        rewrittenConstraint.addAtom(newAtom);
                    }
                }

                // TODO Why getEqualitiesClosure vs getEqualities?
                if (rewrittenConstraint.numberOfAtoms() == 0) {
                    rewrittenConstraint.addEquates(substitution.getEqualitiesClosure());
                } else {
                    rewrittenConstraint.addEquates(substitution.getEqualities());
                }

                // Head variables are not needed in the RewrittenConstraint
                rewrittenConstraints.add(rewrittenConstraint);
            }
        }
        return rewrittenConstraints;
    }

    /**
     * Essentially applying the ECset as a substitution. Replace term with representative of EC it is in
     * @param term the term that we wish to replace
     * @param substitution the ECset to replace the Term using
     * @return the term with the ECset applied as a substitution
     */
    private static Term applyECsetToTerm(Term term, ECset substitution) {
        EquivalenceClass equivalenceClass = substitution.getEquivalenceClasses().get(term);
        if (equivalenceClass != null) {
            return equivalenceClass.getRepresentative();
        } else {
            return term;
        }
    }

    /**
     * Get the applicable atomic ECsets, then merge the open (ie non-applicable) ECsets as pairs, triples, etc
     * @param query the Query
     * @param constraint the Constraint
     * @return the set of all applicable ECsets for this query and constraint.
     */
    private static Set<ECset> getApplicableECsets(DatalogStatement query, DatalogStatement constraint) {
        //atomic (single unification) ECsets that do not apply (and cannot be used for a rewriting without merging with another)
        List<ECset> openAtomicECsets = new ArrayList<>();
        //This set is just used to make things easier for the merging step later
        //It's the set of elements from openAtomicECsets combined with their index in the list
        Set<MemoizedECsetUnion> indexedOpenECsets = new HashSet<>();
        //The set of ECsets that are applicable (both atomic and merged)
        Set<ECset> applicableECsets = new HashSet<>();

        //PROPAGATION: The indices (of openAtomicECsets) of atoms of previous queries that have been unfolded.
        Map<String, Set<Integer>> unfoldedQueries = new HashMap<>();

        for (Atom queryAtom : query.getAtoms()) {
            //If the constraint has been indexed prior, we only need those atoms from the index
            List<Atom> constraintAtoms = constraint.getAtoms(queryAtom.predicate);

            //Try to unify each query predicate with each constraint predicate
            for (Atom constraintAtom : constraintAtoms) {
                //Try to create a memoizedECset - if successful, it's a rewriting we want.
                //If unsuccessful, we want to combine with other unsuccessful rewritings.
                ECset eCset = unify(query, queryAtom, constraintAtom);
                if (eCset != null) {
                    if (doesCoverHead(eCset, query, constraint)) {
                        //Successful - this ECset is applicable!
                        //Only use it if it did not come from an unfolded query
                        if (queryAtom.getUnfoldedQuery() == null) {
                            applicableECsets.add(eCset);
                        }
                    } else {
                        //Unsuccessful - add to open atomic sets and combine later at the recursive step
                        openAtomicECsets.add(eCset);
                        int index = openAtomicECsets.size() - 1;
                        indexedOpenECsets.add(new MemoizedECsetUnion(index, eCset));

                        // BEGIN PROPAGATION SECTION
                        if (queryAtom.getUnfoldedQuery() != null) {
                            String unfoldedQueryName = queryAtom.getUnfoldedQuery();
                            unfoldedQueries.computeIfAbsent(unfoldedQueryName, k -> new HashSet<>()).add(index);
                        }
                        // END PROPAGATION SECTION
                    }
                }
            }
        }
        //If we have only one open ECset, we can't merge it with anything
        if (indexedOpenECsets.size() > 1) {
            //Recursive step - union combinations of open atomic EC sets to find applicables merges
            return getApplicableECsetUnions(query, constraint, openAtomicECsets, indexedOpenECsets,
                    new HashSet<>(), new HashSet<>(unfoldedQueries.values()), applicableECsets, 2);
        } else {
            return applicableECsets;
        }
    }

    /**
     * We've already tried all atomic unifications. Now we need to try unions of open (ie not-applicable) unifications.
     * First we want to try pairs, then triples, etc.
     * If a pair was applicable, we don't want to try any triples containing that pair.
     * This is a recursive function to try merges of size n, by combining open atomic unifications with
     * the memoized unions of n-1 elements.
     * Call from getApplicableECsets.
     * @param query the Query
     * @param constraint the Constraint
     * @param openAtomicECsets the ECsets produced by atomic unifications that were not applicable (passthrough)
     * @param memoizedECsetUnions the set of unioned ECsets of size n-1 that were open
     * @param applicableCombinations the set of combinations of any size that were applicable
     * @param applicableECsets the ECsets that were appliable - we add
     * @param depth - the size of the n-tuple we want to merge
     * @return the set of all applicable ECsets for this query and constraint.
     */
    private static Set<ECset> getApplicableECsetUnions(DatalogStatement query, DatalogStatement constraint,
                                                       List<ECset> openAtomicECsets,
                                                       Set<MemoizedECsetUnion> memoizedECsetUnions,
                                                       Set<Set<Integer>> applicableCombinations,
                                                       Set<Set<Integer>> previousQueries,
                                                       Set<ECset> applicableECsets,
                                                       int depth) {
        //This functionality represents lines 14:26 in the findRewritingsSubstitutionsAndModify part
        //The main difference is we use memoization (as described above) rather than
        //computing successive unions

        //A set of tried combinations at this depth
        Set<Set<Integer>> triedCombinations = new HashSet<>();

        //The set of open unions of unifications at this depth, to send to the recursive step
        Set<MemoizedECsetUnion> newMemoizedECsetUnions = new HashSet<>();
        //For every open union of unifications from the depth-1, try to combine with all open atomic ECsets
        for (MemoizedECsetUnion unsuccessfulSubstitution : memoizedECsetUnions) {
            for (int i = 0; i < openAtomicECsets.size(); i++) {
                try {
                    //Represent the set of indexes of the atomic unifications as the `combination`
                    Set<Integer> combination = new HashSet<>(unsuccessfulSubstitution.combination);
                    combination.add(i);
                    //If the combination hasn't been tried before and isn't subsumed by some applicable combination,
                    //we want to union the atomic guy and the union guy
                    if(isCombinationAllowed(triedCombinations, applicableCombinations, combination, depth)){
                        //Indicate we should never try this combination again
                        triedCombinations.add(combination);
                        //Combination is new, so we should union the two ECsets
                        ECset memoizedECset = unsuccessfulSubstitution.memoizedECset;
                        ECset atomicECset = openAtomicECsets.get(i);

                        //Try the union
                        ECset combinedSubstitution = memoizedECset.unionECsets(atomicECset);
                        //Test applicability of the union
                        if(doesCoverHead(combinedSubstitution, query, constraint)) {

                            //BEGIN PROPAGATION SECTION
                            //Check if all atoms used in this unification come from the same previous (unfolded) query
                            boolean allAtomsFromOnePreviousQuery = false;
                            for (Set<Integer> unfoldedQueryCombination : previousQueries) {
                                if (unfoldedQueryCombination.containsAll(combination)) {
                                    //All atoms are from the same previous query.
                                    allAtomsFromOnePreviousQuery = true;
                                    break;
                                }
                            }
                            if (allAtomsFromOnePreviousQuery) {
                                //Do not add this ECset to applicableECsets as this rewriting must already have been
                                //produced on the original query. However, still add it as an 'applicable combination'
                                //as its rewriting will already have been produced in the original query.
                                applicableCombinations.add(combination);
                            //END PROPAGATION SECTION

                            } else {
                                //Applicable
                                applicableECsets.add(combinedSubstitution);
                                applicableCombinations.add(combination);
                            }

                        } else {
                            //Not applicable - try again next round
                            newMemoizedECsetUnions.add(new MemoizedECsetUnion(combination, combinedSubstitution));
                        }
                    }
                } catch (ConstantClashException e) {
                    //Constant clash just means stop trying after the unionECsets step
                }
            }
        }
        //If our depth is equal to the number of atomic ECsets, we've tried every combination
        //Every ECset at depth n requires at least n open combinations at the previous depth,
        //so if we don't have more open substitutions than our current depth, depth+1 can't happen, so we're done
        if (depth != openAtomicECsets.size() && newMemoizedECsetUnions.size() > depth) {
            //Recursive Step
            return getApplicableECsetUnions(query, constraint, openAtomicECsets, newMemoizedECsetUnions, applicableCombinations, previousQueries, applicableECsets, depth + 1);
        } else {
            //Base case - nothing left to merge
            return applicableECsets;
        }
    }

    /**
     * We must determine if a set has already been tried before, so we don't waste resources trying it again.
     * We don't want to try combinations that have been subsumed by a successful combination, nor do we want to try
     * combinations that have been attempted before.
     * @param triedCombinations the indexes of combinations of atomic unifications that we already tested the applicability of
     * @param successfulCombinations the indexes of combinations of atomic unifications that resulted in applicable ECsets
     * @param combination the combination of indexes of atomic unifications that we want to test if we should bother computing
     * @param requiredCombinationSize the required size of the combination
     * @return Whether or not this combination should be tried.
     */
    private static boolean isCombinationAllowed(Set<Set<Integer>> triedCombinations,
                                                Set<Set<Integer>> successfulCombinations,
                                               Set<Integer> combination, int requiredCombinationSize) {
        //If a combination doesn't have the right size (ie it was created with duplicate indexes),
        //we should reject it
        if (combination.size() != requiredCombinationSize) {
            return false;
        }
        //If we have already tried our combination before, we should not bother trying it again
        if (triedCombinations.contains(combination)){
            return false;
        }
        //If some successful combination is a subset of this combination, then this set is subsumed by that set
        //So we should not bother trying again
        for (Set<Integer> successfulCombination: successfulCombinations) {
            if(combination.containsAll(successfulCombination)) {
                return false;
            }
        }
        //If we got here, it's a potential combination
        return true;
    }

    /**
     * This tests the applicablility of a constraint with a given ECset (applied as a substitution) to the query
     * @param substitution the ECset that we want to test the applicability of
     * @param query the Query
     * @param constraint the Constraint
     * @return <code>true</code> if the ECset is applicable, otherwise <code>false</code>
     */
    private static boolean doesCoverHead(ECset substitution, DatalogStatement query, DatalogStatement constraint) {
        for (Variable variable : constraint.getHeadVariables()) {
            if (!substitution.containsTerm(variable)) {
                return false;
            }
            Term substituted = applyECsetToTerm(variable, substitution);
            if(!(substituted instanceof Constant || query.containsHeadVariable(substituted))){
                return false;
            }
        }
        return true;
    }

    /**
     * Unify two atoms, resulting in a set of equivalence classes, ECset, which represents a substitution
     * @param query the query
     * @param queryAtom the query atom that we are unifying
     * @param constraintAtom the constraint atom that we are unifying
     * @return the set of Equivalence Classes that represent a substitution, or <code>null</code> if they do not unify.
     */
    static ECset unify(DatalogStatement query, Atom queryAtom, Atom constraintAtom) {
        //If the two atoms don't share the same predicate, they can't be unified
        if (!queryAtom.predicate.equals(constraintAtom.predicate)) {
            return null;
        }

        //If the two atoms don't share the same number of terms, they can't be unified
        if (queryAtom.numberOfTerms() != constraintAtom.numberOfTerms()) {
            return null;
        }

        //For each pair of terms at the same index in the atoms, add them to the same Equivalence Class (EC).
        //This will build up a set of equivalence classes.
        ECset eCset = new ECset(query, constraintAtom);
        try {
            for (int i = 0; i < queryAtom.numberOfTerms(); i++) {
                Term queryPredicateArgument = queryAtom.getTerm(i);
                Term constraintPredicateArgument = constraintAtom.getTerm(i);
                //Add these predicate arguments to the unification

                eCset.addSubstitution(queryPredicateArgument, constraintPredicateArgument);
            }
        } catch (ConstantClashException e) {
            //Just stop trying this memoizedECset
            return null;
        }
        return eCset;
    }

}
