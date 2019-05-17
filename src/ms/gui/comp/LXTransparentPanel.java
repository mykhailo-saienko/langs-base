package ms.gui.comp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class LXTransparentPanel extends JPanel {

	private static final long serialVersionUID = -8115962171006352770L;

	private Image image;
	private boolean transparent;

	public LXTransparentPanel(LayoutManager layout) {
		super(layout);
		setOpaque(false);
		image = null;
		transparent = false;
	}

	public LXTransparentPanel() {
		this(null);
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setTransparent(boolean isTransparent) {
		transparent = isTransparent;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!transparent) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			if (image != null) {
				GUIHelper.drawImage(this, image, g);
			}
		}

		super.paintComponent(g);
	}
}
