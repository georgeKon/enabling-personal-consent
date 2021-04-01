package dbconsent;

import java.util.*;

import dbconsent.DatalogParser.ParseDatalog;

/**
 * This class deals with the propagation of consent (ie. when queries use the heads of previous queries in their body).
 */

public class Propagation {

	public static void main(String[] args) {
		ParseDatalog parseDatalog = new ParseDatalog();
        DatalogStatement q1 = parseDatalog.safeParseString("Q1(x) :- P(x,y,z)");
        DatalogStatement q2 = parseDatalog.safeParseString("Q2(f) :- Q1(f),A(f)");

		DatalogStatement n = parseDatalog.safeParseString("N(a) :- P(a,b,c),A(a)");

		try {
			DatalogStatement unfolded = minimalUnfoldAndRewriteQuery(q2, new HashSet<>(Collections.singletonList(q1)), new HashSet<>(Collections.singletonList(n)));
	        System.out.println(unfolded.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Applies constraints to a query using previous queries. Then, only unfolds those previous queries used
	 * in the new query which need to be (if a constraint refers to an existential variable in one of the previous queries).
	 * @param query The query to be rewritten and unfolded
	 * @param previousQueries All possible previous queries that could be used in the body of 'query'
	 * @param constraints The set of constraints that need to be satisfied.
	 * @return The rewritten query with the smallest possible number of previous queries unfolded.
	 * @throws InvalidQueryUnfoldingException If an unfolding is not possible.
	 */
	public static DatalogStatement minimalUnfoldAndRewriteQuery(DatalogStatement query, Set<DatalogStatement> previousQueries, Set<DatalogStatement> constraints) throws InvalidQueryUnfoldingException {

		// Find the previous queries used in the body of 'query'.
		Set<DatalogStatement> usedPreviousQueries = getUsedPreviousQueries(query, previousQueries);
		// Unfold query and get any new rewritings produced by the unfolding.
		Set<RewrittenConstraint> rewrittenConstraints = RewritingAlgorithm.getRewrittenConstraints(unfoldQuery(query, usedPreviousQueries), constraints);

		// Find any previous queries that need to be unfolded (ie. any previous queries with existential variables
		// that are in any of the new rewrittenConstraints.
		Set<DatalogStatement> previousQueriesToUnfold = new HashSet<>();
		queriesLoop: for (DatalogStatement previousQuery : usedPreviousQueries) {
			for (RewrittenConstraint rewrittenConstraint : rewrittenConstraints) {
				// If any of this rewrittenConstraint's variables are existential in this previousQuery, we need to unfold this previousQuery.
				if (!Collections.disjoint(rewrittenConstraint.getVariables(), previousQuery.getExistentialVariables())) {
					previousQueriesToUnfold.add(previousQuery);
					// Don't need to try any of the other constraints for this query.
					continue queriesLoop;
				}
			}
		}

		// Unfold only those previousQueries found to be necessary.
		DatalogStatement result = unfoldQuery(query, previousQueriesToUnfold);
		// Apply the new constraints.
		result.applyConstraints(rewrittenConstraints);
		return result;
	}

	/**
	 * Unfold the query, replacing all previous queries in its body with their respective bodies (with variables remapped).
	 * eg. For the query: "Q3(x,y) :- Q1(x,z),Q2(z,y),D(y,h)"
	 * with previous queries: "Q1(a,b) :- A(a,b),B(b,c)", "Q2(m,n) :- C(m,n,o)"
	 * the result will be: "Q3(x,y) :- A(x,z),B(z,c),C(z,y,o),D(y,h)"
	 * @param query The query to unfold.
	 * @param previousQueries All previous queries that could be used in the body of 'query'.
	 * @return The query with all previous queries in its body unfolded and replaced.
	 * @throws InvalidQueryUnfoldingException If the unfolding is not possible.
	 */
	public static DatalogStatement unfoldQuery(DatalogStatement query, Set<DatalogStatement> previousQueries) throws InvalidQueryUnfoldingException {
		List<Atom> newQueryBody = new ArrayList<>();
		Set<RewrittenConstraint> newConstraints = new HashSet<>();

		// Hash previous query predicates. If two previous queries have the same predicate, only keep one (arbitrarily chosen).
		Map<String, DatalogStatement> previousQueryMap = new HashMap<>();
		for (DatalogStatement previousQuery : previousQueries) {
			previousQueryMap.putIfAbsent(previousQuery.getPredicate(), previousQuery);
		}

		// Check if any Atom has the same predicate as a previous query. 
		// If so, replace it with the previous query body.
		for(Atom atom : query.getAtoms()) {
			if(previousQueryMap.containsKey(atom.predicate)) {
				DatalogStatement expandedAtom = expandQueryAtom(atom, previousQueryMap.get(atom.predicate));
				newQueryBody.addAll(expandedAtom.getAtoms());
				newConstraints.addAll(expandedAtom.getConstraints());
			} else {
				newQueryBody.add(atom);
			}
		}

		// Create the new unfoldedQuery.
		DatalogStatement unfoldedQuery = new DatalogStatement(query.getPredicate());
		for(Variable headVariable : query.getHeadVariables()) {
			unfoldedQuery.addHeadVariable(headVariable);
		}
		for(Atom atom : newQueryBody) {
			unfoldedQuery.addAtom(atom);
		}
		unfoldedQuery.applyConstraints(newConstraints);
		
		return unfoldedQuery;
	}

	/**
	 * Find the set of previous queries referred to by atoms in this query.
	 * eg. For the query "Q3(x,y) :- Q1(x,z),Q2(z,y),B(a,b)" the result will be {Q1, Q2}
	 * @param query The query to analyse.
	 * @param previousQueries All previous queries possible.
	 * @return The set of previous queries used in the body of this query.
	 */
	public static Set<DatalogStatement> getUsedPreviousQueries(DatalogStatement query, Set<DatalogStatement> previousQueries) {
		Set<DatalogStatement> usedPreviousQueries = new HashSet<>();

		// Hash previous query predicates. If two previous queries have the same predicate, only keep one (arbitrarily chosen).
		Map<String, DatalogStatement> previousQueryMap = new HashMap<>();
		for (DatalogStatement previousQuery : previousQueries) {
			previousQueryMap.putIfAbsent(previousQuery.getPredicate(), previousQuery);
		}

		// Check if any Atom has the same predicate as a previous query.
		// If so, replace it with the previous query body.
		for(Atom atom : query.getAtoms()) {
			if(previousQueryMap.containsKey(atom.predicate)) {
				usedPreviousQueries.add(previousQueryMap.get(atom.predicate));
			}
		}

		return usedPreviousQueries;
	}

	/**
	 * Renames the variables in the query to match those in the atomToReplace.
	 * eg. With previous query "Q1(x,y) :- A(x,y),B(y,z)" and the atom "Q1(a,b)"
	 * the result is "Q1(a,b) :- A(a,b),B(b,z)"
	 * @param atomToReplace The previous query head atom to be expanded.
	 * @param query The query whose body will replace the atom.
	 * @return The query with its variables renamed to match those of the atomToReplace.
	 * @throws InvalidQueryUnfoldingException If a remapping is not possible.
	 */
	private static DatalogStatement expandQueryAtom(Atom atomToReplace, DatalogStatement query) throws InvalidQueryUnfoldingException {
		List<Variable> queryHeadVars = query.getHeadVariables();
		List<Term> atomVars = atomToReplace.getTerms();
		if(atomToReplace.numberOfTerms() != queryHeadVars.size()) {
			throw new InvalidQueryUnfoldingException("Incorrect number of terms in atom: " + atomToReplace.predicate);
		}

		// Create a mapping from the original query's head variables to the atom's variables
		Map<Variable, Term> queryMapping = new HashMap<>();
		for(int i = 0; i < atomVars.size(); i++) {

			// If we are trying to map the same variable in the previous query to two different terms, throw an exception.
			// Eg. with query = "Q(x,x) :- A(x,x)" and atomToReplace = "Q(a,b)", it is not possible to produce a sensible mapping.
			if(queryMapping.containsKey(queryHeadVars.get(i)) && !queryMapping.get(queryHeadVars.get(i)).equals(atomVars.get(i))) {
				throw new InvalidQueryUnfoldingException("Atom '" + atomToReplace.predicate + "' terms are too general");
			}
			
			queryMapping.put(queryHeadVars.get(i), atomVars.get(i));
		}

		// Apply the mapping to the query body.
		List<Atom> remappedQueryBody = new ArrayList<>();
		for(Atom queryAtom : query.getAtoms()) {
			Atom newAtom = new Atom(queryAtom.predicate, query.getPredicate());
			newAtom.addAllTerms(queryAtom.getTerms());
			remappedQueryBody.add(applyMapping(newAtom, queryMapping));
		}

		// Apply to mapping to any RewrittenConstraints.
		Set<RewrittenConstraint> remappedConstraints = new HashSet<>();
		if(query.getConstraints().size() > 0) {
			for(RewrittenConstraint constraint : query.getConstraints()) {
				remappedConstraints.add(applyMapping(constraint, queryMapping));
			}	
		}
		
		DatalogStatement remappedQuery = new DatalogStatement("Q'");
		remappedQuery.addAtoms(remappedQueryBody);
		remappedQuery.applyConstraints(remappedConstraints);
		
		return remappedQuery;
	}

	/**
	 * Remap variables of an atom. This is in-place (does not create a new Atom).
	 * @param atom The atom to be remapped.
	 * @param mapping The mapping to be applied.
	 * @return The atom with the mapping applied.
	 */
	private static Atom applyMapping(Atom atom, Map<Variable, Term> mapping) {
		List<Term> newTerms = new ArrayList<>();
		for(Term term : atom.getTerms()) {
			if(term instanceof Variable) {
				newTerms.add(mapping.getOrDefault(term, term));
			} else {
				newTerms.add(term);
			}
		}
		atom.removeTerms();
		atom.addAllTerms(newTerms);
		return atom;
	}

	/**
	 * Remap variables of a RewrittenConstraint. This is in-place (does not create a new RewrittenConstraint).
	 * @param constraint The RewrittenConstraint to be remapped.
	 * @param mapping The mapping to be applied.
	 * @return The constraint with the mapping applied.
	 */
	private static RewrittenConstraint applyMapping(RewrittenConstraint constraint, Map<Variable, Term> mapping) {
		RewrittenConstraint newConstraint = new RewrittenConstraint("N");
		for(Atom atom : constraint.getAtoms()) {
			newConstraint.addAtom(applyMapping(atom, mapping));
		}
		Set<Equality> newEqualities = new HashSet<>();
		for(Equality equality : constraint.getEqualities()) {
			newEqualities.add(applyMapping(equality, mapping));
		}
		newConstraint.addEquates(newEqualities);
		return newConstraint;
	}

	/**
	 * Remap variables of an Equality. This is not in-place (does create a new Equality and Terms).
	 * @param equality The Equality to be remapped.
	 * @param mapping The mapping to be applied.
	 * @return The equality with the mapping applied.
	 */
	private static Equality applyMapping(Equality equality, Map<Variable, Term> mapping) {
		Term newTerm1 = equality.getTerm1() instanceof Variable ?
				mapping.getOrDefault(equality.getTerm1(), equality.getTerm1()) : equality.getTerm1();
		Term newTerm2 = equality.getTerm2() instanceof Variable ?
				mapping.getOrDefault(equality.getTerm2(), equality.getTerm2()) : equality.getTerm2();

		return new Equality(newTerm1, newTerm2);
	}
}
