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
import ms.utils.KWs;

public abstract class StdFactory<T> implements TFactory {
	private static final Logger logger = LogManager.getLogger();

	public static interface Producer<T> {
		public static <U> Producer<U> nullProducer(Consumer<KWs<Object>> cons) {
			return concatF(cons, t -> (U) null)::apply;
		}

		T create(KWs<Object> options);
	}

	public static interface DeferredProducer<T> {
		public static <U> DeferredProducer<U> noParent(BiFunction<KWs<Object>, List<KeyValue<U, Object>>, U> cons) {
			return (p, o, c) -> cons.apply(o, c);
		}

		T create(T parent, KWs<Object> options, List<KeyValue<T, Object>> children);
	}

	public static interface Prefinalizer<T> {
		public static <U> Prefinalizer<U> nullProcessor(Runnable proc) {
			return (t, c, p) -> proc.run();
		}

		void process(String tag, T current, T parent);
	}

	private final Context<T> context;

	private final KWs<Producer<T>> producers;
	private final KWs<DeferredProducer<T>> deferredProducers;
	private final KWs<List<Prefinalizer<T>>> prefinalizers;
	private final KWs<Set<String>> attributes;
	private final Set<String> noBind;

	public StdFactory() {
		context = new Context<>();

		producers = new KWs<>();
		deferredProducers = new KWs<>();
		prefinalizers = new KWs<>();
		attributes = new KWs<>();
		noBind = new HashSet<>();
	}

	@Override
	public void terminate() {
	}

	@Override
	public void init() {
	}

	@Override
	public void parseTag(String tag, KWs<String> values) {
		T parent = getContext().getNonNullParent(0);

		KWs<Object> attrs = parseAttributes(parent, tag, values);
		getContext().push(tag, attrs);

		T result = create(tag, attrs);
		getContext().last().setConstructed(result);

		logger.trace("Start processing tag '{}' with attributes {} and parent {}. Component created: {}", //
				tag, attrs, parent.getClass().getSimpleName(),
				(result == null ? null : result.getClass().getSimpleName()));
	}

	@Override
	public void endParseTag() {
		T parent = getContext().getNonNullParent(1);
		String tag = getContext().last().getTag();

		T result = postCreate(tag, parent);
		prefinalize(tag, result, parent);
		bind(tag, result, parent);

		getContext().pop();
		logger.trace("End processing tag '{}'", tag);
	}

	public void setNoBind(String tag) {
		noBind.add(tag);
	}

	public void register(String tag, DeferredProducer<T> producer) {
		deferredProducers.put(tag, producer);
	}

	public void registerPP(String tag, Prefinalizer<T> processor) {
		prefinalizers.getInsert(tag, ArrayList::new).add(processor);
	}

	public void register(String tag, Producer<T> producer, String... attrs) {
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
	private T create(String tag, KWs<Object> attrs) {
		logger.trace("Creating element '{}' with attributes {}", tag, attrs);

		if (deferredCreation(tag)) {
			return createInterim(tag, attrs);
		} else {
			Producer<T> producer = producers.get(tag);
			if (producer != null) {
				T result = producer.create(attrs);
				doCreate(tag, result, attrs);
				return result;
			}
			throw new IllegalArgumentException("Unknown tag '" + tag + "'");
		}

	}

	protected Context<T> getContext() {
		return context;
	}

	protected void doInit() {
	}

	protected void doCreate(String tag, T current, KWs<Object> attrs) {
	}

	protected abstract void bind(T parent, T current);

	protected abstract T createInterim(String tag, KWs<Object> attrs);

	protected abstract KWs<Object> parseAttributes(T parent, String tag, KWs<String> values);

	protected boolean hasAttr(String tag, String attr) {
		return attributes.tryGet(attr, HashSet<String>::new).contains(tag);
	}

	private void prefinalize(String tag, T current, T parent) {
		prefinalizers.tryDo(tag, l -> l.forEach(p -> p.process(tag, current, parent)), null);
	}

	private T postCreate(String tag, T parent) {
		T current = getContext().last().getConstructed();
		if (deferredCreation(tag)) {
			// hot-swap the InterimContainer with a real component
			KWs<Object> attrs = getContext().last().getAttrs();
			@SuppressWarnings("unchecked")
			Deferred<T> deferred = (Deferred<T>) current;
			return deferredProducers.get(tag).create(parent, attrs, deferred.getContent());
		} else {
			return current;
		}
	}

	private boolean noBind(String tag) {
		return noBind.contains(tag);
	}

	private void bind(String tag, T parent, T current) {
		if (current != null && !noBind(tag)) {
			bind(parent, current);
			logger.trace("Adding component {} to parent {}", current.getClass().getSimpleName(),
					parent.getClass().getSimpleName());
		}
	}

	private boolean deferredCreation(String tag) {
		return deferredProducers.containsKey(tag);
	}

}
