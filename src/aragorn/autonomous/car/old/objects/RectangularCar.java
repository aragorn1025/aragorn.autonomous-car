package aragorn.autonomous.car.old.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.autonomous.car.old.math.operation.GeometryParallelogram;
import aragorn.autonomous.car.old.math.operation.MathOperation;
import aragorn.gui.Coordinate2D;

public class RectangularCar extends Car {
	public RectangularCar() {
		this(6, 3, 0, 0, 90);
	}

	public RectangularCar(int length, int width, double x, double y, double direction) {
		super(length, width, x, y, direction);
	}

	@Override
	public boolean isInside(GeometryParallelogram parallelogram) {
		// the offset of the center and the front of the car body
		double fdx = getLength() * MathOperation.cos(getDirection()) / 2;
		double fdy = getLength() * MathOperation.sin(getDirection()) / 2;

		// the offset of the center and the right of the car body
		double rdx = getWidth() * MathOperation.sin(getDirection()) / 2;
		double rdy = -getWidth() * MathOperation.cos(getDirection()) / 2;
		for (int i = -1; i <= 1; i += 2) {
			for (int j = -1; j <= 1; j += 2) {
				if (!parallelogram.isInside(new Point2D.Double(getX() + i * fdx + j * rdx, getY() + i * fdy + j * rdy))) {
					return false;
				}

			}
		}
		return true;
	}

	@Override
	public Rectangle getBounds() {
		// the offset of the center and the front of the car body
		double fdx = Math.abs(getLength() * MathOperation.cos(getDirection()) / 2);
		double fdy = Math.abs(getLength() * MathOperation.sin(getDirection()) / 2);

		// the offset of the center and the right of the car body
		double rdx = Math.abs(getWidth() * MathOperation.sin(getDirection()) / 2);
		double rdy = Math.abs(-getWidth() * MathOperation.cos(getDirection()) / 2);

		int x = (int) Math.floor(getX() - fdx - rdx);
		int y = (int) Math.floor(getY() - fdy - rdy);
		int width = (int) (Math.ceil(getX() + fdx + rdx) - x);
		int height = (int) (Math.ceil(getY() + fdy + rdy) - y);
		return new Rectangle(x, y, width, height);
	}

	@Override
	protected void paintCarBody(Graphics g, Coordinate2D c) {
		// the center of the car body
		double cx = c.toX(getX());
		double cy = c.toY(getY());

		// the offset of the center and the front of the car body
		double fdx = c.toDX(getLength() * MathOperation.cos(getDirection()) / 2);
		double fdy = c.toDY(getLength() * MathOperation.sin(getDirection()) / 2);

		// the offset of the center and the right of the car body
		double rdx = c.toDX(getWidth() * MathOperation.sin(getDirection()) / 2);
		double rdy = c.toDY(-getWidth() * MathOperation.cos(getDirection()) / 2);

		paintLine(g, cx + fdx + rdx, cy + fdy + rdy, cx + fdx - rdx, cy + fdy - rdy);
		paintLine(g, cx + fdx - rdx, cy + fdy - rdy, cx - fdx - rdx, cy - fdy - rdy);
		paintLine(g, cx - fdx - rdx, cy - fdy - rdy, cx - fdx + rdx, cy - fdy + rdy);
		paintLine(g, cx - fdx + rdx, cy - fdy + rdy, cx + fdx + rdx, cy + fdy + rdy);
	}
}