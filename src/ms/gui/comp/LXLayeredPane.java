package ms.gui.comp;

import java.awt.Component;

import javax.swing.JLayeredPane;

import org.jdesktop.swingx.StackLayout;

/**
 * A subclass of {@link JLayeredPane} which recognizes (via usage of
 * {@link StackLayout} all existing LayoutManagers.
 * 
 * @author mykhailo.saienko
 *
 */
public class LXLayeredPane extends JLayeredPane {

	private static final long serialVersionUID = 1946283565823567689L;

	public static final class StackConstraints {
		public final Integer layer;
		public final Object layoutConstraints;

		public StackConstraints(Integer layer, Object layoutConstraints) {
			this.layer = layer;
			this.layoutConstraints = layoutConstraints;
		}
	}

	/**
	 * See
	 * http://stackoverflow.com/questions/852631/java-swing-how-to-show-a-panel-on-top-of-another-panel
	 * for why this has to be set.
	 */
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}

	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		int layer = 0;
		int pos = 0;
		Object constr = null;
		if (constraints instanceof StackConstraints) {
			layer = ((StackConstraints) constraints).layer.intValue();
			constr = ((StackConstraints) constraints).layoutConstraints;
		} else {
			layer = getLayer(comp);
			constr = constraints;
		}

		pos = insertIndexForLayer(layer, index);
		super.addImpl(comp, constr, pos);
		setLayer(comp, layer, pos);
		comp.validate();
		comp.repaint();
	}
}
