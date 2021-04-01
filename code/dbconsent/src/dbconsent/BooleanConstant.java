package dbconsent;

/**
 * This class is a Term that represents a Boolean Constant in a Datalog Atom
 *
 * 
 */

public class BooleanConstant extends Constant<Boolean> {

    /**
     * @param bool (value) of constant
     */
    public BooleanConstant(Boolean bool) {
        super(bool);
    }

    /**
     * For convenience, we represent booleans in Datalog in the same format PostgreSQL expects
     * @return the String
     */
    @Override public String toString() {
        return this.value.booleanValue() ? "True" : "False";
    }

}
