package toberumono.namelist.parser;

/**
 * The types of values that can be in {@link Namelist} files.
 * 
 * @author Toberumono
 */
public enum NamelistType {
	/**
	 * Represents a decimal number.
	 */
	Number {
		@Override
		public java.lang.String stringValue(Object o) {
			return o.toString();
		}
	},
	/**
	 * Represents a boolean value.
	 */
	Boolean {
		@Override
		public java.lang.String stringValue(Object o) {
			return "." + ((Boolean) o) + ".";
		}
	},
	/**
	 * Represents a string value.
	 */
	String {
		@Override
		public java.lang.String stringValue(Object o) {
			return "'" + ((String) o) + "'";
		}
	};
	
	/**
	 * Function to convert a value into a syntactically correct {@link String} for a {@link Namelist} file.
	 * 
	 * @param v
	 *            the value to convert
	 * @return the value as a syntactically correct {@link String} for a {@link Namelist} file
	 */
	public abstract String stringValue(Object v);
}
