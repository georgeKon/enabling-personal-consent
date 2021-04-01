package dbconsent;

import java.util.*;

/**
 * An Equivalence Class represents a set of Datalog terms that have all been made equal to each other during unification
 * An Equivalence Class has a Representative - that is the most preferable (>r) term.
 * Constant >r Query Distinguished Variable >r Query Existential Variable >r Constraint Variable >r Null
 * If two terms are in the same category above, we prefer the lexicographically smallest.
 * (Lexicographically smallest is arbitrary - we just need a set preference order -
 * In the paper we say lexicographically largest as it's easier to explain)
 *
 * You can see an EquivalenceClass as a set of substitutions - each term in the equivalence class gets substituted
 * with the Representative!
 *
 */
class EquivalenceClass implements Iterable<Term> {

    /**
     * The query we want to compare against, useful for determining the representative for this equivalence class
     */
    private DatalogStatement query;
    private Set<Term> equivalences = new HashSet<>();
    private Term representative;


    private Set<Equality> equalities = new HashSet<>();

    public EquivalenceClass(DatalogStatement query){
        this.query = query;
    }

    /**
     * Copy constructor
     */
    public EquivalenceClass(EquivalenceClass q1) {
        this.query = q1.query;
        for(Equality e : q1.equalities) {
            this.equalities.add(e);
        }
        for(Term predicateArgument : q1.equivalences) {
            this.equivalences.add(predicateArgument);
        }
        this.representative = q1.representative;
    }

    public Set<Equality> getEquateClosure() {
        Set<Term> constantOrQueryTerms = new HashSet<>();
        for (Term predicateArgument : equivalences) {
            if (predicateArgument instanceof Constant || query.containsTerm(predicateArgument)) {
                constantOrQueryTerms.add(predicateArgument);
            }
        }
        Set<Equality> equalities = new HashSet<>();
        for (Term predicateArgument : constantOrQueryTerms) {
            for (Term predicateArgument2 : constantOrQueryTerms) {
                if (!predicateArgument.equals(predicateArgument2)) {
                    equalities.add(new Equality(predicateArgument, predicateArgument2));
                }
            }
        }
        return equalities;
    }

    public Term getRepresentative() {
        return representative;
    }

    public int size() {
        return equivalences.size();
    }

    public boolean containsElement(Term predicateArgument) {
        return equivalences.contains(predicateArgument);
    }

    public boolean containsCommonElement(EquivalenceClass equivalenceClass) {
        return !Collections.disjoint(this.equivalences, equivalenceClass.equivalences);
    }

    @Override
    public Iterator<Term> iterator() {
        return equivalences.iterator();
    }

    private boolean shouldReplaceRepresentative(Term predicateArgument) {
    	//Do not replace if representative is already a constant
        return !(representative instanceof Constant) &&
        //If representative is null (ie equivalence set is empty), pick this new guy
        (representative == null ||
        //If this guy is a Constant, he should always be the representative
        //(but we check that there is no constant in the equivalences before calling this method)
        predicateArgument instanceof Constant ||
        //If the predicateArgument is distinguished but the representative isn't, replace it too
        (query.containsHeadVariable(predicateArgument) && !query.containsHeadVariable(representative)) ||
        //If both are distinguished terms in the query, and the predicateArgument is lexicographically smaller, replace
        (query.containsHeadVariable(predicateArgument) && query.containsHeadVariable(representative) &&
                ((Variable)predicateArgument).getName().compareTo(((Variable)representative).getName()) < 0) ||
        //If the predicateArgument is in the query, but the representative isn't, replace it
        (query.containsTerm(predicateArgument) && !query.containsTerm(representative)) ||
        //If both are in the query, and the predicate argument is lexicographically smaller
        (query.containsTerm(predicateArgument) && query.containsTerm(representative) &&
                ((Variable)predicateArgument).getName().compareTo(((Variable)representative).getName()) < 0));
    }

