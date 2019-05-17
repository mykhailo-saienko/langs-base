package ms.gui.comp;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LXLayerUI<T extends JComponent> extends LayerUI<T> {

	private static final Logger logger = LogManager.getLogger();

	private static final long serialVersionUID = -2442465569263267375L;

	private boolean active;

	private JLayer<T> layer;
	private float transparency;

	public LXLayerUI(float transparency) {
		setActive(false);
		this.setTransparency(transparency);
		layer = null;
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		JLayer<T> jlayer = (JLayer<T>) c;
		jlayer.setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		layer = jlayer;
	}

	@Override
	public void uninstallUI(JComponent c) {
		layer = null;
		JLayer<T> jlayer = (JLayer<T>) c;
		jlayer.setLayerEventMask(0);
		super.uninstallUI(c);
	}

	@Override
	public void eventDispatched(AWTEvent e, JLayer<? extends T> l) {
		if (isActive()) {
			// block the mouse event
			if (e instanceof InputEvent) {
				((InputEvent) e).consume();
			}
		} else {
			super.eventDispatched(e, l);
		}
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		if (!isActive()) {
			return;
		}

		int w = c.getWidth();
		int h = c.getHeight();
		Graphics2D g2 = (Graphics2D) g.create();

		// Gray it out.
		Composite urComposite = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g2.fillRect(0, 0, w, h);
		g2.setComposite(urComposite);
	}

	private synchronized boolean isActive() {
		return active;
	}

	public synchronized void setActive(boolean active) {
		this.active = active;
	}

	public float getTransparency() {
		return transparency;
	}

	public void setTransparency(float transparency) {
		logger.trace("Setting transparency to {}", transparency);
		this.transparency = transparency;
	}

}
