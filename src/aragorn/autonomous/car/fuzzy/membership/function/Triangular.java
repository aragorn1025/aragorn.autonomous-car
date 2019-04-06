package aragorn.autonomous.car.fuzzy.membership.function;

import java.security.InvalidParameterException;

public class Triangular implements MembershipFunction {

	private Trapezoidal function;

	public Triangular(double a, double b, double c) {
		if (b > c)
			throw new InvalidParameterException("The second parameter should be smaller than the third parameter.");
		function = new Trapezoidal(a, b, b, c);
	}

	@Override
	public double f(double x) {
		return function.f(x);
	}
}