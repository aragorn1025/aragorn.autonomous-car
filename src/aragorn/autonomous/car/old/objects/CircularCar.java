package aragorn.autonomous.car.old.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.gui.Coordinate2D;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathVector2D;

public class CircularCar extends Car {

	public CircularCar() {
		this(3, 0, 0, 90);
	}

	public CircularCar(int radius, double x, double y, double direction) {
		super(radius * 2, radius * 2, x, y, direction);
	}

	@Override
	protected void drawCarBody(Graphics g, Coordinate2D c) {
		Point2D.Double left_top_point = new Point2D.Double(getLocation().getX() - getRadius(), getLocation().getY() + getRadius());
		Point2D.Double ltpc = c.convertToPanel(left_top_point);

		// the width and the height of the car body
		MathVector2D v = new MathVector2D(getRadius() * 2, -getRadius() * 2);
		MathVector2D vc = c.convertToPanel(v);

		g.drawOval((int) ltpc.x, (int) ltpc.y, (int) vc.getX(), (int) vc.getY());
	}

	@Override
	public Rectangle getBounds() {
		int x = (int) Math.floor(getLocation().getX() - getRadius());
		int y = (int) Math.floor(getLocation().getY() - getRadius());
		int width = (int) (Math.ceil(getLocation().getX() + getRadius()) - x);
		int height = (int) (Math.ceil(getLocation().getY() + getRadius()) - y);
		return new Rectangle(x, y, width, height);
	}

	private int getRadius() {
		return getWidth() / 2;
	}

	@Override
	public boolean isInside(MathGeometryParallelogram2D parallelogram) {
		for (int i = -1; i <= 1; i += 2) {
			if (!parallelogram.isSurround(new Point2D.Double(getLocation().getX(), getLocation().getY() + i * getRadius()))) {
				return false;
			}
			if (!parallelogram.isSurround(new Point2D.Double(getLocation().getX() + i * getRadius(), getLocation().getY()))) {
				return false;
			}
		}
		return true;
	}
}