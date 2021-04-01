package dbconsent;

/**
 * This class is a Term representing a Datalog Variable
 *
 */
public class Variable extends Term {

	/** name of variable (eg 'x') */
	private String name;

	/**
	 * @param name of variable
	 */
	public Variable(String name) {
		this.name = name;
	}

	/**
	 * @return the name of this Variable
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the string representation of this Variable, in Datalog format. ie - the name of the Variable
	 */
	@Override public String toString() {
		return this.name;
	}

	/**
	 * Return <code>true</code> if Object is a Variable with the same name
	 * @param o the Object to compare against
	 * @return <code>true</code> if object is equal to this Variable, otherwise <code>false</code>
	 */
	@Override public boolean equals(Object o) {
		if (o instanceof Variable){
			Variable other = (Variable)o;
			//Variables only equal if they have the same name
			return this.name.equals(other.name);
		} else {
			return false;
		}
	}

	/**
	 * Hash Codes are used to store equal Java objects in the same 'bucket';
	 * For our purposes, two Variables with the same String name (and therefore the same name String hashcode)
	 * represent the same object. Therefore, they should have the hashcode of the name.
	 * @return the hashcode of this Variable
	 */
	@Override public int hashCode() { return this.name.hashCode(); }
}
