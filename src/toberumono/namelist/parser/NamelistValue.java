package toberumono.namelist.parser;

import toberumono.structures.tuples.Pair;

/**
 * Root class for a value in a {@link Namelist} file.
 * 
 * @author Toberumono
 * @param <T>
 *            the type that the {@link NamelistValue} holds
 */
public abstract class NamelistValue<T> extends Pair<NamelistType, T> {
	
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
		super(type, value);
	}
	
	/**
	 * @return the {@link NamelistType} of the {@link NamelistValue}
	 */
	public NamelistType type() {
		return getX();
	}
	
	/**
	 * @return the value wrapped by the {@link NamelistValue}
	 */
	public T value() {
		return getY();
	}
	
	/**
	 * @return the wrapped value as a {@link String} that is syntactically correct for a {@link Namelist} file.
	 */
	public abstract String toNamelistString();
}
