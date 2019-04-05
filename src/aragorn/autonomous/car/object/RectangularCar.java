package aragorn.autonomous.car.object;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import aragorn.math.geometry.Coordinate2D;
import aragorn.math.geometry.Paintable;
import aragorn.util.MathVector2D;

public class RectangularCar extends Car {

	public RectangularCar() {
		this(6, 3, new CarStatus(new Point2D.Double(0, 0), 90, 0));
	}

	private RectangularCar(double length, double width, CarStatus status) {
		super(length, width, status);
	}

	@Override
	protected void drawCarBody(Graphics g, Coordinate2D c) {
		MathVector2D front_vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection()), getLength() / 2.0);
		MathVector2D backk_vector = front_vector.getNegative();
		MathVector2D leftt_vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection() + Math.PI / 2.0), getWidth() / 2.0);
		MathVector2D right_vector = leftt_vector.getNegative();

		Point2D.Double leftt_backk_point = MathVector2D.add(getStatus().getLocation(), backk_vector, leftt_vector);
		Point2D.Double leftt_front_point = MathVector2D.add(getStatus().getLocation(), front_vector, leftt_vector);
		Point2D.Double right_backk_point = MathVector2D.add(getStatus().getLocation(), backk_vector, right_vector);
		Point2D.Double right_front_point = MathVector2D.add(getStatus().getLocation(), front_vector, right_vector);

		Paintable.drawLine(g, c, leftt_backk_point, leftt_front_point);
		Paintable.drawLine(g, c, leftt_front_point, right_front_point);
		Paintable.drawLine(g, c, right_front_point, right_backk_point);
		Paintable.drawLine(g, c, right_backk_point, leftt_backk_point);
	}

	@Override
	public Rectangle2D.Double getBounds() {
		MathVector2D front_vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection()), getLength() / 2.0);
		MathVector2D right_vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection() - Math.PI / 2.0), getWidth() / 2.0);

		double x = getStatus().getLocation().getX() - Math.abs(front_vector.getX()) - Math.abs(right_vector.getX());
		double y = getStatus().getLocation().getY() - Math.abs(front_vector.getY()) - Math.abs(right_vector.getY());
		double width = getStatus().getLocation().getX() + Math.abs(front_vector.getX()) + Math.abs(right_vector.getX()) - x;
		double height = getStatus().getLocation().getY() + Math.abs(front_vector.getY()) + Math.abs(right_vector.getY()) - y;
		return new Rectangle2D.Double(x, y, width, height);
	}

	@Override
	public double getFrontSensorOffset() {
		return getLength() / 2;
	}

	@Override
	public double getLeftSensorOffset() {
		return Math.min(getLength(), getWidth()) / Math.sqrt(2);
	}

	@Override
	public double getRightSensorOffset() {
		return Math.min(getLength(), getWidth()) / Math.sqrt(2);
	}
}