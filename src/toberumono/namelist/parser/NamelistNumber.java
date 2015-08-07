package toberumono.namelist.parser;

/**
 * Represents a {@link Number} value in a {@link Namelist} file.
 * 
 * @author Toberumono
 */
public class NamelistNumber extends NamelistValue<Number> {
	
	/**
	 * Constructs a {@link NamelistNumber} with the given value
	 * 
	 * @param value
	 *            {@code true} or {@code false}
	 */
	public NamelistNumber(Number value) {
		super(NamelistType.Number, value);
	}
	
	@Override
	public String toNamelistString() {
		return value().toString();
	}
	
}
