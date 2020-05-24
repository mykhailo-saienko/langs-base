package ms.gui.comp;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

import ms.ipp.base.KeyValue;

public class MSPopupCellEditor<In, Out> extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = -5216626201862226466L;

	private final JButton button;
	private Out out;

	private final Function<In, String> prepare;

	/**
	 * Returns the supplier which generates the COMMIT/CANCEL-event according to the
	 * standard convention, i.e. COMMIT if result is not null and CANCEL otherwise
	 * 
	 * @param sup
	 * @return
	 */
	public static <U> Supplier<KeyValue<Boolean, U>> stdCommit(Supplier<U> sup) {
		return () -> {
			U result = sup.get();
			return new KeyValue<>(result != null, result);
		};
	}

	/**
	 * For this general sort of Popup cell editors, In is assumed to be the type
	 * getValueAt(r,c) and Out is the type of the value passed to setValueAt(r,c,o).
	 * 
	 * @param prepare
	 * @param produce
	 */
	public MSPopupCellEditor(Function<In, String> prepare,
			Supplier<KeyValue<Boolean, Out>> produce) {
		this.prepare = prepare;
		out = null;
		button = new JButton();
		button.addActionListener(e -> {
			KeyValue<Boolean, Out> result = produce.get();
			out = result.getValue();
			if (result.getKey() == true) {
				fireEditingStopped();
			} else {
				fireEditingCanceled();
			}
		});
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFont(button.getFont().deriveFont(Font.PLAIN));
		button.setMargin(new Insets(0, 2, 0, 0));
	}

	public MSPopupCellEditor(MSCustomDialog<In, Out> dialog, BiFunction<In, Out, String> display) {
		this(i -> {
			dialog.updatePanel(i);
			return display.apply(i, dialog.getResult());
		}, () -> dialog.show(null, null));
	}

	public MSPopupCellEditor(MSCustomDialog<In, Out> dialog) {
		// TODO: dialog.getResult() is null unless the user clicks OK/Cancel
		// => Val is most probably null => button text is most probably "".
		this(dialog, (i, val) -> val == null ? "" : val.toString());
	}

	public MSPopupCellEditor(JFrame parent, MSCustomPanel<In, Out> panel, String title,
			Image image) {
		this(new MSCustomDialog<>(parent, panel, title, image));
	}

	@Override
	public Out getCellEditorValue() {
		return out;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column) {
		button.setText(prepare.apply((In) value));
		return button;
	}
}