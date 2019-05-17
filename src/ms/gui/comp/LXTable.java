package ms.gui.comp;

import static ms.gui.comp.GUIHelper.DUST_GRAY;
import static ms.gui.comp.GUIHelper.STAINED_WHITE;
import static ms.gui.comp.GUIHelper.THUNDER_GRAY;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class LXTable<T extends LXTableModel> extends JTable {

	private static final long serialVersionUID = 67085190056479720L;
	private Color[] colors;

	public LXTable(T model) {
		super(model);
		setColors(new Color[] { STAINED_WHITE, DUST_GRAY });
		setGridColor(THUNDER_GRAY);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getModel() {
		return (T) super.getModel();
	}

	public void setColors(Color[] colors) {
		if (colors == null || colors.length == 0) {
			throw new IllegalArgumentException("Colors must be an array of length at least 1");
		}
		this.colors = colors;
	}

	public <U> U getAttribute(int colIndex, String attr) {
		return getModel().getAttribute(convertColumnIndexToModel(colIndex), attr);
	}

	public boolean attributeExists(int colIndex, String attr) {
		return getModel().attributeExists(convertColumnIndexToModel(colIndex), attr);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Object value = getValueAt(row, column);

		JComponent cell = (JComponent) renderer.getTableCellRendererComponent(this, value, isCellSelected(row, column),
				false, row, column);
		prepareCell(cell, row, column, value);
		renderCell(renderer, cell, row, column);
		return super.prepareRenderer(renderer, row, column);
	}

	private void prepareCell(JComponent cell, int row, int column, Object value) {
		cell.setForeground(getForeground());
		cell.setBackground(getCellBackground(row, column, value));
	}

	@SuppressWarnings("unchecked")
	protected Color getCellBackground(int row, int column, Object value) {
		Object attr = getAttribute(column, Descriptor.BACKGROUND_ATTR);
		Color color = null;

		if (attr instanceof Color) {
			color = (Color) attr;
		} else if (attr instanceof Map) {
			color = ((Map<Object, Color>) attr).get(value);
		}

		if (color == null) {
			color = colors[row % colors.length];
		}
		return color;
	}

	protected void renderCell(TableCellRenderer renderer, JComponent cell, int row, int column) {

	}
}
