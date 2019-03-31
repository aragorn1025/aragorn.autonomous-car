package aragorn.autonomous.car.old.fuzzy.system;

import java.util.ArrayList;
import aragorn.autonomous.car.old.fuzzy.defuzzifier.PseudoDefuzzifierForAutonomousCar;
import aragorn.autonomous.car.old.fuzzy.defuzzifier.PseudoModifiedMeanOfMaximalDefuzzifier;
import aragorn.autonomous.car.old.fuzzy.memebership.function.HalfTrapezoidal;
import aragorn.autonomous.car.old.fuzzy.memebership.function.MembershipFunction;
import aragorn.autonomous.car.old.fuzzy.memebership.function.Trapezoidal;
import aragorn.autonomous.car.old.fuzzy.memebership.function.Triangular;
import aragorn.autonomous.car.old.objects.Car;
import aragorn.autonomous.car.old.objects.Maze;
import aragorn.util.MathGeometryPolyline2D;
import aragorn.util.MathUtilities;

public class FuzzyAutonomousSystem implements AutonomousSystem {

	private Maze maze;

	private Car car;

	private double INFINITY;

	private ArrayList<Car> tracks = new ArrayList<>();

	private ArrayList<Double> front = new ArrayList<>();

	private ArrayList<Double> left = new ArrayList<>();

	private ArrayList<Double> right = new ArrayList<>();

	public FuzzyAutonomousSystem(Maze maze, Car car) {
		setMaze(maze);
		setCar(car);
	}

	@Override
	public void addCarTrack() {
		tracks.add(getCar().toShadow());
		front.add(detectFront());
		left.add(detectLeft());
		right.add(detectRight());
	}

	@Override
	public boolean control() {
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
				PseudoDefuzzifierForAutonomousCar defuzzifier = new PseudoModifiedMeanOfMaximalDefuzzifier() {

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
	public double detect(double angle) {
		double val = INFINITY;
		int i, j;
		double x0, x1, x2, y0, y1, y2, theta, delta, l, t;
		MathGeometryPolyline2D p;

		x0 = car.getLocation().getX();
		y0 = car.getLocation().getY();
		theta = Math.toRadians((car.getDirection() + angle + 360.0) % 360.0);
		for (j = 0; j < 3; j++) {
			switch (j) {
				case 0:
					p = maze.getLeftWall();
					break;
				case 1:
					p = maze.getRightWall();
					break;
				case 2:
					p = maze.getEndWall();
					break;
				default:
					throw new UnknownError("Error happened at the method detect.");
			}
			for (i = 1; i < p.getPointsNumber(); i++) {
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
		}
		return val;
	}

	@Override
	public Car getCar() {
		return car;
	}

	public Car getCarTracks(int index) {
		return tracks.get(index);
	}

	@Override
	public int getCarTracksNumber() {
		if (tracks.size() != front.size() || tracks.size() != right.size() || tracks.size() != left.size()) {
			throw new UnknownError("Arraylist size is not equal.");
		}
		return tracks.size();
	}

	@Override
	public Maze getMaze() {
		return maze;
	}

	@Override
	public String getSensor(int index) {
		String f, r, l;
		f = (front.get(index) == INFINITY) ? "Infinity" : String.format("%.7f", front.get(index));
		r = (right.get(index) == INFINITY) ? "Infinity" : String.format("%.7f", right.get(index));
		l = (left.get(index) == INFINITY) ? "Infinity" : String.format("%.7f", left.get(index));
		return String.format("%s %s %s", f, r, l);
	}

	@Override
	public boolean isReachEnd() {
		return car.isInside(maze.getEndArea());
	}

	private MembershipFunction mu_a(int i) {
		double length = car.getLength();
		double n = length * 1.0;
		double f = length * 2.0;

		switch (i) {
			case 1:
				return new Trapezoidal(0.0, length * 0.5, n, f);
			// return new HalfTrapezoidal("-infinity", n, f);
			case 2:
				return new HalfTrapezoidal(n, f, "+infinity");
			default:
				throw new NullPointerException();
		}
	}

	private MembershipFunction mu_b(int i) {
		double length = car.getLength();
		double or = length * -1.0;
		double cr = length * -0.0;
		double cl = length * +0.0;
		double ol = length * +1.0;
		switch (i) {
			case 1:
				return new HalfTrapezoidal("-infinity", or, cr);
			case 2:
				return new Trapezoidal(or, cr, cl, ol);
			case 3:
				return new HalfTrapezoidal(cr, ol, "+infinity");
			default:
				throw new NullPointerException();
		}
	}

	private MembershipFunction mu_z(int i, int j) {
		switch (i) {
			case 1:
				switch (j) {
					case 1:
						return new HalfTrapezoidal(0, 12, "+infinity");
					case 2:
						return new Triangular(-24, 0, 24);
					case 3:
						return new HalfTrapezoidal("-infinity", -12, 0);
					default:
						throw new NullPointerException();
				}
			case 2:
				switch (j) {
					case 1:
						return new HalfTrapezoidal(0, 24, "+infinity");
					case 2:
						return new Trapezoidal(-12, -6, 6, 12);
					case 3:
						return new HalfTrapezoidal("-infinity", -24, 0);
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
		car.reset();
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
		if (car == null) {
			throw new NullPointerException();
		} else if (car.getClass().getSuperclass() != Car.class) {
			throw new Error("Error class for setCar(Car).");
		} else {
			this.car = car;
		}
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
	public void setMaze(Maze maze) {
		if (maze == null) {
			throw new NullPointerException();
		} else {
			this.maze = maze;
			INFINITY = 4.0 * maze.getBoundsHypotenuse();
		}
	}
}