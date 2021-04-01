package dbconsent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An Atom is an object to represent some relation in the Datalog Notation
 * An Atom is a list of terms labelled with a predicate.
 * The Predicate represents which relation this atom is across. The terms represent the attributes of that relation.
 * in Datalog, this takes the form Predicate(Term, Term, ...)
 * An example atom may be A(x,y)
 *
 */
public class Atom {

	/** Predicate of the atom (ie relation name) */
	public String predicate;

	/** Terms (Variables and Constants) in the body of the atom */
	private List<Term> terms = new ArrayList<>();

	/** Name of the query this atom has been unfolded from if used in Propagation, empty otherwise **/
	private String unfoldedQuery;
	
	/**
	 * A constructor that requires terms to be added later
	 * @param predicate the predicate of this atom
	 */
	public Atom(String predicate) {
		this.predicate = predicate;
		unfoldedQuery = null;
	}
	
	/**
	 * A constructor that sets the unfolded query head and requires terms to be added later
	 * @param predicate the predicate of this atom
	 * @param queryHead the unfolded query head
	 */
	public Atom(String predicate, String queryHead) {
		this.predicate = predicate;
		this.unfoldedQuery = queryHead;
	}

	/**
	 * A constructor where terms are added at instantiation
	 * @param predicate the predicate of this atom
	 * @param terms the terms of this atom
	 */
	public Atom(String predicate, List<Term> terms) {
		//Note we have to take a list of terms here, not a collection, as terms must be ordered
		this(predicate);
		this.terms.addAll(terms);
	}

	/**
	 * A Copy Constructor, creates a duplicate of the Atom parameter
	 * @param atom the atom to duplicate
	 */
	public Atom(Atom atom) {
		this.predicate = atom.predicate;
		this.unfoldedQuery = atom.unfoldedQuery;
		for(Term predicateArgument : atom.getTerms()) {
			if (predicateArgument instanceof Variable) {
				this.addTerm(new Variable(((Variable) predicateArgument).getName()));
			} else {
				this.addTerm(predicateArgument);
			}
		}
	}
	
	/**
	 * Add all terms from a list of terms to this atom
	 *
	 * @param terms the terms to add to this atom
	 */
	public void addAllTerms(List<Term> terms) {
		this.terms.addAll(terms);
	}

	/**
	 * Returns the list of all terms of the atom
	 *
	 * @return list of terms
	 */
	public List<Term> getTerms() {
		return terms;
	}

	/**
	 * Returns the list of all variables of the atom
	 *
	 * @return list of variables
	 */
	public List<Variable> getVariables() {
		List<Variable> variables = new ArrayList<>();
		for (Term term : terms) {
			if (term instanceof Variable) {
				variables.add((Variable) term);
			}
		}
		return variables;
	}

	/**
	 * Add a term to the atom.
	 *
	 * @param term the term to add to this atom
	 */
	public void addTerm(Term term) { terms.add(term); }

	/**
	 * @param index the index of the term to replace
	 * @param term the term to replace with
	 */
	public void replaceTerm(int index, Term term) { terms.set(index, term); }

	/**
	 * Returns the term at a given index
	 * 
	 * @param index the index of the term to return
	 * @return the term at the given index
	 */
	public Term getTerm(int index) {
		return terms.get(index);
	}

	/**
	 * Returns <code>true</code> if given term is contained in the atom.
	 * 
	 * @param term the term that we want ask if is in atom
	 * @return <code>true</code> if term is contained in the atom, otherwise false
	 */
	public boolean contains(Term term) {
		return terms.contains(term);
	}

	/**
	 * Returns the total number of terms in the atom
	 * 
	 * @return count of terms
	 */
	public int numberOfTerms() {
		return terms.size();
	}
	
	public void removeTerms() {
		this.terms = new ArrayList<>();
	}

	public String getUnfoldedQuery() {
		return unfoldedQuery;
	}
	
	/**
	 * Return <code>true</code> if Object is an Atoms with the same list of terms as this Atom
	 * @param o the Object to compare against
	 * @return <code>true</code> if object is equal to this Atom, otherwise <code>false</code>
	 */
	public boolean equals(Object o) {
		if(o instanceof Atom) {
			Atom other = (Atom) o;
			//If two atoms have different predicates, they're not equal
			if (!other.predicate.equals(predicate)) return false;
			//If two atoms have different lengths, they're not equal
			if (other.numberOfTerms() != numberOfTerms()) return false;
			//If each term at the same index doesn't equal, the atoms are not equal
			for (int i = 0; i < numberOfTerms(); i++) {
				if (!other.getTerm(i).equals(getTerm(i))) return false;
			}
			//We're equal
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the String representation of the atom in the Datalog notation
	 */
	@Override public String toString() {
		String str = predicate + "(";
		for (Term term : terms) {
			str += "," + term.toString();
		}
		// Remove leading comma and append missing rparen
		return str.replaceFirst(",", "") + ")";
	}

}
