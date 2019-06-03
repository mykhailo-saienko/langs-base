package ms.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Attribute {
	private final String value;
	private final List<Attribute> subattrs;

	public Attribute(String value) {
		if (value == null) {
			value = "";
		}
		this.value = value;
		subattrs = new ArrayList<>();
	}

	public String getValue() {
		return value;
	}

	public List<String> getParams() {
		return subattrs.stream().map(a -> a.value).collect(Collectors.toList());
	}

	public List<Attribute> getSubattributes() {
		return subattrs;
	}

	public void add(String param) {
		add(new Attribute(param));
	}

	public void add(Attribute attr) {
		subattrs.add(attr);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(500);
		appendTo(sb);
		return sb.toString();
	}

	private void appendTo(StringBuffer sb) {
		sb.append(getValue());
		if (!getParams().isEmpty()) {
			sb.append("[");
			sb.append(getParams().get(0));
			for (int i = 1; i < getParams().size(); ++i) {
				sb.append(",");
				subattrs.get(i).appendTo(sb);
			}
			sb.append("]");
		}
	}
}