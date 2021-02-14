package ms.gui.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

public class GUIHelper {
    private static final Logger logger = LogManager.getLogger();

    public static final Color HEADER_BORDER_COLOR = Color.white.darker();
    public static final Color LIGHT_BLUE = new Color(0.7f, 0.91f, 1.0f);
    public static final Color HIGHLIGHT_BLUE = new Color(0.93f, 0.93f, 1.0f);

    public static final Color STAINED_WHITE = new Color(0.98f, 0.98f, 0.98f);
    public static final Color THUNDER_GRAY = new Color(0.87f, 0.87f, 0.87f);
    public static final Color DUST_GRAY = new Color(0.94f, 0.94f, 0.94f);

    public static final Color DEFAULT_BACKGROUND = DUST_GRAY;
    public static final Color DEFAULT_FOREGROUND = new Color(60, 60, 60);
    public static final Color EMPTY_TABLE = STAINED_WHITE;

    public static final Color BLEAK_BROWN = new Color(251, 238, 221);

    private static ThreadPoolExecutor executor
            = new ThreadPoolExecutor(1,
                                     1,
                                     2000,
                                     TimeUnit.MILLISECONDS,
                                     new LinkedBlockingQueue<Runnable>(100),
                                     r -> new Thread(r, "LXStack"));

    public static ImageIcon loadResImage(String path) {
        URL url = GUIHelper.class.getResource(path);
        if (url == null) {
            logger.error("Cannot initialise image at '{}'", path);
            return new ImageIcon();
        } else {
            return new ImageIcon(url);
        }
    }

    public static void runInAWT(Runnable r) {
        try {
            SwingUtilities.invokeAndWait(r);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();

            if (t instanceof IllegalArgumentException) {
                logger.warn("An exception occured in the AWT-thread:" + t.getMessage(), t);
            } else {
                logger.error("An unexpected error occured in the AWT-thread", t);
            }
        } catch (InterruptedException e) {
            logger.warn(e);
        }
    }

    public static void runInJAWT(Runnable r) {
        executor.execute(r);
    }

    public static GridBagConstraints gbc(int x,
                                         int y,
                                         int xwidth,
                                         int ywidth,
                                         int fill,
                                         double weightx,
                                         double weighty) {
        return gbc(x, y, xwidth, ywidth, 0, 0, 0, 0, fill, weightx, weighty);
    }

    public static GridBagConstraints gbc(int x,
                                         int y,
                                         int xwidth,
                                         int ywidth,
                                         int tInset,
                                         int lInset,
                                         int bInset,
                                         int rInset,
                                         int fill,
                                         double weightx,
                                         double weighty) {
        return gbc(x,
                   y,
                   xwidth,
                   ywidth,
                   tInset,
                   lInset,
                   bInset,
                   rInset,
                   fill,
                   weightx,
                   weighty,
                   0,
                   0);
    }

    public static GridBagConstraints gbc(int x,
                                         int y,
                                         int xwidth,
                                         int ywidth,
                                         int tInset,
                                         int lInset,
                                         int bInset,
                                         int rInset,
                                         int fill,
                                         double weightx,
                                         double weighty,
                                         int ipadx,
                                         int ipady) {
        return new GridBagConstraints(x,
                                      y,
                                      xwidth,
                                      ywidth,
                                      weightx,
                                      weighty,
                                      GridBagConstraints.CENTER,
                                      fill,
                                      new Insets(tInset, lInset, bInset, rInset),
                                      ipadx,
                                      ipady);
    }

    public static String toString(GridBagConstraints c) {
        return "{pos=[" + c.gridx + "," + c.gridy + "], size=[" + c.gridwidth + "," + c.gridheight
               + "], insets=" + c.insets + ", weight=[" + c.weightx + "," + c.weighty + "], fill="
               + c.fill + "}";
    }

