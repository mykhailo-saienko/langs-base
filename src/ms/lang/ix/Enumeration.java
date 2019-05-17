package ms.lang.ix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ms.ipp.base.KeyValue;

public class Enumeration {

	private final String name;
	private final ArrayList<Element> elements;

	public <T> Enumeration(String name, Map<String, T> source) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Cannot create a nameless enum " + source);
		}
		if (source == null || source.isEmpty()) {
			throw new IllegalArgumentException("Cannot create an empty enum '" + name + "'");
		}
		elements = new ArrayList<>();
		this.name = name;
		source.forEach((n, v) -> elements.add(new Element(n, v, name)));
	}

	public Element getFirst() {
		return elements.get(0);
	}

	public List<Element> getElements() {
		return elements;
	}

	public String getName() {
		return name;
	}

	public <T> Map<String, T> toMap() {
		Map<String, T> map = new HashMap<>();
		elements.forEach(e -> map.put(e.getName(), e.getValue()));
		return map;
	}

	public <T> Vector<KeyValue<String, T>> toVector() {
		Vector<KeyValue<String, T>> result = new Vector<>();
		elements.forEach(e -> result.add(e.toKVP()));
		return result;
	}

	public Element getElement(String name) {
		for (Element e : elements) {
			if (e.getName().equals(name)) {
				return e;
			}
		}

		throw new IllegalArgumentException("Enumeration '" + getName() + "' does not contain element '" + name + "'");
	}

	public <T> T getValue(String name) {
		return getElement(name).getValue();
	}

	public static class Element {
		private final String name;
		private final Object value;
		private final String parent;

		public <T> Element(String name, T value, String parent) {
			if (name == null) {
				throw new IllegalArgumentException("Cannot create an element with an empty name");
			}
			if (value == null) {
				throw new IllegalArgumentException("Cannot create an element '" + name + "' with null-value");
			}
			if (parent == null) {
				throw new IllegalArgumentException(
						"Cannot create an element '" + name + "' without the enumeration reference");
			}
			this.name = name;
			this.value = value;
			this.parent = parent;
		}

		public <T> KeyValue<String, T> toKVP() {
			return new KeyValue<>(getName(), getValue());
		}

		public String getName() {
			return name;
		}

		public <T> T getValue() {
			return (T) value;
		}

		public Class<?> getType() {
			return value.getClass();
		}

		public String getParent() {
			return parent;
		}

		@Override
		public String toString() {
			return name + ": " + value;
		}
	}
}
