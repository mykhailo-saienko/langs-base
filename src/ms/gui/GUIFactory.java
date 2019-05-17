package ms.gui;

import static ms.ipp.Algorithms.concatF;
import static ms.ipp.Iterables.getInsert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.Iterables;

public class GUIFactory<T> {
	private static final Logger logger = LogManager.getLogger();

	public static interface GUIProducer<T> {
		public static <U> GUIProducer<U> nullProducer(Consumer<Map<String, Object>> cons) {
			return concatF(cons, t -> (U) null)::apply;
		}

		T create(Map<String, Object> options);
	}

	private final Map<String, GUIProducer<T>> producers;
	private final Map<String, Set<String>> attributes;

	public GUIFactory() {
		producers = new HashMap<>();
		attributes = new HashMap<>();
	}

	public void register(String tag, GUIProducer<T> producer, String... attrs) {
		producers.put(tag, producer);
		// register tag as possessing all given attributes.
		for (String attr : attrs) {
			getInsert(attr, attributes, HashSet::new).add(tag);
		}
	}

	/**
	 * Creates a component with a given tag.
	 * 
	 * @param tag
	 * @param errorOnUnknown if true, raises {@link IllegalArgumentException} if the
	 *                       tag is unknown. Returns null otherwise.
	 * @param attrs
	 * @return A generated component (may be null).
	 */
	public T create(String tag, Map<String, Object> attrs, boolean errorOnUnknown) {
		logger.trace("Creating element '{}' with attributes {}", tag, attrs);
		GUIProducer<T> producer = producers.get(tag);
		if (producer != null) {
			return producer.create(attrs);
		} else if (errorOnUnknown) {
			throw new IllegalArgumentException("Unknown tag '" + tag + "'");
		}
		return null;
	}

	public boolean contains(String tag) {
		return producers.containsKey(tag);
	}

	public boolean hasAttr(String tag, String attr) {
		return Iterables.get(attr, attributes, HashSet::new).contains(tag);
	}
}
