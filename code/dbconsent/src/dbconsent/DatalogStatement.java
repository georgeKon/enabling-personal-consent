package dbconsent;

import java.util.*;

/**
 * A DatalogStatement is an object used to represent some statement written in the Datalog Notation
 *
 * A Datalog Statement has a head, representing the projections, and a body, representing selections and joins.
 * The head has a predicate (ie the predicate of the statement), and then a set of Variables.
 * The body contains a list of atoms, each of which contain their own predicate, and a list of Terms
 *
 */
public class DatalogStatement {

	/** Predicate of the Datalog statement. This is the predicate of the head */
	private String predicate;

	/** List of head variables - order matters!
	 * In Datalog representation, order is arbitrary.
	 * But if we ever serialize to SQL, it does matter!
	 * Applications need a specific order, so when parsing SQL -> Rewrite -> Serialize SQL, should return same order.
	 */
	private List<Variable> headVariables;

	/** Atoms of the body */
	private List<Atom> atoms;
	/** Index to allow accessing atoms with a specific predicate to be faster
	 * Used only for Constraints. */
	private Map<String, List<Atom>> index;

	/** Set of rewritten constraints for when the query has been rewritten. Empty otherwise. */
	private Set<RewrittenConstraint> constraints = new HashSet<RewrittenConstraint>();

	/**
	 * DatalogStatement constructor
	 *
	 * @param predicate the predicate of the Datalog query
	 *
	 */
	public DatalogStatement(String predicate) {
		this.predicate = predicate;
		headVariables = new ArrayList<>();
		atoms = new ArrayList<>();
	}

	/**
	 * Copy Constructor
	 * @param query the query to copy
	 */
	public DatalogStatement(DatalogStatement query) {
		this.predicate = query.predicate;
		headVariables = new ArrayList<>();
		for (Variable headVariable : query.getHeadVariables()) {
			headVariables.add(new Variable(headVariable.getName()));
		}
		atoms = new ArrayList<>();
		for (Atom atom : query.getAtoms()) {
			atoms.add(new Atom(atom));
		}
	}

	/**
	 * Associate a set of Rewritten Constraints with this query.
	 * This is similar to adding the `/ rewrittenConstraints` to our query (ie. Query / rewrittenConstraints )
	 * @param rewrittenConstraints the set of constraints that , when rewritten to apply to this query
	 */
	public void applyConstraints(Set<RewrittenConstraint> rewrittenConstraints) {
		//TODO: Perhaps move stuff relating to the rewritings to a subclass.
		if (constraints == null) constraints = new HashSet<>();
		constraints.addAll(rewrittenConstraints);
	}

	/**
	 * @return the set of constraints that have been associated with this query
	 */
	public Set<RewrittenConstraint> getConstraints(){
		return this.constraints;
	}

	/**
	 * @return the predicate of this DatalogStatement
	 */
	public String getPredicate() {
		return this.predicate;
	}

	/**
	 * @param predicate the predicate to change to
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	/**
	 * Adds a head variable to DatalogStatement
	 * @param variable the variable to add to the head
	 */
	public void addHeadVariable(Variable variable) {
		headVariables.add(variable);
	}

	/**
	 * Returns the head variables of this DatalogStatement
	 * @return the list of head variables for this DatalogStatement
	 */
	public List<Variable> getHeadVariables() {
		return headVariables;
	}

	/**
	 * @param headVariables the list of Variable which should become the head of this DatalogStatement
	 */
	public void setHeadVariables(List<Variable> headVariables) {
		this.headVariables = headVariables;
	}

	/**
	 * @return the list of atoms in this DatalogStatement
	 */
	public List<Atom> getAtoms() {
		return atoms;
	}

	/**
	 * Index the Atoms in this DatalogStatement by their predicate.
	 * Useful to prevent unneccessary iterations.
	 */
	public void indexAtoms(){
		index = new HashMap<String,List<Atom>>();
		for(Atom atom : getAtoms()) {
			String key = atom.predicate;
			if (index.containsKey(key)) {
				index.get(key).add(atom);
			} else {
				List<Atom> atoms = new ArrayList<>();
				atoms.add(atom);
				index.put(key, atoms);
			}
		}
	}

	/**
	 * Test if the Atoms have been indexed by their predicate
	 * @return <code>true</code> if the Atoms in this DatalogStatement have been index, otherwise <code>false</code>
	 */
	public boolean hasBeenIndexed(){
		return !(index == null);
	}

	/**
	 * TODO check if exception should be thrown when input is not in this datalogstatement 
	 * @param predicate the predicate of the Atoms to retrieve
	 * @return the list of atoms with a given predicate.
	 */
	public List<Atom> getAtoms(String predicate){
		if(!hasBeenIndexed()) {
			indexAtoms();
		}
		return index.containsKey(predicate) ? index.get(predicate) : new ArrayList<>();
	}

	/**
	 * @return the index of predicate to list of atoms, if it exists, otherwise null
	 */
	public Map<String, List<Atom>> getIndex(){
		return index;
	}

