package dbconsent;

import java.util.Objects;

/**
 * This class is a Term and represents a constant of a Datalog Atom
 * This class should be extended to represent different types.
 *
 */

public abstract class Constant<T> extends Term {


	/** The value that this constant takes */
	protected T value;

	/**
	 * @param value the value the of constant
	 */
	public Constant(T value) {
		this.value = value;
	}

	/**
	 * All Constants with the same value are the same
	 * @param o the Object that we want to check is equal
	 * @return <code>true</code> if the Object has the same value, otherwise <code>false</code>
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Constant){
			Constant other = (Constant)o;
			return this.value.equals(other.value);
		} else {
			return false;
		}
	}

	/**
	 * Default implementation so that most subclasses don't have to repeat it
	 * @return the String representation of this constant's value
	 */
	@Override public String toString() { return this.value.toString(); }

	/**
	 * All Constants that have the same value should have the same hash code - ie the hash code is the hashcode of the value
	 * @return the hash code of the Constant
	 */
	@Override public int hashCode() {
		return Objects.hashCode(value);
	}

}
