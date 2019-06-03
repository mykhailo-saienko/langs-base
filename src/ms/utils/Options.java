package ms.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import ms.ipp.Iterables;

public class Options<T> extends HashMap<String, T> {
	private static final long serialVersionUID = 3482188431032730748L;

	/**
	 * Performs a given action on an element with a given key if such element
	 * exists. Is equivalent to:
	 * 
	 * <pre>
	 * ifExistsDo(name, attrs, null, proc);
	 * </pre>
	 * 
	 * @param name the key of the target element, not null
	 * @param proc the action to be performed on the element with a given key, not
	 *             null.
	 */
	public <U extends T> void doIfExists(String name, Consumer<U> proc) {
		Iterables.ifExistsDo(name, this, proc);
	}

	/**
	 * Returns the element with a given key from a given map. If the element doesn't
	 * exist or is null, it is created by means of a given Supplier and returned
	 * afterwards. In contrast to {@link #getInsert(Object, Map, Supplier)}, the
	 * newly created element is NOT inserted into the map.
	 * 
	 * @param key
	 * @param def is called when the element is null or doesn't exist, not null.
	 * @return
	 */
	public <U> U tryGet(String key, Supplier<U> def) {
		return Iterables.get(key, this, def);
	}

	public <U> U tryGet(String key, U def) {
		return Iterables.get(key, this, def);
	}

	/**
	 * Returns the element with a given key from a given map. If the element doesn't
	 * exist or is null, it is created by means of a given Supplier and inserted
	 * into the map prior to being returned.
	 * 
	 * @param key
	 * @param onNull is called when the element is null or doesn't exist, not null.
	 * @return
	 */
	public T getInsert(String key, Supplier<T> onNull) {
		return Iterables.getInsert(key, this, onNull);
	}

}
