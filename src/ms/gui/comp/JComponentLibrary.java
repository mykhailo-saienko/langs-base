package ms.gui.comp;

import static java.util.Arrays.asList;
import static ms.gui.comp.GUIHelper.loadResImage;
import static ms.gui.comp.GUIHelper.runInJAWT;
import static ms.ipp.Iterables.appendList;
import static ms.ipp.Iterables.get;
import static ms.ipp.Iterables.ifExistsDo;
import static ms.utils.NumberHelper.bd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ms.gui.Attribute;
import ms.gui.GUIFactory;
import ms.ipp.Iterables;
import ms.ipp.base.KeyValue;
import ms.lang.ix.Enumeration;
import ms.lang.ix.LXClass;
import ms.lang.ix.Var;
import ms.utils.StringHelper;

public class JComponentLibrary {

	public static interface AttributeParser {
		Object parse(String tag, String name, String value);
	}

	public static final Logger logger = LogManager.getLogger();

	public static final String BUTTON_TAG = "button";
	public static final String HINT_TAG = "hint";
	public static final String LABEL_TAG = "label";
	public static final String MULTILAYER_TAG = "multilayer";
	public static final String OVERLAY_TAG = "overlay";
	public static final String PANEL_TAG = "panel";
	public static final String TABBEDPANE_TAG = "tabbedpane";
	public static final String TITLEDPANEL_TAG = "titledpanel";
	public static final String TEXTFIELD_TAG = "text";

	// for titled panel
	public static final String TITLE_SUBTAG = "title";

	// for hint-control
	public static final String ARROW_SUBTAG = "arrow";

	public static final String ACTIVE_NAME = "active";
	public static final String ACTION_NAME = "action";
	public static final String ALIGNMENT_NAME = "alignment";
	public static final String BACKGROUND_NAME = "background";
	public static final String BORDER_NAME = "border";
	public static final String DECORATED_NAME = "decorated";
	// hint-control
	public static final String ELLIPTICITY_NAME = "ellipticity";
	public static final String FONT_NAME = "font";
	public static final String FOCUSABLE_NAME = "focusable";
	public static final String FOREGROUND_NAME = "foreground";
	// for text/password fields
	public static final String ECHO_NAME = "echo";
	public static final String HINTCOLOR_NAME = "hintcolor";
	public static final String HINT_NAME = "hint";

	public static final String GRIDCOLOR_NAME = "gridcolor";
	public static final String IMAGE_NAME = "image";
	public static final String LABEL_FOR = "labelfor";
	public static final String LAYOUT_NAME = "layout";
	// needed for the null-layout manager.
	public static final String LOCATION_NAME = "location";
	public static final String MAXSIZE_NAME = "maximumsize";
	public static final String MINSIZE_NAME = "minimumsize";
	// table model specification.
	public static final String MODEL_NAME = "model";

	public static final String ONKEY_NAME = "onkey";
	// hint-control
	public static final String OFFSET_NAME = "offset";
	public static final String OPAQUE_NAME = "opaque";
	public static final String PREFSIZE_NAME = "preferredsize";
	public static final String ROWHEIGHT_NAME = "rowheight";
	public static final String ROUNDNESS_NAME = "roundness";

	// needed for the null-layout manager + hint-control.
	public static final String SIZE_NAME = "size";
	public static final String TABPLACEMENT_NAME = "tabplacement";
	public static final String TEXT_NAME = "text";
	public static final String TITLE_NAME = "title";
	public static final String VIEWCOLOR_NAME = "viewcolor";
	public static final String VISIBLE_NAME = "visible";
	public static final String TRANSPARENCY_NAME = "transparency";

	public static final String CONSTRAINT_TAG = "c";
	public static final String VAR_TAG = "var";

	public static final String GBC_TYPE = "gbc";// GridBagConstraint
	public static final String TAB_TYPE = "tab"; // JTabbedPane

	// for grid bag constraints.
	public static final String POS_NAME = "pos";
	public static final String WEIGHT_NAME = "weight";
	public static final String IPADS_NAME = "ipads";
	public static final String INSETS_NAME = "insets";

