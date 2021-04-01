package dbconsent;

import java.util.*;

/**
 * A RewrittenConstraint is a DatalogStatement that has had the atoms we unified on removed,
 * and has a set of Equalities associated with it.
 *
 */
public class RewrittenConstraint extends DatalogStatement {

    /** The set of equalities (of query variables and constants) produced by the unification
     * that made this RewrittenConstraint */
    private Set<Equality> equalities;

    /**
     * @param predicate the predicate of this RewrittenConstraint
     */
    public RewrittenConstraint(String predicate) {
        super(predicate);
    }

    /**
     * @param equalities the equalities we want to associate with this RewrittenConstraint
     */
    public void addEquates(Set<Equality> equalities) {
        this.equalities = equalities;
    }

    /**
     * @return the set of Equalities associated with this RewrittenConstraint.
     */
    public Set<Equality> getEqualities() {
        return equalities;
    }

    /**
     * @return The Variables used in the body of this RewrittenConstraint.
     */
    public List<Variable> getVariables() {
        // Get Variables in atoms.
        List<Variable> variables = super.getVariables();

        // Get Variables in Equalities.
        for (Equality equality : getEqualities()) {
            if (equality.getTerm1() instanceof Variable) {
                variables.add((Variable) equality.getTerm1());
            }
            if (equality.getTerm2() instanceof Variable) {
                variables.add((Variable) equality.getTerm2());
            }
        }

        return variables;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RewrittenConstraint) {
            RewrittenConstraint o = (RewrittenConstraint) obj;

            return new HashSet<>(this.getAtoms()).equals(new HashSet<>(o.getAtoms())) &&
                    this.equalities.equals(o.getEqualities());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(equalities, new HashSet<>(this.getAtoms()));
    }

    /**
     * @return the String representation of this RewrittenConstraint in Datalog notation
     */
    @Override public String toString() {
        String string = "";
        // If this (right hand side part of
        if(numberOfAtoms() != 0) {
            string += super.getDatalogBodyString();
            for (Equality equality : equalities) {
                string += ", " + equality.toString();
            }
        }
        return string;
    }

}
