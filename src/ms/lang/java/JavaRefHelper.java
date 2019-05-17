package ms.lang.java;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isProtected;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.asList;

import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.lang.types.BaseMethod;
import ms.lang.types.Mod;
import ms.lang.types.TypeName;
import ms.lang.types.Variable;

public class JavaRefHelper {
	private static final Logger logger = LogManager.getLogger();

	static <T> void forEachMember(Class<?> clazz, Function<Class<?>, T[]> prod, Predicate<T> pred,
			Consumer<T> processor, boolean returnOnFirstHit) {
		ClassIterator it = new ClassIterator(clazz);
		outer: while (it.hasNext()) {
			Class<?> c = it.next();
			T[] array = prod.apply(c);
			logger.trace("Scanning class {}: obtained elements are {}", () -> c.getCanonicalName(),
					() -> asList(array));
			for (T f : array) {
				if (pred == null || pred.test(f)) {
					processor.accept(f);
					if (returnOnFirstHit) {
						break outer;
					}
				}
			}
		}
	}

	static <T> T look(Class<?> clazz, Function<Class<?>, T[]> prod, Predicate<T> pred) {
		List<T> results = new ArrayList<>();
		forEachMember(clazz, prod, pred, f -> results.add(f), true);
		return results == null || results.isEmpty() ? null : results.get(0);
	}

	public static Mod modifierFromJava(int modifiers) {
		if (isPublic(modifiers)) {
			return Mod.PUBLIC;
		} else if (isProtected(modifiers)) {
			return Mod.PROTECTED;
		} else if (isPrivate(modifiers)) {
			return Mod.PRIVATE;
		}
		return null;
	}

	public static BaseMethod methodFromJava(String name, int modifiers, Parameter[] params,
			java.lang.reflect.Type[] genericTypes, Class<?> retType) {
		Mod mod = modifierFromJava(modifiers);

		boolean isStatic = isStatic(modifiers);
		TypeName returnType = new TypeName(retType.getSimpleName(), retType.getCanonicalName(), false);

		BaseMethod m2 = new BaseMethod(mod, isStatic, returnType, name, "");
		if (Modifier.isAbstract(modifiers)) {
			m2.setAbstract();
		}
		List<Variable> result = new ArrayList<>();
		for (int i = 0; i < params.length; ++i) {
			Parameter p = params[i];
			java.lang.reflect.Type t = genericTypes[i];
			result.add(new Variable(JavaRef.fromReflectType(t), p.getName(), null));
		}
		m2.setArguments(result);
		return m2;
	}

}