	public static final String TYPE_NAME = "type";

	public static final String CHILDREN_NAME = "children";
	public static final String DEFERRED_ATTR = "deferred";

	public static void registerJComponents(GUIFactory<JComponent> factory) {
		factory.register(PANEL_TAG, JComponentLibrary::createPanel);
		factory.register(TITLEDPANEL_TAG, JComponentLibrary::createTitledPanel);
		factory.register(LABEL_TAG, JComponentLibrary::createLabel);
		factory.register(BUTTON_TAG, JComponentLibrary::createButton);
		factory.register(TEXTFIELD_TAG, JComponentLibrary::createTextField);
		factory.register(HINT_TAG, JComponentLibrary::createHint);

		Function<Map<String, Object>, List<KeyValue<JComponent, Object>>> getChildren = a -> Iterables
				.remove(CHILDREN_NAME, a, ArrayList::new);
		factory.register(MULTILAYER_TAG, a -> createMultilayer(getChildren.apply(a), a), DEFERRED_ATTR);
		factory.register(OVERLAY_TAG, a -> createLayered(getChildren.apply(a), a), DEFERRED_ATTR);
		factory.register(TABBEDPANE_TAG, a -> createTabbedPane(getChildren.apply(a), a), DEFERRED_ATTR);
	}

	public static TitledPanel createTitledPanel(Map<String, Object> attrs) {
		String title = (String) attrs.get(TITLE_NAME);
		TitledPanel panel = new TitledPanel(title);
		processComponent(panel, attrs);
		processPanel(panel, attrs);

		Map<String, Object> subs = new HashMap<>();
		StringHelper.filterPrefix(TITLE_SUBTAG, attrs, subs,
				asList(BACKGROUND_NAME, BORDER_NAME, FONT_NAME, FOREGROUND_NAME, OPAQUE_NAME));
		ifExistsDo(ALIGNMENT_NAME, subs, (Integer i) -> panel.setTitleAlignment(i));
		ifExistsDo(BACKGROUND_NAME, subs, (Color c) -> panel.setTitleBackground(c));
		ifExistsDo(FONT_NAME, subs, (Font f) -> panel.setTitleFont(f));
		ifExistsDo(FOREGROUND_NAME, subs, (Color c) -> panel.setTitleForeground(c));
		ifExistsDo(BORDER_NAME, subs, (Border b) -> panel.setTitleBorder(b));
		ifExistsDo(OPAQUE_NAME, subs, (Boolean b) -> panel.setTitleOpaque(b));
		return panel;
	}

	public static JPanel createPanel(Map<String, Object> attrs) {
		LXTransparentPanel p = new LXTransparentPanel();
		processComponent(p, attrs, asList(OPAQUE_NAME));
		processPanel(p, attrs);
		ifExistsDo(IMAGE_NAME, attrs, (String s) -> p.setImage(loadResImage(s).getImage()));
		ifExistsDo(OPAQUE_NAME, attrs, (Boolean b) -> p.setTransparent(!b));
		return p;
	}

	private static LXIconButton createButton(Map<String, Object> attrs) {
		boolean opaque = get(OPAQUE_NAME, attrs, false);
		String path = get(IMAGE_NAME, attrs);
		ImageIcon image = path == null ? null : loadResImage(path);
		LXIconButton button = new LXIconButton(image, opaque);
		processComponent(button, attrs);

		logger.debug("Creating button with attrs", attrs);

		ifExistsDo(TEXT_NAME, attrs, (String s) -> button.setText(s));
		ifExistsDo(ALIGNMENT_NAME, attrs, (Integer i) -> button.setHorizontalAlignment(i));
		ifExistsDo(DECORATED_NAME, attrs, (Boolean b) -> button.setDecorated(b));
		ifExistsDo(FOCUSABLE_NAME, attrs, (Boolean b) -> button.setFocusPainted(b));

		Consumer<Var<?>> cons = v -> button.addActionListener(e -> GUIHelper.runInJAWT(() -> v.getValue()));
		ifExistsDo(ACTION_NAME, attrs, cons);
		return button;
	}

