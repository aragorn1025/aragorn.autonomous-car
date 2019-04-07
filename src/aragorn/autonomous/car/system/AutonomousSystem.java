package aragorn.autonomous.car.system;

import java.util.ArrayList;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.math.geometry.Polygon2D;
import aragorn.util.MathUtilities;

public abstract class AutonomousSystem {

	private LinearMaze maze;

	private Car car;

	private ArrayList<CarStatus> tracks = new ArrayList<>();

	private ArrayList<Double> front = new ArrayList<>();

	private ArrayList<Double> left = new ArrayList<>();

	private ArrayList<Double> right = new ArrayList<>();

	public AutonomousSystem(LinearMaze maze, Car car) {
		setCar(car);
		setMaze(maze);
	}

	public void addCarTrack() {
		tracks.add(getCar().getCloneStatus());
		front.add(detectFront());
		left.add(detectLeft());
		right.add(detectRight());
	}

	public abstract int control();

	public double detect(double angle) {
		double val = Double.POSITIVE_INFINITY;
		double x0, x1, x2, y0, y1, y2, theta, delta, l, t;
		Polygon2D p;

		x0 = car.getStatus().getLocation().getX();
		y0 = car.getStatus().getLocation().getY();
		theta = ((car.getStatus().getDirection() + Math.toRadians(angle)) % (Math.PI * 2.0) + Math.PI * 2.0) % (Math.PI * 2.0);
		p = maze.getWall();
		for (int i = 1; i < p.getPointNumber(); i++) {
			x1 = p.getPoint(i - 1).getX();
			y1 = p.getPoint(i - 1).getY();
			x2 = p.getPoint(i).getX();
			y2 = p.getPoint(i).getY();

			delta = MathUtilities.determinant_2_2(Math.cos(theta), Math.sin(theta), -x2 + x1, -y2 + y1);
			if (delta == 0) {
				continue;
			}
			l = MathUtilities.determinant_2_2(x1 - x0, y1 - y0, -x2 + x1, -y2 + y1) / delta;
			if (l < 0) {
				continue;
			}
			t = MathUtilities.determinant_2_2(Math.cos(theta), Math.sin(theta), x1 - x0, y1 - y0) / delta;
			if (t < 0 || t > 1) {
				continue;
			}
			val = Math.min(val, l);
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

	public Car getCar() {
		return car;
	}

	public CarStatus getCarInitialStatus() {
		return maze.getCarInitialStatus();
	}

	public CarStatus getCarTracks(int index) {
		return tracks.get(index);
	}

	public int getCarTracksNumber() {
		if (tracks.size() != front.size() || tracks.size() != right.size() || tracks.size() != left.size())
			throw new UnknownError("Arraylist size is not equal.");
		return tracks.size();
	}

	public LinearMaze getMaze() {
		return maze;
	}

	public String getSensor(int index) {
		return String.format("%.7f %.7f %.7f", front.get(index), right.get(index), left.get(index));
	}

	public boolean isReachEnd() {
		return car.isTouchLineSegment(maze.getEndLine());
	}

	public boolean isTouchWall() {
		for (int i = 0; i < maze.getWall().getPointNumber(); i++) {
			if (car.isTouchLineSegment(maze.getWall().getLineSegment(i))) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		front.clear();
		left.clear();
		right.clear();
		tracks.clear();
		car.reset(getCarInitialStatus());
		addCarTrack();
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
			throw new NullPointerException();
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
			throw new NullPointerException();
		this.maze = maze;
		this.reset();
	}
}