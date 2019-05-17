package ms.lang.types;

import static java.util.Arrays.asList;
import static ms.ipp.Algorithms.and;
import static ms.ipp.Iterables.unique;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import ms.ipp.iterable.tree.path.StdPathManipulator;

public interface IType {

	public void addVariable(ClassVariable var, BiConsumer<ClassVariable, ClassVariable> updater);

	public void addMethod(BaseMethod method, BiConsumer<BaseMethod, BaseMethod> updater);

	public void addConstructor(BaseConstructor constructor, BiConsumer<BaseConstructor, BaseConstructor> updater);

	Iterable<Definition> definitions(Predicate<? super Definition> pred);

	Iterable<BaseMethod> methods(Predicate<? super BaseMethod> pred);

	Iterable<BaseConstructor> constructors(Predicate<? super BaseConstructor> pred);

	Iterable<ClassVariable> variables(Predicate<? super ClassVariable> pred);

	TypeName getType();

	TypeName getBase();

	void setAbstract(boolean abstr);

	boolean isAbstract();

	boolean isFleeting();

	Integer getLanguage();

	String getSource();

	void setSource(String source);

	List<String> collectImports(boolean includePackage);

	boolean isAssignableFrom(IType type);

	Instance getTypeRef();

	default BaseMethod getStaticMethod(String name, String... params) {
		return getMethod(name, m -> m.hasParamNames(asList(params)) && m.isStatic());
	}

	default BaseMethod getMemberMethod(String name, Object instance, String... params) {
		BaseMethod method = getMethod(name, m -> m.hasParamNames(asList(params)) && !m.isStatic());
		if (method == null) {
			return null;
		}
		method.setThis(instance);
		return method;
	}

	/**
	 * Returns a method with a given name which satisfies a given predicate. If
	 * there is more than one method, throws an {@link IllegalArgumentException}. If
	 * no methods are found, returns null.
	 * 
	 * @param name
	 * @param pred
	 * @return
	 */
	default BaseMethod getMethod(String name, Predicate<? super BaseMethod> pred) {
		Predicate<BaseMethod> newPred = m -> m.getName().equals(name);
		return unique(methods(and(newPred::test, pred::test)), null);
	}

	default BaseConstructor getConstructor(Predicate<? super BaseConstructor> pred) {
		return unique(constructors(pred), null);
	}

	default BaseConstructor getConstructor(String... params) {
		return getConstructor(m -> m.hasParamNames(Arrays.asList(params)));
	}

	default BaseConstructor getStdConstructor() {
		return getConstructor(m -> m.isStdConstructor());
	}

	default ClassVariable getVariable(String name, Predicate<ClassVariable> pred) {
		for (ClassVariable v : variables(null)) {
			if (v.getName().equals(name) && (pred == null || pred.test(v))) {
				return v;
			}
		}
		return null;
	}

	default String getFullName() {
		return getType().getFullName();
	}

	default String getSimpleName() {
		return getType().getName();
	}

	default String getBaseName() {
		return getBase() == null ? null : getBase().getName();
	}

	default String getPackage() {
		if (getType().getImport() == null) {
			return null;
		}
		return StdPathManipulator.getPackage(getType().getFullName());
	}

	default boolean isCompatible(IType type) {
		return getClass().equals(type.getClass());
	}

}