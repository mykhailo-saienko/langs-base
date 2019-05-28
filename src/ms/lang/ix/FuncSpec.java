package ms.lang.ix;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	private final Function<List<Var<?>>, ?> body;

	private final String name;

	// Parameterless functions are vars and basically treated as named constant
	// primitives
	public static <U> Var<U> fromFunc(String name, Supplier<U> getter, LXClass<U> retType) {
		return Var.createSafe(name, getter, retType, Arrays.asList(name));
	}

	public static <U, V> FuncSpec fromFunc(String name, Function<U, V> func, LXClass<U> argType, LXClass<V> retType) {
		// convert prior to applying in the lambda (so that the conversion will not be
		// executed upon each lambda call)
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Function<Object, V> coerced = (Function) func;
		return new FuncSpec(name, v -> coerced.apply(v.get(0).getValue()), Arrays.asList(argType), retType);
	}

	public static <U, V, W> FuncSpec fromFunc(String name, BiFunction<U, V, W> func, LXClass<U> argType1,
			LXClass<V> argType2, LXClass<W> returnType) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		BiFunction<Object, Object, W> coerced = (BiFunction) func;
		return new FuncSpec(name, v -> coerced.apply(v.get(0).getValue(), v.get(1).getValue()),
				asList(argType1, argType2), returnType);
	}

	public static <U, V, W, X> FuncSpec fromFunc(String name, TriFunction<U, V, W, X> func, LXClass<U> argType1,
			LXClass<V> argType2, LXClass<W> argType3, LXClass<X> returnType) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		TriFunction<Object, Object, Object, X> coerced = (TriFunction) func;
		return new FuncSpec(name, v -> coerced.apply(v.get(0).getValue(), v.get(1).getValue(), v.get(2).getValue()),
				asList(argType1, argType2, argType3), returnType);
	}

	public static <U, V, W, X, Y> FuncSpec fromFunc(String name, QuadFunction<U, V, W, X, Y> func, LXClass<U> argType1,
			LXClass<V> argType2, LXClass<W> argType3, LXClass<X> argType4, LXClass<Y> returnType) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		QuadFunction<Object, Object, Object, Object, Y> coerced = (QuadFunction) func;
		return new FuncSpec(name,
				v -> coerced.apply(v.get(0).getValue(), v.get(1).getValue(), v.get(2).getValue(), v.get(3).getValue()),
				asList(argType1, argType2, argType3, argType4), returnType);
	}

	public <T> FuncSpec(String name, Function<List<Var<?>>, T> func, List<LXClass<?>> argTypes, LXClass<T> returnType) {
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

	public Function<List<Var<?>>, ?> getBody() {
		return body;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
