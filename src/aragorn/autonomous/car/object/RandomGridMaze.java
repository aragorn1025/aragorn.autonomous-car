package aragorn.autonomous.car.object;

import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import java.util.Arrays;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;
import aragorn.math.geometry.Polyline2D;
import aragorn.util.MathVector2D;

public class RandomGridMaze extends LinearMaze {

	private static void check(double maze_width, double maze_height, double path_width, int point_pair_number) {
		// check maze size
		if (maze_width < 0)
			throw new InvalidParameterException("The width should be a positive number.");
		if (maze_height < 0)
			throw new InvalidParameterException("The height should be a positive number.");

		// check path width
		if (!Double.isFinite(path_width) || path_width <= 0)
			throw new InvalidParameterException("The path width should be a finite positive number.");

		// check point pair number
		if (point_pair_number < 0)
			throw new InvalidParameterException("The point pairs should be a non-negative integer.");
		if (point_pair_number > (maze_height / path_width - 1) / 2)
			throw new InvalidParameterException(String.format("The point pairs should be smaller than %.2f.", (-1.0 + maze_height / path_width) / 2.0));
	}

	private static LineSegment2D getEndLine(Polyline2D path, double path_width) {
		Point2D.Double last_point = path.getPoint(path.getPointNumber() - 1);
		MathVector2D vector = new MathVector2D(+path_width / 2.0, 0);
		return new LineSegment2D(MathVector2D.add(last_point, vector), MathVector2D.add(last_point, vector.getNegative()));
	}

	private static Polyline2D getPath(double maze_width, double maze_height, double path_width, int point_pair_number) {
		double[] x = new double[point_pair_number * 2 + 2];
		double[] y = new double[point_pair_number * 2 + 2];
		double r, t, x_min = 1.0, x_max = 0.0;
		for (int i = -1; i < point_pair_number; i++) {
			r = Math.random();
			x_min = Math.min(x_min, r);
			x_max = Math.max(x_max, r);
			x[2 * i + 2] = r;
		}
		r = x_max - x_min;
		for (int i = 0; i < point_pair_number; i++) {
			t = (x[2 * i + 2] - x[0]) * maze_width / r;
			x[2 * i + 2] = t;
			x[2 * i + 3] = t;
		}
		x[0] = 0;
		x[1] = 0;

		y[0] = 0;
		for (int i = 0; i < point_pair_number; i++) {
			r = Math.random();
			y[2 * i + 1] = r;
			y[2 * i + 2] = r;
		}
		Arrays.sort(y, 1, 2 * point_pair_number + 1);
		double rest = maze_height - path_width * (point_pair_number * 2.0 + 1.0);
		for (int i = 0; i < point_pair_number; i++) {
			t = y[2 * i + 1] * rest + (1.5 + 2.0 * i) * path_width;
			y[2 * i + 1] = t;
			y[2 * i + 2] = t;
		}
		y[2 * point_pair_number + 1] = maze_height;

		Polyline2D val = new Polyline2D();
		for (int i = 0; i < point_pair_number * 2 + 2; i++) {
			val.addPoint(new Point2D.Double(x[i], y[i]));
		}
		return val;
	}

	private static Polygon2D getWall(Polyline2D path, double path_width) {
		double t;

		Polyline2D wall = new Polyline2D();

		// the right wall
		for (int i = 0; i < path.getPointNumber(); i++) {
			if (i == 0) {
				t = +0.5;
			} else if (i == path.getPointNumber() - 1) {
				t = -2;
			} else if (i % 2 == 1) {
				t = (path.getPoint(i).getX() < path.getPoint(i + 1).getX()) ? (+1) : (-1);
			} else {
				t = (path.getPoint(i - 1).getX() < path.getPoint(i).getX()) ? (+1) : (-1);
			}
			wall.addPoint(path.getPoint(i).getX() + path_width / 2.0, path.getPoint(i).getY() - t * path_width / 2.0);
		}

		// the left wall
		for (int i = path.getPointNumber() - 1; i >= 0; i--) {
			if (i == 0) {
				t = -0.5;
			} else if (i == path.getPointNumber() - 1) {
				t = +2;
			} else if (i % 2 == 1) {
				t = (path.getPoint(i).getX() < path.getPoint(i + 1).getX()) ? (+1) : (-1);
			} else {
				t = (path.getPoint(i - 1).getX() < path.getPoint(i).getX()) ? (+1) : (-1);
			}
			wall.addPoint(path.getPoint(i).getX() - path_width / 2.0, path.getPoint(i).getY() + t * path_width / 2.0);
		}

		// enclose the wall
		wall.addPoint(wall.getPoint(0));

		return new Polygon2D(wall);
	}

	public RandomGridMaze() {
		this(160, 90, 12, 3);
	}

	private RandomGridMaze(double maze_width, double maze_height, double path_width, int point_pair_number) {
		super();
		RandomGridMaze.check(maze_width, maze_height, path_width, point_pair_number);
		Polyline2D path = RandomGridMaze.getPath(maze_width, maze_height, path_width, point_pair_number);
		car_initial_status = new CarStatus(new Point2D.Double(0, 0), Math.toRadians(90), 0);
		end_line = RandomGridMaze.getEndLine(path, path_width);
		wall = RandomGridMaze.getWall(path, path_width);
	}
}