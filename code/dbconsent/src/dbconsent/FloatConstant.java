package dbconsent;

/**
 * This class is a Term that represents a Floating Point Constant in a Datalog Atom
 *
 */

public class FloatConstant extends Constant<Double> {

	/**
	 * @param value the value of Constant
	 */
    public FloatConstant(Double value) {
    	super(value);
    }


    @Override
	public String toString() {
    	return this.value.toString();
	}

}
