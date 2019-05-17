package ms.trading;

/**
 * @author saienko
 * 
 */
public enum OrderSide {
	BUY("B"), SELL("S");

	private String code;

	private OrderSide(OrderSide side) {
		this(new String(side.code));
	}

	private OrderSide(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public OrderSide opposite() {
		return this == BUY ? SELL : BUY;
	}
}
