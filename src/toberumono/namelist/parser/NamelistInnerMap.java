package toberumono.namelist.parser;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stores the key-value mappings for a single section of a {@link Namelist} file.
 * 
 * @author Toberumono
 */
public class NamelistInnerMap extends LinkedHashMap<String, NamelistInnerList<?>> {
	private int keyWidth = 15;
	private int valueWidth = 7;
	private static final String wordPattern = "[a-z_A-Z][a-z_A-Z0-9]*", quotedPattern = "('.*?(?<!\\\\)')", valueLinePattern = "(" + quotedPattern + "|[^\\\\\n]*\\\\\n)*(" + quotedPattern
			+ "|[^\n])*";
	private static final Pattern keyValuePattern = Pattern.compile("\\s*(" + wordPattern + ")(\\s|\\\\\n)*=(\\s|\\\\\n)*(" + valueLinePattern + ")", Pattern.MULTILINE);
	
	/**
	 * Constructs an empty {@link NamelistInnerMap}.
	 */
	public NamelistInnerMap() {/* This is a super-complicated constructor. */}
	
	/**
	 * Constructs a {@link NamelistInnerList} from a {@link String} containing key-value pairs (at most one pair per line).
	 * 
	 * @param keyValue
	 *            the value string
	 */
	public NamelistInnerMap(String keyValue) {
		final StringBuilder sb = new StringBuilder();
		String groupName = "";
		for (String line : keyValue.split(System.lineSeparator())) {
			line = line.trim();
			final Matcher m = keyValuePattern.matcher(line);
			if (m.matches()) {
				String v = sb.toString().trim();
				if (v.length() > 0)
					put(groupName, new NamelistInnerList<>(v));
				groupName = m.group(1);
				sb.delete(0, sb.length());
				sb.append(m.group(4).trim());
			}
			else
				sb.append("\\\n").append(line.trim());
		}
	}
	
	@Override
	public NamelistInnerList<?> put(String key, NamelistInnerList<?> value) {
		if (key.length() > keyWidth)
			keyWidth = key.length();
		if (value.getValueWidth() > valueWidth)
			valueWidth = value.getValueWidth();
		return super.put(key, value);
	}
	
	@Override
	public String toString() {
		return toNamelistString(getValueWidth());
	}
	
	/**
	 * Converts the {@link NamelistInnerMap} to syntactically correct {@link Namelist} text
	 * 
	 * @param valueWidth
	 *            the width of the largest value
	 * @return the {@link NamelistInnerMap} as syntactically correct {@link Namelist} text
	 */
	public String toNamelistString(int valueWidth) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, NamelistInnerList<?>> value : entrySet()) {
			sb.append(" ").append(value.getKey());
			for (int i = 0, spaces = getKeyWidth() - value.getKey().length() + 3; i < spaces; i++)
				sb.append(" ");
			sb.append("= ").append(value.getValue().toNamelistString(valueWidth)).append(System.lineSeparator());
		}
		return sb.toString().trim();
	}
	
	int getKeyWidth() {
		return keyWidth;
	}
	
	void setKeyWidth(int keyWidth) {
		this.keyWidth = keyWidth;
	}
	
	void updateKeyWidth(int keyWidth) {
		if (keyWidth > this.keyWidth)
			this.keyWidth = keyWidth;
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
}
