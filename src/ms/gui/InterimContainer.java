package ms.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import ms.ipp.base.KeyValue;

public class InterimContainer extends JComponent implements Deferred<JComponent> {
	private static final long serialVersionUID = 7949017577830045144L;

	// Key is the component, Value are optional constraints.
	private final List<KeyValue<JComponent, Object>> children;

	public InterimContainer() {
		children = new ArrayList<>();
	}

	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		// index is ignored.
		if (comp != null && comp instanceof JComponent) {
			children.add(new KeyValue<>((JComponent) comp, constraints));
		} else {
			throw new IllegalArgumentException("Component " + comp + " is not a JComponent");
		}
	}

	@Override
	public List<KeyValue<JComponent, Object>> getContent() {
		return children;
	}
}