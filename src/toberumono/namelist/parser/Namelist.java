package toberumono.namelist.parser;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * A representation of a {@link Namelist} file.
 * 
 * @author Toberumono
 */
public class Namelist extends LinkedHashMap<String, NamelistInnerMap> {
	private static final String lineSep = System.lineSeparator();
	private int keyWidth = 7;
	private int valueWidth = 7;
	
	private static final class ParseNamelistState {
		boolean inGroup = false;
		String groupName = "";
	}
	
	/**
	 * Constructs an empty {@link Namelist}.
	 */
	public Namelist() {/* This is a super-complicated constructor. */}
	
	/**
	 * Constructs a {@link Namelist} from the given {@link String}.<br>
	 * Each value in the returned inner map is a {@link NamelistInnerList} of {@link NamelistValue NamelistValues},
	 * regardless of how many values were found for that key.
	 * 
	 * @param text
	 *            the unescaped text (including newlines) of a {@link Namelist} file
	 */
	public Namelist(String text) {
		this(Arrays.stream(text.split(System.lineSeparator())));
	}
	
	/**
	 * Constructs a {@link Namelist} from the file at the given {@link Path}.<br>
	 * Each value in the returned inner map is a {@link NamelistInnerList} of {@link NamelistValue NamelistValues},
	 * regardless of how many values were found for that key.
	 * 
	 * @param path
	 *            the {@link Path} to a valid {@link Namelist} file
	 * @throws IOException
	 *             if an I/O error occured
	 */
	public Namelist(Path path) throws IOException {
		this(Files.lines(path));
	}
	
	/**
	 * Constructs a {@link Namelist} from the given {@link Stream}.<br>
	 * Each value in the returned inner map is a {@link NamelistInnerList} of {@link NamelistValue NamelistValues},
	 * regardless of how many values were found for that key.
	 * 
	 * @param in
	 *            a {@link Stream} of lines of syntactically valid namelist text
	 */
	public Namelist(Stream<String> in) {
		final StringBuilder sb = new StringBuilder();
		final ParseNamelistState pns = new ParseNamelistState();
		in.forEach(line -> {
			line = line.trim();
			if (line.length() < 1)
				return;
			if (pns.inGroup) {
				if (line.equals("/")) {
					pns.inGroup = false;
					NamelistInnerMap o = new NamelistInnerMap(sb.toString());
					put(pns.groupName, o);
					sb.delete(0, sb.length());
				}
				else
					sb.append(line).append("\n");
			}
			else if (line.charAt(0) == '&') {
				pns.groupName = line.substring(1);
				pns.inGroup = true;
			}
		});
	}
	
	@Override
	public NamelistInnerMap put(String key, NamelistInnerMap value) {
		if (value.getKeyWidth() > keyWidth)
			keyWidth = value.getKeyWidth();
		if (key.length() > keyWidth)
			keyWidth = key.length();
		if (value.getValueWidth() > valueWidth)
			valueWidth = value.getValueWidth();
		return super.put(key, value);
	}
	
	/**
	 * Writes a {@link Namelist} to the file located at {@link Path}
	 * 
	 * @param path
	 *            a {@link Path} to the file to which the {@link Namelist} will be written
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void write(Path path) throws IOException {
		Files.write(path, Arrays.asList(toString().split(lineSep)), Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	/**
	 * Writes the {@link Namelist} to the given {@link Writer}.
	 * 
	 * @param writer
	 *            the {@link Writer} to which the {@link Namelist} will be written
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public void write(Writer writer) throws IOException {
		writer.write(toString());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, NamelistInnerMap> e : entrySet()) {
			sb.append("&").append(e.getKey()).append(lineSep);
			sb.append(e.getValue().toNamelistString(getKeyWidth(), getValueWidth())).append(lineSep);
			sb.append("/").append(lineSep).append(lineSep);
		}
		return sb.toString();
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
