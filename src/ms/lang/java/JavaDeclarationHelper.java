package ms.lang.java;

import static ms.ipp.Iterables.appendList;
import static ms.ipp.Iterables.map;
import static ms.lang.DeclarationHelper.call;
import static ms.lang.DeclarationHelper.varCall;
import static ms.lang.types.BaseMethod.constructor;
import static ms.lang.types.Mod.PUBLIC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ms.ipp.Iterables;
import ms.lang.DeclarationHelper;
import ms.lang.types.BaseConstructor;
import ms.lang.types.BaseMethod;
import ms.lang.types.ClassVariable;
import ms.lang.types.IType;
import ms.lang.types.MethodType;
import ms.lang.types.TypeName;
import ms.lang.types.Variable;

public class JavaDeclarationHelper {

	private final static Map<String, JavaPrimitiveTraits> traits = new HashMap<>();

	static {
		traits.put("int",
				new JavaPrimitiveTraits(int.class, Integer.class, "java.util.function.IntSupplier", "getAsInt", "0"));
		traits.put("long",
				new JavaPrimitiveTraits(long.class, Long.class, "java.util.function.LongSupplier", "getAsLong", "0"));
		traits.put("boolean", new JavaPrimitiveTraits(boolean.class, Boolean.class,
				"java.util.function.BooleanSupplier", "getAsBoolean", "true"));
		traits.put("float", new JavaPrimitiveTraits(float.class, Float.class, "java.util.function.DoubleSupplier",
				"getAsDouble", "0.0f"));
		traits.put("double", new JavaPrimitiveTraits(double.class, Double.class, "java.util.function.DoubleSupplier",
				"getAsDouble", "0.0"));
	}

	public static JavaPrimitiveTraits getTraits(String primitive) {
		return traits.get(primitive);
	}

	public static String javaDeclaration(BaseMethod method, boolean body, boolean defaults) {
		StringBuffer sb = new StringBuffer(2000);
		if (method.getModifier() != null) {
			sb.append(method.getModifier().toJava()).append(" ");
		}
		if (method.isStatic()) {
			sb.append("static ");
		}
		if (method.getMethodType() == MethodType.METHOD) {
			sb.append(method.getReturnType().serialize()).append(" ");
		}
		List<String> varSer = map(method.getArguments(), v -> v.serialize(true, defaults));
		sb.append(DeclarationHelper.call(method.getName(), varSer));
		if (body) {
			if (!method.isAbstract()) {
				sb.append("{\n").append(method.getBody()).append("\n}");
			} else {
				sb.append(";");
			}
		}
		return sb.toString();
	}

	public static String javaMethodName(BaseMethod method) {
		return method.getMethodType() == MethodType.METHOD ? method.getName() : "new " + method.getName();
	}

	public static List<BaseConstructor> createJavaConstructors(IType type, BaseMethod baseCr) {
		// full constructor appropriate for named-param convention
		List<BaseConstructor> constrs = new ArrayList<>();
		List<Variable> superVar = baseCr == null ? new ArrayList<>() : baseCr.getArguments();
		// add metadata + body
		List<ClassVariable> members = Iterables.list(type.variables(c -> !c.isStatic()));

		String body = appendList(members, "", "", "\n", (v, s) -> s.append("\t").append("this.").append(v.getName())
				.append(" = ").append(v.getName()).append(";"));
		if (!superVar.isEmpty()) {
			body = "\t" + varCall("super", superVar) + ";\n" + body;
		}
		List<Variable> args = new ArrayList<>();
		args.addAll(map(members, Variable::new));
		args.addAll(map(superVar, Variable::new));
		constrs.add(constructor(PUBLIC, true, type, args, body));

		// if the full constructor contains at least one new field, create a
		// "minimal"-constructor with only params needed to initialise super
		if (!members.isEmpty()) {
			List<String> params = new ArrayList<>();
			params.addAll(map(members, m -> m.getExpression() == null ? "null" : m.getExpression()));
			params.addAll(map(superVar, v -> v.getName()));

			body = superVar.isEmpty() ? "" : "\t" + call("this", params) + ";";
			constrs.add(constructor(PUBLIC, false, type, map(superVar, Variable::new), body));
		}

		return constrs;
	}

	public static boolean isJavaInterface(IType type) {
		if (type == null || !(type instanceof JType)) {
			return false;
		}
		JType jType = (JType) type;
		return jType.getCompiled().isInterface();
	}

	public static boolean isVoid(TypeName type) {
		return type.getName().equals("void") && !type.isTemplate();
	}

	public static class JavaPrimitiveTraits {

		JavaPrimitiveTraits(Class<?> primitiveClass, Class<?> wrapperClass, String supplierName, String getterName,
				String defaultValue) {
			this.supplierName = supplierName;
			this.getterName = getterName;
			this.defaultValue = defaultValue;
			type = new JType(primitiveClass);
			wrapperType = new JType(wrapperClass);
		}

		public String getSupplierName() {
			return supplierName;
		}

		public String getGetterName() {
			return getterName;
		}

		public IType getType() {
			return type;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public IType getWrapperType() {
			return wrapperType;
		}

		private final String defaultValue;
		private final String supplierName;
		private final String getterName;
		private final IType type, wrapperType;
	}

	public static String defaultJavaBody(BaseMethod m) {
		TypeName returnType = m.getReturnType();
		if (isVoid(returnType)) {
			return "";
		}
		String value = "null";
		JavaPrimitiveTraits t = null;
		if ((t = getTraits(returnType.getName())) != null) {
			value = t.getDefaultValue();
		}
		return "\treturn " + value + ";";
	}
}
