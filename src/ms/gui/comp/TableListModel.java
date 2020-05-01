package ms.gui.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.swing.table.AbstractTableModel;

public class TableListModel<E> extends AbstractTableModel {
	private static final long serialVersionUID = 4542641151386450966L;

	private final List<E> elements;

	// if true, last entry will be showed first
	private boolean reversed;

	private final Function<E, ?>[] getters;
	private List<BiConsumer<E, Object>> setters;

	private String[] headers;
	private int emptyRows;

	@SafeVarargs
	public TableListModel(Function<E, ?>... getters) {
		this.getters = getters;
		setSetters();
		elements = new ArrayList<>();
		// extra empty non-editable rows for user-defined purposes
		emptyRows = 0;
	}

	public void setEmptyRows(int rows) {
		if (rows < 0) {
			throw new IllegalArgumentException("Number of empty rows cannot be negative");
		}
		this.emptyRows = rows;
		fireTableDataChanged();
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	@SafeVarargs
	public final void setSetters(BiConsumer<E, Object>... setters) {
		if (setters != null && setters.length != 0) {
			if (setters.length != getColumnCount()) {
				throw new IllegalArgumentException("Setters must have length " + getColumnCount()
						+ " but they have " + setters.length);
			}
			this.setters = Arrays.asList(setters);
		} else {
			ArrayList<BiConsumer<E, Object>> sets = new ArrayList<>();
			for (int i = 0; i < getColumnCount(); ++i) {
				sets.add(null);
			}
			this.setters = sets;
		}
	}

	public void setHeaders(String... headers) {
		if (headers != null && headers.length != getColumnCount()) {
			throw new IllegalArgumentException(
					"Headers " + Arrays.asList(headers) + " must have length " + getters.length);
		}
		this.headers = headers;
	}

	@Override
	public String getColumnName(int column) {
		return headers == null ? super.getColumnName(column) : headers[column];
	}

	@Override
	public int getRowCount() {
		return getNrElements() + emptyRows;
	}

	@Override
	public int getColumnCount() {
		return getters.length;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// all extra empty rows are not editable by default
		return columnIndex >= 0 && columnIndex < getColumnCount() //
				&& rowIndex < getNrElements() && setters.get(columnIndex) != null;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// additional empty rows are always empty
		if (rowIndex < 0 || rowIndex >= getNrElements() || getters[columnIndex] == null) {
			return null;
		}
		int elIndex = reversed ? getRowCount() - rowIndex - 1 : rowIndex;
		E element = elements.get(elIndex);
		return getters[columnIndex].apply(element);
	}

	public int getNrElements() {
		return elements.size();
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (!isCellEditable(rowIndex, columnIndex)) {
			return;
		}
		BiConsumer<E, Object> setter = setters.get(columnIndex);
		if (setter == null) {
			return;
		}
		E elem = rowIndex >= getNrElements() ? null : elements.get(rowIndex);
		setter.accept(elem, aValue);
		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	public void add(E elem) {
		elements.add(elem);
		fireTableRowsInserted(getNrElements() - 1, getNrElements() - 1);
	}

	public void addAll(List<E> elems) {
		elements.addAll(elems);
		fireTableRowsInserted(getNrElements() - elems.size(), getNrElements() - 1);
	}

	public E get(int i) {
		return elements.get(i);
	}

	public void remove(int row) {
		elements.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public List<E> getValues() {
		return elements;
	}

	public void setValues(List<E> updated) {
		elements.clear();
		elements.addAll(updated);
		fireTableDataChanged();
	}

}