	public static JTextField createTextField(Map<String, Object> attrs) {
		String echoChar = get(ECHO_NAME, attrs);
		String hint = get(HINT_NAME, attrs, "");
		Color hintColour = get(HINTCOLOR_NAME, attrs, Color.GRAY);

		JTextField result = null;
		if (echoChar != null && !echoChar.isEmpty()) {
			HintPasswordField res = new HintPasswordField(hint);
			res.setEchoChar(echoChar.charAt(0));
			res.setHintColour(hintColour);
			result = res;
		} else {
			HintTextField res = new HintTextField(hint);
			res.setHintColour(hintColour);
			result = res;
		}

		processComponent(result, attrs);
		return result;
	}

	public static LXHint createHint(Map<String, Object> attrs) {
		Map<String, Object> arrowAttrs = new HashMap<>();
		StringHelper.filterPrefix(ARROW_SUBTAG, attrs, arrowAttrs, null);
		logger.trace("Creating hint with attributes {}\nArrow attrs are {}", attrs, arrowAttrs);

		LXHint hint = new LXHint();
		// the panel remains transparent and the label is drawn around it.
		processComponent(hint, attrs, asList(BORDER_NAME, OPAQUE_NAME));
		processLabel(hint.toLabel(), attrs);

		int side = get(ALIGNMENT_NAME, arrowAttrs, SwingConstants.CENTER); // i.e.none;
		ifExistsDo(SIZE_NAME, arrowAttrs,
				(List<Integer> d) -> hint.setArrowDim(side, new Dimension(d.get(0), d.get(1))));
		ifExistsDo(OFFSET_NAME, arrowAttrs, (BigDecimal f) -> hint.setArrowPos(side, f.floatValue()));
		ifExistsDo(BORDER_NAME, attrs, hint::setLineBorder);
		ifExistsDo(ROUNDNESS_NAME, attrs, hint::setRadius);
		ifExistsDo(ELLIPTICITY_NAME, attrs, (List<Integer> i) -> hint.setRadii(i.get(0), i.get(1)));
		return hint;
	}

	public static JLabel createLabel(Map<String, Object> attrs) {
		JLabel label = new JLabel();
		processComponent(label, attrs);
		processLabel(label, attrs);
		return label;
	}

	private static JLayeredPane createMultilayer(List<KeyValue<JComponent, Object>> children,
			Map<String, Object> attrs) {
		JLayeredPane pane = new LXLayeredPane();
		processComponent(pane, attrs);
		processPanel(pane, attrs);

		for (int i = 0; i < children.size(); ++i) {
			KeyValue<JComponent, Object> constObjs = children.get(i);

			final int j = i;
			logger.trace("Creating layer panel with index {} and attributes {}; constraints are {}", () -> j,
					() -> attrs, () -> GUIHelper.toString((GridBagConstraints) constObjs.getValue()));

			pane.add(constObjs.getKey(), new LXLayeredPane.StackConstraints(i, constObjs.getValue()));
		}

		return pane;
	}

	private static JLayer<JComponent> createLayered(List<KeyValue<JComponent, Object>> children,
			Map<String, Object> attrs) {
		if (children.size() != 1) {
			throw new IllegalArgumentException(
					"Cannot create overlay with children " + children + ". Exactly one child is allowed");
		}
		JComponent child = children.get(0).getKey();
		BigDecimal transparency = get(TRANSPARENCY_NAME, attrs, bd(0.0));
		LXLayerUI<JComponent> layer = new LXLayerUI<>(transparency.floatValue());
		ifExistsDo(ACTIVE_NAME, attrs, (Boolean b) -> layer.setActive(b));
		JLayer<JComponent> jlayer = new JLayer<>(child, layer);
		return jlayer;
	}

	public static JTabbedPane createTabbedPane(List<KeyValue<JComponent, Object>> children, Map<String, Object> attrs) {
		JTabbedPane pane = new JTabbedPane();
		processComponent(pane, attrs);
		ifExistsDo(TABPLACEMENT_NAME, attrs, (Integer i) -> pane.setTabPlacement(i));

		for (KeyValue<JComponent, Object> tabDesc : children) {
			String title = (String) tabDesc.getValue();
			pane.addTab(title, tabDesc.getKey());
		}

		return pane;
	}

