package aragorn.autonomous.car.object;

import java.awt.geom.Point2D;
import java.security.InvalidParameterException;

public class CarStatus implements Cloneable {

	public static final double MIN_WHEEL_ANGLE = Math.toRadians(-40);

	public static final double MAX_WHEEL_ANGLE = Math.toRadians(+40);

	private Point2D.Double location = new Point2D.Double(0, 0);

	private double direction;

	private double wheel_angle;

	public CarStatus(Point2D.Double location, double direction, double wheel_angle) {
		setLocationByOffset(location.x, location.y);
		setDirection(direction);
		setWheelAngle(wheel_angle);
	}

	@Override
	public Object clone() {
		CarStatus val = null;
		try {
			val = (CarStatus) super.clone();
			val.location = (Point2D.Double) location.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		return val;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarStatus other = (CarStatus) obj;
		if (Double.doubleToLongBits(direction) != Double.doubleToLongBits(other.direction))
			return false;
		if (Double.doubleToLongBits(wheel_angle) != Double.doubleToLongBits(other.wheel_angle))
			return false;
		if (location == null)
			return other.location == null;
		else
			return location.equals(other.location);
	}

	public double getDirection() {
		return direction;
	}

	public double getDirectionOutput() {
		return Math.toDegrees(direction - (direction < 1.5 * Math.PI ? 0 : 2 * Math.PI));
	}

	public Point2D.Double getLocation() {
		return location;
	}

	public double getWheelAngle() {
		return wheel_angle;
	}

	public double getWheelAngleOutput() {
		return Math.toDegrees((wheel_angle == 0.0 ? 1 : -1) * wheel_angle);
	}

	/**
	 * Set the direction that be normalized into the range {@code [0, 2 * PI)}.<br>
	 * The range asked for in the purpose document of the class homework one is {@code [-0.5 * PI, 1.5 * PI)} instead.
	 * 
	 * @param direction
	 *     new direction to be set
	 */
	public void setDirection(double direction) {
		if (!Double.isFinite(direction))
			throw new InvalidParameterException("The input parameter direction should be a finite number.");
		this.direction = direction % (Math.PI * 2.0) + (direction >= 0.0 ? 0.0 : 1.0) * (Math.PI * 2.0);
	}

	public void setLocationByOffset(double dx, double dy) {
		if (!Double.isFinite(dx))
			throw new InvalidParameterException("The input parameter dx should be a finite number.");
		if (!Double.isFinite(dy))
			throw new InvalidParameterException("The input parameter dy should be a finite number.");
		location.x += dx;
		location.y += dy;
	}

	public void setWheelAngle(double wheel_angle) {
		if (!Double.isFinite(wheel_angle))
			throw new InvalidParameterException("The input parameter wheel angle should be a finite number.");
		this.wheel_angle = (wheel_angle + Math.PI) % (Math.PI * 2.0) - (wheel_angle + Math.PI >= 0.0 ? 1.0 : -1.0) * Math.PI;
		if (this.wheel_angle > CarStatus.MAX_WHEEL_ANGLE) {
			this.wheel_angle = CarStatus.MAX_WHEEL_ANGLE;
		} else if (this.wheel_angle < CarStatus.MIN_WHEEL_ANGLE) {
			this.wheel_angle = CarStatus.MIN_WHEEL_ANGLE;
		}
	}
}