    public static void drawImage(JComponent component, Image image, Graphics g) {
        Rectangle visibleRect = component.getVisibleRect();
        int contWidth = visibleRect.width;
        int contHeight = visibleRect.height;

        int imWidth = image.getWidth(null);
        int imHeight = image.getHeight(null);
        Point start = GUIHelper.getIdealCenter(contWidth, contHeight, imWidth, imHeight);
        g.drawImage(image,
                    visibleRect.x + start.x,
                    visibleRect.y + start.y,
                    imWidth,
                    imHeight,
                    component);
    }

    public static Point getIdealCenter(int containerWidth,
                                       int containerHeight,
                                       int objectWidth,
                                       int objectHeight) {
        int idealX = (containerWidth < objectWidth) ? 0 : (containerWidth - objectWidth) >> 1;
        int idealY = (containerHeight < objectHeight) ? 0 : (containerHeight - objectHeight) >> 1;
        return new Point(idealX, idealY);
    }

    public static JLabel createLabel(String caption) {
        JLabel input = createLabel(caption, null, 150);
        input.setPreferredSize(new Dimension(150, 30));
        input.setFont(input.getFont().deriveFont(Font.BOLD | Font.ITALIC, 14.f));
        return input;
    }

    public static void addKeyValue(JPanel panel, String caption, JComponent field, int capWidth) {
        JLabel label = createLabel(caption, field, capWidth);
        panel.add(label);
        panel.add(field);
    }

    public static JLabel createLabel(String caption, Component forComponent, int capWidth) {
        JLabel label = new JLabel(caption);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setLabelFor(forComponent);
        label.setPreferredSize(new Dimension(capWidth, 20));
        label.setMinimumSize(new Dimension(capWidth, 20));
        return label;
    }

    public static JXDatePicker createDatePicker(Date initDate) {
        JXDatePicker datePicker = new JXDatePicker(initDate);
        datePicker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
        return datePicker;
    }

    public static JSpinner createTimeSpinner(Date initTime) {
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(initTime); // will only show the current time
        timeSpinner.setPreferredSize(new Dimension(120, 26));

        return timeSpinner;
    }

    public static JTextField createTextField(boolean editable) {
        return createTextField(editable, "-");
    }

    public static JTextField createTextField(boolean editable, String initText) {
        return formatTextField(new JTextField(), initText, editable);
    }

    public static JTextField formatTextField(JTextField field, String caption, boolean editable) {
        return formatTextField(field, caption, 90, editable);
    }

    public static JTextField formatTextField(JTextField field,
                                             String caption,
                                             int width,
                                             boolean editable) {
        field.setText(caption);
        field.setEditable(editable);
        field.setPreferredSize(new Dimension(width, 20));
        field.setMinimumSize(new Dimension(width, 20));
        field.setBackground(Color.WHITE);
        return field;
    }

    public static void fixSize(JComponent component, Dimension size) {
        component.setPreferredSize(size);
        component.setMinimumSize(size);
        component.setMaximumSize(size);
    }

    public static Color createRedGreen(double value, double negCap, double posCap) {
        int red = 255;
        int green = 255;
        int blue = 255;
        if (negCap < 0) {
            throw new IllegalArgumentException("Negative cap " + negCap
                                               + " is negative but must be positive!");
        }
        if (posCap < 0) {
            throw new IllegalArgumentException("Positive cap " + posCap
                                               + " is negative but must be positive!");
        }
        if (value > 0) {
            double factor = Math.min(1.0, Math.abs(value) / posCap);
            red = (int) (255 * (1. - factor) + 146 * factor);
            green = (int) (255 * (1. - factor) + 202 * factor);
            blue = (int) (255 * (1. - factor) + 152 * factor);
        } else {
            double factor = Math.min(1.0, Math.abs(value) / negCap);
            red = (int) (255 * (1. - factor) + 204 * factor);
            green = (int) (255 * (1. - factor) + 103 * factor);
            blue = (int) (255 * (1. - factor) + 103 * factor);
        }
        return new Color(red, green, blue);
    }
}
