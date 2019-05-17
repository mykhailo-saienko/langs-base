package ms.lang.ix;

import static java.util.Arrays.asList;
import static ms.ipp.Iterables.appendList;
import static ms.lang.ix.Var.createUnsafe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import ms.ipp.base.QuadFunction;
import ms.ipp.base.TriFunction;

/**
 * Function specification
 * 
 * @author mykhailo.saienko
 *
 */
public class FuncSpec {
	private final List<LXClass<?>> args;
	private final LXClass<?> returnType;
	private final Function<List<Var>, ?> body;

	private final String name;

	// Parameterless functions are vars and basically treated as named constant
	// primitives
	public static <T> Var noParamSpec(String name, Supplier<T> func, LXClass<T> retType) {
		return Var.createUnsafe(name, func, retType, Arrays.asList(name));
	}

	/**
	 * Creates an anonymous block which executes all given vars and returns the
	 * evaluation result of the last statement.
	 * 
	 * @param statements
	 * @param retType
	 * @return
	 */
	public static <T> Var anonymousBlock(List<Var> statements, LXClass<T> retType) {
		if (statements.isEmpty()) {
			throw new IllegalArgumentException("There must be at least one statement in a block");
		}
		Var last = statements.get(statements.size() - 1);
		if (retType != null && !last.isConvertible(retType)) {
			throw new IllegalArgumentException(
					"Statement '" + last.getName() + "' is not convertible to type " + retType);
		}
		if (statements.size() == 1) {
			return last;
		}

		Supplier<T> body = () -> {
			if (statements.isEmpty()) {
				return null;
			}
			// execute all but last
			for (int i = 0; i < statements.size() - 1; ++i) {
				statements.get(i).eval(LXClass.STRING);
			}
			return last.eval(retType);
		};

		Collection<String> prims = collectPrimitives(null, statements);
		return Var.createUnsafe(null, body, retType, prims);
	}

	public <T> FuncSpec(String name, Function<List<Var>, T> func, List<LXClass<?>> argTypes, LXClass<T> returnType) {
		this.name = name;
		this.args = new ArrayList<>(argTypes);
		this.returnType = returnType;
		this.body = func;
	}

	public List<LXClass<?>> getArgs() {
		return args;
	}

	public LXClass<?> getReturnType() {
		return returnType;
	}

	public <T> FuncSpec(String name, Supplier<T> func, LXClass<T> returnType) {
		this(name, v -> func.get(), new ArrayList<>(), returnType);
	}

	public <U, T> FuncSpec(String name, Function<U, T> func, LXClass<U> argType, LXClass<T> returnType) {
		this(name, v -> func.apply(v.get(0).eval(argType)), asList(argType), returnType);
	}

	public <U, V, T> FuncSpec(String name, BiFunction<U, V, T> func, LXClass<U> argType1, LXClass<V> argType2,
			LXClass<T> returnType) {
		this(name, v -> func.apply(v.get(0).eval(argType1), v.get(1).eval(argType2)), asList(argType1, argType2),
				returnType);
	}

	public <U, V, W, T> FuncSpec(String name, TriFunction<U, V, W, T> func, LXClass<U> argType1, LXClass<V> argType2,
			LXClass<W> argType3, LXClass<T> returnType) {
		this(name, v -> func.apply(v.get(0).eval(argType1), v.get(1).eval(argType2), v.get(2).eval(argType3)),
				asList(argType1, argType2, argType3), returnType);
	}

	public <U, V, W, X, T> FuncSpec(String name, QuadFunction<U, V, W, X, T> func, LXClass<U> argType1,
			LXClass<V> argType2, LXClass<W> argType3, LXClass<X> argType4, LXClass<T> returnType) {
		this(name, v -> func.apply(v.get(0).eval(argType1), v.get(1).eval(argType2), v.get(2).eval(argType3),
				v.get(3).eval(argType4)), asList(argType1, argType2, argType3, argType4), returnType);
	}

	public String getName() {
		return name;
	}

	/**
	 * Returns a parameterless BaseVar whose eval method just applies the function
	 * to a given list of parameters.
	 * 
	 * @param params
	 * @return
	 */
	public Var plugIn(List<Var> params) {
		if (!isCallable(params)) {
			return null;
		}

		Supplier<?> getter = () -> body.apply(params);
		Collection<String> prims = collectPrimitives(getName(), params);
		String fullName = appendList(params, "(", ")", ",", p -> p.getName());
		return createUnsafe(fullName, getter, returnType, prims);
	}

	private boolean isCallable(List<Var> params) {
		if (params.size() != args.size()) {
			return false;
		}
		for (int i = 0; i < params.size(); ++i) {
			Var param = params.get(i);
			boolean convertible = param.isConvertible(args.get(i));
			if (!convertible) {
				return false;
			}
		}
		return true;
	}

	private static Set<String> collectPrimitives(String name, List<Var> params) {
		Set<String> prims = new HashSet<>();
		if (name != null) {
			prims.add(name);
		}
		params.forEach(v -> prims.addAll(v.getPrimitives()));
		return prims;
	}

	@Override
	public String toString() {
		return name;
	}
}
