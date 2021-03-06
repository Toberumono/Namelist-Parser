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
public class NamelistSection extends LinkedHashMap<String, NamelistValueList<?>> {
	private int keyWidth = 7;
	private int valueWidth = 7;
	private static final String wordPattern = "[a-z_A-Z][a-z_A-Z0-9]*", quotedPattern = "('.*?(?<!\\\\)')", valueLinePattern = "(" + quotedPattern + "|[^\\\\\n]*\\\\\n)*(" + quotedPattern
			+ "|[^\n])*";
	private static final Pattern keyValuePattern = Pattern.compile("\\s*(" + wordPattern + ")(\\s|\\\\\n)*=(\\s|\\\\\n)*(" + valueLinePattern + ")", Pattern.MULTILINE);
	
	/**
	 * Constructs an empty {@link NamelistSection}.
	 */
	public NamelistSection() {/* This is a super-complicated constructor. */}
	
	/**
	 * Constructs a {@link NamelistSection} from a {@link String} containing key-value pairs (at most one pair per line).
	 * 
	 * @param keyValue
	 *            the value string
	 */
	public NamelistSection(String keyValue) {
		final StringBuilder sb = new StringBuilder();
		String groupName = "";
		for (String line : keyValue.split(System.lineSeparator())) {
			line = line.trim();
			final Matcher m = keyValuePattern.matcher(line);
			if (m.matches()) {
				String v = sb.toString().trim();
				if (v.length() > 0)
					put(groupName, new NamelistValueList<>(v));
				groupName = m.group(1);
				sb.delete(0, sb.length());
				sb.append(m.group(4).trim());
			}
			else
				sb.append("\\\n").append(line.trim());
		}
		if (keyValue.length() > 0) {
			String v = sb.toString().trim();
			if (v.length() > 0)
			put(groupName, new NamelistValueList<>(v));
		}
	}
	
	@Override
	public NamelistValueList<?> put(String key, NamelistValueList<?> value) {
		if (key.length() > keyWidth)
			keyWidth = key.length();
		if (value.getValueWidth() > valueWidth)
			valueWidth = value.getValueWidth();
		return super.put(key, value);
	}
	
	@Override
	public String toString() {
		return toNamelistString(getKeyWidth(), getValueWidth());
	}
	
	/**
	 * Converts the {@link NamelistSection} to syntactically correct {@link Namelist} text
	 * 
	 * @param keyWidth
	 *            the width of the longest key
	 * @param valueWidth
	 *            the width of the longest value
	 * @return the {@link NamelistSection} as syntactically correct {@link Namelist} text
	 */
	public String toNamelistString(int keyWidth, int valueWidth) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, NamelistValueList<?>> value : entrySet()) {
			sb.append(" ").append(value.getKey());
			for (int i = 0, spaces = keyWidth - value.getKey().length() + 3; i < spaces; i++)
				sb.append(" ");
			int vw = value.getValue().getValueWidth();
			sb.append("= ").append(value.getValue().toNamelistString(vw < valueWidth / 2 ? vw : valueWidth)).append(System.lineSeparator());
		}
		return sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
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