    /**
     */
    public void equatePredicateArgument(Term predicateArgument) throws ConstantClashException {
        //It isn't allowed to equate two different constants
        if (predicateArgument instanceof Constant && representative instanceof Constant
                && !representative.equals(predicateArgument)) {
            throw new ConstantClashException();
        }

        //Save ourselves some time by failing early if it already exists, plus stops us creating duplicate equalities
        if(!equivalences.contains(predicateArgument)) {
            //We're allowed to add this Term
            equivalences.add(predicateArgument);

            //If both representative and predicateArgument are in query or are constant, and they aren't the same thing,
            //add an Equality
            if ((representative instanceof Constant || query.containsTerm(representative)) &&
                    (predicateArgument instanceof Constant || query.containsTerm(predicateArgument)) &&
                    !predicateArgument.equals(representative)) {
                equalities.add(new Equality(representative, predicateArgument));
            }

            //See if this new Term should become the new representative
            if (shouldReplaceRepresentative(predicateArgument)) {
                representative = predicateArgument;
            }
        }
    }

    public static EquivalenceClass union(EquivalenceClass q1, EquivalenceClass q2) throws ConstantClashException {
        //We want to add the smaller equivalence to the larger.
        EquivalenceClass larger;
        EquivalenceClass smaller;
        if(q1.size() >= q2.size()) {
            larger = new EquivalenceClass(q1);
            smaller = q2;
        } else {
            larger = new EquivalenceClass(q2);
            smaller = q1;
        }
        return inPlaceUnion(larger, smaller);
    }


    /**
     * Perform a not-in-place unionECsets on two equivalences, adding to the largest.
     */
    public static EquivalenceClass inPlaceUnion(EquivalenceClass q1, EquivalenceClass q2) throws ConstantClashException {
        //We want to add the smaller equivalence to the larger.
        EquivalenceClass larger;
        EquivalenceClass smaller;
        if(q1.size() >= q2.size()) {
            larger = q1;
            smaller = q2;
        } else {
            larger = q2;
            smaller = q1;
        }

        // When we're unioning two Equivalences, we don't need to check that they aren't constants for each element,
        // we just need to check that the representatives aren't constants (there can only be one constant in
        // the equivalence and if there is it must be the representative).
        if (larger.representative instanceof Constant && smaller.representative instanceof Constant
                && !larger.representative.equals(smaller.representative)) {
            throw new ConstantClashException();
        }

        //If both representatives are distinguished, add a new equate
        DatalogStatement query = larger.query;
        if ((larger.representative instanceof Constant || query.containsTerm(larger.representative)) &&
                (smaller.representative instanceof Constant || query.containsTerm(smaller.representative)) &&
                !larger.representative.equals(smaller.representative)) {
            larger.equalities.add(new Equality(larger.representative, smaller.representative));
        }

        //See if this new Term should become the new representative
        if (larger.shouldReplaceRepresentative(smaller.representative)) {
            larger.representative = smaller.representative;
        }

        //No constant clash, so add each of smaller to larger, changing representative, etc
        larger.equivalences.addAll(smaller.equivalences);

        //Now we want to add each of the equalities
        larger.equalities.addAll(smaller.equalities);
        return larger;
    }

    /**
     * Performs out of place unionECsets of this and another equivalence
     * @return
     */
    public EquivalenceClass union(EquivalenceClass q1) throws ConstantClashException {
        return union(this, q1);
    }

    public Set<Equality> getEqualities() {
        return equalities;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("(");
        string.append(representative).append(":");
        for(Term predicateArgument : equivalences){
            string.append(",").append(predicateArgument.toString());
        }
        string.append(")");
        return string.toString().replaceFirst(",", "");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EquivalenceClass) {
            EquivalenceClass oEquivalenceClass = (EquivalenceClass)o;
            return oEquivalenceClass.equivalences.equals(this.equivalences);
        }
        return false;
    }
}
