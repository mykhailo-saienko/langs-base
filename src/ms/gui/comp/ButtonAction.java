package ms.gui.comp;

import java.awt.event.ActionEvent;
import java.util.function.BiConsumer;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public abstract class ButtonAction<T extends BaseTable<? extends AbstractTableModel>> extends AbstractAction {

	private static final long serialVersionUID = 6373901193084213108L;
	private int row;
	private final T table;

	public static <U extends BaseTable<? extends AbstractTableModel>> ButtonAction<U> create(U table,
			BiConsumer<ButtonAction<U>, ActionEvent> action) {
		return new ButtonAction<U>(table) {
			private static final long serialVersionUID = -5262566406548015777L;

			@Override
			protected void act(ActionEvent e) {
				action.accept(this, e);
			}
		};
	}

	public ButtonAction(T table) {
		this.table = table;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public T getTable() {
		return table;
	}

	public AbstractTableModel getModel() {
		return getTable().getModel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			act(e);
		} catch (IllegalArgumentException e1) {
			JOptionPane.showMessageDialog(getTable(), e1.getMessage());
		}
		getTable().getCellEditor().stopCellEditing();
		getModel().fireTableDataChanged();
	}

	protected abstract void act(ActionEvent e);
}