package aragorn.autonomous.car.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.gui.GuiCoordinate2D;
import aragorn.gui.GuiPaintable;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathVector2D;

public class RectangularCar extends Car {

	public RectangularCar() {
		this(6, 3, new CarStatus(new Point2D.Double(0, 0), 90, 0));
	}

	private RectangularCar(int length, int width, CarStatus status) {
		super(length, width, status);
	}

	@Override
	protected void drawCarBody(Graphics g, GuiCoordinate2D c) {
		MathVector2D back_vector = new MathVector2D(-getLength() * Math.cos(getStatus().getDirection()) / 2, -getLength() * Math.sin(getStatus().getDirection()) / 2);
		MathVector2D front_vector = back_vector.getNegative();
		MathVector2D left_vector = new MathVector2D(-getWidth() * Math.sin(getStatus().getDirection()) / 2, getWidth() * Math.cos(getStatus().getDirection()) / 2);
		MathVector2D right_vector = left_vector.getNegative();

		Point2D.Double left_back_point = MathVector2D.add(getStatus().getLocation(), back_vector, left_vector);
		Point2D.Double left_front_point = MathVector2D.add(getStatus().getLocation(), front_vector, left_vector);
		Point2D.Double right_back_point = MathVector2D.add(getStatus().getLocation(), back_vector, right_vector);
		Point2D.Double right_front_point = MathVector2D.add(getStatus().getLocation(), front_vector, right_vector);

		GuiPaintable.drawLine(g, c, left_back_point, left_front_point);
		GuiPaintable.drawLine(g, c, left_front_point, right_front_point);
		GuiPaintable.drawLine(g, c, right_front_point, right_back_point);
		GuiPaintable.drawLine(g, c, right_back_point, left_back_point);
	}

	@Override
	public Rectangle getBounds() {
		// the offset of the center and the front of the car body
		double fdx = Math.abs(getLength() * Math.cos(getStatus().getDirection()) / 2);
		double fdy = Math.abs(getLength() * Math.sin(getStatus().getDirection()) / 2);

		// the offset of the center and the right of the car body
		double rdx = Math.abs(getWidth() * Math.sin(getStatus().getDirection()) / 2);
		double rdy = Math.abs(-getWidth() * Math.cos(getStatus().getDirection()) / 2);

		int x = (int) Math.floor(getStatus().getLocation().getX() - fdx - rdx);
		int y = (int) Math.floor(getStatus().getLocation().getY() - fdy - rdy);
		int width = (int) (Math.ceil(getStatus().getLocation().getX() + fdx + rdx) - x);
		int height = (int) (Math.ceil(getStatus().getLocation().getY() + fdy + rdy) - y);
		return new Rectangle(x, y, width, height);
	}

	@Override
	public boolean isInside(MathGeometryParallelogram2D parallelogram) {
		// the offset of the center and the front of the car body
		double fdx = getLength() * Math.cos(getStatus().getDirection()) / 2;
		double fdy = getLength() * Math.sin(getStatus().getDirection()) / 2;

		// the offset of the center and the right of the car body
		double rdx = getWidth() * Math.sin(getStatus().getDirection()) / 2;
		double rdy = -getWidth() * Math.cos(getStatus().getDirection()) / 2;
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
				if (!parallelogram
						.isSurround(new Point2D.Double(getStatus().getLocation().getX() + i * fdx + j * rdx, getStatus().getLocation().getY() + i * fdy + j * rdy))) {
					return false;
				}

			}
		}
		return true;
	}
}