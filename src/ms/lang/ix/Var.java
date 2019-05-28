package ms.lang.ix;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import ms.ipp.iterable.tree.DelegatingTree;
import ms.ipp.iterable.tree.StdMultiTree;
import ms.ipp.iterable.tree.StdTree;
import ms.lang.types.Ref;
import ms.vm.VMObserver;

// TODO: Implement Instance<T> after migrating LXClass to Type(Name).
public class Var<T> extends DelegatingTree<Object> implements Ref<T> {
	private Supplier<T> getter;
	private Consumer<T> setter;
	private final LXClass<T> type;
	private final Set<String> prims;
	private final String name;

	public static <U> VMObserver varObserver(Consumer<U> onVarChange, Var<U> source) {
		return new VMObserver(source::getValue, onVarChange);
	}

	public static <U> Var<U> createSafe(String name, Supplier<U> getter, LXClass<U> type,
			Collection<String> primitives) {
		Var<U> var = new Var<U>(name, type, getter);
		var.addPrimitives(primitives);
		return var;
	}

	public static Var<?> createUnsafe(String name, Supplier<?> getter, LXClass<?> type, Collection<String> primitives) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Var<?> var = new Var(name, type, getter);
		var.addPrimitives(primitives);
		return var;
	}

	public Var(String name, LXClass<T> type, Supplier<T> getter) {
		super(Object.class);
		this.name = name;
		this.getter = getter;
		this.type = type;
		this.setter = null;
		this.prims = new HashSet<>();

		// init infrastructure
		add(Var.class, new StdTree<>(Var.class));
		add(FuncSpec.class, new StdMultiTree<>(FuncSpec.class));
	}

	public LXClass<T> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean canSet(LXClass<?> type) {
		return this.type.getType().isAssignableFrom(type.getType());
	}

	public boolean isConvertible(LXClass<?> type) {
		return type.getType().isAssignableFrom(this.type.getType());
	}

	public Supplier<T> getGetter() {
		return getter;
	}

	@Override
	public T getValue() {
		if (getter == null) {
			throw new IllegalArgumentException("The variable has no getter");
		}
		return getter.get();
	}

	@SuppressWarnings("unchecked")
	public T set(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot set variable to null");
		}
		if (setter == null) {
			throw new IllegalArgumentException("This variable is constant");
		}
		if (!canSet(LXClass.getType(value))) {
			throw new IllegalArgumentException("Value '" + value + "' is not convertible to " + getType());
		}
		setter.accept((T) value);
		return getValue();
	}

	public Collection<String> getPrimitives() {
		return prims;
	}

	@Override
	public String toString() {
		return getName();
	}

	protected void setSetter(Consumer<T> setter) {
		this.setter = setter;
	}

	protected void setGetter(Supplier<T> getter) {
		this.getter = getter;
	}

	private void addPrimitives(Collection<String> prims) {
		prims.stream().filter(s -> s != null).forEach(this.prims::add);
	}
}