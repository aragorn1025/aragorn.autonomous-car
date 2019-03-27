package aragorn.autonomous.car.old.math.operation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import aragorn.gui.Coordinate2D;
import aragorn.gui.Paintable;

public class GeometryParallelogram implements Paintable {
	public static int				POINTS_NUMBER	= 4;
	Point2D.Double					point			= new Point2D.Double();
	private static MathVector._2D[]	vectors			= new MathVector._2D[2];

	/**
	 * Constructs and initializes an empty {@code Parallelogram}.
	 */
	public GeometryParallelogram() {
		this(new Point2D.Double(), new MathVector._2D(), new MathVector._2D());
	}

	/**
	 * Constructs and initializes a {@code Parallelogram} from the specified parameters.
	 * 
	 * @param point
	 *            the origin point
	 * @param vector0
	 *            the first vector
	 * @param vector1
	 *            the second vector
	 */
	public GeometryParallelogram(Point2D.Double point, MathVector._2D vector0, MathVector._2D vector1) {
		if (vector0.getDimension() != 2 || vector1.getDimension() != 2) {
			throw new InvalidParameterException("The dimension of input vectors should be 2.");
		}
		this.point.setLocation(point);
		vectors[0] = vector0;
		vectors[1] = vector1;
	}

	public Rectangle getBounds() {
		double[] xPoints = new double[POINTS_NUMBER];
		double[] yPoints = new double[POINTS_NUMBER];
		for (int i = 0; i < POINTS_NUMBER; i++) {
			Point2D.Double point = getPoint(i);
			xPoints[i] = point.getX();
			yPoints[i] = point.getY();
		}
		int x = (int) Math.floor(MathOperation.getArrayMin(xPoints, 0, POINTS_NUMBER));
		int y = (int) Math.floor(MathOperation.getArrayMin(yPoints, 0, POINTS_NUMBER));
		int width = (int) Math.ceil(MathOperation.getArrayMax(xPoints, 0, POINTS_NUMBER)) - x;
		int height = (int) Math.ceil(MathOperation.getArrayMax(yPoints, 0, POINTS_NUMBER)) - y;
		return new Rectangle(x, y, width, height);
	}

	public Point2D.Double getPoint(int index) {
		if (index >= 4) {
			throw new ArrayIndexOutOfBoundsException("The index is out of bounds at getPoint(int).");
		}
		double x = point.getX() + ((index + 1) / 2 % 2) * vectors[0].getX() + (index / 2) * vectors[1].getX();
		double y = point.getY() + ((index + 1) / 2 % 2) * vectors[0].getY() + (index / 2) * vectors[1].getY();
		return new Point2D.Double(x, y);
	}

	public boolean isInside(Point2D.Double point) {
		double delta = MathOperation.determinant(vectors[0].getX(), vectors[0].getY(), vectors[1].getX(), vectors[1].getY());
		if (delta == 0) {
			return false;
		}
		MathVector._2D vector = new MathVector._2D(this.point, point);
		double a = MathOperation.determinant(vector.getX(), vector.getY(), vectors[1].getX(), vectors[1].getY()) / delta;
		if (a < 0 || a > 1) {
			return false;
		}
		double b = MathOperation.determinant(vectors[0].getX(), vectors[0].getY(), vector.getX(), vector.getY()) / delta;
		if (b < 0 || b > 1) {
			return false;
		}
		return true;
	}

	@Override
	public void paint(Graphics g, Coordinate2D c) {
		int[] x = new int[POINTS_NUMBER];
		int[] y = new int[POINTS_NUMBER];
		for (int i = 0; i < POINTS_NUMBER; i++) {
			Point2D.Double point = getPoint(i);
			x[i] = (int) c.toX(point.getX());
			y[i] = (int) c.toY(point.getY());
		}
		for (int i = 1; i < POINTS_NUMBER; i++) {
			g.drawLine(x[i - 1], y[i - 1], x[i], y[i]);
		}
		g.drawLine(x[POINTS_NUMBER - 1], y[POINTS_NUMBER - 1], x[0], y[0]);
	}
}