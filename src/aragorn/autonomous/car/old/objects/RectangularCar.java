package aragorn.autonomous.car.old.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.gui.GuiCoordinate2D;
import aragorn.gui.GuiPaintable;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathVector2D;

public class RectangularCar extends Car {

	public RectangularCar() {
		this(6, 3, 0, 0, 90);
	}

	public RectangularCar(int length, int width, double x, double y, double direction) {
		super(length, width, x, y, direction);
	}

	@Override
	protected void drawCarBody(Graphics g, GuiCoordinate2D c) {
		// the offset of the center and the front of the car body
		MathVector2D fv = new MathVector2D(getLength() * Math.cos(Math.toRadians(getDirection())) / 2, getLength() * Math.sin(Math.toRadians(getDirection())) / 2);
		MathVector2D bv = fv.getNegative();

		// the offset of the center and the right of the car body
		MathVector2D rv = new MathVector2D(getWidth() * Math.sin(Math.toRadians(getDirection())) / 2, -getWidth() * Math.cos(Math.toRadians(getDirection())) / 2);
		MathVector2D lv = rv.getNegative();

		GuiPaintable.drawLine(g, c, MathVector2D.add(getLocation(), fv, rv), MathVector2D.add(getLocation(), fv, lv));
		GuiPaintable.drawLine(g, c, MathVector2D.add(getLocation(), fv, lv), MathVector2D.add(getLocation(), bv, lv));
		GuiPaintable.drawLine(g, c, MathVector2D.add(getLocation(), bv, rv), MathVector2D.add(getLocation(), fv, rv));
		GuiPaintable.drawLine(g, c, MathVector2D.add(getLocation(), bv, lv), MathVector2D.add(getLocation(), bv, rv));
	}

	@Override
	public Rectangle getBounds() {
		// the offset of the center and the front of the car body
		double fdx = Math.abs(getLength() * Math.cos(Math.toRadians(getDirection())) / 2);
		double fdy = Math.abs(getLength() * Math.sin(Math.toRadians(getDirection())) / 2);

		// the offset of the center and the right of the car body
		double rdx = Math.abs(getWidth() * Math.sin(Math.toRadians(getDirection())) / 2);
		double rdy = Math.abs(-getWidth() * Math.cos(Math.toRadians(getDirection())) / 2);

		int x = (int) Math.floor(getLocation().getX() - fdx - rdx);
		int y = (int) Math.floor(getLocation().getY() - fdy - rdy);
		int width = (int) (Math.ceil(getLocation().getX() + fdx + rdx) - x);
		int height = (int) (Math.ceil(getLocation().getY() + fdy + rdy) - y);
		return new Rectangle(x, y, width, height);
	}

	@Override
	public boolean isInside(MathGeometryParallelogram2D parallelogram) {
		// the offset of the center and the front of the car body
		double fdx = getLength() * Math.cos(Math.toRadians(getDirection())) / 2;
		double fdy = getLength() * Math.sin(Math.toRadians(getDirection())) / 2;

		// the offset of the center and the right of the car body
		double rdx = getWidth() * Math.sin(Math.toRadians(getDirection())) / 2;
		double rdy = -getWidth() * Math.cos(Math.toRadians(getDirection())) / 2;
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
				if (!parallelogram.isSurround(new Point2D.Double(getLocation().getX() + i * fdx + j * rdx, getLocation().getY() + i * fdy + j * rdy))) {
					return false;
				}

			}
		}
		return true;
	}
}