package aragorn.autonomous.car.fuzzy.system;

import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.system.AutonomousSystem;

public class FuzzyAutonomousSystem extends AutonomousSystem {

	public FuzzyAutonomousSystem(LinearMaze maze, Car car) {
		super(maze, car);
	}

	/** @deprecated */
	@Override
	public int control() {
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
			getCar().move(0);
		} else {
			getCar().move(z_star_a / z_star_b);
		}
		addCarTrack();
		if (isTouchWall())
			return -1;
		if (isReachEnd())
			return 1;
		return 0;
	}

	private FuzzyMembershipFunction mu_a(int i) {
		double length = getCar().getLength();
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
		double length = getCar().getLength();
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
}