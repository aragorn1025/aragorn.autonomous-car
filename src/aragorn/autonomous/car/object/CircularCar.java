package aragorn.autonomous.car.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.gui.GuiCoordinate2D;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathVector2D;

public class CircularCar extends Car {

	public CircularCar() {
		this(3, new Point2D.Double(), 90);
	}

	private CircularCar(int radius, Point2D.Double location, double direction) {
		super(radius * 2, radius * 2, new CarStatus(location, direction, 0));
	}

	@Override
	protected void drawCarBody(Graphics g, GuiCoordinate2D c) {
		Point2D.Double left_top_point = new Point2D.Double(getStatus().getLocation().getX() - getRadius(), getStatus().getLocation().getY() + getRadius());
		Point2D.Double ltpc = c.convertToPanel(left_top_point);

		// the width and the height of the car body
		MathVector2D v = new MathVector2D(getRadius() * 2, -getRadius() * 2);
		MathVector2D vc = c.convertToPanel(v);

		g.drawOval((int) ltpc.x, (int) ltpc.y, (int) vc.getX(), (int) vc.getY());
	}

	@Override
	public Rectangle getBounds() {
		int x = (int) Math.floor(getStatus().getLocation().getX() - getRadius());
		int y = (int) Math.floor(getStatus().getLocation().getY() - getRadius());
		int width = (int) (Math.ceil(getStatus().getLocation().getX() + getRadius()) - x);
		int height = (int) (Math.ceil(getStatus().getLocation().getY() + getRadius()) - y);
		return new Rectangle(x, y, width, height);
	}

	private int getRadius() {
		return getWidth() / 2;
	}

	@Override
	public boolean isInside(MathGeometryParallelogram2D parallelogram) {
		for (int i = -1; i <= 1; i += 2) {
			if (!parallelogram.isSurround(new Point2D.Double(getStatus().getLocation().getX(), getStatus().getLocation().getY() + i * getRadius()))) {
				return false;
			}
			if (!parallelogram.isSurround(new Point2D.Double(getStatus().getLocation().getX() + i * getRadius(), getStatus().getLocation().getY()))) {
				return false;
			}
		}
		return true;
	}
}