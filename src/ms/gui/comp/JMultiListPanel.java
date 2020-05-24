package ms.gui.comp;

import static java.util.Arrays.asList;
import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static ms.ipp.Iterables.getIndexes;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JList;

public class JMultiListPanel<T> extends MSCustomPanel<List<T>, List<T>> {
	private static final long serialVersionUID = 5827750536061122998L;

	private final JList<T> list;
	private final Supplier<T[]> selectables;

	private final boolean nonEmpty;

	public JMultiListPanel(Supplier<T[]> selectables, boolean nonEmpty) {
		super(ButtonPosition.BOTTOM, ButtonAlignment.CENTER, false);

		this.nonEmpty = nonEmpty;
		list = new JList<>();
		list.setSelectionMode(MULTIPLE_INTERVAL_SELECTION);

		this.selectables = selectables;
		list.setListData(selectables.get());
	}

	@Override
	protected JComponent createCanvas() {
		return JListPanel.createListPanel(list, getBackground());
	}

	@Override
	public void doUpdateGUI(List<T> values) {
		T[] newData = selectables.get();
		System.out.println("Refreshing selectables to " + asList(newData));
		list.setListData(newData);
		list.setSelectedIndices(getIndexes(values, asList(newData)));
	}

	@Override
	protected List<T> prepareInput() {
		if (list.getSelectedValuesList().isEmpty() && nonEmpty) {
			throw new IllegalArgumentException("At least one item must be selected");
		}
		return list.getSelectedValuesList();
	}

	@Override
	protected List<T> onCancel() {
		return getLastUpdate();
	}
}
