package dbconsent;

/**
 * This class is a Term that represents a String Constant in a Datalog Atom.
 *
 */

public class StringConstant extends Constant<String> {

	/**
	 * @param value the value of the Constant
	 */
    public StringConstant(String value) {
    	super(value);
    }

}
