package aragorn.autonomous.car.old.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import aragorn.gui.Coordinate2D;
import aragorn.gui.Paintable;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathVector2D;

public abstract class Car implements Cloneable, Paintable {

	public static final double MIN_WHEEL_ANGLE = -40;

	public static final double MAX_WHEEL_ANGLE = +40;

	/** The arc angle for {@code paintWheelDirectionRange(...)} in degree. */
	private static final int ARC_ANGLE = (int) (Car.MAX_WHEEL_ANGLE - Car.MIN_WHEEL_ANGLE);

	protected static void paintLine(Graphics g, double x1, double y1, double x2, double y2) {
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	private int length;

	private int width;

	// private double x;

	// private double y;

	private Point2D.Double location;

	private double direction;

	private double wheelAngle;

	/** The bar value for {@code paintCar(...)} and the others. */
	private int bar = 10;

	/** The radius length for {@code paintWheelDirection(...)} and {@code paintWheelDirectionRange(...)}. */
	private int wheelRange = 20;

	protected Car(int length, int width, double x, double y, double direction) {
		this.length = length;
		this.width = width;
		location = new Point2D.Double(x, y);
		setDirection(direction);
		setWheelAngle(0.0);

		// initial parameters for paint car
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
	public void draw(Graphics g, Coordinate2D c) {
		g.setColor(Color.GRAY);
		drawCarBodyCrossLine(g, c);
		drawWheelDirectionRange(g, c);
		g.setColor(Color.BLACK);
		drawCarBody(g, c);
		g.setColor(Color.BLUE);
		drawWheelDirection(g, c);
	}

	protected abstract void drawCarBody(Graphics g, Coordinate2D c);

	private void drawCarBodyCrossLine(Graphics g, Coordinate2D c) {
		// the offset of the center and the bar end of the front of the car body
		MathVector2D fv = new MathVector2D((getLength() + bar * 2) * Math.cos(Math.toRadians(getDirection())) / 2,
				(getLength() + bar * 2) * Math.sin(Math.toRadians(getDirection())) / 2);

		// the offset of the center and the bar end of the right of the car body
		MathVector2D rv = new MathVector2D((getWidth() + bar * 2) * Math.sin(Math.toRadians(getDirection())) / 2,
				-(getWidth() + bar * 2) * Math.cos(Math.toRadians(getDirection())) / 2);

		Paintable.drawLine(g, c, MathVector2D.add(location, fv), MathVector2D.add(location, fv.getNegative()));
		Paintable.drawLine(g, c, MathVector2D.add(location, rv), MathVector2D.add(location, rv.getNegative()));
	}

	public void drawCarShadow(Graphics g, Coordinate2D c) {
		g.setColor(Color.LIGHT_GRAY);
		drawCarBody(g, c);
		drawDirection(g, c);
	}

	private void drawDirection(Graphics g, Coordinate2D c) {
		MathVector2D v = new MathVector2D((getWheelRange() + bar) * Math.cos(Math.toRadians(getDirection())),
				(getWheelRange() + bar) * Math.sin(Math.toRadians(getDirection())));
		Paintable.drawLine(g, c, location, v);
	}

	private void drawWheelDirection(Graphics g, Coordinate2D c) {
		MathVector2D v = new MathVector2D((getWheelRange() + bar) * Math.cos(Math.toRadians(getDirection() + getWheelAngle())),
				(getWheelRange() + bar) * Math.sin(Math.toRadians(getDirection() + getWheelAngle())));
		Paintable.drawLine(g, c, location, v);
	}

	private void drawWheelDirectionRange(Graphics g, Coordinate2D c) {
		// the offset of the center and the bar end of the right side bar
		MathVector2D rv = new MathVector2D((getWheelRange() + bar) * Math.cos(Math.toRadians(getDirection() + Car.MIN_WHEEL_ANGLE)),
				(getWheelRange() + bar) * Math.sin(Math.toRadians(getDirection() + Car.MIN_WHEEL_ANGLE)));

		// the offset of the center and the bar end of the left side bar
		MathVector2D lv = new MathVector2D((getWheelRange() + bar) * Math.cos(Math.toRadians(getDirection() + Car.MAX_WHEEL_ANGLE)),
				(getWheelRange() + bar) * Math.sin(Math.toRadians(getDirection() + Car.MAX_WHEEL_ANGLE)));

		// the left top of the arc
		Point2D.Double ltp = new Point2D.Double(location.x - getWheelRange(), location.y + getWheelRange());
		Point2D.Double ltpc = c.convertToPanel(ltp);

		// the width and the height of the arc
		MathVector2D av = new MathVector2D(2 * getWheelRange(), -2 * getWheelRange());
		MathVector2D avc = c.convertToPanel(av);

		// the start angle of the arc
		int startAngle = (int) (getDirection() + Car.MIN_WHEEL_ANGLE);

		Paintable.drawLine(g, c, location, rv);
		Paintable.drawLine(g, c, location, lv);
		g.drawArc((int) ltpc.getX(), (int) ltpc.getY(), (int) avc.getX(), (int) avc.getY(), startAngle, ARC_ANGLE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car pointer = (Car) obj;
		if (getLength() != pointer.getLength())
			return false;
		if (getWidth() != pointer.getWidth())
			return false;
		if (location == null) {
			if (pointer.location != null) {
				return false;
			}
		} else {
			if (!location.equals(pointer.location)) {
				return false;
			}
		}
		if ((int) getDirection() != (int) pointer.getDirection())
			return false;
		if ((int) getWheelAngle() != (int) pointer.getWheelAngle())
			return false;
		return true;
	}

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
		return location;
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

	public abstract boolean isInside(MathGeometryParallelogram2D parallelogram);

	public void move(double wheelAngle) {
		if (Double.isNaN(wheelAngle)) {
			throw new InvalidParameterException("Input parameter wheel angle is NaN.");
		}
		setWheelAngle(wheelAngle);
		setLocationByOffset(Math.cos(Math.toRadians(getDirection())) * Math.cos(Math.toRadians(getWheelAngle())),
				Math.sin(Math.toRadians(getDirection())) * Math.cos(Math.toRadians(getWheelAngle())));
		setDirection(getDirection() + Math.toDegrees(Math.asin(2.0 * Math.sin(Math.toRadians(getWheelAngle())) / getLength())));
	}

	public void reset() {
		location.x = 0;
		location.y = 0;
		setDirection(90);
		setWheelAngle(0);
	}

	/**
	 * Set the new direction that be normalized into the range {@code [0, 360)}.<br>
	 * The range asked for in the purpose document of the class homework one is {@code [-90, 270)} instead.
	 * 
	 * @param direction
	 *     new direction to be set
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
		location.x += dx;
		location.y += dy;
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

	public Car toShadow() {
		return this.clone();
	}
}