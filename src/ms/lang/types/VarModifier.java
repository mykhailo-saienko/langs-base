package ms.lang.types;

public enum VarModifier {
	FINAL, STANDARD, DYNAMIC, LOCAL;

	public static VarModifier parse(String mod) {
		if (mod == null) {
			return STANDARD;
		} else if ("final".equals(mod)) {
			return FINAL;
		} else if ("dynamic".equals(mod)) {
			return DYNAMIC;
		}
		// in particular, LOCAL should not be serializable
		throw new IllegalArgumentException("Unknown modifier '" + mod + "'");
	}

	public String toJava() {
		return this == FINAL ? "final" : this == DYNAMIC ? "dynamic" : "";
	}
}
