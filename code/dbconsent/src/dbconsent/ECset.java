package dbconsent;

import java.util.*;

/**
 * A set of disjoint EquivalenceClass (EC). In aggregation, these represent a memoizedECset -
 * every Term is substituted with the representative of the EC that contains it.
 * 
 */
public class ECset {

    /** The query that this ECset is over.
     * We need to know this so that we can determine which terms in the ECs are query terms. */
    private DatalogStatement query;

    /** Map of Term to the EquivalenceClass that contains it */
    private Map<Term, EquivalenceClass> equivalenceClasses = new HashMap<>();

    /** Set of Atoms from the constraint that were used for the unification(s) that are part of this ECset.
     * We don't want to add these to the rewriting serialization that uses this ECset. */
    private Set<Atom> unifiedConstraintAtoms = new HashSet<>();

    /**
     * Atomic ECset constructor for when creating atomic unifications
     * @param query the query that the ECset is operating on
     * @param unifiedConstraintAtom the Atom from the constraint that was used for unification that is creating this ECset
     */
    public ECset(DatalogStatement query, Atom unifiedConstraintAtom) {
        this.query = query;
        this.unifiedConstraintAtoms.add(unifiedConstraintAtom);
    }

    /**
     * Copy Constructor
     * @param otherECset the ECset to copy
     */
    public ECset(ECset otherECset) {
        this.query = otherECset.query;
        for (Term predicateArgument : otherECset.equivalenceClasses.keySet()) {
            EquivalenceClass equivalenceClass = otherECset.equivalenceClasses.get(predicateArgument);
            equivalenceClasses.put(predicateArgument, new EquivalenceClass(equivalenceClass));
        }
        this.unifiedConstraintAtoms = new HashSet<>(otherECset.unifiedConstraintAtoms);
    }

    /**
     * ECset constructor for use when unioning ECsets
     * @param query the query that the ECset is operating on
     * @param equivalenceClasses The set of ECs that this ECset will contain
     * @param atoms Atoms from the constraint that were used for the unification(s) that are part of this ECset
     */
    public ECset(DatalogStatement query, Set<EquivalenceClass> equivalenceClasses, Set<Atom> atoms) {
        this.query = query;
        for (EquivalenceClass equivalenceClass : equivalenceClasses) {
            for (Term predicateArgument : equivalenceClass) {
                this.equivalenceClasses.put(predicateArgument, equivalenceClass);
            }
        }
        this.unifiedConstraintAtoms = atoms;
    }

    /**
     * When unifying, we want to take two terms (one each from query and constraint),
     * and put them in the same equivalence class.
     * We may have to create an equivalence class for them, or merge two existing, etc
     * Represents lines 5-11 from the `unify` pseudocode
     * @param constraintTerm The constraint term
     * @param queryTerm The query term
     * @throws ConstantClashException thrown when we cause an EquivalenceClass to contain two different Constants
     */
    public void addSubstitution(Term constraintTerm, Term queryTerm) throws ConstantClashException {
        // Our two terms may already be in some equivalence classes.
        // If both are in an equivalence class, we need to unionECsets those two equivalence classes
        // If just one is, we can safely add the other to it
        // If neither is, we need to create a new equivalence class
        if(equivalenceClasses.containsKey(constraintTerm) && equivalenceClasses.containsKey(queryTerm)){
            //Both terms have an associated EquivalenceClass - we need to unionECsets them
            EquivalenceClass replaceEquiv = equivalenceClasses.get(constraintTerm);
            EquivalenceClass withEquiv = equivalenceClasses.get(queryTerm);
            //Are these two equivalenceClasses
            if(replaceEquiv.equals(withEquiv)) {
                //They're both already here, and in the same equivalence - do nothing
            } else {
                //They're different
                EquivalenceClass newEquivalenceClass = EquivalenceClass.inPlaceUnion(replaceEquiv, withEquiv);
                //Replace references of the smaller equivalence - all of it's values were added to the larger equivalence
                EquivalenceClass smallestEquivalenceClass = replaceEquiv.size() < withEquiv.size() ? replaceEquiv : withEquiv;
                for (Term key : smallestEquivalenceClass){
                    equivalenceClasses.put(key, newEquivalenceClass);
                }
            }
        } else if (equivalenceClasses.containsKey(constraintTerm)) {
            //Only 'constraintTerm' is in an equivalence class - add `queryTerm` to it
            EquivalenceClass equivalenceClass = equivalenceClasses.get(constraintTerm);
            equivalenceClass.equatePredicateArgument(queryTerm);
            equivalenceClasses.put(queryTerm, equivalenceClass);
        } else if (equivalenceClasses.containsKey(queryTerm)) {
            //Only 'queryTerm' is in an equivalence class - add `constraintTerm` to it
            EquivalenceClass equivalenceClass = equivalenceClasses.get(queryTerm);
            equivalenceClass.equatePredicateArgument(constraintTerm);
            equivalenceClasses.put(constraintTerm, equivalenceClass);
        } else {
            //Neither term is in an equivalenceClass, so create a new one
            EquivalenceClass equivalenceClass = new EquivalenceClass(query);
            equivalenceClass.equatePredicateArgument(constraintTerm);
            equivalenceClass.equatePredicateArgument(queryTerm);
            equivalenceClasses.put(constraintTerm, equivalenceClass);
            equivalenceClasses.put(queryTerm, equivalenceClass);
        }
    }

