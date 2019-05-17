package ms.lang.types;

import static ms.ipp.Iterables.isEqualOrNull;

public class ClassVariable extends Variable {
	private final Mod modifier;
	private final VarModifier varModifier;
	private final boolean isStatic;

	public ClassVariable(Mod modifier, VarModifier varModifier, boolean isStatic, TypeName type, String name,
			String expression) {
		super(type, name, expression);
		this.modifier = modifier;
		this.varModifier = varModifier;
		this.isStatic = isStatic;
	}

	public VarModifier getVarModifier() {
		return varModifier;
	}

	public Mod getModifier() {
		return modifier;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassVariable)) {
			return false;
		}
		ClassVariable v = (ClassVariable) obj;
		return isEqualOrNull(modifier, v.modifier) && varModifier.equals(v.varModifier) && super.equals(v);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(1000);
		if (modifier != null) {
			sb.append(modifier.toJava()).append(" ");
		}
		if (isStatic()) {
			sb.append("static ");
		}
		if (varModifier != VarModifier.STANDARD) {
			sb.append(varModifier.toJava()).append(" ");
		}
		super.serialize(sb, true, false);
		sb.append(";");
		return sb.toString();
	}

}
