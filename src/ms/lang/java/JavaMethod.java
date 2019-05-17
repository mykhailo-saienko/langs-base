package ms.lang.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ms.lang.types.BaseMethod;

public class JavaMethod extends BaseMethod {

	private final Method compiledMethod;
	private Object self;

	public JavaMethod(JavaMethod source) {
		this(source, source.getCompiledMethod());
	}

	public JavaMethod(BaseMethod source, Method compiledMethod) {
		super(source);
		if (isAuxConstructor()) {
			throw new IllegalArgumentException(
					"Cannot initialise a method from a constructor '" + source.getName() + "'");
		}
		if (compiledMethod == null) {
			throw new IllegalArgumentException(
					"Cannot initialise method '" + source.getName() + "': compiled method must not be null");
		}
		compiledMethod.setAccessible(true);
		this.compiledMethod = compiledMethod;
		this.self = null;
	}

	@Override
	public JavaMethod setThis(Object self) {
		if (isStatic()) {
			throw new IllegalArgumentException("Cannot set this to " + self + " for a static method " + compiledMethod);
		}
		this.self = self;
		return this;
	}

	public Method getCompiledMethod() {
		return compiledMethod;
	}

	@Override
	public Object invoke(Object... parameters) {
		try {
			logParams(compiledMethod, self, parameters);
			Object result = compiledMethod.invoke(self, parameters);
			logResult(result);
			return result;
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("IllegalAccessException should never be raised", e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}
}