    /**
     * @return the map of Term to the EquivalenceClass that contains it
     */
    public Map<Term, EquivalenceClass> getEquivalenceClasses() {
        return equivalenceClasses;
    }

    /**
     * @return the set of constraint Atoms that were unified to create this ECset
     */
    public Set<Atom> getUnifiedConstraintAtoms() {
        return unifiedConstraintAtoms;
    }

    /* Abuse iterator to get 'some random' (ie 'first' that the iterator has) element from the set. */
    private static EquivalenceClass getEquivalenceFromSet(Set<EquivalenceClass> set){
        for(EquivalenceClass equivalenceClass : set){
            return equivalenceClass;
        }
        return null;
    }

    /**
     * Union two ECsets, repeatedly merging all ECs that can be merged, resulting in a new set of disjoint ECs.
     * @param otherECset the ECset to unionECsets with
     * @return the merged set of disjoint ECs.
     * @throws ConstantClashException thrown when we cause an EquivalenceClass to contain two different Constants
     */
    public ECset unionECsets(ECset otherECset) throws ConstantClashException {
        // New set of ECs
        Set<EquivalenceClass> newEquivalences = new HashSet<>(this.equivalenceClasses.values());

        // For every EC in the other ECSet, try to union it with each EC in this set in turn.
        // Then, add this new (unioned) EC to this ECSet.
        for (EquivalenceClass otherEquivalenceClass : otherECset.equivalenceClasses.values()) {
            Iterator<EquivalenceClass> thisECSetIterator = newEquivalences.iterator();
            while (thisECSetIterator.hasNext()) {
                EquivalenceClass thisEquivalenceClass = thisECSetIterator.next();
                if (otherEquivalenceClass.containsCommonElement(thisEquivalenceClass) &&
                        !otherEquivalenceClass.equals(thisEquivalenceClass)) {
                    otherEquivalenceClass = otherEquivalenceClass.union(thisEquivalenceClass);

                    // Since this EC has been added to the other EC, remove it.
                    thisECSetIterator.remove();
                }
            }
            newEquivalences.add(otherEquivalenceClass);
        }

        //Create a set of all atoms to create our ECset with
        Set<Atom> atoms = new HashSet<>(unifiedConstraintAtoms);
        atoms.addAll(otherECset.unifiedConstraintAtoms);
        return new ECset(query, newEquivalences, atoms);
    }

    /**
     * @param term the Term that we want to check has an EC that contains it
     * @return <code>true</code> if an EC in this ECset contains the term, otherwise <code>false</code>
     */
    public boolean containsTerm(Term term) {
        return equivalenceClasses.containsKey(term);
    }

    /**
     * Get the minimal set of equalities for each EC in this ECset.
     * This was computed by merging and adding terms to ECs.
     * @return the minimal set of equalities for each EC in this ECset
     */
    public Set<Equality> getEqualities() {
        //We simplify the pseudocode by omitting the fact that an Equivalence Classes (EC)
        //'carries with it' some Equalities. Instead, we say that those are always computed transitively.
        //In reality, each EC computes a more minimal set
        Set<Equality> equalities = new HashSet<>();
        for (EquivalenceClass equivalenceClass : equivalenceClasses.values()) {
            equalities.addAll(equivalenceClass.getEqualities());
        }
        return equalities;
    }

    /**
     * Get the transitive closure of equalities for each EC in this ECset
     * @return the transitive closure of equalities for each EC in this ECset
     */
    public Set<Equality> getEqualitiesClosure() {
        Set<Equality> equalities = new HashSet<>();
        for (EquivalenceClass equivalenceClass : equivalenceClasses.values()) {
            equalities.addAll(equivalenceClass.getEquateClosure());
        }
        return equalities;
    }

    /**
     * @return the String representation of this ECset
     */
    @Override public String toString() {
        //This is probably just for debugging
        StringBuilder string = new StringBuilder();
        Set<EquivalenceClass> uniqueECs = new HashSet<>(equivalenceClasses.values());
        for(EquivalenceClass equivalenceClass : uniqueECs) {
            string.append(",").append(equivalenceClass.toString());
        }
        return string.toString().replaceFirst(",", "");
    }

}
