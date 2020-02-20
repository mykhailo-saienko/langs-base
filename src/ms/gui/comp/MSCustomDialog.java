package ms.gui.comp;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JDialog;
import javax.swing.JPanel;

import ms.ipp.base.KeyValue;

public class MSCustomDialog<In, Out> extends JDialog {
	private static final long serialVersionUID = -2270345328861731924L;

	public static <T, U> U show(Frame owner, MSCustomPanel<T, U> panel, String title, Image icon) {
		MSCustomDialog<T, U> dialog = new MSCustomDialog<>(owner, panel, title, icon);
		return dialog.show(null, null).getValue();
	}

	public static <T, U> U show(Frame owner, MSCustomPanel<T, U> panel, T init, String title,
			Image icon) {
		MSCustomDialog<T, U> dialog = new MSCustomDialog<>(owner, panel, init, title, icon);
		return dialog.show(null, null).getValue();
	}

	public static <T, U> void showProcess(Frame owner, MSCustomPanel<T, U> panel, T init,
			String title, Image icon, Consumer<U> onOk, Runnable onCancel) {
		MSCustomDialog<T, U> dialog = new MSCustomDialog<>(owner, panel, init, title, icon);
		dialog.showProcess(onOk, onCancel);
	}

	private Consumer<MSCustomPanel<In, Out>> preprocessor;
	private MSCustomPanel<In, Out> panel;

	public MSCustomDialog(Frame owner, MSCustomPanel<In, Out> panel, String title, Image icon) {
		super(owner, title, true);
		this.panel = panel;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				panel.cancel();
			}
		});

		panel.createGUI();
		panel.addPropertyChangeListener((evt) -> {
			if (isVisible() && evt.getSource() == panel) {
				setVisible(false);
			}
		});
		setContentPane(panel);
		pack();
		setLocationRelativeTo(owner);
		if (icon != null) {
			setIconImage(icon);
		}
	}

	MSCustomDialog(Frame owner, MSCustomPanel<In, Out> panel, In init, String title, Image icon) {
		this(owner, panel, title, icon);
		panel.updateGUI(init);
	}

	public void showProcess(Consumer<Out> onOk, Runnable onCancel) {
		setVisible(true);
		try {
			if (isCanceled()) {
				if (onCancel != null) {
					onCancel.run();
				}
			} else {
				if (onOk != null) {
					onOk.accept(getResult());
				}
			}
		} finally {
			dispose();
		}
	}

	public KeyValue<Boolean, Out> show(Function<Out, Out> onOk, Supplier<Out> onCancel) {
		setVisible(true);
		try {
			Out out = null;
			boolean canceled = isCanceled();
			if (canceled) {
				out = onCancel != null ? onCancel.get() : null;
			} else {
				out = onOk != null ? onOk.apply(getResult()) : getResult();
			}
			return new KeyValue<>(!canceled, out);
		} finally {
			dispose();
		}
	}

	public void updatePanel(In input) {
		panel.updateGUI(input);
	}

	private boolean isCanceled() {
		JPanel panel = (JPanel) getContentPane();
		if (panel instanceof MSCustomPanel) {
			return ((MSCustomPanel<?, ?>) panel).isCanceled();
		}
		return false;
	}

	public Out getResult() {
		return panel.getResult();
	}
}
