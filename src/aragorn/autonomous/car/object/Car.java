package aragorn.autonomous.car.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.security.InvalidParameterException;

import aragorn.math.geometry.ConvexQuadrilateral2D;
import aragorn.math.geometry.Coordinate2D;
import aragorn.math.geometry.Paintable;
import aragorn.util.MathVector2D;

public abstract class Car implements Cloneable, Paintable {

	/** The arc angle for {@code paintWheelDirectionRange(...)} in degree. */
	private static final int ARC_DEGREE = (int) Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE - CarStatus.MIN_WHEEL_ANGLE);

	private double length;

	private double width;

	private CarStatus status;

	/** The parameter for drawing car body. */
	private double length_bar;

	/** The parameter for drawing car body. */
	private double widthh_bar;

	/** The parameter for drawing car wheel. */
	private double wheel_range;

	/** The parameter for drawing car wheel. */
	private double wheel_range_bar;

	protected Car(double length, double width, CarStatus status) {
		this.length = length;
		this.width = width;
		this.status = (CarStatus) status.clone();

		// initial parameters for paint car
		double bar = Math.min(length / 5.0, 5.0);
		wheel_range = Math.min(length * 3.0 / 4.0, 5.0);
		length_bar = length + bar * 2;
		widthh_bar = width + bar * 2;
		wheel_range_bar = wheel_range + bar;
	}

	@Override
	protected Object clone() {
		Car val = null;
		try {
			val = (Car) super.clone();
			val.status = (CarStatus) val.status.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		return val;
	}

	@Override
	public void draw(Graphics g, Coordinate2D c) {
		g.setColor(Color.LIGHT_GRAY);
		drawCarBodyCrossLine(g, c);
		drawWheelDirectionRange(g, c);
		g.setColor(Color.BLUE);
		drawCarBody(g, c);
		g.setColor(Color.BLUE);
		drawWheelDirection(g, c);
	}

	protected abstract void drawCarBody(Graphics g, Coordinate2D c);

	private void drawCarBodyCrossLine(Graphics g, Coordinate2D c) {
		MathVector2D front_bar_vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection()), length_bar / 2.0);
		MathVector2D right_bar_vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection() - Math.PI / 2.0), widthh_bar / 2.0);

		Paintable.drawLine(g, c, MathVector2D.add(status.getLocation(), front_bar_vector), MathVector2D.add(status.getLocation(), front_bar_vector.getNegative()));
		Paintable.drawLine(g, c, MathVector2D.add(status.getLocation(), right_bar_vector), MathVector2D.add(status.getLocation(), right_bar_vector.getNegative()));
	}

	private void drawDirection(Graphics g, Coordinate2D c) {
		MathVector2D vector = MathVector2D.getScalarMultiply(new MathVector2D(getStatus().getDirection()), wheel_range_bar);
		Paintable.drawLine(g, c, status.getLocation(), vector);
	}

	public void drawShadow(Graphics g, Coordinate2D c, CarStatus status) {
		Car val = (Car) clone();
		val.status = status;

		g.setColor(Color.LIGHT_GRAY);
		val.drawCarBody(g, c);
		val.drawDirection(g, c);
	}

	private void drawWheelDirection(Graphics g, Coordinate2D c) {
		MathVector2D v = MathVector2D.getScalarMultiply(new MathVector2D(status.getDirection() + status.getWheelAngle()), wheel_range_bar);
		Paintable.drawLine(g, c, status.getLocation(), v);
	}

	private void drawWheelDirectionRange(Graphics g, Coordinate2D c) {
		MathVector2D right_bar_vector = MathVector2D.getScalarMultiply(new MathVector2D(status.getDirection() + CarStatus.MIN_WHEEL_ANGLE), wheel_range_bar);
		MathVector2D leftt_bar_vector = MathVector2D.getScalarMultiply(new MathVector2D(status.getDirection() + CarStatus.MAX_WHEEL_ANGLE), wheel_range_bar);
		Paintable.drawLine(g, c, status.getLocation(), right_bar_vector);
		Paintable.drawLine(g, c, status.getLocation(), leftt_bar_vector);

		Point2D.Double reference_point = c.convertToPanel(new Point2D.Double(status.getLocation().x - wheel_range, status.getLocation().y + wheel_range));
		MathVector2D size_vector = c.convertToPanel(MathVector2D.getScalarMultiply(new MathVector2D(1, -1), 2 * wheel_range));
		int starting_degree = (int) Math.toDegrees(status.getDirection() + CarStatus.MIN_WHEEL_ANGLE);
		g.drawArc((int) reference_point.getX(), (int) reference_point.getY(), (int) size_vector.getX(), (int) size_vector.getY(), starting_degree, ARC_DEGREE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length))
			return false;
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
			return false;
		return true;
	}

	public CarStatus getCloneStatus() {
		return (CarStatus) this.status.clone();
	}

	public abstract double getFrontSensorOffset();

	public abstract double getLeftSensorOffset();

	public double getLength() {
		return length;
	}

	public abstract double getRightSensorOffset();

	public CarStatus getStatus() {
		return status;
	}

	public double getWidth() {
		return width;
	}

	public boolean isInside(ConvexQuadrilateral2D quadrilateral) {
		Rectangle2D.Double bounds = getBounds();
		System.out.println(quadrilateral.toString());
		System.out.println(bounds.toString());
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!quadrilateral.isSurround(new Point2D.Double(bounds.getX() + i * bounds.getWidth(), bounds.getY() + j * bounds.getHeight()))) {
					return false;
				}
			}
		}
		return true;
	}

	public void move(double wheel_angle) {
		if (!Double.isFinite(wheel_angle))
			throw new InvalidParameterException("Input parameter wheel angle should be a finite number.");
		status.setWheelAngle(Math.toRadians(wheel_angle));
		status.setLocationByOffset(Math.cos(getStatus().getDirection()) * Math.cos(status.getWheelAngle()),
				Math.sin(getStatus().getDirection()) * Math.cos(status.getWheelAngle()));
		status.setDirection(status.getDirection() + Math.asin(2.0 * Math.sin(status.getWheelAngle()) / length));
	}

	public void reset() {
		status.getLocation().x = 0;
		status.getLocation().y = 0;
		status.setDirection(Math.PI / 2.0);
		status.setWheelAngle(0);
	}
}