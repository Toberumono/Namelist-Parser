package toberumono.namelist.parser;

/**
 * Root class for a value in a {@link Namelist} file.
 * 
 * @author Toberumono
 * @param <T>
 *            the type that the {@link NamelistValue} holds
 */
public abstract class NamelistValue<T> {
	private final NamelistType type;
	private final T value;
	
	/**
	 * Constructs a {@link NamelistValue} with the given type and value. This should be overridden with a constructor that
	 * <i>only</i> takes a value in implementing classes.
	 * 
	 * @param type
	 *            the {@link NamelistType} of the {@link NamelistValue}
	 * @param value
	 *            the value wrapped by the {@link NamelistValue}
	 */
	public NamelistValue(NamelistType type, T value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * @return the {@link NamelistType} of the {@link NamelistValue}
	 */
	public NamelistType type() {
		return type;
	}
	
	/**
	 * @return the value wrapped by the {@link NamelistValue}
	 */
	public T value() {
		return value;
	}
	
	/**
	 * @return the wrapped value as a {@link String} that is syntactically correct for a {@link Namelist} file.
	 */
	public abstract String toNamelistString();
}
