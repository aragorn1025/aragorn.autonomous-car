package aragorn.autonomous.car.zold.fuzzy.memebership.function;

import java.security.InvalidParameterException;

class ZCurve implements MembershipFunction {

	private SCurve function;

	public ZCurve(double a, double b) {
		if (a > b) {
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		}
		function = new SCurve(a, b);
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		return 1 - function.f(x);
	}
}