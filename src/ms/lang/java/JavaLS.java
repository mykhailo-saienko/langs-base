package ms.lang.java;

import static java.util.Arrays.asList;
import static ms.ipp.Iterables.map;
import static ms.utils.StringHelper.makeList;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.lang.AbstractLS;
import ms.lang.types.BaseMethod;
import ms.lang.types.IType;
import ms.lang.types.Instance;
import ms.lang.types.TypeName;
import ms.utils.DateHelper;
import ms.utils.NumberHelper;

public class JavaLS extends AbstractLS {
	private final Map<String, BiFunction<String, TypeName, Object>> evaluators;
	private static final Logger logger = LogManager.getLogger();
	private final BiFunction<String, TypeName, Instance> refResolver;

	private static BiFunction<String, TypeName, Object> noTypeName(Function<String, ?> source) {
		return (s, t) -> source.apply(s);
	}

	public JavaLS(Function<String, Instance> refResolver) {
		this.refResolver = (s, t) -> refResolver.apply(s);

		evaluators = new HashMap<>();
		addEval(String.class.getCanonicalName(), noTypeName(s -> s));
		addEval(Date.class.getCanonicalName(), noTypeName(DateHelper::safeParse));
		addEval(BigDecimal.class.getCanonicalName(), noTypeName(NumberHelper::safeParse));
		addEval(boolean.class.getCanonicalName(), noTypeName(Boolean::valueOf));
		addEval(Boolean.class.getCanonicalName(), noTypeName(Boolean::valueOf));
		addEval(Integer.class.getCanonicalName(), noTypeName(Integer::parseInt));
		addEval(int.class.getCanonicalName(), noTypeName(Integer::parseInt));
		addEval(Long.class.getCanonicalName(), noTypeName(Long::parseLong));
		addEval(long.class.getCanonicalName(), noTypeName(Long::parseLong));
		addEval(Float.class.getCanonicalName(), noTypeName(Float::parseFloat));
		addEval(float.class.getCanonicalName(), noTypeName(Float::parseFloat));
		addEval(Double.class.getCanonicalName(), noTypeName(Double::parseDouble));
		addEval(double.class.getCanonicalName(), noTypeName(Double::parseDouble));
		addEval(List.class.getCanonicalName(), this::evaluateList);
	}

	@Override
	protected List<TypeLoader> initTypeLoaders() {
		return Arrays.asList(new TypeLoader(this::loadPrimitive, TypeLoader::noPackage), //
				new TypeLoader(this::loadStdClass, p -> TypeLoader.add(p, asList("java.lang.*"))));
	}

	@Override
	public boolean isSupported(BaseMethod method) {
		return method instanceof JavaMethod || method instanceof JavaConstructor;
	}

	@Override
	public boolean isSupported(IType type) {
		return isNative(type);
	}

	@Override
	public boolean isNative(IType type) {
		return type instanceof JType;
	}

	@Override
	public Object eval(String source, TypeName type) {
		BiFunction<String, TypeName, ?> parser = evaluators.get(type.getFullName());
		if (parser != null) {
			Object result = parser.apply(source, type);
			logger.debug("Evaluating value {} with type {}: result={} of class {}", source, type, result,
					result == null ? null : result.getClass());
			return result;
		} else {
			Instance result = refResolver.apply(source, type);
			return result == null ? null : result.getValue();
		}
	}

	@Override
	protected List<IType> extract(String source, Map<String, Object> options) {
		throw new UnsupportedOperationException("Compiling is not supported");
	}

	@Override
	protected void compile(String source, List<IType> types, Map<String, Object> options) {

	}

	private void addEval(String className, BiFunction<String, TypeName, Object> eval) {
		evaluators.put(className, eval);
	}

	private Object evaluateList(String source, TypeName listType) {
		TypeName valueType = listType.getTemplateArg(0);
		Function<String, Object> parser = s -> eval(s == null ? null : s.trim(), valueType);
		List<Object> result = map(makeList(source, ","), parser);
		logger.debug("Evaluated List<{}>: {} with size {}", valueType, result, result.size());
		return result;
	}

	@Override
	public void forEachType(String packageName, Predicate<IType> pred, Consumer<IType> processor) {
		// TODO: There seems to be no standard means of retrieving this info
		// without explicitly registering the classes (i.e. custom class loader
		// or file manager)
		// Should we introduce the Reflections-Lib here?
		// https://github.com/ronmamo/reflections
		// Or maybe use alternative suggestions without Reflections
		// https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
	}

	/**
	 * Does nothing, as there are no user-defined types in static java
	 */
	@Override
	public void forEachUserType(String packageName, Predicate<IType> pred, Consumer<IType> processor) {

	}

	@Override
	public boolean deleteType(String typeName) {
		// Cannot delete types in static java
		return false;
	}

	protected IType loadPrimitive(String className) {
		JavaDeclarationHelper.JavaPrimitiveTraits t = JavaDeclarationHelper.getTraits(className);
		if (t != null) {
			return t.getType();
		}
		return null;
	}

	protected IType loadStdClass(String className) {
		try {
			return new JType(Class.forName(className));
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
