package ms.gui.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {

	private static final long serialVersionUID = -7391179666157918498L;

	private final String hint;
	private boolean showingHint;
	private Color hintColour;
	private Font hintFont;

	private Color foregroundColour;
	private Font mainFont;

	public HintTextField(final String hint) {
		super("");
		this.hint = hint;
		hintColour = Color.GRAY;
		foregroundColour = getForeground();

		setShowingHint(true);
		super.addFocusListener(this);
	}

	@Override
	public void setFont(Font f) {
		super.setFont(f);
		mainFont = f;
		hintFont = f.deriveFont(Font.ITALIC);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			setShowingHint(false);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (this.getText().isEmpty()) {
			setShowingHint(true);
		}
	}

	public void setShowingHint(boolean showingHint) {
		this.showingHint = showingHint;
		if (showingHint) {
			super.setText(hint);
			super.setFont(hintFont);
			super.setForeground(hintColour);

		} else {
			super.setText("");
			super.setFont(mainFont);
			super.setForeground(foregroundColour);
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}

	@Override
	public void setText(String t) {
		if ((t == null || t.isEmpty()) && !isFocusOwner()) {
			setShowingHint(true);
		} else {
			setShowingHint(false);
			super.setText(t);
		}
	}

	public void setHintColour(Color hintColour) {
		this.hintColour = hintColour;
	}

	@Override
	public void setForeground(Color foregroundColour) {
		this.foregroundColour = foregroundColour;
	}
}
