package ms.lang;

import java.util.Arrays;
import java.util.Collection;

public class Language {
	public static final String LX_S = "LX";
	public static final String PYTHON_S = "Python";
	public static final String JAVA_S = "Java";

	public static final Integer LX = 0;
	public static final Integer JAVA = 1;
	public static final Integer PYTHON = 2;

	public static Integer parse(String source) {
		if (source == null) {
			return null;
		} else if (source.equalsIgnoreCase(JAVA_S)) {
			return JAVA;
		} else if (source.equalsIgnoreCase(PYTHON_S)) {
			return PYTHON;
		} else if (source.equalsIgnoreCase(LX_S)) {
			return LX;
		}
		throw new IllegalArgumentException("Unknown language '" + source + "'");
	}

	public static Collection<Integer> values() {
		return Arrays.asList(LX, JAVA, PYTHON);
	}

}
