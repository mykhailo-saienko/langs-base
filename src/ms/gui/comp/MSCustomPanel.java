package ms.gui.comp;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class MSCustomPanel<In, Out> extends JPanel {

	protected static enum ButtonAlignment {
		START, CENTER, END;
	}

	protected static enum ButtonPosition {
		RIGHT, BOTTOM;
	}

	private static final long serialVersionUID = -519343577465346833L;

	private boolean canceled;
	private In lastUpdate;
	private Out result;
	private final boolean readOnly;
	private final ButtonPosition position;
	private final ButtonAlignment alignment;
	private Dimension buttonSize;

	private JComponent canvas;
	private JPanel dialogPanel;

	private String okCaption;

	private boolean created;

	public MSCustomPanel(ButtonPosition position, ButtonAlignment alignment, In init, boolean readOnly) {
		this(position, alignment, readOnly);
		this.lastUpdate = init;
	}

	/**
	 * 
	 * @param position
	 *            gives the position of the button panel. If null, the button
	 *            panel is not displayed.
	 * @param alignment
	 *            gives the default buttons' alignment within the button pannel.
	 *            If null, the default buttons are not displayed.
	 * @param readOnly
	 */
	public MSCustomPanel(ButtonPosition position, ButtonAlignment alignment, boolean readOnly) {
		this.readOnly = readOnly;
		this.position = position;
		this.alignment = alignment;
		this.result = null;
		this.created = false;
		setButtonSize(new Dimension(80, 26));
		setOkCaption("OK");
	}

	public boolean isCanceled() {
		return canceled || readOnly;
	}

	public Out getResult() {
		return isCanceled() ? onCancel() : result;
	}

	/**
	 * Updates Panel, sets canceled to true.
	 * 
	 * @param input
	 */
	public final void updateGUI(In input) {
		if (!created) {
			createGUI();
		}
		this.lastUpdate = input;
		canceled = true;
		doUpdateGUI(input);
		revalidate();
	}

	void createGUI() {
		if (created) {
			return;
		}
		setLayout(new BoxLayout(this, getAxis(false)));

		canvas = createCanvas();
		add(canvas);
		if (getPosition() != null) {
			add(createStrut(5, false));
			dialogPanel = createDialogPanel();
			add(dialogPanel);
			add(createStrut(5, false));
		}
		created = true;
	}

	/**
	 * Validates the input before the results are committed and are publicly
	 * available. If validation fails, the method should throw an
	 * {@link IllegalArgumentException}
	 * 
	 * @return
	 */
	protected Out prepareInput() {
		return null;
	}

	protected abstract JComponent createCanvas();

	protected void addEnd(List<JButton> buttons) {
	}

	protected void addCenter(List<JButton> buttons) {
	}

	protected void addStart(List<JButton> buttons) {
	}

	protected void addBefore(List<JButton> buttons) {
	}

	protected void addAfter(List<JButton> buttons) {
	}

	protected void addBetween(List<JButton> buttons) {
	}

	protected void doUpdateGUI(In input) {
	}

	protected Out onCancel() {
		return null;
	}

	protected boolean isReadOnly() {
		return readOnly;
	}

	protected ButtonPosition getPosition() {
		return position;
	}

	protected ButtonAlignment getAlignment() {
		return alignment;
	}

	protected In getLastUpdate() {
		return lastUpdate;
	}

	protected JComponent getCanvas() {
		return canvas;
	}

	protected JPanel getDialogPanel() {
		return dialogPanel;
	}

	protected void submit() {
		submit(() -> prepareInput());
	}

	protected void submit(Supplier<Out> result) {
		try {
			this.result = result == null ? null : result.get();
			canceled = false;
			this.firePropertyChange("Done", false, true);
		} catch (IllegalArgumentException e) {
			String message = "Error while validating input: " + e.getMessage();
			showMessageDialog(this, message);
		}
	}

	protected void cancel() {
		canceled = true;
		this.firePropertyChange("Done", false, true);
	}

	protected JButton createButton(String caption, ActionListener listener, boolean isSensitive) {
		JButton button = new JButton(caption);
		button.addActionListener(listener);
		button.setPreferredSize(getButtonSize());
		button.setMaximumSize(getButtonSize());
		if (isSensitive) {
			button.setEnabled(!isReadOnly());
		}
		return button;
	}

	protected Dimension getButtonSize() {
		return buttonSize;
	}

	protected void setButtonSize(Dimension buttonSize) {
		this.buttonSize = buttonSize;
	}

	protected String getOkCaption() {
		return okCaption;
	}

	protected void setOkCaption(String okCaption) {
		this.okCaption = okCaption;
	}

	protected Component createStrut(int measure, boolean perp) {
		return isHorizontal(perp) ? Box.createHorizontalStrut(measure) : Box.createVerticalStrut(measure);
	}

	protected Component createGlue(boolean perp) {
		return isHorizontal(perp) ? Box.createHorizontalGlue() : Box.createVerticalGlue();
	}

	private JPanel createDialogPanel() {
		JPanel dialogPanel = new JPanel();
		dialogPanel.setBackground(getBackground());
		dialogPanel.setLayout(new BoxLayout(dialogPanel, getAxis(true)));
		addButtons(dialogPanel, getAlignment() == ButtonAlignment.START ? this::addDefaultButtons : this::addStart);
		dialogPanel.add(createGlue(true));
		addButtons(dialogPanel, getAlignment() == ButtonAlignment.CENTER ? this::addDefaultButtons : this::addCenter);
		dialogPanel.add(createGlue(true));
		addButtons(dialogPanel, getAlignment() == ButtonAlignment.END ? this::addDefaultButtons : this::addEnd);
		return dialogPanel;
	}

	private void addDefaultButtons(List<JButton> buts) {
		addBefore(buts);
		buts.add(createButton(getOkCaption(), e -> submit(), true));
		addBetween(buts);
		buts.add(createButton("Cancel", e -> cancel(), false));
		addAfter(buts);
	}

	private void addButtons(JPanel panel, Consumer<List<JButton>> adder) {
		List<JButton> buttons = new ArrayList<>();
		adder.accept(buttons);
		if (buttons == null || buttons.isEmpty()) {
			return;
		}
		panel.add(buttons.get(0));
		for (int i = 1; i < buttons.size(); ++i) {
			panel.add(createStrut(10, true));
			panel.add(buttons.get(i));
		}
	}

	/**
	 * Gets the axial or perpendicular direction which depends on the
	 * ButtonPosition. For example, getAxis(true) if the button pannel is at the
	 * bottom gives the axis perpendicular to the vertical axis (which would
	 * result into the button panel being at the bottom), i.e. the horizontal
	 * axis.
	 * 
	 * @param perp
	 *            if true gives the perpendicular direction, otherwise gives the
	 *            axial direction
	 * @return
	 */
	private int getAxis(boolean perp) {
		return isHorizontal(perp) ? BoxLayout.LINE_AXIS : BoxLayout.PAGE_AXIS;
	}

	private boolean isHorizontal(boolean perp) {
		return (position == ButtonPosition.RIGHT) ^ perp;
	}
}
