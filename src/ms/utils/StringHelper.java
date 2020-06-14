package ms.utils;

import static java.util.Arrays.asList;
import static ms.ipp.Iterables.list;
import static ms.ipp.Iterables.mapped;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import ms.ipp.base.KeyValue;

public class StringHelper {

	public static String replaceTokens(String source, Map<String, String> tokens,
			Map<String, Integer> counts) {
		// This approach has complexity at least N * M, where N is length of source and
		// M is the number of tokens. If M is very high, this might become inefficient.
		// TODO: Maybe replace this naive approach with something more elaborate
		// https://de.wikipedia.org/wiki/String-Matching-Algorithmus#Multi-String-Matching
		// https://github.com/robert-bor/aho-corasick
		StringBuffer sb = new StringBuffer(source);
		for (var e : tokens.entrySet()) {
			int count = replaceToken(sb, e.getKey(), e.getValue());
			if (count > 0) {
				counts.put(e.getKey(), count);
			}
		}
		return sb.toString();
	}

	/**
	 * Replaces any occurrence of {@code tokenName} in {@code buffer} by a given
	 * {@code tokenValue}.
	 * 
	 * @param buffer     The String to look for tokens in, not null
	 * @param tokenName  The String to look for in {@code buffer}, not null
	 * @param tokenValue The String to replace {@code tokenName}, not null
	 * @return
	 */
	public static int replaceToken(StringBuffer buffer, String tokenName, String tokenValue) {
		int index = 0;
		int count = 0;
		while ((index = buffer.indexOf(tokenName, index)) != -1) {
			buffer.replace(index, index + tokenName.length(), tokenValue);
			count++;
		}
		return count;
	}

	/**
	 * If a given String is null, returns an empty {@link ArrayList}, otherwise,
	 * splits the String by means of {@code String.split(String)} method and returns
	 * an array containing the resulting elements.
	 * 
	 * @see String#split(String)
	 * @param source
	 * @param regex
	 * @return
	 */
	public static List<String> makeList(String source, String regex) {
		return (source != null) ? asList(source.split(regex)) : new ArrayList<String>();
	}

	public static String quote(String source) {
		if (source == null || source.length() == 0) {
			return "\"\"";
		}

		// Pessimistically, every char needs to be replaced (more memory, but
		// avoids unnecessary reallocations) + 2 bytes for quotes.
		StringBuffer replaced = new StringBuffer(source.length() * 2 + 2);
		replaced.append('"');
		for (int i = 0; i < source.length(); ++i) {
			char c = source.charAt(i);
			if (c == '\\') {
				replaced.append("\\\\");
			} else if (c == '\"') {
				replaced.append("\\\"");
			} else if (c == '\n') {
				replaced.append("\\n");
			} else if (c == '\t') {
				replaced.append("\\t");
			} else {
				replaced.append(c);
			}
		}
		replaced.append('"');
		return replaced.toString();
	}

	public static List<String> quote(List<String> strings) {
		List<String> quoted = new ArrayList<String>();
		for (String string : strings) {
			quoted.add(quote(string));
		}
		return quoted;
	}

	public static String dequote(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}

		StringBuffer replaced = new StringBuffer(source.length());
		// if source is quoted, remove quotes
		int start = 0, end = source.length();
		if (source.charAt(0) == '"' && source.charAt(source.length() - 1) == '"') {
			start++;
			end--;
		}

		for (int i = start; i < end;) {
			char c = source.charAt(i);
			// escape sequences are two-char long, meaning the last char
			// is definitely not an escape sequence.
			if (c == '\\' && i < end - 1) {
				char next = source.charAt(i + 1);
				if (next == '\\' || next == '\"') {
					replaced.append(next);
				} else if (next == 'n') {
					replaced.append('\n');
				} else if (next == 't') {
					replaced.append('\t');
				} else {
					throw new IllegalArgumentException("Unexpected escape sequence '"
							+ source.substring(i, i + 2) + "' at position " + i);
				}
				i += 2; // skip both characters
			} else {
				// this is just a normal character
				replaced.append(c);
				i++;
			}
		}

		return replaced.toString();
	}

	public static //
	<V, M extends Map<String, V>, N extends Map<String, V>> //
	void filterPrefix(String prefix, M attrs, N result, Collection<String> defaults) {
		if (result == null) {
			return;
		}
		if (defaults == null) {
			defaults = new ArrayList<>();
		}

		// load with defaults.
		for (String def : defaults) {
			if (attrs.containsKey(def)) {
				result.put(def, attrs.get(def));
			}
		}
		attrs.entrySet().forEach(e -> {
			KeyValue<String, String> split = splitPrefix(e.getKey(), '.');
			if (split.getKey().equals(prefix)) {
				result.put(split.getValue(), e.getValue());
			}
		});
	}

	/**
	 * Returns the prefix and the main name as a pair.
	 * 
	 * @param var
	 * @param separator
	 * @return
	 */
	public static KeyValue<String, String> splitPrefix(String var, char separator) {
		int dotIndex = var.indexOf(separator);
		if (dotIndex == -1) {
			return new KeyValue<>("", var);
		} else {
			return new KeyValue<>(var.substring(0, dotIndex), var.substring(dotIndex + 1));
		}
	}

	public static KeyValue<String, String> splitSuffix(String var, char separator) {
		int dotIndex = var.lastIndexOf(separator);
		if (dotIndex == -1) {
			return new KeyValue<>(var, "");
		} else {
			return new KeyValue<>(var.substring(0, dotIndex), var.substring(dotIndex + 1));
		}
	}

	public static String prefix(String var) {
		return prefix(var, '.');
	}

	public static String prefix(String var, char separator) {
		return splitSuffix(var, separator).getKey();
	}

	public static String essential(String var) {
		return splitPrefix(var, '.').getValue();
	}

	public static String ultimateTrim(String source) {
		return source.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
	}

	/**
	 * Split by commas, using positive non-matching lookahead for non-quoted and
	 * quoted strings
	 * 
	 * @param input
	 * @return
	 */
	public static List<String> splitQuoted(String input) {
		return list(mapped(asList(input.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")), String::trim));
	}

	public static String leftAddChar(String trunk, char chr, int count) {
		char[] chrs = new char[count];
		Arrays.fill(chrs, chr);
		String prefix = new String(chrs);
		return prefix + trunk;
	}

	public static String serialize(String separator, Object... params) {
		if (params.length == 0) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		buf.append(params[0]);
		for (int i = 1; i < params.length; ++i) {
			buf.append(separator).append(params[i]);
		}
		return buf.toString();
	}
}
