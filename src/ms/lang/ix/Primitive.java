package ms.lang.ix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Primitive<T> extends Var {
	private T value;
	private final LXClass<T> type;

	public static Var create(String name, Object value, boolean isConst) {
		Primitive<?> prim = new Primitive<>(name, value, isConst);
		Var.process(prim, LXClass.getType(value));
		return prim;
	}

	private Primitive(String name, T val, boolean isConst) {
		super(name);
		setValue(val); // don't use Var::set because Primitive may be const

		type = LXClass.getType(val);
		addGetter(type, this::getValue);
		if (!isConst) {
			addSetter(type, this::setValue);
		}
	}

	@Override
	public Collection<String> getPrimitives() {
		return getName() == null ? new ArrayList<>() : Arrays.asList(getName());
	}

	public LXClass<T> getType() {
		return type;
	}

	protected T getValue() {
		return value;
	}

	protected void setValue(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot set primitive '" + getName() + "' to null");
		}

		this.value = value;
	}

	@Override
	public String toString() {
		return type + " " + super.toString() + "=" + value;
	}
}