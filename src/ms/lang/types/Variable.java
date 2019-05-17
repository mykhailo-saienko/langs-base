package ms.lang.types;

import static ms.ipp.Iterables.isEqualOrNull;

import java.util.List;

public class Variable implements Definition {
	private final String name;
	private final TypeName type;

	// defining expression
	private String expression;

	public static final int NAME_ONLY = 0;
	public static final int TYPE_AND_NAME = 1;
	public static final int NAME_AND_EXPR = 2;
	public static final int COMPLETE = 3;

	public Variable(Variable var) {
		this(new TypeName(var.getTypeName()), var.getName(), var.getExpression());
	}

	public Variable(TypeName type, String name, String expression) {
		this.type = type;
		this.name = name;
		this.expression = expression;
	}

	@Override
	public String toString() {
		return serialize(true, true);
	}

	public String serialize(boolean showType, boolean showExpr) {
		StringBuffer sb = new StringBuffer(1000);
		serialize(sb, showType, showExpr);
		return sb.toString();
	}

	public void serialize(StringBuffer sb, boolean showType, boolean showExpr) {
		if (showType) {
			sb.append(type.serialize()).append(" ");
		}
		sb.append(name);
		if (showExpr && expression != null) {
			sb.append(" = ").append(expression);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public TypeName getTypeName() {
		return type;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void addImports(List<String> imports) {
		type.addImports(imports);
		// TODO: expression must enrich imports, too.
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Variable)) {
			return false;
		}
		Variable v = (Variable) obj;
		return name.equals(v.name) && type.equals(v.type) && isEqualOrNull(getExpression(), v.getExpression());
	}

}
