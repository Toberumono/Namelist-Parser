package toberumono.namelist.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import toberumono.customStructures.tuples.Pair;

public class NamelistParser {
	private static final String wordPattern = "[a-z_A-Z][a-z_A-Z0-9]*", quotedPattern = "('.*?(?<!\\\\)')", valueLinePattern = "(" + quotedPattern + "|[^\\\\\n]*\\\\\n)*(" + quotedPattern
			+ "|[^\n])*";
	private static final Pattern booleanPattern = Pattern.compile("\\.((true)|false)\\."), separationPattern = Pattern.compile("([\\s&&[^\n]]*|,|\\\\\n)*", Pattern.MULTILINE);
	private static final Pattern keyValuePattern = Pattern.compile("\\s*(" + wordPattern + ")(\\s|\\\\\n)*=(\\s|\\\\\n)*(" + valueLinePattern + ")", Pattern.MULTILINE);
	private static final String lineSep = System.getProperty("line.separator");
	
	private static final class ParseNamelistState {
		boolean inGroup = false;
		String groupName = "";
	}
	
	/**
	 * Returns a map-representation of the namelist file contained in <tt>string</tt>.<br>
	 * Each value in the returned inner map is a {@link List} of {@link Pair Pairs}, regardless of how many values were found
	 * for that key.
	 * 
	 * @param string
	 *            the unescaped text of a namelist file including newlines
	 * @return a {@link Map} that maps group names to {@link Map Maps} that map key-value pairs
	 */
	public static Namelist parseNamelist(String string) {
		return parse(Arrays.stream(string.split(lineSep)));
	}
	
	/**
	 * Returns a map-representation of the namelist file located at <tt>path</tt>.<br>
	 * Each value in the returned inner map is a {@link List} of {@link Pair Pairs}, regardless of how many values were found
	 * for that key.
	 * 
	 * @param path
	 *            the {@link Path} to a valid namelist file
	 * @return a {@link Map} that maps group names to {@link Map Maps} that map key-value pairs
	 * @throws IOException
	 *             if an I/O error occured
	 */
	public static Namelist parseNamelist(Path path) throws IOException {
		return parse(Files.lines(path));
	}
	
	private static final Namelist parse(Stream<String> in) {
		final StringBuilder sb = new StringBuilder();
		final ParseNamelistState pns = new ParseNamelistState();
		return in.collect(Namelist::new, (out, s) -> {
			s = s.trim();
			if (s.length() < 1)
				return;
			if (pns.inGroup) {
				if (s.equals("/")) {
					pns.inGroup = false;
					NamelistInnerMap o = parseKeyValuePairs(sb.toString());
					out.put(pns.groupName, o);
					sb.delete(0, sb.length());
				}
				else
					sb.append(s).append("\n");
			}
				else if (s.charAt(0) == '&') {
					pns.groupName = s.substring(1);
					pns.inGroup = true;
				}
			}, Namelist::putAll);
	};
	
	private static NamelistInnerMap parseKeyValuePairs(String keyValue) {
		keyValue = keyValue.trim();
		if (keyValue.length() < 3)
			return new NamelistInnerMap();
		final StringBuilder sb = new StringBuilder();
		final ParseNamelistState pns = new ParseNamelistState();
		NamelistInnerMap o =
				Arrays.stream(keyValue.split(lineSep)).collect(NamelistInnerMap::new, (out, s) -> {
					s = s.trim();
					final Matcher m = keyValuePattern.matcher(s);
					if (m.matches()) {
						String v = sb.toString().trim();
						if (v.length() > 0)
							out.put(pns.groupName, parseValueString(v));
						pns.groupName = m.group(1);
						sb.delete(0, sb.length());
						sb.append(m.group(4).trim());
					}
						else
							sb.append("\\\n").append(s.trim());
					}, NamelistInnerMap::putAll);
		String s = sb.toString().trim();
		if (s.length() > 0)
			o.put(pns.groupName, parseValueString(s));
		return o;
	}
	
	private static NamelistInnerList parseValueString(String value) {
		NamelistInnerList output = new NamelistInnerList();
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
						output.add(processElement(e));
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
					output.add(processElement(e));
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
			output.add(processElement(e));
		return output;
	}
	
	private static Pair<NamelistType, Object> processElement(String element) {
		try {
			return new Pair<>(NamelistType.Number, Integer.parseInt(element));
		}
		catch (NumberFormatException e) {
			try {
				return new Pair<>(NamelistType.Number, Double.parseDouble(element));
			}
			catch (NumberFormatException ex) {
				Matcher m = booleanPattern.matcher(element);
				if (m.matches())
					return new Pair<>(NamelistType.Boolean, m.group(2) != null);
				else
					return new Pair<>(NamelistType.String, element);
			}
		}
	}
	
	public static void writeNamelist(Namelist namelist, Path path) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, NamelistInnerMap> e : namelist.entrySet()) {
			sb.append("&").append(e.getKey()).append(lineSep);
			for (Entry<String, NamelistInnerList> value : e.getValue().entrySet()) {
				sb.append(" ").append(value.getKey());
				for (int i = 0, spaces = namelist.getKeyWidth() - value.getKey().length() + 3; i < spaces; i++)
					sb.append(" ");
				sb.append("= ");
				for (Iterator<Pair<NamelistType, Object>> it = value.getValue().iterator(); it.hasNext();) {
					Pair<NamelistType, Object> v = it.next();
					String sv = v.getX().stringValue(v.getY());
					sb.append(sv).append(",");
					if (it.hasNext())
						for (int i = 0, spaces = namelist.getValueWidth() - sv.length() + 3; i < spaces; i++)
							sb.append(" ");
				}
				sb.append(lineSep);
			}
			sb.append("/").append(lineSep).append(lineSep);
		}
		Files.write(path, Arrays.asList(sb.toString().split(lineSep)), Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
}
