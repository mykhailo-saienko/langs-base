package ms.gui.comp;

import static ms.ipp.Iterables.first;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ms.ipp.base.KeyValue;

public class BaseTable<T extends AbstractTableModel> extends JTable {

	private static <T> T find(Collection<KeyValue<BiPredicate<Integer, Integer>, T>> items, int row, int column,
			BiFunction<Integer, Integer, T> def) {
		KeyValue<BiPredicate<Integer, Integer>, T> find = first(items, r -> r.getKey().test(row, column));
		return find == null ? def.apply(row, column) : find.getValue();
	}

	private static final long serialVersionUID = -1321594076232116510L;

	private static final int ROW_HEIGHT = 25;

	private final List<KeyValue<BiPredicate<Integer, Integer>, TableCellRenderer>> renderers;
	private final List<KeyValue<BiPredicate<Integer, Integer>, TableCellEditor>> editors;

	public BaseTable(T model, Integer[] preferredWidths, Integer[] maxWidths) {
		super(model);
		renderers = new ArrayList<>();
		editors = new ArrayList<>();

		setRowHeight(ROW_HEIGHT);

		getTableHeader().setFont(getTableHeader().getFont().deriveFont(Font.BOLD));
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);

		if (preferredWidths.length != getColumnCount()) {
			throw new IllegalArgumentException("Preferred widths' length " + preferredWidths.length
					+ " does not equal to column count " + getColumnCount());
		}
		if (maxWidths.length != getColumnCount()) {
			throw new IllegalArgumentException(
					"Max widths' length " + maxWidths.length + " does not equal to column count " + getColumnCount());
		}

		for (int colIndex = 0; colIndex < getColumnCount(); ++colIndex) {
			TableColumn column = getColumnModel().getColumn(colIndex);
			Integer prefWidth = preferredWidths[colIndex];
			if (prefWidth != null) {
				column.setPreferredWidth(prefWidth);
			}
			Integer maxWidth = maxWidths[colIndex];
			if (maxWidth != null) {
				column.setMaxWidth(maxWidth);
			}
		}
		setCellSelectionEnabled(false);
	}

	protected void register(BiPredicate<Integer, Integer> matcher, Object comp) {
		if (comp instanceof TableCellRenderer) {
			renderers.add(new KeyValue<>(matcher, (TableCellRenderer) comp));
		}
		if (comp instanceof TableCellEditor) {
			editors.add(new KeyValue<>(matcher, (TableCellEditor) comp));
		}
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		return find(editors, row, column, super::getCellEditor);
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		return find(renderers, row, column, super::getCellRenderer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getModel() {
		return (T) super.getModel();
	}

	public boolean isLastRow(int r) {
		return r == getRowCount() - 1;
	}
}