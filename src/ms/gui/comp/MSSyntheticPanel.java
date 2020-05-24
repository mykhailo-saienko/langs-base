package ms.gui.comp;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MSSyntheticPanel<T> extends StatePanel<T> {
	private static final long serialVersionUID = 8939910859318397363L;
	private Supplier<T> supplier;
	private Consumer<T> provider;

	public void setSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	/**
	 * Sets optional provider to initialize the panel with an incoming value.
	 * 
	 * @param provider
	 */
	public void setProvider(Consumer<T> provider) {
		this.provider = provider;
	}

	@Override
	public T getState() {
		if (supplier == null) {
			throw new IllegalArgumentException(
					MSSyntheticPanel.class.getSimpleName() + "'s state supplier is not set.");
		}
		return supplier.get();
	}

	@Override
	public void setState(T in) {
		super.setState(in);
		if (provider != null) {
			provider.accept(in);
		}
	}
}
