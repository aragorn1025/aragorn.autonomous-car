package aragorn.autonomous.car.old.fuzzy.memebership.function;

import java.security.InvalidParameterException;

public class Triangular implements MembershipFunction {
	private Trapezoidal function;

	public Triangular(double a, double b, double c) {
		if (a > b) {
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		} else if (b > c) {
			throw new InvalidParameterException("The second parameter should be smaller than the third parameter.");
		}
		function = new Trapezoidal(a, b, b, c);
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		return function.f(x);
	}
}