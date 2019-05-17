package ms.gui.comp;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LXIconButton extends JButton {

	private static final long serialVersionUID = 1824075666902268213L;

	public LXIconButton(Icon icon, boolean opaque) {
		super(icon);
		if (icon != null) {
			setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		}
		setDecorated(false);
		setOpaque(opaque);
	}

	public LXIconButton(URL url, boolean opaque) {
		this(new ImageIcon(url), opaque);
	}

	public void setIcon(String path) {
		setIcon(new ImageIcon(path));
	}

	public void setDecorated(boolean undecorated) {
		setBorderPainted(undecorated);
		setContentAreaFilled(undecorated);
		setFocusPainted(undecorated);
	}
}
