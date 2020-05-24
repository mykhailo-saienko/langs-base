package ms.gui.comp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class JListDialog<T> extends JDialog implements ActionListener {

	private static final long serialVersionUID = 565013808414829461L;

	private final JList<T> list;
	private final List<T> selectables;
	private final List<T> preselected;
	private boolean submitted;

	private final boolean multiple;

	public JListDialog(T[] values, Frame owner, String title, Image icon, boolean multiple) {
		super(owner, title, true);
		setIconImage(icon);
		list = new JList<>();
		list.setListData(values);
		list.setSelectionMode(
				multiple ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION);
		selectables = Arrays.asList(values);
		preselected = new ArrayList<>();
		this.multiple = multiple;

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

		JLabel headerLabel = new JLabel("Please select desired values");
		headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD));

		header.add(createOrthoStrut(10));
		header.add(headerLabel);

		JPanel central = new JPanel();
		central.setLayout(new BoxLayout(central, BoxLayout.LINE_AXIS));
		central.add(createOrthoStrut(40));
		central.add(new JScrollPane(list));
		central.add(createOrthoStrut(40));
		central.setPreferredSize(new Dimension(320, 120));

		JPanel buttonPanel = createDialogPanel();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(createStrut(5));
		panel.add(header);
		panel.add(createStrut(10));
		panel.add(central);
		panel.add(createStrut(5));
		panel.add(buttonPanel);
		panel.add(createStrut(5));

		setContentPane(panel);
		pack();
	}

	protected Component createOrthoStrut(int measure) {
		return Box.createHorizontalStrut(measure);
	}

	protected Component createStrut(int measure) {
		return Box.createVerticalStrut(measure);
	}

	protected JPanel createDialogPanel() {
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		okButton.setPreferredSize(new Dimension(80, 26));

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setPreferredSize(new Dimension(80, 26));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createHorizontalGlue());
		return buttonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		submitted = (e.getActionCommand() == "OK");
		setVisible(false);
	}

	public Object getResult() {
		return multiple ? getSelectedValues() : getSelectedValues().get(0);
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSelectedObject(List<T> values) {
		if (values == null) {
			values = new ArrayList<>();
		}
		if (!multiple && values.size() != 1) {
			throw new IllegalArgumentException(
					"Must select exactly one value while in a single-selection mode. Selected: " + values);
		}
		preselected.clear();
		preselected.addAll(values);

		int[] selIdx = values.stream().mapToInt(s -> selectables.indexOf(s)).toArray();
		list.setSelectedIndices(selIdx);
	}

	private List<T> getSelectedValues() {
		return submitted ? list.getSelectedValuesList() : preselected;
	}

}