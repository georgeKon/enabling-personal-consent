package dbconsent;

import java.util.Objects;

/**
 * This class is a Term that represents a `Null` Constant in a Datalog Atom.
 *
 */
public class NullConstant extends Constant<Object> {

    public static Object nullObject = new Object();

    /**
     * NullConstant constructor, takes no arguments as all Null Constants are the same.
     */
    public NullConstant() {
        super(nullObject);
    }

    /**
     * All NullConstants are fundamentally the same thing
     * @param o the Object that we want to check is a NullConstant
     * @return <code>true</code> if the Object is a NullConstant, otherwise <code>false</code>
     */
    @Override public boolean equals(Object o) { return o instanceof NullConstant; }

    /**
     * @return the String representation of a Null Constant in Datalog notation
     */
    @Override public String toString() { return "Null"; }

    /**
     * All NullConstants are fundamentally the same thing, so should have the same hashcode
     * We want to differentiate from the String "Null" hashcode, so add some tildes.
     * @return the hash code of all Null Constants
     */
    @Override public int hashCode() {
        return Objects.hashCode("~~~Null");
    }

}
