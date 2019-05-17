package ms.lang;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import ms.lang.types.BaseMethod;
import ms.lang.types.IType;
import ms.lang.types.TypeName;

public interface LanguageSystem {

	boolean isSupported(BaseMethod method);

	/**
	 * Returns true if the type is known to the LS
	 * 
	 * @param type
	 * @return
	 */
	boolean isSupported(IType type);

	/**
	 * Returns true if the type originates from this LS.
	 * 
	 * @param type
	 * @return
	 */
	boolean isNative(IType type);

	/**
	 * Evaluates a given String-input and a given expected object type.
	 * 
	 * @param        source, the input, may be null. Concrete LS-implementations may
	 *               react differently to null inputs. Typically, null is returned.
	 * @param result the expected result type
	 * @return
	 */
	Object eval(String source, TypeName result);

	// boolean isSupported(Instance obj);

	List<IType> compile(String source, Map<String, Object> options);

	IType getType(List<String> packages, String name, Predicate<IType> pred);

	default IType getType(List<String> packages, String name) {
		return getType(packages, name, null);
	}

	boolean isAssignableFrom(IType base, IType target);

	void forEachType(String packageName, Predicate<IType> pred, Consumer<IType> processor);

	void forEachUserType(String packageName, Predicate<IType> pred, Consumer<IType> processor);

	boolean deleteType(String typeName);

	default void close() {
	}
}
