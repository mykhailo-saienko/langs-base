package ms.gui.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

public class HintPasswordField extends JPasswordField implements FocusListener {

    private static final long serialVersionUID = -7391179666157918498L;
    private final String hint;
    private boolean showingHint;
    private Color hintColour;
    private Font hintFont;
    private char echoChar;

    private Color foregroundColour;
    private Font mainFont;

    public HintPasswordField(final String hint) {
        super("");
        this.hint = hint;
        setHintColour(Color.GRAY);
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
            super.setEchoChar((char) 0);
        } else {
            super.setText("");
            super.setFont(mainFont);
            super.setForeground(foregroundColour);
            super.setEchoChar(echoChar);
        }
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

    @Override
    public void setEchoChar(char c) {
        echoChar = c;
    }

    @Override
    public char[] getPassword() {
        return showingHint ? new char[] {} : super.getPassword();
    }

    @Override
    public String getText() {
        return showingHint ? "" : new String(super.getPassword());
    }

    public void setHintColour(Color hintColour) {
        this.hintColour = hintColour;
    }

    @Override
    public void setForeground(Color foregroundColour) {
        this.foregroundColour = foregroundColour;
    }
}
