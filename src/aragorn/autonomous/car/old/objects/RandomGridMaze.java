package aragorn.autonomous.car.old.objects;

import java.security.InvalidParameterException;
import java.util.Arrays;
import aragorn.autonomous.car.old.math.operation.MathOperation;

public class RandomGridMaze extends LinearMaze {

	public static final int GO_UP = 1;

	public static final int GO_RIGHT = 2;

	private int type;

	private int boundsWidth;

	private int boundsHeight;

	private double pathWidth;

	private int pointPairs;

	public RandomGridMaze() {
		this(GO_UP);
	}

	private RandomGridMaze(int type) {
		this(type, 160, 90, 12, 3);
	}

	private RandomGridMaze(int type, int boundsWidth, int boundsHeight, double pathWidth, int pointPairs) {
		super();
		setType(type);
		setBounds(boundsWidth, boundsHeight);
		setPathWidth(pathWidth);
		setPointPairs(pointPairs);
		addRandomPoints();
	}

	private void addRandomPoints() {
		double[] x = new double[pointPairs * 2 + 2];
		double[] y = new double[pointPairs * 2 + 2];
		switch (type) {
			case RandomGridMaze.GO_UP:
				initialXY(pointPairs, x, y, boundsWidth, boundsHeight, boundsHeight - pathWidth * (pointPairs * 2.0 + 1.0));
				break;
			case RandomGridMaze.GO_RIGHT:
				initialXY(pointPairs, y, x, boundsHeight, boundsWidth, boundsWidth - pathWidth * (pointPairs * 2.0 + 1.0));
				break;
			default:
				throw new NullPointerException("The type has not been set.");
		}
		double p;
		for (int i = 0; i < pointPairs * 2 + 2; i++) {
			if (type == GO_UP) {
				if (i == 0 || i == pointPairs * 2 + 1) {
					p = 0;
				} else if (i % 2 == 1) {
					p = (x[i] < x[i + 1]) ? (+1) : (-1);
				} else {
					p = (x[i - 1] < x[i]) ? (+1) : (-1);
				}
				getLeftWall().addPoint(x[i] - pathWidth / 2.0, y[i] + p * pathWidth / 2.0);
				getRightWall().addPoint(x[i] + pathWidth / 2.0, y[i] - p * pathWidth / 2.0);
			} else {
				if (i == 0 || i == pointPairs * 2 + 1) {
					p = 0;
				} else if (i % 2 == 1) {
					p = (y[i] < y[i + 1]) ? (+1) : (-1);
				} else {
					p = (y[i - 1] < y[i]) ? (+1) : (-1);
				}
				getLeftWall().addPoint(x[i] + p * pathWidth / 2.0, y[i] - pathWidth / 2.0);
				getRightWall().addPoint(x[i] - p * pathWidth / 2.0, y[i] + pathWidth / 2.0);
			}
		}

		double ds = pathWidth / 2.0;
		double df = pathWidth + 1;
		if (type == GO_UP) {
			getEndWall().addPoint(x[pointPairs * 2 + 1] - ds, y[pointPairs * 2 + 1] + df * 0);
			getEndWall().addPoint(x[pointPairs * 2 + 1] - ds, y[pointPairs * 2 + 1] + df * 1);
			getEndWall().addPoint(x[pointPairs * 2 + 1] + ds, y[pointPairs * 2 + 1] + df * 1);
			getEndWall().addPoint(x[pointPairs * 2 + 1] + ds, y[pointPairs * 2 + 1] + df * 0);
		} else {
			getEndWall().addPoint(x[pointPairs * 2 + 1] + df * 0, y[pointPairs * 2 + 1] - ds);
			getEndWall().addPoint(x[pointPairs * 2 + 1] + df * 1, y[pointPairs * 2 + 1] - ds);
			getEndWall().addPoint(x[pointPairs * 2 + 1] + df * 1, y[pointPairs * 2 + 1] + ds);
			getEndWall().addPoint(x[pointPairs * 2 + 1] + df * 0, y[pointPairs * 2 + 1] + ds);
		}
	}

	private void initialXY(int pairsNumber, double[] x, double[] y, int width, int height, double rest) {
		double t, p;
		for (int i = -1; i < pairsNumber; i++) {
			t = Math.random();
			x[2 * i + 2] = t;
			x[2 * i + 3] = t;
		}
		p = MathOperation.getArrayMax(x, 0, pairsNumber * 2 + 2) - MathOperation.getArrayMin(x, 0, pairsNumber * 2 + 2);
		for (int i = 0; i < pairsNumber; i++) {
			t = (x[2 * i + 2] - x[0]) * width / p;
			x[2 * i + 2] = t;
			x[2 * i + 3] = t;
		}
		x[0] = 0;
		x[1] = 0;

		y[0] = 0;
		for (int i = 0; i < pairsNumber; i++) {
			t = Math.random();
			y[2 * i + 1] = t;
			y[2 * i + 2] = t;
		}
		Arrays.sort(y, 1, 2 * pairsNumber + 1);
		for (int i = 0; i < pairsNumber; i++) {
			t = y[2 * i + 1] * rest + (1.5 + 2.0 * i) * pathWidth;
			y[2 * i + 1] = t;
			y[2 * i + 2] = t;
		}
		y[2 * pairsNumber + 1] = height;
	}

	private void setBounds(int width, int height) {
		if (width < 0) {
			throw new InvalidParameterException("The bounds width should be positive integer.");
		} else if (height < 0) {
			throw new InvalidParameterException("The bounds height should be positive integer.");
		}
		boundsWidth = width;
		boundsHeight = height;
	}

	private void setPathWidth(double pathWidth) {
		if (Double.isNaN(pathWidth)) {
			throw new InvalidParameterException("The path width should not be NaN.");
		} else if (Double.isInfinite(pathWidth)) {
			throw new InvalidParameterException("The path width should not be infinity.");
		} else if (pathWidth <= 0) {
			throw new InvalidParameterException("The path width should be a positive number.");
		}
		this.pathWidth = pathWidth;
	}

	private void setPointPairs(int pointPairs) {
		if (pointPairs < 0) {
			throw new InvalidParameterException("The point pairs should be positive integer.");
		} else if (type == RandomGridMaze.GO_UP && pointPairs > (boundsHeight / pathWidth - 1) / 2) {
			throw new InvalidParameterException(String.format("The point pairs should be smaller than %.2f.", (-1.0 + boundsHeight / pathWidth) / 2.0));
		} else if (type == RandomGridMaze.GO_RIGHT && pointPairs > (boundsWidth / pathWidth - 1) / 2) {
			throw new InvalidParameterException(String.format("The point pairs should be smaller than %.2f.", (-1.0 + boundsWidth / pathWidth) / 2.0));
		}
		this.pointPairs = pointPairs;
	}

	private void setType(int type) {
		switch (type) {
			case GO_UP:
			case GO_RIGHT:
				this.type = type;
				return;
			default:
				throw new InvalidParameterException("The type should be \"GO_UP\" or \"GO_UP\".");
		}
	}
}