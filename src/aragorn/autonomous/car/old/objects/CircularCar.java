package aragorn.autonomous.car.old.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.autonomous.car.old.math.operation.GeometryParallelogram;
import aragorn.gui.Coordinate2D;

public class CircularCar extends Car {
	public CircularCar() {
		this(3, 0, 0, 90);
	}

	public CircularCar(int radius, double x, double y, double direction) {
		super(radius * 2, radius * 2, x, y, direction);
	}

	@Override
	public boolean isInside(GeometryParallelogram parallelogram) {
		for (int i = -1; i <= 1; i += 2) {
			if (!parallelogram.isInside(new Point2D.Double(getX(), getY() + i * getRadius()))) {
				return false;
			}
			if (!parallelogram.isInside(new Point2D.Double(getX() + i * getRadius(), getY()))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Rectangle getBounds() {
		int x = (int) Math.floor(getX() - getRadius());
		int y = (int) Math.floor(getY() - getRadius());
		int width = (int) (Math.ceil(getX() + getRadius()) - x);
		int height = (int) (Math.ceil(getY() + getRadius()) - y);
		return new Rectangle(x, y, width, height);
	}

	private int getRadius() {
		return getWidth() / 2;
	}

	@Override
	protected void paintCarBody(Graphics g, Coordinate2D c) {
		// the left top of the car body
		int ltx = (int) c.toX(getX() - getRadius());
		int lty = (int) c.toY(getY() + getRadius());

		// the width and the height of the car body
		int dx = (int) c.toDX(getRadius() * 2);
		int dy = (int) c.toDY(-getRadius() * 2);

		g.drawOval(ltx, lty, dx, dy);
	}
}