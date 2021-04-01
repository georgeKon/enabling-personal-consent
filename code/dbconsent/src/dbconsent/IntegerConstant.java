package dbconsent;

import java.util.Objects;

/**
 * This class is a Term that represents a Integer Constant in a Datalog Atom.
 *
 */

public class IntegerConstant extends Constant<Integer> {

	/**
	 * @param value Integer value of the Constant
	 */
    public IntegerConstant(Integer value) {
    	super(value);
    }

}
