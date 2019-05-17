package ms.lang.ix;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static ms.lang.ix.LXClass.getType;
import static ms.utils.NumberHelper.larger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import ms.ipp.iterable.tree.DelegatingTree;
import ms.ipp.iterable.tree.StdMultiTree;
import ms.ipp.iterable.tree.StdTree;
import ms.utils.DateHelper;
import ms.utils.NumberHelper;
import ms.vm.VMObserver;

public class Var extends DelegatingTree<Object> {
	private final Map<LXClass<?>, Supplier<?>> getters;
	private final Map<LXClass<?>, Consumer<?>> setters;
	private final Set<String> prims;
	private final String name;

	public static <T> VMObserver varObserver(Consumer<T> onVarChange, Var source, LXClass<T> type) {
		if (!source.isConvertible(type)) {
			throw new IllegalArgumentException(
					"Expression '" + source.getName() + " is not convertible to type " + type);
		}
		return new VMObserver(() -> source.eval(type), onVarChange);
	}

	static Var createUnsafe(String name, Supplier<?> getter, LXClass<?> type, Collection<String> primitives) {
		Var var = new Var(name);
		var.addGetterUnsafe(type, getter);
		var.addPrimitives(primitives);
		process(var, type);
		return var;
	}

	protected static String format(BigDecimal value) {
		if (larger(value.abs(), 10000)) {
			return NumberHelper.format(value.setScale(0, ROUND_HALF_UP));
		}
		return NumberHelper.format(value);
	}

	protected static <T, U> U convert(Var var, LXClass<T> type, Function<T, U> converter) {
		T value = var.eval(type);
		return value == null ? null : converter.apply(value);
	}

	static void process(Var v, LXClass<?> type) {
		LXClass<BigDecimal> nType = LXClass.NUM;
		LXClass<Long> lType = LXClass.LONG;
		LXClass<Integer> iType = LXClass.INT;
		LXClass<Date> dType = LXClass.DATE;
		LXClass<String> sType = LXClass.STRING;

		if (type.equals(iType)) {
			v.addGetter(nType, () -> convert(v, iType, NumberHelper::bd));
			v.addGetter(lType, () -> convert(v, iType, i -> (long) i));
			v.addGetter(sType, () -> convert(v, nType, Var::format));
		} else if (type.equals(lType)) {
			v.addGetter(nType, () -> convert(v, lType, NumberHelper::bd));
			v.addGetter(iType, () -> convert(v, nType, d -> d.intValue()));
			v.addGetter(sType, () -> convert(v, nType, Var::format));
		} else if (type.equals(nType)) {
			v.addGetter(iType, () -> convert(v, nType, d -> d.intValue()));
			v.addGetter(lType, () -> convert(v, nType, d -> d.longValue()));
			v.addGetter(sType, () -> convert(v, nType, Var::format));
		} else if (type.equals(dType)) {
			v.addGetter(lType, () -> convert(v, dType, d -> d.getTime()));
			v.addGetter(nType, () -> convert(v, lType, NumberHelper::bd));
			v.addGetter(iType, () -> convert(v, lType, Math::toIntExact));
			v.addGetter(sType, () -> convert(v, dType, DateHelper::format));
		} else if (!type.equals(sType)) {
			v.addGetter(sType, () -> convert(v, type, o -> o.toString()));
		}
	}

	public Var(String name) {
		super(Object.class);
		this.getters = new HashMap<>();
		this.setters = new HashMap<>();
		this.prims = new HashSet<>();
		this.name = name;
		initInfrastructure();
	}

	private void initInfrastructure() {
		add(Var.class, new StdTree<>(Var.class));
		add(FuncSpec.class, new StdMultiTree<>(FuncSpec.class));
	}

	public <T> void addSetter(LXClass<T> type, Consumer<T> setter) {
		setters.put(type, setter);
	}

	public <T> void addGetter(LXClass<T> type, Supplier<T> getter) {
		addGetterUnsafe(type, getter);
	}

	public String getName() {
		return name;
	}

	/**
	 * This method is only used when the equality of types has been ensured
	 * somewhere else.
	 * 
	 * @param type
	 * @param getter
	 */
	private void addGetterUnsafe(LXClass<?> type, Supplier<?> getter) {
		getters.put(type, getter);
	}

	public <T> boolean isConvertible(LXClass<T> type) {
		return getters.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	public <T> T eval(LXClass<T> type) {
		Supplier<T> getter = (Supplier<T>) getters.get(type);
		if (getter == null) {
			throw new IllegalArgumentException("Cannot convert variable '" + getName() + "' to type " + type);
		}
		return getter.get();
	}

	@SuppressWarnings("unchecked")
	public <T> void set(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot set variable to null");
		}
		LXClass<T> type = getType(value);
		Consumer<T> setter = (Consumer<T>) setters.get(type);
		if (setter == null) {
			throw new IllegalArgumentException("Cannot convert variable from value " + value + " of type " + type);
		}
		setter.accept(value);
	}

	public Collection<String> getPrimitives() {
		return prims;
	}

	public void addPrimitives(Collection<String> prims) {
		prims.stream().filter(s -> s != null).forEach(s -> this.prims.add(s));
	}

	@Override
	public String toString() {
		return getName();
	}
}