	/**
	 * @param term head variable that is tested to be contained
	 * @return <code>true</code> if the term is in the head of this DatalogStatement, otherwise <code>false</code>
	 */
	public boolean containsHeadVariable(Term term) {
		return headVariables.contains(term);
	}

	/**
	 * @param term the term that we want to check is in some Atom
	 * @return <code>true</code> if some atoms contains the given term, otherwise <code>false</code>
	 */
	public boolean containsTerm(Term term) {
		for (Atom atom : atoms) {
			if (atom.getTerms().contains(term)) return true;
		}
		return false;
	}

	/**
	 * @param atom the Atom to be added to this DatalogStatement
	 */
	public void addAtom(Atom atom) {
		atoms.add(atom);
	}
	
	public void addAtoms(List<Atom> newAtoms) {
		atoms.addAll(newAtoms);
	}

	/**
	 * @return The number of Atoms in this DatalogStatement.
	 */
	public int numberOfAtoms() {
		return atoms.size();
	}

	/**
	 * @return The Variables used in the body of this DatalogStatement.
	 */
	public List<Variable> getVariables() {
		List<Variable> variables = new ArrayList<>();
		for (Atom atom : getAtoms()) {
			variables.addAll(atom.getVariables());
		}
		return variables;
	}

	/**
	 * @return The Variables used in the body of this DatalogStatement which do not appear in the head.
	 */
	public List<Variable> getExistentialVariables() {
		List<Variable> existentialVariables = new ArrayList<>();
		for (Variable variable : getVariables()) {
			if (!containsHeadVariable(variable)) {
				existentialVariables.add(variable);
			}
		}
		return existentialVariables;
	}

	/**
	 * @return the String representation of this DatalogStatement
	 */
	public String toString() {
		//Compute the head
		String head = predicate + "(";
		for (Variable variable : headVariables) {
			head += "," + variable.toString();
		}
		head = head.replaceFirst(",","") + ")";

		//Compute the body for this guy
		String body = getDatalogBodyString();

		//THIS PART ONLY FOR REWRITTEN QUERIES
		//If there are constraints, we want to 'subtract them' from the query
		if(constraints != null && constraints.size() > 0) {
			String deMorganEqualities = "";
			String rewrittenParts = " /";

			for (RewrittenConstraint rewrittenConstraint : constraints) {
				if (rewrittenConstraint.numberOfAtoms() == 0) {
					if (rewrittenConstraint.getEqualities().size() > 0) {
						String thisEqualities = "";
						for (Equality equality : rewrittenConstraint.getEqualities()) {
							thisEqualities += " | " + equality.toStringDeMorgan();
						}
						deMorganEqualities += ", (" + thisEqualities.replaceFirst(" \\|", "") + " )";
					} else {
						deMorganEqualities += ", ( 1 ≠ 1 )";
					}
				} else {
					//Subtract the union of each query
					rewrittenParts += " ∪ " + rewrittenConstraint.toString();
				}
			}
			rewrittenParts = rewrittenParts.equals(" /") ? "": rewrittenParts;
			body = body + deMorganEqualities + rewrittenParts.replaceFirst(" ∪", "");
		}
		//END PART ONLY FOR REWRITTEN QUERIES

		//Remove superfluous `/` if present
		return head + " :- " + body;
	}

	/**
	 * In some cases, we need the body of a DatalogStatement without the head.
	 * @return the body of this DatalogStatement
	 */
	protected String getDatalogBodyString() {
		String body = "";
		for (Atom atom : atoms) {
			body += "," + atom.toString();
		}
		return body.replaceFirst(",","");
	}

	/**
	 * @return The String representation of this DatalogStatement in PostgreSQL
	 */
	public String toPostgreSQLString() {
		return toPostgreSQLString(new HashMap<>(), new HashMap<>(), TPCHSchema.getInstance());
	}

	public String toPostgreSQLString(Schema schema) {
		return toPostgreSQLString(new HashMap<>(), new HashMap<>(), schema);
	}
	
