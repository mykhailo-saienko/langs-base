package ms.gui;

import static ms.ipp.Algorithms.concatF;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.utils.Options;

public class GUIFactory<T> {
	private static final Logger logger = LogManager.getLogger();

	public static interface GUIProducer<T> {
		public static <U, V> GUIProducer<U> nullProducer(Consumer<Options<Object>> cons) {
			return concatF(cons, t -> (U) null)::apply;
		}

		T create(Options<Object> options);
	}

	private final Options<GUIProducer<T>> producers;
	private final Options<Set<String>> attributes;

	public GUIFactory() {
		producers = new Options<>();
		attributes = new Options<>();
	}

	public void register(String tag, GUIProducer<T> producer, String... attrs) {
		producers.put(tag, producer);
		// register tag as possessing all given attributes.
		for (String attr : attrs) {
			attributes.getInsert(attr, HashSet::new).add(tag);
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
	public T create(String tag, Options<Object> attrs, boolean errorOnUnknown) {
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
		return attributes.tryGet(attr, HashSet<String>::new).contains(tag);
	}
}
