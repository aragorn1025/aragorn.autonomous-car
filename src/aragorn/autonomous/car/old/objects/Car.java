package aragorn.autonomous.car.old.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import aragorn.autonomous.car.old.math.operation.GeometryParallelogram;
import aragorn.autonomous.car.old.math.operation.MathOperation;
import aragorn.gui.Coordinate2D;
import aragorn.gui.Paintable;

public abstract class Car implements Cloneable, Paintable {
	public static final double	MIN_WHEEL_ANGLE	= -40;
	public static final double	MAX_WHEEL_ANGLE	= +40;

	/** The arc angle for {@code paintWheelDirectionRange(...)} in degree. */
	private static final int ARC_ANGLE = (int) (Car.MAX_WHEEL_ANGLE - Car.MIN_WHEEL_ANGLE);

	private int		length, width;
	private double	x, y;
	private double	direction;
	private double	wheelAngle;

	/** The bar value for {@code paintCar(...)} and the others. */
	private int bar = 10;

	/** The radius length for {@code paintWheelDirection(...)} and {@code paintWheelDirectionRange(...)}. */
	private int wheelRange = 20;

	protected Car(int length, int width, double x, double y, double direction) {
		this.length = length;
		this.width = width;
		setLocationByOffset(x, y);
		setDirection(direction);
		setWheelAngle(0.0);
		initialParameterForPaintCar(length, width);
	}

	private void initialParameterForPaintCar(int length, int width) {
		bar = Math.min(length / 3, 10);
		wheelRange = Math.min(length, 10);
	}

