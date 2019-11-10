package ms.utils;

import java.util.List;
import java.util.function.Function;

public class NamedAttribute<T, V> implements Function<T, V> {
	public static NamedAttribute<List<?>, Integer> size = new NamedAttribute<>("size", List::size);

	private final String id;
	private final Function<T, V> func;

	public NamedAttribute(String id, Function<T, V> func) {
		this.id = id;
		this.func = func;
	}

	@Override
	public V apply(T t) {
		return func.apply(t);
	}

	public String getId() {
		return id;
	}

}
