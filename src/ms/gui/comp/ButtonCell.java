package ms.gui.comp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ButtonCell<T extends BaseTable<? extends AbstractTableModel>>
		extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 3028762939307929853L;
	private final JPanel cell;
	private final ButtonAction<T> action;
	private final JButton button;

	private final String buttonText;

	public static <U extends BaseTable<? extends AbstractTableModel>> ButtonCell<U> buttonCell(
			String text, U table, Consumer<ButtonAction<U>> action) {
		return buttonCell(text, table, (b, e) -> action.accept(b));
	}

	public static <U extends BaseTable<? extends AbstractTableModel>> ButtonCell<U> buttonCell(
			String text, U table, BiConsumer<ButtonAction<U>, ActionEvent> action) {
		return new ButtonCell<>(text, ButtonAction.create(table, action));
	}

	public ButtonCell(String text, ButtonAction<T> action) {
		buttonText = text;
		cell = new JPanel();
		cell.setLayout(new BoxLayout(cell, BoxLayout.LINE_AXIS));
		cell.setOpaque(false);
		this.action = action;

		button = new JButton();
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setPreferredSize(new Dimension(20, 20));
		button.setAction(action);

		cell.add(Box.createHorizontalGlue());
		cell.add(button);
		cell.add(Box.createHorizontalGlue());
	}

	public void setButtonSize(Dimension size) {
		button.setPreferredSize(size);
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		button.setText(buttonText);
		return cell;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column) {
		action.setRow(row);
		button.setText(buttonText);
		return cell;
	}
}