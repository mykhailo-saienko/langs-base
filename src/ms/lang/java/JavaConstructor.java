package ms.lang.java;

import static java.util.Arrays.asList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import ms.ipp.Iterables;
import ms.ipp.base.KeyValue;
import ms.lang.types.BaseConstructor;
import ms.lang.types.BaseMethod;

public class JavaConstructor extends BaseConstructor {
	private final Constructor<?> compiledConstructor;

	public JavaConstructor(JavaConstructor source) {
		this(source, source.getCompiledConstructor());
	}

	public JavaConstructor(BaseMethod source, Constructor<?> compiledConstructor) {
		super(source);
		if (!isAuxConstructor()) {
			throw new IllegalArgumentException(
					"Cannot initialise a method from a normal method '" + source.getName() + "'");
		}
		if (compiledConstructor == null) {
			throw new IllegalArgumentException(
					"Cannot initialise constructor '" + source.getName() + "': compiled constructor must not be null");
		}
		compiledConstructor.setAccessible(true);
		this.compiledConstructor = compiledConstructor;
	}

	public Constructor<?> getCompiledConstructor() {
		return compiledConstructor;
	}

	@Override
	public Object invoke(Object... params) {
		try {
			List<Object> parms = asList(params);
			List<KeyValue<Object, Class<?>>> kvps = Iterables.map(parms,
					p -> p == null ? null : new KeyValue<>(p, p.getClass()));
			System.out.println("Invoking " + compiledConstructor + " with params " + kvps);

			Object result = compiledConstructor.newInstance(params);
			System.out.println("Result is " + result + " of type=" + (result == null ? null : result.getClass()));
			return result;
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("IllegalAccessException should never be raised", e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Type '" + getName() + "' is abstract");
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

}
