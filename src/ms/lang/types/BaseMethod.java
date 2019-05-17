package ms.lang.types;

import static java.util.Arrays.asList;
import static ms.ipp.Iterables.isEqualOrNull;
import static ms.ipp.Iterables.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.base.KeyValue;
import ms.lang.java.JavaDeclarationHelper;

public class BaseMethod implements Definition {
	private static final Logger logger = LogManager.getLogger();

	public static final int GENERAL = 0;
	public static final int INTERNAL_CALL = 1;
	public static final int EXTERNAL_CALL = 2;

	private final Mod modifier;
	private final boolean isStatic;
	private final String name;
	// first is Type, second is ID
	private List<Variable> arguments;
	private final TypeName returnType;
	private String body;

	private MethodType type;

	public static BaseConstructor constructor(Mod mod, boolean std, IType type, String name, List<Variable> vars,
			String body) {
		BaseConstructor c = new BaseConstructor(mod, type.getType(), name, body);
		c.setMethodType(std ? MethodType.STD_CONSTRUCTOR : MethodType.CONSTRUCTOR);
		c.setArguments(vars);
		return c;
	}

	public static BaseConstructor constructor(Mod mod, boolean std, IType type, List<Variable> vars, String body) {
		return constructor(mod, std, type, type.getSimpleName(), vars, body);
	}

	public BaseMethod(BaseMethod source) {
		this(source.modifier, source.isStatic, new TypeName(source.returnType), source.name, source.getBody());
		this.type = source.type;
		setArguments(map(source.arguments, Variable::new));
	}

	public BaseMethod(Mod modifier, boolean isStatic, TypeName returnType, String name, String body) {
		this.name = name;
		this.body = body;
		this.isStatic = isStatic;
		this.returnType = returnType;
		this.modifier = modifier;
		this.type = MethodType.METHOD;
		arguments = new ArrayList<>();
	}

	public BaseMethod setThis(Object instance) {
		// Do nothing, as the method cannot be invoked anyway.
		// We do not throw an exception, as Type::getMemberMethod calls
		// this method and expects it to succeed. This doesn't matter, as
		// the method throws upon being invoked.
		return this;
	}

	public Object invoke(Object... params) {
		throw new IllegalStateException("BaseMethod cannot be invoked: objects=" + Arrays.asList(params));
	}

	public void setArguments(List<Variable> source) {
		arguments = source;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String getName() {
		return name;
	}

	public List<Variable> getArguments(int mode) {
		return arguments;
	}

	public final List<Variable> getArguments() {
		return arguments;
	}

	public Variable getArgument(String name) {
		// TODO: Store arguments as map and make this method const-time?
		for (Variable v : arguments) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}

	public Mod getModifier() {
		return modifier;
	}

	public TypeName getReturnType() {
		return returnType;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isAbstract() {
		return getBody() == null;
	}

	public void setAbstract() {
		setBody(null);
	}

	public boolean sameSignatureAs(BaseMethod source) {
		return name.equals(source.name) && hasParamsAs(source.arguments);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseMethod)) {
			return false;
		}
		BaseMethod m = (BaseMethod) obj;
		return name.equals(m.name) && arguments.equals(m.arguments) && returnType.equals(m.returnType)
				&& isEqualOrNull(getBody(), m.getBody()) && modifier.equals(m.modifier);
	}

	public boolean hasParamNames(List<String> paramNames) {
		List<String> thisParamTypes = map(arguments, v -> v.getTypeName().getFullName());
		return isEqualOrNull(thisParamTypes, paramNames);
	}

	public boolean hasParamsAs(List<Variable> theseVars) {
		return hasParamNames(map(theseVars, v -> v.getTypeName().getFullName()));
	}

	public boolean isAuxConstructor() {
		return type == MethodType.CONSTRUCTOR;
	}

	public boolean isStdConstructor() {
		return type == MethodType.STD_CONSTRUCTOR;
	}

	public void setMethodType(MethodType type) {
		this.type = type;
	}

	public MethodType getMethodType() {
		return type;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addImports(List<String> imports) {
		if (!isAuxConstructor()) {
			returnType.addImports(imports);
		}
		for (Variable a : arguments) {
			a.addImports(imports);
		}
	}

	protected static void logResult(Object result) {
		logger.debug("Result is {} of type {}", result, result == null ? null : result.getClass());
	}

	protected static void logParams(Object compiled, Object self, Object... parameters) {
		if (logger.isDebugEnabled()) {
			List<Object> parms = asList(parameters);
			List<KeyValue<Object, Class<?>>> kvps = map(parms, p -> new KeyValue<>(p, p.getClass()));
			logger.debug("Invoking {} with this={} and params {}", () -> compiled, () -> "{" + self + "}", () -> kvps);
		}
	}

	/**
	 * Used for <b>displaying purposes</b> only, as it does not necessarily obeys
	 * the language syntax rules in favour of expressivity and user-friendly format.
	 * This method should be used instead of {@link #toString()} whenever possible
	 * (so that we actually keep track of its usage). <br>
	 * <b>NOTE:</b> {@link #toString()} calls this method by default.
	 * 
	 * @return
	 */
	public String getSignature() {
		return JavaDeclarationHelper.javaDeclaration(this, false, false);
	}

	@Override
	public String toString() {
		return getSignature();
	}

	public static void updateMeta(BaseMethod target, BaseMethod source) {
		List<Variable> sourceParams = source.getArguments();
		List<Variable> thisParams = target.getArguments();
		if (sourceParams.size() != thisParams.size()) {
			// fail-fast
			throw new IllegalArgumentException("Source method '" + source + "' has different number of arguments");
		}
		for (int i = 0; i < sourceParams.size(); ++i) {
			thisParams.get(i).setExpression(sourceParams.get(i).getExpression());
		}
		target.setBody(source.getBody());
		target.setMethodType(source.getMethodType());
	}
}
