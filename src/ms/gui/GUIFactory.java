package ms.gui;

import static ms.ipp.Algorithms.concatF;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.base.KeyValue;
import ms.utils.Options;

public abstract class GUIFactory<T> {
	private static final Logger logger = LogManager.getLogger();

	public static interface GUIProducer<T> {
		public static <U> GUIProducer<U> nullProducer(Consumer<Options<Object>> cons) {
			return concatF(cons, t -> (U) null)::apply;
		}

		T create(Options<Object> options);
	}

	public static interface GUIDeferredProducer<T> {
		public static <U> GUIDeferredProducer<U> noParent(
				BiFunction<Options<Object>, List<KeyValue<U, Object>>, U> cons) {
			return (p, o, c) -> cons.apply(o, c);
		}

		T create(T parent, Options<Object> options, List<KeyValue<T, Object>> children);
	}

	public static interface GUIPrefinalizer<T> {
		public static <U> GUIPrefinalizer<U> nullProcessor(Runnable proc) {
			return (t, c, p) -> proc.run();
		}

		void process(String tag, T current, T parent);
	}

	private final Context<T> context;

	private final Options<GUIProducer<T>> producers;
	private final Options<GUIDeferredProducer<T>> deferredProducers;
	private final Options<List<GUIPrefinalizer<T>>> prefinalizers;
	private final Options<Set<String>> attributes;
	private final Set<String> noBind;

	public GUIFactory() {
		context = new Context<>();

		producers = new Options<>();
		deferredProducers = new Options<>();
		prefinalizers = new Options<>();
		attributes = new Options<>();
		noBind = new HashSet<>();
	}

	public void terminate() {
	}

	public void init() {
	}

	public void setNoBind(String tag) {
		noBind.add(tag);
	}

	protected boolean noBind(String tag) {
		return noBind.contains(tag);
	}

	public void register(String tag, GUIDeferredProducer<T> producer) {
		deferredProducers.put(tag, producer);
	}

	public void register(String tag, GUIPrefinalizer<T> processor) {
		prefinalizers.getInsert(tag, ArrayList::new).add(processor);
	}

	public void register(String tag, GUIProducer<T> producer, String... attrs) {
		producers.put(tag, producer);
		// register tag as possessing all given attributes.
		for (String attr : attrs) {
			attributes.getInsert(attr, HashSet::new).add(tag);
		}
	}

	public void beginProcess(String tag, Options<String> values) {
		T parent = getContext().getNonNullParent(0);
		Options<Object> attrs = parseAttributes(parent, tag, values);
		T result = create(tag, attrs);
		getContext().last().setConstructed(result);
		logger.trace("Start processing tag '{}' with attributes {} and parent {}. Component created: {}", //
				tag, attrs, parent.getClass().getSimpleName(),
				(result == null ? null : result.getClass().getSimpleName()));
	}

	public void endProcess() {
		T parent = getContext().getNonNullParent(1);
		T current = getContext().last().getConstructed();
		String tag = getContext().last().getTag();

		T result = postCreate(tag, current, parent);
		prefinalize(tag, result, parent);
		bind(tag, result, parent);

		getContext().pop();
		logger.trace("End processing tag '{}'", tag);
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
	private T create(String tag, Options<Object> attrs) {
		logger.trace("Creating element '{}' with attributes {}", tag, attrs);

		if (deferredCreation(tag)) {
			return createInterim(tag, attrs);
		} else {
			GUIProducer<T> producer = producers.get(tag);
			if (producer != null) {
				T result = producer.create(attrs);
				doCreate(tag, result, attrs);
				return result;
			}
			throw new IllegalArgumentException("Unknown tag '" + tag + "'");
		}

	}

	private void prefinalize(String tag, T current, T parent) {
		prefinalizers.doIfExists(tag, l -> l.forEach(p -> p.process(tag, current, parent)));
	}

	private T postCreate(String tag, T incoming, T parent) {
		// hot-swap the InterimContainer with a real component
		// TODO: Extracting attrs from the interim container was relevant, when we
		// didn't have the context-stack. Should be just use the context stack here
		// instead of extracting attrs from the container?
		return deferredCreation(tag)
				? deferredProducers.get(tag).create(parent, extractAttrs(incoming), extract(incoming))
				: incoming;
	}

	private void bind(String tag, T parent, T current) {
		if (current != null && !noBind(tag)) {
			bind(parent, current);
			logger.trace("Adding component {} to parent {}", () -> current.getClass().getSimpleName(),
					() -> parent.getClass().getSimpleName());
		}
	}

	protected Context<T> getContext() {
		return context;
	}

	protected void doInit() {
	}

	protected void doCreate(String tag, T current, Options<Object> attrs) {
	}

	protected abstract void bind(T parent, T current);

	protected abstract T createInterim(String tag, Options<Object> attrs);

	protected abstract List<KeyValue<T, Object>> extract(T interim);

	protected abstract Options<Object> extractAttrs(T interim);

	protected abstract Options<Object> parseAttributes(T parent, String tag, Options<String> values);

	protected boolean deferredCreation(String tag) {
		return deferredProducers.containsKey(tag);
	}

	public boolean hasAttr(String tag, String attr) {
		return attributes.tryGet(attr, HashSet<String>::new).contains(tag);
	}
}
