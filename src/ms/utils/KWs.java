package ms.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import ms.ipp.Iterables;

public class KWs<T> extends HashMap<String, T> {

    private static final long serialVersionUID = 3482188431032730748L;

    /**
     * Performs a check on a key if it exists
     * 
     * @param key
     * @param pred
     */
    public void assertKey(String key, Meta<? extends T> pred) {
        tryDo(key, pred::test, null);
    }

    @SuppressWarnings("unchecked")
    public <U, V> void assertEquals(String key, NamedAttribute<? super V, U> attribute, U value) {
        // TODO: May be used to collect info about attributes on a given key
        tryDo(key, t -> Iterables.isEqualOrNull(attribute.apply((V) t), value), null);
    }

    /**
     * Performs a given action on an element with a given key if such element exists. Is equivalent
     * to:
     * 
     * <pre>
     * ifExistsDo(name, attrs, null, proc);
     * </pre>
     * 
     * @param name  the key of the target element, not null
     * @param proc  the action to be performed on the element with a given key, not null.
     * @param clazz the expected value type (may be null if no type check at runtime is desired)
     */
    public <U extends T> void tryDo(String name, Consumer<? super U> proc, Class<U> clazz) {
        tryDo(name, null, proc, clazz);
    }

    public <U extends T> void tryDo(String name,
                                    List<String> ignore,
                                    Consumer<? super U> proc,
                                    Class<U> clazz) {
        // TODO: Perform runtime checks
        Iterables.ifExistsDo(name, this, ignore, proc);
    }

    public <U extends T, V> V tryApply(String name,
                                       Function<U, V> func,
                                       Class<U> clazz,
                                       Supplier<V> onMiss) {
        return tryApply(name, null, func, clazz, onMiss);
    }

    public <U extends T, V> V tryApply(String name,
                                       List<String> ignore,
                                       Function<U, V> func,
                                       Class<U> clazz,
                                       Supplier<V> onMiss) {
        // TODO: Perform runtime checks
        return Iterables.ifExistsApply(name, this, ignore, func, onMiss);
    }

    public <U extends T, V> V apply(String name, Function<U, V> func, Class<U> clazz) {
        // TODO: ScanningOptions should notice that this key is mandatory
        return tryApply(name, null, func, clazz, () -> {
            throw new IllegalArgumentException("Key " + name + " of type " + clazz.getSimpleName()
                                               + " is missing");
        });
    }

    public <U extends T> void tryDoList(String name, Consumer<List<U>> proc, Class<U> clazz) {
        tryDoList(name, null, proc, clazz);
    }

    public <U extends T> void tryDoList(String name,
                                        List<String> ignore,
                                        Consumer<List<U>> proc,
                                        Class<U> clazz) {
        // TODO: Perform runtime checks
        Iterables.ifExistsDo(name, this, ignore, proc);
    }

    public <U extends T> void tryDoPair(String name, BiConsumer<U, U> proc, Class<U> clazz) {
        tryDoPair(name, null, proc, clazz);
    }

    @SuppressWarnings("unchecked")
    public <U extends T> void tryDoPair(String name,
                                        List<String> ignore,
                                        BiConsumer<U, U> proc,
                                        Class<U> clazz) {
        // TODO: Additional information is that we expect exactly two values here
        assertEquals(name, (NamedAttribute<T, Integer>) NamedAttribute.size, 2);
        tryDoList(name, ignore, d -> proc.accept(d.get(0), d.get(1)), clazz);
    }

    public <K, U> void tryDoMap(String name,
                                Consumer<Map<K, U>> proc,
                                Class<K> keyClass,
                                Class<U> valueClass) {
        tryDoMap(name, null, proc, keyClass, valueClass);
    }

    public <K, U> void tryDoMap(String name,
                                List<String> ignore,
                                Consumer<Map<K, U>> proc,
                                Class<K> keyClass,
                                Class<U> valueClass) {
        // TODO: Perform runtime checks
        Iterables.ifExistsDo(name, this, ignore, proc);
    }

    /**
     * Returns the element with a given key from a given map. If the element doesn't exist or is
     * null, it is created by means of a given Supplier and returned afterwards. In contrast to
     * {@link #getInsert(Object, Map, Supplier)}, the newly created element is NOT inserted into the
     * map.
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

    public <U> U get(String key, Class<U> clazz) {
        return clazz.cast(get(key));
    }

    public KWs<T> filterPrefix(String prefix, Collection<String> defaults) {
        KWs<T> result = new KWs<>();
        StringHelper.filterPrefix(prefix, this, result, defaults);
        return result;
    }

    /**
     * Returns the element with a given key from a given map. If the element doesn't exist or is
     * null, it is created by means of a given Supplier and inserted into the map prior to being
     * returned.
     * 
     * @param key
     * @param onNull is called when the element is null or doesn't exist, not null.
     * @return
     */
    public T getInsert(String key, Supplier<T> onNull) {
        return Iterables.getInsert(key, this, onNull);
    }

}
