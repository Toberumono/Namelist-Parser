package toberumono.namelist.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the values for a single key-section pair in a {@link Namelist} file.
 * 
 * @author Toberumono
 * @param <T>
 *            the type of values in the list
 */
public class NamelistInnerList<T extends NamelistValue<?>> extends ArrayList<T> {
	private static final Pattern separationPattern = Pattern.compile("([\\s&&[^\n]]*|,|\\\\\n)*", Pattern.MULTILINE);
	private static final Pattern booleanPattern = Pattern.compile("\\.((true)|false)\\.");
	int valueWidth = 7;
	
	/**
	 * Constructs an empty {@link NamelistInnerList}.
	 */
	public NamelistInnerList() {/* This is a super-complicated constructor. */}
	
	/**
	 * Constructs a {@link NamelistInnerList} from a value string
	 * 
	 * @param value
	 *            the value string
	 */
	public NamelistInnerList(String value) {
		value = value.trim();
		char[] val = value.toCharArray();
		Matcher sm = separationPattern.matcher(value);
		boolean quoted = false;
		StringBuilder element = new StringBuilder();
		for (int i = 0; i < val.length; i++) {
			if (val[i] == '\\') {
				if (val[++i] == '\n') {
					String e = element.toString().trim();
					if (e.length() > 0)
						add(processElement(e));
					element.delete(0, element.length());
					if (sm.find(++i))
						i = sm.end() - 1;
					else
						break;
				}
				else
					element.append(val[i]);
			}
			else if (quoted) {
				if (val[i] == '\'')
					quoted = false;
				else
					element.append(val[i]);
			}
			else if (val[i] == ',') {
				String e = element.toString().trim();
				if (e.length() > 0)
					add(processElement(e));
				element.delete(0, element.length());
				if (sm.find(++i))
					i = sm.end() - 1;
				else
					break;
			}
			else {
				if (val[i] == '\'')
					quoted = true;
				else
					element.append(val[i]);
			}
		}
		String e = element.toString().trim();
		if (e.length() > 0)
			add(processElement(e));
	}
	
	@SuppressWarnings("unchecked")
	private T processElement(String element) {
		try {
			return (T) new NamelistNumber(Integer.parseInt(element));
		}
		catch (NumberFormatException e) {
			try {
				return (T) new NamelistNumber(Double.parseDouble(element));
			}
			catch (NumberFormatException ex) {
				Matcher m = booleanPattern.matcher(element);
				if (m.matches())
					return (T) new NamelistBoolean(m.group(2) != null);
				else
					return (T) new NamelistString(element);
			}
		}
	}
	
	@Override
	public boolean add(T value) {
		String v = value.type().stringValue(value.value());
		if (v.length() > valueWidth)
			valueWidth = v.length();
		return super.add(value);
	}
	
	int getValueWidth() {
		return valueWidth;
	}
	
	void setValueWidth(int valueWidth) {
		this.valueWidth = valueWidth;
	}
	
	void updateValueWidth(int valueWidth) {
		if (valueWidth > this.valueWidth)
			this.valueWidth = valueWidth;
	}
	
	/**
	 * Converts the {@link NamelistInnerList} to syntactically correct {@link Namelist} text
	 * 
	 * @param valueWidth
	 *            the width of the longest value
	 * @return the {@link NamelistInnerList} as syntactically correct {@link Namelist} text
	 */
	public String toNamelistString(int valueWidth) {
		StringBuilder sb = new StringBuilder(valueWidth * size());
		for (T v : this) {
			String sv = v.type().stringValue(v.value());
			sb.append(sv).append(",");
			for (int i = 0, spaces = valueWidth - sv.length() + 3; i < spaces; i++)
				sb.append(" ");
		}
		return sb.toString().trim();
	}
}
