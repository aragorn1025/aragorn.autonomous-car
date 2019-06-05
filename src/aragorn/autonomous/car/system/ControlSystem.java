package aragorn.autonomous.car.system;

import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.math.geometry.Polygon2D;
import aragorn.util.MathUtilities;

class ControlSystem {

	private LinearMaze maze;

	private Car car;

	private Algorithm algorithm;

	protected ControlSystem(Algorithm algorithm, LinearMaze maze, Car car) {
		setAlgorithm(algorithm);
		setCar(car);
		setMaze(maze);
	}

	private double detect(double angle) {
		double val = Double.POSITIVE_INFINITY;
		double x_0, x_1, x_2, y_0, y_1, y_2, theta, delta, d_l, d_t;
		Polygon2D p;

		x_0 = car.getStatus().getLocation().getX();
		y_0 = car.getStatus().getLocation().getY();
		theta = ((car.getStatus().getDirection() + Math.toRadians(angle)) % (Math.PI * 2.0) + Math.PI * 2.0) % (Math.PI * 2.0);
		p = maze.getWall();
		for (int i = 1; i < p.getPointNumber(); i++) {
			x_1 = p.getPoint(i - 1).getX();
			y_1 = p.getPoint(i - 1).getY();
			x_2 = p.getPoint(i).getX();
			y_2 = p.getPoint(i).getY();

			delta = MathUtilities.determinant_2_2(Math.cos(theta), Math.sin(theta), -x_2 + x_1, -y_2 + y_1);
			if (delta == 0) {
				continue;
			}
			d_l = MathUtilities.determinant_2_2(x_1 - x_0, y_1 - y_0, -x_2 + x_1, -y_2 + y_1) / delta;
			if (d_l < 0) {
				continue;
			}
			d_t = MathUtilities.determinant_2_2(Math.cos(theta), Math.sin(theta), x_1 - x_0, y_1 - y_0) / delta;
			if (d_t < 0 || d_t > 1) {
				continue;
			}
			val = Math.min(val, d_l);
		}
		return val;
	}

	public double detectFront() {
		return detect(0) - getCar().getFrontSensorOffset();
	}

	public double detectLeft() {
		return detect(45) - getCar().getLeftSensorOffset();
	}

	public double detectRight() {
		return detect(-45) - getCar().getRightSensorOffset();
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public Car getCar() {
		return car;
	}

	private CarStatus getCarInitialStatus() {
		return maze.getCarInitialStatus();
	}

	public LinearMaze getMaze() {
		return maze;
	}

	protected boolean isReachEnd() {
		return car.isTouchLineSegment(maze.getEndLine());
	}

	protected boolean isTouchWall() {
		for (int i = 0; i < maze.getWall().getPointNumber(); i++) {
			if (car.isTouchLineSegment(maze.getWall().getLineSegment(i))) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		car.reset(getCarInitialStatus());
	}

	public void setAlgorithm(Algorithm algorithm) {
		if (algorithm == null)
			throw new NullPointerException("The algorithm should not be null.");
		this.algorithm = algorithm;
	}

	/**
	 * Set the car reference.
	 * 
	 * @param car
	 *     the reference of the car
	 * @throws NullPointerException
	 *     if car is null
	 * @throws Error
	 *     if the super class of the car is not {@code Car}
	 */
	public void setCar(Car car) {
		if (car == null)
			throw new NullPointerException("The car should not be null.");
		if (car.getClass().getSuperclass() != Car.class)
			throw new Error("Error class for setCar(Car).");
		this.car = car;
	}

	/**
	 * Set the maze reference.
	 * 
	 * @param maze
	 *     the reference of the maze
	 * @throws NullPointerException
	 *     if maze is null
	 */
	public void setMaze(LinearMaze maze) {
		if (maze == null)
			throw new NullPointerException("The maze should not be null.");
		this.maze = maze;
		this.reset();
	}
}