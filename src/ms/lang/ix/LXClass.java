package ms.lang.ix;

import static ms.ipp.Iterables.isEqualOrNull;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;

import ms.lang.ix.Enumeration.Element;

public class LXClass<T> {
	public static final LXClass<Date> DATE = createPrimType(Date.class);
	public static final LXClass<String> STRING = createPrimType(String.class);
	public static final LXClass<Boolean> BOOL = createPrimType(Boolean.class);
	public static final LXClass<Integer> INT = createPrimType(Integer.class);
	public static final LXClass<BigDecimal> NUM = createPrimType(BigDecimal.class);
	public static final LXClass<Long> LONG = createPrimType(Long.class);
	public static final LXClass<Color> COLOR = createPrimType(Color.class);
	private final Class<T> type;
	private final String subtype;

	public static <U> LXClass<U> createPrimType(Class<U> type) {
		if (isEnum(type)) {
			throw new IllegalArgumentException("Enumeration is not a primitive type");
		}
		return new LXClass<U>(type, null);
	}

	public static LXClass<Enumeration.Element> createEnumType(String subtype) {
		return new LXClass<>(Enumeration.Element.class, subtype);
	}

	@SuppressWarnings("unchecked")
	public static <U> LXClass<U> getType(U value) {
		if (value == null) {
			throw new IllegalArgumentException("Cannot determine LXClass of a null value");
		}
		if (isEnum(value.getClass())) {
			Element elem = (Element) value;
			return (LXClass<U>) createEnumType(elem.getParent());
		} else {
			Class<U> type = (Class<U>) value.getClass();
			return createPrimType(type);
		}
	}

	public static <U> boolean isEnum(LXClass<U> type) {
		return isEnum(type.getType());
	}

	private static <U> boolean isEnum(Class<U> type) {
		return Enumeration.Element.class.equals(type);
	}

	public LXClass(Class<T> type, String subtype) {
		if (type == null) {
			throw new IllegalArgumentException("Cannot create a null-type");
		}
		this.type = type;
		this.subtype = subtype;
	}

	public Class<T> getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}

	@Override
	public int hashCode() {
		int hash = type.hashCode();
		if (subtype != null) {
			hash ^= subtype.hashCode();
		}
		return hash;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LXClass) {
			LXClass<T> other = (LXClass<T>) obj;
			return type.equals(other.type) && isEqualOrNull(subtype, other.subtype);
		}
		return false;
	}

	@Override
	public String toString() {
		return type.getSimpleName() + (subtype == null ? "" : ("[" + subtype + "]"));
	}
}