	public static JScrollPane createScroll(JComponent c, Map<String, Object> attrs) {
		JScrollPane pane = new JScrollPane(c);
		pane.setBorder(null);
		processComponent(pane, attrs);
		ifExistsDo(VIEWCOLOR_NAME, attrs, (Color c1) -> pane.getViewport().setBackground(c1));
		ifExistsDo(OPAQUE_NAME, attrs, (Boolean b) -> pane.getViewport().setOpaque(b));

		return pane;
	}

	public static <T> JComboBox<T> createComboBox(Map<String, Object> attrs) {
		JComboBox<T> box = new JComboBox<>();
		processComponent(box, attrs);
		return box;
	}

	public static void processLabel(JLabel label, Map<String, Object> attrs) {
		ifExistsDo(LABEL_FOR, attrs, (JComponent c) -> label.setLabelFor(c));
		ifExistsDo(TEXT_NAME, attrs, (String s) -> label.setText(s));
		ifExistsDo(ALIGNMENT_NAME, attrs, (Integer i) -> label.setHorizontalAlignment(i));
	}

	public static void processTable(LXTable<? extends LXTableModel> table, Map<String, Object> attrs) {
		table.setDoubleBuffered(true);
		table.setFont(table.getFont().deriveFont(Font.BOLD));
		table.getTableHeader().setFont(table.getFont());
		ifExistsDo(GRIDCOLOR_NAME, attrs, (Color c) -> table.setGridColor(c));
		ifExistsDo(ROWHEIGHT_NAME, attrs, (Integer i) -> table.setRowHeight(i));
		ifExistsDo(MODEL_NAME, attrs, (List<Descriptor> d) -> table.getModel().setColumns(d));
	}

	public static void processPanel(JComponent panel, Map<String, Object> attrs) {
		ifExistsDo(LAYOUT_NAME, attrs, (LayoutManager l) -> panel.setLayout(l));
	}

	public static void processComponent(JComponent c, Map<String, Object> attrs) {
		processComponent(c, attrs, new ArrayList<>());
	}

	public static void processComponent(JComponent c, Map<String, Object> attrs, List<String> ignore) {
		ifExistsDo(MINSIZE_NAME, attrs, ignore,
				(List<Integer> i) -> c.setMinimumSize(new Dimension(i.get(0), i.get(1))));
		ifExistsDo(MAXSIZE_NAME, attrs, ignore,
				(List<Integer> i) -> c.setMaximumSize(new Dimension(i.get(0), i.get(1))));
		ifExistsDo(PREFSIZE_NAME, attrs, ignore,
				(List<Integer> i) -> c.setPreferredSize(new Dimension(i.get(0), i.get(1))));

		ifExistsDo(SIZE_NAME, attrs, ignore, (List<Integer> i) -> c.setSize(i.get(0), i.get(1)));
		ifExistsDo(LOCATION_NAME, attrs, ignore, (List<Integer> i) -> c.setLocation(i.get(0), i.get(1)));

		ifExistsDo(ONKEY_NAME, attrs, ignore, (Map<String, Var<?>> m) -> m.forEach((k, v) -> setBinding(c, k, v)));
		ifExistsDo(BACKGROUND_NAME, attrs, ignore, (Color c1) -> c.setBackground(c1));
		ifExistsDo(FOREGROUND_NAME, attrs, ignore, (Color c1) -> c.setForeground(c1));
		ifExistsDo(BORDER_NAME, attrs, ignore, (Border b) -> c.setBorder(b));
		ifExistsDo(FONT_NAME, attrs, ignore, (Font f) -> c.setFont(f));
		ifExistsDo(OPAQUE_NAME, attrs, ignore, (Boolean b) -> c.setOpaque(b));
		ifExistsDo(VISIBLE_NAME, attrs, ignore, (Boolean b) -> c.setVisible(b));
	}

