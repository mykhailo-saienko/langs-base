package ms.gui.comp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LXHint extends LXTransparentPanel {

	private static final long serialVersionUID = -6030234452435904204L;
	private static final Logger logger = LogManager.getLogger();

	private static final int LEFT = SwingConstants.LEFT;
	private static final int TOP = SwingConstants.TOP;
	private static final int RIGHT = SwingConstants.RIGHT;
	private static final int BOTTOM = SwingConstants.BOTTOM;

	private int radiusX, radiusY;

	private int oldWidth;
	private int oldHeight;

	private final SideProps left, top, right, bottom;
	private LineBorder border;

	private final JLabel label;

	private static class SideProps {
		public Dimension arrow;
		public float arrowPosition;

		public int gap;

		public SideProps(int arrowWidth, int arrowHeight, float arrowPosition, int gap) {
			setArrow(arrowWidth, arrowHeight, arrowPosition);
			this.gap = gap;
		}

		public void setArrow(int arrowWidth, int arrowHeight, float arrowPosition) {
			arrow = new Dimension(arrowWidth, arrowHeight);
			this.arrowPosition = arrowPosition;
		}
	}

	public LXHint() {
		setBackground(Color.WHITE);

		oldWidth = oldHeight = 0;
		left = new SideProps(0, 0, 0, 0);
		top = new SideProps(0, 0, 0, 0);
		right = new SideProps(0, 0, 0, 1);
		bottom = new SideProps(0, 0, 0, 1);

		label = new JLabel("Test text");
		label.setHorizontalAlignment(JLabel.CENTER);

		setLineBorder(new LineBorder(GUIHelper.HEADER_BORDER_COLOR));
		setRadius(12);

		add(label);
	}

	public void setLineBorder(LineBorder border) {
		this.border = border;
	}

	public LineBorder getLineBorder() {
		return border;
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (label != null) {
			label.setFont(font);
		}
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (label != null) {
			label.setForeground(fg);
		}
	}

	public Dimension getArrowDim(int side) {
		return getSide(side) == null ? new Dimension(0, 0) : getSide(side).arrow;
	}

	public void setArrowDim(int side, Dimension arrow) {
		if (getSide(side) != null) {
			getSide(side).arrow = arrow;
		}
	}

	public float getArrowPos(int side) {
		return getSide(side) == null ? 0.0f : getSide(side).arrowPosition;
	}

	public void setArrowPos(int side, float position) {
		if (getSide(side) != null) {
			getSide(side).arrowPosition = position;
		}
	}

	public void setRadius(int radius) {
		setRadii(radius, radius);
	}

	public void setRadii(int radiusX, int radiusY) {
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		label.setLocation(getTotalGap(LEFT) + radiusX, getTotalGap(TOP) + radiusY);
	}

	public JLabel toLabel() {
		return label;
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		tryResize(width, height);

		final Graphics2D graphics2D = (Graphics2D) g;
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHints(qualityHints);

		int leftX = getTotalGap(LEFT);
		int topY = getTotalGap(TOP);
		int rightX = width - getTotalGap(RIGHT);
		int bottomY = height - getTotalGap(BOTTOM);

		GeneralPath path = new GeneralPath();
		path.moveTo(leftX, topY + radiusY);
		path.curveTo(leftX, topY + radiusY, leftX, topY, leftX + radiusX, topY);

		// top arrow
		int[] arrow = computeArrow(leftX + radiusX, rightX - radiusX, getArrowDim(TOP), getArrowPos(TOP));
		path.lineTo(arrow[0], topY);
		path.lineTo(arrow[1], topY - getArrowDim(TOP).height);
		path.lineTo(arrow[2], topY);
		path.lineTo(rightX - radiusX, topY);

		path.curveTo(rightX - radiusX, topY, rightX, topY, rightX, topY + radiusY);

		// right arrow
		arrow = computeArrow(topY + radiusY, bottomY - radiusY, getArrowDim(RIGHT), getArrowPos(RIGHT));
		path.lineTo(rightX, arrow[0]);
		path.lineTo(rightX + getArrowDim(RIGHT).height, arrow[1]);
		path.lineTo(rightX, arrow[2]);
		path.lineTo(rightX, bottomY - radiusY);

		path.curveTo(rightX, bottomY - radiusY, rightX, bottomY, rightX - radiusX, bottomY);

		// bottom arrow
		arrow = computeArrow(leftX + radiusX, rightX - radiusX, getArrowDim(BOTTOM), getArrowPos(BOTTOM));
		path.lineTo(arrow[2], bottomY);
		path.lineTo(arrow[1], bottomY + getArrowDim(BOTTOM).height);
		path.lineTo(arrow[0], bottomY);
		path.lineTo(leftX + radiusX, bottomY);

		path.curveTo(leftX + radiusX, bottomY, leftX, bottomY, leftX, bottomY - radiusY);

		// left arrow
		arrow = computeArrow(topY + radiusY, bottomY - radiusY, getArrowDim(LEFT), getArrowPos(LEFT));
		path.lineTo(leftX, arrow[2]);
		path.lineTo(leftX - getArrowDim(LEFT).height, arrow[1]);
		path.lineTo(leftX, arrow[0]);
		path.lineTo(leftX, topY + radiusY);
		path.closePath();

		graphics2D.setPaint(getBackground());
		graphics2D.fill(path);
		if (getLineBorder() != null) {
			graphics2D.setPaint(getLineBorder().getLineColor());
			graphics2D.setStroke(new BasicStroke(getLineBorder().getThickness()));
			graphics2D.draw(path);
		}
	}

	private void tryResize(int width, int height) {
		if (width == oldWidth && height == oldHeight) {
			return;
		}
		logger.trace("Resizing from [{},{}] to [{},{}]", oldWidth, oldHeight, width, height);
		oldWidth = width;
		oldHeight = height;

		// do other stuff.
		label.setSize(width - 2 * radiusX - getTotalGap(LEFT) - getTotalGap(RIGHT),
				height - 2 * radiusY - getTotalGap(TOP) - getTotalGap(BOTTOM));

		logger.trace("Size=[{},{}], label bounds {}, radius=[{},{}]" + ", gaps=[{},{},{},{}]", () -> width,
				() -> height, () -> label.getBounds(), () -> radiusX, () -> radiusY, () -> getTotalGap(TOP),
				() -> getTotalGap(LEFT), () -> getTotalGap(BOTTOM), () -> getTotalGap(RIGHT));
	}

	private int[] computeArrow(int lineBegin, int lineEnd, Dimension arrowDim, float arrowPos) {
		if (lineEnd < lineBegin) {
			throw new IllegalArgumentException("Line end " + lineEnd + " is smaller than line begin " + lineBegin);
		}
		int aWidth = arrowDim.width;
		int lWidth = lineEnd - lineBegin;
		// arrow cannot be wider than the line it sits on.
		if (aWidth > lWidth) {
			aWidth = lWidth;
		}

		int effectiveLength = lWidth - aWidth;
		int aBegin = lineBegin + (int) (arrowPos * effectiveLength);
		int aMiddle = aBegin + aWidth / 2;
		int aEnd = aBegin + aWidth;
		return new int[] { aBegin, aMiddle, aEnd };
	}

	private SideProps getSide(int side) {
		switch (side) {
		case LEFT:
			return left;
		case TOP:
			return top;
		case RIGHT:
			return right;
		case BOTTOM:
			return bottom;
		default:
			return null;
		}
	}

	private int getGap(int side) {
		return getSide(side) == null ? 0 : getSide(side).gap;
	}

	/**
	 * Total gap is the height of the arrow + internal padding.
	 * 
	 * @param side
	 * @return
	 */
	private int getTotalGap(int side) {
		return getGap(side) + getArrowDim(side).height;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				JFrame frame = new JFrame("Testing");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new GridBagLayout());
				frame.getContentPane().setBackground(GUIHelper.THUNDER_GRAY);

				LXHint tp = new LXHint();
				tp.setPreferredSize(new Dimension(300, 188));

				GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
				frame.add(tp, c);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
