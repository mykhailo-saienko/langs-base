package ms.vm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.ipp.base.KeyValue;
import ms.ipp.iterable.BiIterable;

public class VMMonitor<T extends VMObserver> implements BiIterable<String, T> {
	private static final Logger logger = LogManager.getLogger();
	private final ScheduledThreadPoolExecutor executor;
	private boolean initialised;
	private final List<Entry<String, T>> observers;

	private final Consumer<Set<Integer>> onBatchUpdate;
	private final Consumer<Integer> onDelete;

	public VMMonitor(Consumer<Integer> onDelete, Consumer<Set<Integer>> onBatchUpdate) {
		this.onDelete = onDelete;
		this.onBatchUpdate = onBatchUpdate;
		observers = new ArrayList<>();
		executor = new ScheduledThreadPoolExecutor(1, t -> new Thread(t, "Monitor"));
		initialised = false;
	}

	public void start(long rate) {
		if (initialised) {
			throw new IllegalStateException("Monitor is already runnning");
		}
		initialised = true;
		executor.scheduleAtFixedRate(this::monitor, 0, rate, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		if (!initialised) {
			throw new IllegalStateException("Monitor is not running");
		}
		initialised = false;
		try {
			executor.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		executor.shutdown();
	}

	@Override
	public Iterator<Entry<String, T>> iterator() {
		return observers.iterator();
	}

	public synchronized void addObserver(String id, T observer) {
		observers.add(new KeyValue<>(id, observer));
	}

	public Entry<String, T> getObserver(int index) {
		return observers.get(index);
	}

	public int count() {
		return observers.size();
	}

	private synchronized void monitor() {
		// System.out.println(
		// "Calling monitor on " + allObservers.size() + " observers and " + "
		// variables " + observers.keySet());
		// long start = System.currentTimeMillis();
		String key = null;
		try {
			TreeSet<Integer> removed = new TreeSet<>();
			Set<Integer> updated = new HashSet<>();
			for (int i = 0; i < count(); ++i) {
				Entry<String, T> entry = getObserver(i);
				key = entry.getKey();
				try {
					// may throw IllegalArg if the variable has been deleted
					if (entry.getValue().checkUpdate()) {
						updated.add(i);
					}
				} catch (IllegalArgumentException e) {
					logger.debug("Entry '{}' has been deleted", key);
					removed.add(i);
				}
			}
			// update before
			if (!updated.isEmpty()) {
				logger.trace("Committing updates: {}", updated);
				onBatchUpdate.accept(updated);
				updated.clear();
			}
			deleteVariables(removed);
		} catch (Exception e) {
			logger.fatal("Unexpected error while monitoring '" + key + "'", e);
		}
		// System.out.println("Update took " + (System.currentTimeMillis() -
		// start) + " millis");
	}

	private void deleteVariables(TreeSet<Integer> removed) {
		logger.trace("Committing deletes: {}", removed);

		// call in descending order so that higher indexes are not distorted
		// when smaller indexes get deleted.
		Iterator<Integer> it = removed.descendingIterator();
		while (it.hasNext()) {
			Integer r = it.next();
			logger.debug("Removing variable '{}' with index {}", getObserver(r).getKey(), r);
			onDelete.accept(r);
			observers.remove((int) r);
		}

	}
}
