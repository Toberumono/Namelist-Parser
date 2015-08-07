package toberumono.namelist.parser;

/**
 * Represents a {@link String} value in a {@link Namelist} file.
 * 
 * @author Toberumono
 */
public class NamelistString extends NamelistValue<String> {
	
	/**
	 * Constructs a {@link NamelistString} with the given value
	 * 
	 * @param value
	 *            {@code true} or {@code false}
	 */
	public NamelistString(String value) {
		super(NamelistType.String, value);
	}
	
	@Override
	public String toNamelistString() {
		return "'" + value() + "'";
	}
	
}
