package toberumono.namelist.parser;

/**
 * Represents a {@link Boolean} value in a {@link Namelist} file.
 * 
 * @author Toberumono
 */
public class NamelistBoolean extends NamelistValue<Boolean> {
	
	/**
	 * Constructs a {@link NamelistBoolean} with the given value
	 * 
	 * @param value
	 *            {@code true} or {@code false}
	 */
	public NamelistBoolean(Boolean value) {
		super(NamelistType.Boolean, value);
	}
	
	@Override
	public String toNamelistString() {
		return "." + value().toString() + ".";
	}
	
}
