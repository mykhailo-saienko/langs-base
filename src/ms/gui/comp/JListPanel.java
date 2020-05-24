package ms.gui.comp;

import static ms.ipp.Iterables.getIndexes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class JListPanel<T> extends MSCustomPanel<T, T> {
	private static final long serialVersionUID = 5827750536061122998L;

	private final JList<T> list;
	private final List<T> selectables;

	static <T> JPanel createListPanel(JList<T> list, Color background) {
		JPanel panel = new JPanel();
		panel.setBackground(background);

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
		header.setBackground(panel.getBackground());

		JLabel headerLabel = new JLabel("Please select desired values");
		headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));

		header.add(Box.createHorizontalStrut(10));
		header.add(headerLabel);

		JPanel central = new JPanel();
		central.setBackground(panel.getBackground());
		central.setLayout(new BoxLayout(central, BoxLayout.LINE_AXIS));
		central.add(Box.createHorizontalStrut(40));
		central.add(new JScrollPane(list));
		central.add(Box.createHorizontalStrut(40));
		central.setPreferredSize(new Dimension(320, 120));

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Box.createVerticalStrut(5));
		panel.add(header);
		panel.add(Box.createVerticalStrut(10));
		panel.add(central);
		return panel;
	}

	public JListPanel(T[] selectables) {
		super(ButtonPosition.BOTTOM, ButtonAlignment.CENTER, false);

		this.selectables = Arrays.asList(selectables);
		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(selectables);
	}

	@Override
	protected JComponent createCanvas() {
		return createListPanel(list, getBackground());
	}

	@Override
	public void doUpdateGUI(T value) {
		List<T> values = value == null ? new ArrayList<>() : Arrays.asList(value);
		list.setSelectedIndices(getIndexes(values, selectables));
	}

	@Override
	protected T prepareInput() {
		if (list.getSelectedValuesList().size() != 1) {
			throw new IllegalArgumentException("Exactly one item must be selected");
		}
		return list.getSelectedValuesList().get(0);
	}

	@Override
	protected T onCancel() {
		return getLastUpdate();
	}
}
