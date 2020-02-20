package ms.gui.comp;

import java.util.function.Supplier;

public class MSSyntheticPanel<T> extends MSPanel<T> {
	private static final long serialVersionUID = 8939910859318397363L;
	private Supplier<T> supplier;

	public void setSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	@Override
	public T getState() {
		return supplier.get();
	}

}
