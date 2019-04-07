package aragorn.autonomous.car.fuzzy.system;

import java.util.ArrayList;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.math.geometry.Polygon2D;
import aragorn.util.MathUtilities;

public class FuzzyAutonomousSystem implements AutonomousSystem {

	private LinearMaze maze;

	private Car car;

	private ArrayList<CarStatus> tracks = new ArrayList<>();

	private ArrayList<Double> front = new ArrayList<>();

	private ArrayList<Double> left = new ArrayList<>();

	private ArrayList<Double> right = new ArrayList<>();

	public FuzzyAutonomousSystem(LinearMaze maze, Car car) {
		setMaze(maze);
		setCar(car);
	}

	@Override
	public void addCarTrack() {
		tracks.add(getCar().getCloneStatus());
		front.add(detectFront());
		left.add(detectLeft());
		right.add(detectRight());
	}

	@Override
	public boolean control() { // TODO uncheck
		double x_a = detectFront();
		double x_b = detectRight() - detectLeft();
		double[][] alpha = new double[2][3];
		double[][] z = new double[2][3];
		double z_star_a = 0;
		double z_star_b = 0;
		for (int i = 0; i < alpha.length; i++) {
			for (int j = 0; j < alpha[i].length; j++) {
				int vi = i + 1, vj = j + 1;
				alpha[i][j] = FuzzyOperator.standardTnorms(mu_a(vi).f(x_a), mu_b(vj).f(x_b));
				// PseudoDefuzzifierForAutonomousCar defuzzifier = new PseudoCenterOfGravityDefuzzifier() {
				// PseudoDefuzzifierForAutonomousCar defuzzifier = new PseudoMeanOfMaximalDefuzzifier() {
				PseudoFuzzyDefuzzifier defuzzifier = new PseudoFuzzyDefuzzifier.ModifiedMeanOfMaximal() {

					@Override
					public double mu(double y) {
						return FuzzyOperator.standardTnorms(mu_z(vi, vj).f(y), alpha[vi - 1][vj - 1]);
					}
				};
				z[i][j] = defuzzifier.deffuzzifier();
				z_star_a += z[i][j] * alpha[i][j];
				z_star_b += alpha[i][j];
			}
		}
		if (z_star_b == 0) {
			car.move(0);
		} else {
			car.move(z_star_a / z_star_b);
		}
		return isReachEnd();
	}

	@Override
	public double detect(double angle) { // TODO checking
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

	@Override
	public Car getCar() {
		return car;
	}

	@Override
	public CarStatus getCarInitialStatus() {
		return maze.getCarInitialStatus();
	}

	public CarStatus getCarTracks(int index) {
		return tracks.get(index);
	}

	@Override
	public int getCarTracksNumber() {
		if (tracks.size() != front.size() || tracks.size() != right.size() || tracks.size() != left.size())
			throw new UnknownError("Arraylist size is not equal.");
		return tracks.size();
	}

	@Override
	public LinearMaze getMaze() {
		return maze;
	}

	@Override
	public String getSensor(int index) {
		return String.format("%.7f %.7f %.7f", front.get(index), right.get(index), left.get(index));
	}

	@Override
	public boolean isReachEnd() {
		return car.isInside(maze.getEndArea());
	}

	private FuzzyMembershipFunction mu_a(int i) {
		double length = car.getLength();
		double n = length * 1.0;
		double f = length * 2.0;

		switch (i) {
			case 1:
				return new FuzzyMembershipFunction.Trapezoidal(0.0, length * 0.5, n, f);
			case 2:
				return new FuzzyMembershipFunction.HalfTrapezoidal(n, f, "+infinity");
			default:
				throw new NullPointerException();
		}
	}

	private FuzzyMembershipFunction mu_b(int i) {
		double length = car.getLength();
		double or = length * -1.0;
		double cr = length * -0.0;
		double cl = length * +0.0;
		double ol = length * +1.0;
		switch (i) {
			case 1:
				return new FuzzyMembershipFunction.HalfTrapezoidal("-infinity", or, cr);
			case 2:
				return new FuzzyMembershipFunction.Trapezoidal(or, cr, cl, ol);
			case 3:
				return new FuzzyMembershipFunction.HalfTrapezoidal(cr, ol, "+infinity");
			default:
				throw new NullPointerException();
		}
	}

	private FuzzyMembershipFunction mu_z(int i, int j) {
		switch (i) {
			case 1:
				switch (j) {
					case 1:
						return new FuzzyMembershipFunction.HalfTrapezoidal(0, 12, "+infinity");
					case 2:
						return new FuzzyMembershipFunction.Triangular(-24, 0, 24);
					case 3:
						return new FuzzyMembershipFunction.HalfTrapezoidal("-infinity", -12, 0);
					default:
						throw new NullPointerException();
				}
			case 2:
				switch (j) {
					case 1:
						return new FuzzyMembershipFunction.HalfTrapezoidal(0, 24, "+infinity");
					case 2:
						return new FuzzyMembershipFunction.Trapezoidal(-12, -6, 6, 12);
					case 3:
						return new FuzzyMembershipFunction.HalfTrapezoidal("-infinity", -24, 0);
					default:
						throw new NullPointerException();
				}
			default:
				throw new NullPointerException(String.format("i = %d, j = %d", i, j));
		}
	}

	@Override
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
	@Override
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
	@Override
	public void setMaze(LinearMaze maze) {
		if (maze == null)
			throw new NullPointerException();
		this.maze = maze;
	}
}