package dbconsent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An Equality represents a statement that two distinct query terms must be equal for a constraint to apply.
 * For example, if you have Q(x,y) :- A(x,y) and N(a) :- A(a,a), then N is implicitly stating that x=y.
 * ie the rewriting for Q / N includes the Equality of (x,y)
 *
 */
class Equality {

    private Term term1;
    private Term term2;

    /**
     * Equality Constructor
     * @param term1 a term we want to equate to term2
     * @param term2 a term we want to equate to term1
     */
    public Equality(Term term1, Term term2) {
        if(term1 == null || term2 == null) {
            throw new NullPointerException();
        }

        setTerms(term1, term2);
    }

    public void setTerms(Term term1, Term term2) {
        //Always put the constant/lexicographically-smaller-variable (ie the preferred term (>r)) in term1
        if(term1 instanceof Constant ||
                term2 instanceof Variable && ((Variable) term1).getName().compareTo(((Variable) term2).getName()) < 0) {
            this.term1 = term1;
            this.term2 = term2;
        } else {
            this.term1 = term2;
            this.term2 = term1;
        }
    }

    public void setTerm1(Term term) {
        setTerms(term, this.term2);
    }

    public void setTerm2(Term term) {
        setTerms(term, this.term1);
    }

    /**
     * Test if either of the terms in the Equality are a given term
     * @param term the term that we want to test if it is in the Equality
     * @return <code>true</code> if the Equality contains the term, otherwise <code>false</code>
     */
    public boolean containsTerm(Term term) {
        return term1.equals(term) || term2.equals(term);
    }

    /**
     * An Equality is only equal when it has exactly the same two terms.
     * @param o the Object that we want to test if it is equal to this Equality
     * @return <code>true</code> if the Object is the same as this Equality, otherwise <code>false</code>
     */
    @Override public boolean equals(Object o){
        if(o instanceof Equality) {
            Equality o2 = (Equality)o;
            //Second part of the OR not necessary as term1 will always be the lexicographically smaller
            //thus we can just compare t1s with t1s and t2s with t2s
            return  (o2.term1.equals(this.term1) && o2.term2.equals(this.term2));
            // || (o2.term1.equals(this.term2) && o2.term2.equals(this.term1));
        }
        return false;
    }

    /**
     * @return the hashCode of this Equality
     */
    @Override public int hashCode() {
        return Objects.hash(term1, term2);
    }

    /**
     * @return the String representation of this Equality, in Datalog format.
     */
    @Override public String toString() {
        return toStringHelper(true);
    }

    /**
     * @return the String representation of the negation of this Equality, in Datalog format.
     */
    public String toStringDeMorgan() { return toStringHelper(false); }

    /* Just a helper method so that we don't have to write essentially the same thing in both methods */
    private String toStringHelper(boolean equality) {
        String operator = equality ? " = " : " ≠ ";
        return term1.toString() + operator + term2.toString();
    }

    /**
     * @param equalities the map of the Datalog Variable Names to the list of PostgreSQL column identifiers
     * @return the String representation of this Equality, using the query's column identifiers
     */
    public String toPostgreSQLString(Map<String, List<String>> equalities) {
        return toPostgreSQLStringHelper(equalities, true);
    }

    /**
     * @param equalities the map of the Datalog Variable Names to the list of PostgreSQL column identifiers
     * @return the String representation of the negation of this Equality, using the query's column identifiers
     */
    public String toPostgreSQLStringDeMorgan(Map<String, List<String>> equalities) {
        return toPostgreSQLStringHelper(equalities, false);
    }

    /* Just a helper method so that we don't have to write essentially the same thing in both methods */
    private String toPostgreSQLStringHelper(Map<String, List<String>> equalities, boolean equality) {
        String operator = equality ? " = " : " != ";
        //We want to use the SQL identifiers, not the Datalog variable names -
        if (term1 instanceof Constant) {
            return term1.toString() + operator + equalities.get(term2.toString()).get(0);
        } else {
            return equalities.get(term1.toString()).get(0) + operator + equalities.get(term2.toString()).get(0);
        }
    }

    /** The two terms in the equality - we put the preferred term (>r) in term1.
     * This makes equals() and hashCode() simpler */
    public Term getTerm1() {
        return term1;
    }

    public Term getTerm2() {
        return term2;
    }
}