	@Override
	protected Car clone() {
		try {
			return (Car) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		} else {
			Car pointer = (Car) obj;
			if (getLength() != pointer.getLength()) {
				return false;
			} else if (getWidth() != pointer.getWidth()) {
				return false;
			} else if (getX() != pointer.getX()) {
				return false;
			} else if (getY() != pointer.getY()) {
				return false;
			} else if ((int) getDirection() != (int) pointer.getDirection()) {
				return false;
			} else if ((int) getWheelAngle() != (int) pointer.getWheelAngle()) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void move(double wheelAngle) {
		if (Double.isNaN(wheelAngle)) {
			throw new InvalidParameterException("Input parameter wheel angle is NaN.");
		}
		setWheelAngle(wheelAngle);
		setLocationByOffset(MathOperation.cos(getDirection()) * MathOperation.cos(getWheelAngle()),
				MathOperation.sin(getDirection()) * MathOperation.cos(getWheelAngle()));
		setDirection(getDirection() + Math.toDegrees(Math.asin(2.0 * MathOperation.sin(getWheelAngle()) / getLength())));
	}

	public void reset() {
		x = 0;
		y = 0;
		setDirection(90);
		setWheelAngle(0);
	}

	public Car toShadow() {
		Car car = clone();
		return car;
	}

	protected static void paintLine(Graphics g, double x1, double y1, double x2, double y2) {
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	@Override
	public void paint(Graphics g, Coordinate2D c) {
		g.setColor(Color.GRAY);
		paintCarBodyCrossLine(g, c);
		paintWheelDirectionRange(g, c);
		g.setColor(Color.BLACK);
		paintCarBody(g, c);
		g.setColor(Color.BLUE);
		paintWheelDirection(g, c);
	}

	public void paintCarShadow(Graphics g, Coordinate2D c) {
		g.setColor(Color.LIGHT_GRAY);
		paintCarBody(g, c);
		paintDirection(g, c);
	}

	protected abstract void paintCarBody(Graphics g, Coordinate2D c);

	private void paintCarBodyCrossLine(Graphics g, Coordinate2D c) {
		// the center of the car body
		double cx = c.toX(getX());
		double cy = c.toY(getY());

		// the offset of the center and the bar end of the front of the car body
		double fdx = c.toDX((getLength() + bar * 2) * MathOperation.cos(getDirection()) / 2);
		double fdy = c.toDY((getLength() + bar * 2) * MathOperation.sin(getDirection()) / 2);

		// the offset of the center and the bar end of the right of the car body
		double rdx = c.toDX((getWidth() + bar * 2) * MathOperation.sin(getDirection()) / 2);
		double rdy = c.toDY(-(getWidth() + bar * 2) * MathOperation.cos(getDirection()) / 2);

		paintLine(g, cx + fdx, cy + fdy, cx - fdx, cy - fdy);
		paintLine(g, cx + rdx, cy + rdy, cx - rdx, cy - rdy);
	}

	private void paintDirection(Graphics g, Coordinate2D c) {
		// the center of the circle sector
		double ox = c.toX(getX());
		double oy = c.toY(getY());

		// the offset of the center and the bar end of the direction bar
		double dx = c.toDX((getWheelRange() + bar) * MathOperation.cos(getDirection()));
		double dy = c.toDY((getWheelRange() + bar) * MathOperation.sin(getDirection()));

		paintLine(g, ox, oy, ox + dx, oy + dy);
	}

	private void paintWheelDirection(Graphics g, Coordinate2D c) {
		// the center of the circle sector
		double ox = c.toX(getX());
		double oy = c.toY(getY());

		// the offset of the center and the bar end of the direction bar
		double dx = c.toDX((getWheelRange() + bar) * MathOperation.cos(getDirection() + getWheelAngle()));
		double dy = c.toDY((getWheelRange() + bar) * MathOperation.sin(getDirection() + getWheelAngle()));

		paintLine(g, ox, oy, ox + dx, oy + dy);
	}

	private void paintWheelDirectionRange(Graphics g, Coordinate2D c) {
		// the center of the circle sector
		double ox = c.toX(getX());
		double oy = c.toY(getY());

		// the offset of the center and the bar end of the right side bar
		double rdx = c.toDX((getWheelRange() + bar) * MathOperation.cos(getDirection() + Car.MIN_WHEEL_ANGLE));
		double rdy = c.toDY((getWheelRange() + bar) * MathOperation.sin(getDirection() + Car.MIN_WHEEL_ANGLE));

		// the offset of the center and the bar end of the left side bar
		double ldx = c.toDX((getWheelRange() + bar) * MathOperation.cos(getDirection() + Car.MAX_WHEEL_ANGLE));
		double ldy = c.toDY((getWheelRange() + bar) * MathOperation.sin(getDirection() + Car.MAX_WHEEL_ANGLE));

		// the left top of the arc
		int ltx = (int) c.toX(getX() - getWheelRange());
		int lty = (int) c.toY(getY() + getWheelRange());

		// the width and the height of the arc
		int adx = (int) c.toDX(2 * getWheelRange());
		int ady = (int) c.toDY(-2 * getWheelRange());

		// the start angle of the arc
		int startAngle = (int) (getDirection() + Car.MIN_WHEEL_ANGLE);

		paintLine(g, ox, oy, ox + rdx, oy + rdy);
		paintLine(g, ox, oy, ox + ldx, oy + ldy);
		g.drawArc(ltx, lty, adx, ady, startAngle, ARC_ANGLE);
	}

	public abstract boolean isInside(GeometryParallelogram parallelogram);

	public double getDirection() {
		return direction;
	}

	public double getDirectionOutput() {
		return direction - (direction < 270 ? 0 : 360);
	}

	public int getLength() {
		return length;
	}

	public Point2D.Double getLocation() {
		return new Point2D.Double(x, y);
	}

	public double getWheelAngle() {
		return wheelAngle;
	}

	public double getWheelAngleOutput() {
		return (wheelAngle == 0.0 ? 1 : -1) * wheelAngle;
	}

	private int getWheelRange() {
		return wheelRange;
	}

	public int getWidth() {
		return width;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	/**
	 * Set the new direction that be normalized into the range {@code [0, 360)}.<br>
	 * The range asked for in the purpose document of the class homework one is {@code [-90, 270)} instead.
	 * 
	 * @param direction
	 *            new direction to be set
	 */
	private void setDirection(double direction) {
		if (Double.isNaN(direction)) {
			throw new InvalidParameterException("The input parameter direction should not be NaN.");
		}
		this.direction = direction % 360.0 + (direction >= 0.0 ? 0.0 : 1.0) * 360.0;
	}

	private void setLocationByOffset(double dx, double dy) {
		if (Double.isNaN(dx)) {
			throw new InvalidParameterException("The input parameter dx should not be NaN.");
		} else if (Double.isNaN(dy)) {
			throw new InvalidParameterException("The input parameter dy should not be NaN.");
		}
		x += dx;
		y += dy;
	}

	private void setWheelAngle(double wheelAngle) {
		if (Double.isNaN(wheelAngle)) {
			throw new InvalidParameterException("The input parameter wheel angle should not be NaN.");
		}
		this.wheelAngle = (wheelAngle + 180.0) % 360.0 - 180.0;
		if (this.wheelAngle > MAX_WHEEL_ANGLE) {
			this.wheelAngle = MAX_WHEEL_ANGLE;
		} else if (this.wheelAngle < MIN_WHEEL_ANGLE) {
			this.wheelAngle = MIN_WHEEL_ANGLE;
		}
	}
}