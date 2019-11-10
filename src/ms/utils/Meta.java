package ms.utils;

import java.util.Collection;
import java.util.function.Predicate;

import ms.ipp.Iterables;

public class Meta<T> implements Predicate<T> {
	private final String id;
	private final Predicate<T> pred;
	private final String messageOnFail;
	private final KWs<Object> metaKWs;

	public static final String SIZE = "size";
	public static final String COLLECTION = "collection";
	public static final String CLASS = "class";

	@SafeVarargs
	private static <U> boolean isContainedIn(U value, U... values) {
		for (U val : values) {
			if (Iterables.isEqualOrNull(value, val)) {
				return true;
			}
		}
		return false;
	}

	public static <T> Meta<Collection<T>> isSize(int size) {
		Meta<Collection<T>> meta = new Meta<>(SIZE + size, t -> t.size() == size);
		meta.add(SIZE, size);
		meta.add(SIZE + "." + size, true);
		return meta;
	}

	@SafeVarargs
	public static <T> Meta<T> isIn(String name, T... values) {
		Meta<T> meta = new Meta<>("in." + name, t -> isContainedIn(t, values));
		meta.add(COLLECTION, name);
		meta.add("in." + name, true);
		return meta;
	}

	public Meta(String id, Predicate<T> pred, String messageOnFail) {
		this.id = id;
		this.pred = pred;
		this.messageOnFail = messageOnFail;
		metaKWs = new KWs<>();
	}

	public Meta(String id, Predicate<T> pred) {
		this(id, pred, null);
	}

	@Override
	public boolean test(T t) {
		return pred.test(t);
	}

	public void assertTrue(T t) {
		if (!test(t)) {
			throw new IllegalArgumentException(
					"Required meta " + id + " failed on " + t + (messageOnFail != null ? ": " + messageOnFail : ""));
		}
	}

	public KWs<Object> getMetaKWs() {
		// These KWs will be added to the compile-time metas known about a target
		// parameter
		return metaKWs;
	}

	public void add(String metaKW, Object value) {
		metaKWs.put(metaKW, value);
	}
}