	/* Helper function -
	 * This method constructed quite badly.
	 * To serialize a rewritten constraint (ie part of the right hand side of a rewritten query),
	 * we need to know both how many atoms exist with a given name
	 * (so that if we have already Customer c0, we now make Customer c1, etc),
	 * but also the identifiers that the query part's (ie everything left of / in `Query / RewrittenConstraint`)
	 * variables converted to. (So that we can link to identifiers of the x,y,z in the left hand side
	 * to the identifiers of x,y,z in the right hand side)
	 * */
	protected String toPostgreSQLString(Map<String, Integer> predicateCounts, Map<String, List<String>> parentEqualities, Schema schema) {

		//Set up the FROM and WHERE clauses
		String fromBody = "FROM ";
		//Map of Datalog Variable predicate to List of SQL Identifiers that they convert to
		Map<String, List<String>> datalogToSQLMap = new HashMap<>();
		//Map of Datalog Constant value to List of SQL Identifier that equal that value
		Map<String, List<String>> datalogConstantToSQLMap = new HashMap<>();
		for (int i = 0; i < atoms.size(); i++) {
			Atom atom = atoms.get(i);
			String tableName = atom.predicate;
			int countOfThisPredicate = predicateCounts.getOrDefault(tableName, 0);
			String alias = tableName + countOfThisPredicate;
			predicateCounts.put(tableName, countOfThisPredicate + 1);

			//Construct the FROM, adding a comma if necessary
			fromBody += tableName + " " + alias;
			fromBody += i < atoms.size() - 1 ? ", " : " \n";

			//Create a map from datalog varname to SQL schema
			for (int j = 0; j < atom.getTerms().size(); j++) {
				Term predicateArgument = atom.getTerms().get(j);
				String argString = predicateArgument.toString();
				List<String> columns = schema.getColumnsForTable(atom.predicate);
				//Might be trying to print some table not in the schema
				if (columns == null) {
					return "TABLENAME '" + atom.predicate + "' DOES NOT EXIST";
				}
				String columnName = columns.get(j);
				String identifier = alias + "." + columnName;

				if(predicateArgument instanceof Variable) {
					//Create a list of tpchschema identifiers which should all equal each other
					if (datalogToSQLMap.containsKey(argString)) {
						List<String> list = datalogToSQLMap.get(argString);
						list.add(identifier);
					} else {
						List<String> list = new ArrayList<>();
						list.add(identifier);
						datalogToSQLMap.put(argString, list);
					}
				} else {
					//Create a list of tpchschema identifiers which should all equal key of constantEqualities
					if (datalogConstantToSQLMap.containsKey(argString)) {
						List<String> list = datalogConstantToSQLMap.get(argString);
						list.add(identifier);
					} else {
						List<String> list = new ArrayList<>();
						list.add(identifier);
						datalogConstantToSQLMap.put(argString, list);
					}
				}
			}
		}

		String selectBody = "SELECT ";
		int i = 0;
		if (headVariables.size() > 0) {
			for (Variable headVariable : headVariables) {
				i++;
				String identifier = datalogToSQLMap.get(headVariable.getName()).get(0);
				// Construct the FROM, adding a comma if necessary
				selectBody += identifier;
				selectBody += i < headVariables.size() ? ", " : " \n";
			}
		} else {
			selectBody += "*\n";
		}

		String whereBody = "";
		// Need to equate all identifiers representing the same variables
		for (String variable : datalogToSQLMap.keySet()) {
			List<String> identifiers = datalogToSQLMap.get(variable);
			// We have a list of identifiers which should all be equated within the current datalog query
			// We could do first = second, second = third, etc,
			// or we could do first = second, first = third, etc
			// This method uses the latter
			String primaryLinker = identifiers.get(0);
			for (int j = 1; j < identifiers.size(); j++) {
				String secondaryLinker = identifiers.get(j);
				whereBody += "AND " + primaryLinker + " = " + secondaryLinker + "\n";
			}
			//We also need to connect this datalogQuery to the parent datalogQuery (if there is one)
			//Connecting the first of each identifier from this query with the first identifier from the parent is enough because
			//of the transitivity of the equalities
			List<String> parentIdentifiers = parentEqualities.get(variable);
			if(parentIdentifiers != null) {
				String parentPrimaryLinker = parentIdentifiers.get(0);
				whereBody += "AND " + primaryLinker + " = " + parentPrimaryLinker + "\n";
			}
		}
		//Add all the identifier = constant conditions
		for (String constant : datalogConstantToSQLMap.keySet()) {
			List<String> identifiers = datalogConstantToSQLMap.get(constant);
			for (int j = 0; j < identifiers.size(); j++) {
				whereBody += "AND " + identifiers.get(j) + " = " + constant + "\n";
			}
		}
		//Add the DeMorgan Equality for constraints with no atoms remaining
		if (constraints != null) {
			for (RewrittenConstraint rewrittenConstraint : constraints) {
				if(rewrittenConstraint.numberOfAtoms() == 0) {
					if (rewrittenConstraint.getEqualities().size() > 0) {
						whereBody += "AND (\n";
						String deMorganEquate = "";
						for (Equality equality : rewrittenConstraint.getEqualities()) {
							deMorganEquate += "OR " + equality.toPostgreSQLStringDeMorgan(datalogToSQLMap) + "\n";
						}
						whereBody += deMorganEquate.replaceFirst("OR ","") + ")";
					} else {
						//TODO: What if no other WHERE?
						whereBody += "AND 1 = 0\n";
					}
				}
			}
		

			//Add the equates if we're a rewrittenConstraint and have some atoms left
			if(this instanceof RewrittenConstraint & this.numberOfAtoms() > 0) {
				RewrittenConstraint thisConstraint = (RewrittenConstraint)this;
				for (Equality equality : thisConstraint.getEqualities()) {
					whereBody += "AND " + equality.toPostgreSQLString(parentEqualities) + "\n";
				}
			}
	
			for (RewrittenConstraint constraint : constraints) {
				if (constraint.numberOfAtoms() > 0) {
					whereBody += "AND NOT EXISTS(" + constraint.toPostgreSQLString(predicateCounts, datalogToSQLMap, schema) + ")\n";
				}
			}
		}
		if (whereBody.length() != 0) {
			whereBody = "WHERE " + whereBody.replaceFirst("AND ", "");
		}
		return selectBody + fromBody + whereBody;
	}

}
