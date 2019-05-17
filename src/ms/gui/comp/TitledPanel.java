package ms.gui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class TitledPanel extends JPanel {

	private static final long serialVersionUID = -5871012753913257100L;

	private final JLabel title;
	private final JPanel panel;

	public TitledPanel(String title) {
		super.setLayout(new GridBagLayout());
		this.title = new JLabel(title);
		this.title.setOpaque(true);
		this.panel = new JPanel();
		setLayout(new GridBagLayout());

		super.addImpl(this.title, GUIHelper.gbc(0, 0, 1, 1, GridBagConstraints.BOTH, 1.0, 0.05), -1);
		super.addImpl(this.panel, GUIHelper.gbc(0, 1, 1, 3, GridBagConstraints.BOTH, 1.0, 0.95), -1);
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (title != null) {
			title.setBackground(bg);
		}
		if (panel != null) {
			panel.setBackground(bg);
		}
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (title != null) {
			title.setFont(font);
		}
		if (panel != null) {
			panel.setFont(font);
		}
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setTitleAlignment(int alignment) {
		this.title.setHorizontalAlignment(alignment);
	}

	public void setTitleFont(Font font) {
		this.title.setFont(font);
	}

	public void setTitleBorder(Border border) {
		title.setBorder(border);
	}

	public void setTitleOpaque(boolean opaque) {
		title.setOpaque(opaque);
	}

	public void setTitleBackground(Color color) {
		title.setBackground(color);
	}

	public void setTitleForeground(Color color) {
		title.setForeground(color);
	}

	@Override
	public void setLayout(LayoutManager mgr) {
		if (panel == null) {
			super.setLayout(mgr);
		} else {
			panel.setLayout(mgr);
		}
	}

	@Override
	public LayoutManager getLayout() {
		return panel.getLayout();
	}

	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		if (constraints == null) {
			constraints = GUIHelper.gbc(0, 0, 1, 1, 0, 0, 0, 0, GridBagConstraints.BOTH, 1.0, 1.0);
		}
		panel.add(comp, constraints);
	}
}
