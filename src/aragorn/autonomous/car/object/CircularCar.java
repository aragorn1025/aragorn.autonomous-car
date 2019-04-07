package aragorn.autonomous.car.object;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import aragorn.math.geometry.Coordinate2D;
import aragorn.util.MathVector2D;

public class CircularCar extends Car {

	public CircularCar() {
		this(3, new Point2D.Double(), Math.toRadians(90));
	}

	private CircularCar(double radius, Point2D.Double location, double direction) {
		super(radius * 2, radius * 2, new CarStatus(location, direction, 0));
	}

	@Override
	protected void drawCarBody(Graphics g, Coordinate2D c) {
		Rectangle2D bounds = getBounds();
		Point2D.Double reference_point = c.convertToPanel(new Point2D.Double(bounds.getX(), bounds.getY() + getRadius() * 2));
		MathVector2D size_vector = c.convertToPanel(new MathVector2D(bounds.getWidth(), -bounds.getHeight()));
		g.drawOval((int) reference_point.x, (int) reference_point.y, (int) size_vector.getX(), (int) size_vector.getY());
	}

	@Override
	public Rectangle2D.Double getBounds() {
		double x = getStatus().getLocation().getX() - getRadius();
		double y = getStatus().getLocation().getY() - getRadius();
		double w = getStatus().getLocation().getX() + getRadius() - x;
		double h = getStatus().getLocation().getY() + getRadius() - y;
		return new Rectangle2D.Double(x, y, w, h);
	}

	@Override
	public double getFrontSensorOffset() {
		return getRadius();
	}

	@Override
	public double getLeftSensorOffset() {
		return getRadius();
	}

	private double getRadius() {
		return getWidth() / 2.0;
	}

	@Override
	public double getRightSensorOffset() {
		return getRadius();
	}
}