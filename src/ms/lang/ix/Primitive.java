package ms.lang.ix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Primitive<T> extends Var<T> {
	private T value;

	public Primitive(String name, T val, boolean isConst) {
		super(name, LXClass.getType(val), null);
		setValue(val); // don't use Var::set because Primitive may be const
		setGetter(this::getValue);
		if (!isConst) {
			setSetter(this::setValue);
		}
	}

	@Override
	public Collection<String> getPrimitives() {
		return getName() == null ? new ArrayList<>() : Arrays.asList(getName());
	}

	@Override
	public T getValue() {
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
		return getType() + " " + super.toString() + "=" + value;
	}
}