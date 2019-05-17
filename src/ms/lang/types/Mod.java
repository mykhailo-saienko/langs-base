package ms.lang.types;

public enum Mod {
	PUBLIC, PROTECTED, PRIVATE;

	public static Mod parse(String mod) {
		if (mod == null) {
			return null;
		} else if ("private".equals(mod)) {
			return Mod.PRIVATE;
		} else if ("public".equals(mod)) {
			return Mod.PUBLIC;
		} else if ("protected".equals(mod)) {
			return Mod.PROTECTED;
		}
		throw new IllegalArgumentException("Unknown modifier '" + mod + "'");
	}

	public String toJava() {
		return this == PUBLIC ? "public" : (this == PRIVATE ? "private" : "protected");
	}
}
