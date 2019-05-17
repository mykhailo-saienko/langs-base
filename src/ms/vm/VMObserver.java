package ms.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VMObserver {

	private Object curValue;
	private final List<Consumer<Object>> onEvent;
	private final Supplier<?> observable;

	public VMObserver(Supplier<?> observable) {
		this.observable = observable;
		curValue = null;
		this.onEvent = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public <T> VMObserver(Supplier<T> observable, Consumer<T> onEvent) {
		this(observable);

		// We know that values come only from observable and are of type T
		addOnEvent(t -> onEvent.accept((T) t));
	}

	public final void addOnEvent(Consumer<Object> onEvent) {
		this.onEvent.add(onEvent);
	}

	protected boolean isObservable(Object value) {
		return value instanceof VMObservable;
	}

	protected VMObservable cast(Object value) {
		return (VMObservable) value;
	}

	protected boolean hasChanged(Object newValue) {
		return getCurValue() != newValue;
	}

	protected final boolean hasObservableChanged(Object value) {
		if (value == null) {
			return false;
		}
		if (isObservable(value)) {
			VMObservable cast = cast(value);
			boolean result = cast.hasChanged();
			if (result) {
				cast.reset();
			}
			return result;
		} else {
			return false;
		}
	}

	protected final void setCurValue(Object value) {
		curValue = value;
	}

	public final Object getCurValue() {
		return curValue;
	}

	public boolean checkUpdate() {
		Object value = observable.get();
		if (hasChanged(value) || hasObservableChanged(value)) {
			curValue = value;
			for (Consumer<Object> onEvent : this.onEvent) {
				onEvent.accept(value);
			}
			return true;
		}
		return false;
	}
}
