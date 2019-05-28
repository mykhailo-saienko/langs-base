package ms.lang.java;

import static ms.ipp.Algorithms.error;
import static ms.ipp.Iterables.first;
import static ms.ipp.Iterables.toIterable;
import static ms.ipp.iterator.NestedIterator.array;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import ms.ipp.iterable.tree.DelegatingTree;
import ms.ipp.iterable.tree.SyntheticTree;
import ms.ipp.iterable.tree.path.StdPathManipulator;
import ms.lang.types.Instance;
import ms.lang.types.TypeName;

public class JavaRef extends DelegatingTree<Instance<Object>> implements Instance<Object> {

	private final TypeName type;
	private final Supplier<Object> getter;

	// Java Class Ref
	public JavaRef(Class<?> staticClass) {
		super((Class) Instance.class);
		this.getter = () -> staticClass;
		this.type = fromClass(Class.class);
		create(null, staticClass, true);
	}

	/// Java Instance Ref
	public JavaRef(Object instance, Type type) {
		super((Class) Instance.class);
		this.getter = () -> instance;
		this.type = fromReflectType(type);
		create(instance, fromType(type), false);
	}

	@Override
	public TypeName getTypeName() {
		return type;
	}

	@Override
	public Object getValue() {
		return getter.get();
	}

	private void create(Object instObj, Class<?> instClass, boolean onlyStatic) {
		Iterable<Field> fit = toIterable(() -> array(new ClassIterator(instClass), Class::getDeclaredFields));
		Function<String, Field> retriever = s -> first(fit, f -> f.getName().equals(s));
		Function<Field, String> idRetriever = Field::getName;
		Function<Field, Instance<Object>> converter = f -> new JavaRef(getValue(instObj, f), f.getGenericType());
		BiConsumer<Field, Instance<Object>> setter = (f, o) -> setValue(instObj, f, o.getValue());

		Iterable<Class<?>> fit1 = toIterable(() -> array(new ClassIterator(instClass), Class::getDeclaredClasses));
		Function<String, Class<?>> retriever1 = s -> first(fit1, c -> c.getName().equals(s));
		Function<Class<?>, String> idRetriever1 = c -> c.getSimpleName();
		Function<Class<?>, Instance<Object>> converter1 = JavaRef::new;
		BiConsumer<Class<?>, Instance<Object>> setter1 = (c,
				o) -> error("Cannot set nested member class " + c.getCanonicalName());

		SyntheticTree<Instance<Object>> memberEntity = new SyntheticTree<>(converter, setter, idRetriever, retriever,
				fit, (Class) Instance.class);
		memberEntity.setDeleter(s -> error(
				"Cannot delete member '" + s + "' in instance of type '" + instClass.getCanonicalName() + "'"));

		SyntheticTree<Instance<Object>> typeEntity = new SyntheticTree<>(converter1, setter1, idRetriever1, retriever1,
				fit1, (Class) Instance.class);
		typeEntity.setDeleter(
				s -> error("Cannot delete nested class '" + s + "' in '" + instClass.getCanonicalName() + "'"));

		add(memberEntity);
		add(typeEntity);
	}

	private static void checkField(Object parent, Field field) {
		if (field == null) {
			throw new IllegalArgumentException("Cannot use member object with null-field");
		}
		if (parent == null && !Modifier.isStatic(field.getModifiers())) {
			throw new IllegalArgumentException(
					"Cannot use non-static member object '" + field.getName() + "' if the parent is null");
		}
	}

	private static Object getValue(Object parent, Field field) {
		checkField(parent, field);
		try {
			return field.get(parent);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException(
					"System-design-fault: illegal argument|access exception is inacceptable");
		}
	}

	private static void setValue(Object parent, Field field, Object value) {
		checkField(parent, field);
		try {
			field.set(parent, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalArgumentException(
					"System-design-fault: illegal argument|access exception is inacceptable");
		}
	}

	static TypeName fromClass(Class<?> jClass) {
		// We cannot say at run-time if a java type was generic or not.
		return jClass == null ? null : new TypeName(jClass.getSimpleName(), jClass.getCanonicalName(), false);
	}

	static TypeName fromReflectType(java.lang.reflect.Type type) {
		boolean generic = type instanceof ParameterizedType;
		if (generic) {
			ParameterizedType pt = (ParameterizedType) type;
			String inName = pt.getRawType().getTypeName();
			TypeName result = new TypeName(StdPathManipulator.toSimpleName(inName), inName, true);
			for (java.lang.reflect.Type inType : pt.getActualTypeArguments()) {
				result.addTemplateArg(fromReflectType(inType));
			}
			return result;
		} else {
			return new TypeName(StdPathManipulator.toSimpleName(type.getTypeName()), type.getTypeName(), false);
		}
	}

	static Class<?> fromType(Type type) {
		if (type == null) {
			return null;
		} else if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) type).getRawType();
		}
		throw new IllegalArgumentException("Type " + type + " is of unknown class " + type.getClass());
	}

}
