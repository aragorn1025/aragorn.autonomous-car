package aragorn.autonomous.car.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import aragorn.gui.GuiCoordinate2D;
import aragorn.gui.GuiPaintable;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathVector2D;

public abstract class Car implements GuiPaintable {

	/** The arc angle for {@code paintWheelDirectionRange(...)} in degree. */
	private static final int ARC_DEGREE = (int) Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE - CarStatus.MIN_WHEEL_ANGLE);

	private double length;

	private double width;

	private CarStatus status;

	/** The bar value for {@code paintCar(...)} and the others. */
	private double bar;

	/** The radius length for {@code paintWheelDirection(...)} and {@code paintWheelDirectionRange(...)}. */
	private double wheel_range;

	protected Car(double length, double width, CarStatus status) {
		this.length = length;
		this.width = width;
		this.status = (CarStatus) status.clone();

		// initial parameters for paint car
		bar = Math.min(length / 3.0, 10.0);
		wheel_range = Math.min(length, 10.0);
	}

	@Override
	public void draw(Graphics g, GuiCoordinate2D c) {
		g.setColor(Color.GRAY);
		drawCarBodyCrossLine(g, c);
		drawWheelDirectionRange(g, c);
		g.setColor(Color.BLACK);
		drawCarBody(g, c);
		g.setColor(Color.BLUE);
		drawWheelDirection(g, c);
	}

	protected abstract void drawCarBody(Graphics g, GuiCoordinate2D c);

	private void drawCarBodyCrossLine(Graphics g, GuiCoordinate2D c) {
		// the offset of the center and the bar end of the front of the car body
		double length_bar = getLength() + bar * 2;
		MathVector2D front_vector = new MathVector2D(length_bar * Math.cos(getStatus().getDirection()) / 2, length_bar * Math.sin(getStatus().getDirection()) / 2);

		// the offset of the center and the bar end of the right of the car body
		double width_bar = getWidth() + bar * 2;
		MathVector2D right_vector = new MathVector2D(width_bar * Math.sin(getStatus().getDirection()) / 2, -width_bar * Math.cos(getStatus().getDirection()) / 2);

		GuiPaintable.drawLine(g, c, MathVector2D.add(status.getLocation(), front_vector), MathVector2D.add(status.getLocation(), front_vector.getNegative()));
		GuiPaintable.drawLine(g, c, MathVector2D.add(status.getLocation(), right_vector), MathVector2D.add(status.getLocation(), right_vector.getNegative()));
	}

	public void drawShadow(Graphics g, GuiCoordinate2D c, CarStatus status) {
		CarStatus temp = this.status;
		this.status = status;
		g.setColor(Color.LIGHT_GRAY);
		drawCarBody(g, c);
		drawDirection(g, c);
		this.status = temp;
	}

	private void drawDirection(Graphics g, GuiCoordinate2D c) {
		MathVector2D v = new MathVector2D((getWheelRange() + bar) * Math.cos(getStatus().getDirection()), (getWheelRange() + bar) * Math.sin(getStatus().getDirection()));
		GuiPaintable.drawLine(g, c, status.getLocation(), v);
	}

	private void drawWheelDirection(Graphics g, GuiCoordinate2D c) {
		MathVector2D v = new MathVector2D((getWheelRange() + bar) * Math.cos(status.getDirection() + status.getWheelAngle()),
				(getWheelRange() + bar) * Math.sin(status.getDirection() + status.getWheelAngle()));
		GuiPaintable.drawLine(g, c, status.getLocation(), v);
	}

	private void drawWheelDirectionRange(Graphics g, GuiCoordinate2D c) {
		// the offset of the center and the bar end of the right side bar
		MathVector2D rv = new MathVector2D((getWheelRange() + bar) * Math.cos(status.getDirection() + CarStatus.MIN_WHEEL_ANGLE),
				(getWheelRange() + bar) * Math.sin(status.getDirection() + CarStatus.MIN_WHEEL_ANGLE));

		// the offset of the center and the bar end of the left side bar
		MathVector2D lv = new MathVector2D((getWheelRange() + bar) * Math.cos(status.getDirection() + CarStatus.MAX_WHEEL_ANGLE),
				(getWheelRange() + bar) * Math.sin(status.getDirection() + CarStatus.MAX_WHEEL_ANGLE));

		// the left top of the arc
		Point2D.Double ltp = new Point2D.Double(status.getLocation().x - getWheelRange(), status.getLocation().y + getWheelRange());
		Point2D.Double ltpc = c.convertToPanel(ltp);

		// the width and the height of the arc
		MathVector2D av = new MathVector2D(2 * getWheelRange(), -2 * getWheelRange());
		MathVector2D avc = c.convertToPanel(av);

		// the start angle of the arc
		int startAngle = (int) Math.toDegrees(status.getDirection() + CarStatus.MIN_WHEEL_ANGLE);

		GuiPaintable.drawLine(g, c, status.getLocation(), rv);
		GuiPaintable.drawLine(g, c, status.getLocation(), lv);
		g.drawArc((int) ltpc.getX(), (int) ltpc.getY(), (int) avc.getX(), (int) avc.getY(), startAngle, ARC_DEGREE);
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

	public int getLength() {
		return (int) length;
	}

	public CarStatus getStatus() {
		return status;
	}

	private int getWheelRange() {
		return (int) wheel_range;
	}

	public int getWidth() {
		return (int) width;
	}

	public abstract boolean isInside(MathGeometryParallelogram2D parallelogram);

	public void move(double wheelAngle) {
		if (Double.isNaN(wheelAngle)) {
			throw new InvalidParameterException("Input parameter wheel angle is NaN.");
		}
		status.setWheelAngle(Math.toRadians(wheelAngle));
		status.setLocationByOffset(Math.cos(getStatus().getDirection()) * Math.cos(status.getWheelAngle()),
				Math.sin(getStatus().getDirection()) * Math.cos(status.getWheelAngle()));
		status.setDirection(status.getDirection() + Math.asin(2.0 * Math.sin(status.getWheelAngle()) / getLength()));
	}

	public void reset() {
		status.getLocation().x = 0;
		status.getLocation().y = 0;
		status.setDirection(Math.PI / 2.0);
		status.setWheelAngle(0);
	}

	public CarStatus toShadow() {
		return (CarStatus) this.status.clone();
	}
}