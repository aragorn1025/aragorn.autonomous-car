package aragorn.autonomous.car.old.math.operation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import aragorn.gui.Coordinate2D;
import aragorn.gui.Paintable;

public class GeometryPolyline implements Paintable {
	private ArrayList<Point2D.Double>	points	= new ArrayList<>();
	private int							nPoints	= 0;

	/**
	 * Constructs and initializes an empty {@code Polyline}.
	 */
	public GeometryPolyline() {
		this(null, 0);
	}

	/**
	 * Constructs and initializes a {@code Polyline} from the specified parameters.
	 * 
	 * @param points
	 *            an array of the points
	 * @param nPoints
	 *            the total number of points in the {@code Polyline}
	 * @throws NegativeArraySizeException
	 *             if the value of {@code nPoints} is negative
	 * @throws NullPointerException
	 *             if points is null and nPoints is not zero
	 * @throws IndexOutOfBoundsException
	 *             if {@code nPoints} is greater than the length of {@code xPoints} or the length of {@code yPoints}
	 */
	public GeometryPolyline(Point2D.Double[] points, int nPoints) {
		if (nPoints < 0) {
			throw new NegativeArraySizeException();
		} else if (nPoints != 0 && points == null) {
			throw new NullPointerException();
		} else if (points != null && nPoints > points.length) {
			throw new IndexOutOfBoundsException();
		}
		this.nPoints = 0;
		for (int i = 0; i < nPoints; i++) {
			addPoint(points[i]);
		}
	}

	public void addPoint(double x, double y) {
		points.add(new Point2D.Double(x, y));
		nPoints++;
	}

	public void addPoint(Point2D.Double point) {
		this.addPoint(point.getX(), point.getY());
	}

	@Override
	public Rectangle getBounds() {
		if (getPointsNumber() == 0) {
			return new Rectangle();
		} else {
			double[] xPoints = new double[getPointsNumber()];
			double[] yPoints = new double[getPointsNumber()];
			for (int i = 0; i < getPointsNumber(); i++) {
				xPoints[i] = points.get(i).getX();
				yPoints[i] = points.get(i).getY();
			}
			int x = (int) Math.floor(MathOperation.getArrayMin(xPoints, 0, getPointsNumber()));
			int y = (int) Math.floor(MathOperation.getArrayMin(yPoints, 0, getPointsNumber()));
			int width = (int) Math.ceil(MathOperation.getArrayMax(xPoints, 0, getPointsNumber())) - x;
			int height = (int) Math.ceil(MathOperation.getArrayMax(yPoints, 0, getPointsNumber())) - y;
			return new Rectangle(x, y, width, height);
		}
	}

	public Point2D.Double getPoint(int index) {
		if (index >= getPointsNumber()) {
			throw new NullPointerException("The index is out of bounds at getPoint(int).");
		}
		return points.get(index);
	}

	@Override
	public void paint(Graphics g, Coordinate2D c) {
		if (getPointsNumber() != 0) {
			int[] x = new int[getPointsNumber()];
			int[] y = new int[getPointsNumber()];
			for (int i = 0; i < getPointsNumber(); i++) {
				x[i] = (int) c.toX(points.get(i).getX());
				y[i] = (int) c.toY(points.get(i).getY());
			}
			for (int i = 1; i < getPointsNumber(); i++) {
				g.drawLine(x[i - 1], y[i - 1], x[i], y[i]);
			}
		}
	}

	public int getPointsNumber() {
		return nPoints;
	}
}