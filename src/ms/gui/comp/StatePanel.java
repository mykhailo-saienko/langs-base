package ms.gui.comp;

import javax.swing.JPanel;

public abstract class StatePanel<T> extends JPanel {

	private static final long serialVersionUID = -2147329891902912082L;

	public abstract T getState();

	public void setState(T in) {

	}
}