	private static void setBinding(JComponent comp, String key, Var<?> code) {
		Action a = new AbstractAction() {
			private static final long serialVersionUID = -1221076732554940398L;

			@Override
			public void actionPerformed(ActionEvent e) {
				runInJAWT(() -> code.getValue());
			}
		};
		String actionName = key.toLowerCase();
		KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
		comp.getActionMap().put(actionName, a);
		comp.getInputMap(JComponent.WHEN_FOCUSED).put(keyStroke, actionName);
	}

	public static boolean isBlock(String name) {
		return ACTION_NAME.equals(name);
	}

	public static boolean isSwingConstant(String name) {
		return ALIGNMENT_NAME.equals(StringHelper.essential(name)) || TABPLACEMENT_NAME.equals(name);
	}

	public static boolean isVerbatim(String name) {
		return TEXT_NAME.equals(StringHelper.essential(name)) || TITLE_NAME.equals(StringHelper.essential(name))
				|| IMAGE_NAME.equals(name) || ECHO_NAME.equals(StringHelper.essential(name))
				|| HINT_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isNumber(String name) {
		return TRANSPARENCY_NAME.equals(name) || OFFSET_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isSize(String name) {
		return MINSIZE_NAME.equals(StringHelper.essential(name)) || MAXSIZE_NAME.equals(StringHelper.essential(name))
				|| PREFSIZE_NAME.equals(StringHelper.essential(name)) || SIZE_NAME.equals(StringHelper.essential(name))
				|| LOCATION_NAME.equals(StringHelper.essential(name))
				|| ELLIPTICITY_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isQuantity(String name) {
		return ROWHEIGHT_NAME.equals(name) || ROUNDNESS_NAME.equals(name);
	}

	public static boolean isBoolean(String name) {
		return ACTIVE_NAME.equals(name) || DECORATED_NAME.equals(name)
				|| OPAQUE_NAME.equals(StringHelper.essential(name)) || VISIBLE_NAME.equals(StringHelper.essential(name))
				|| FOCUSABLE_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isFont(String name) {
		return FONT_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isBorder(String name) {
		return BORDER_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isColor(String name) {
		return BACKGROUND_NAME.equals(StringHelper.essential(name))
				|| FOREGROUND_NAME.equals(StringHelper.essential(name)) || VIEWCOLOR_NAME.equals(name)
				|| GRIDCOLOR_NAME.equals(name) || HINTCOLOR_NAME.equals(StringHelper.essential(name));
	}

	public static boolean isPanel(String tag) {
		return TITLEDPANEL_TAG.equals(tag) || PANEL_TAG.equals(tag) || MULTILAYER_TAG.equals(tag);
	}

	public static boolean isDescriptor(String tag, String name) {
		return MODEL_NAME.equals(StringHelper.essential(name));
	}

	public static void throwAttr(String name, String value, String format) {
		throw new IllegalArgumentException(
				"Malformed '" + name + "'-attribute " + value + ", expected '" + format + "'");
	}

	public static void validate(String name, Attribute attr, Collection<String> expValues, int expNrParams,
			String expFormat) {
		if (attr == null) {
			throw new IllegalArgumentException("Attribute '" + name + "' is null.");
		}
		if ((expValues != null && !expValues.contains(attr.getValue())) //
				|| attr.getParams().size() != expNrParams) {
			throwAttr(name, attr.toString(), expFormat);
		}
	}

	public static void validate(String name, Attribute attr, String expValue, int expNrParams, String expFormat) {
		List<String> allowedOption = expValue == null ? null : asList(expValue);
		validate(name, attr, allowedOption, expNrParams, expFormat);
	}

	public static String getFormat(Enumeration enums) {
		StringBuffer sb = new StringBuffer(500);
		appendList(sb, enums.toMap().keySet(), "[", "]", " | ", (s, b) -> b.append(s));
		return sb.toString();
	}

	public static String getFormat(LXClass<?> type, int size) {
		List<String> types = new ArrayList<>(size);
		for (int i = 0; i < size; ++i) {
			types.add(type.toString());
		}
		StringBuffer sb = new StringBuffer(500);
		appendList(sb, types);
		return sb.toString();
	}
}
