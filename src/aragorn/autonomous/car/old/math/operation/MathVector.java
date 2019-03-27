package aragorn.autonomous.car.old.math.operation;

import java.awt.geom.Point2D;

public class MathVector {
	private double[] n;

	public MathVector(double n0, double... nx) {
		this.n = new double[1 + nx.length];
		n[0] = n0;
		for (int i = 0; i < nx.length; i++) {
			n[i + 1] = nx[i];
		}
	}

	public double getComponent(int index) {
		return n[index];
	}

	public int getDimension() {
		return n.length;
	}

	public double getLength() {
		double val = 0;
		for (int i = 0; i < n.length; i++) {
			val += Math.pow(n[i], 2);
		}
		return Math.pow(val, 0.5);
	}

	public static class _2D extends MathVector {
		public _2D() {
			this(0, 0);
		}

		public _2D(double n0, double n1) {
			super(n0, n1);
		}

		public _2D(Point2D.Double p0, Point2D.Double p1) {
			super(p1.getX() - p0.getX(), p1.getY() - p0.getY());
		}

		public double getX() {
			return super.getComponent(0);
		}

		public double getY() {
			return super.getComponent(1);
		}
	}
}