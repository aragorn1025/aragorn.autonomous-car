package aragorn.autonomous.car.zold.fuzzy.memebership.function;

import java.security.InvalidParameterException;

class SimplePi implements MembershipFunction {

	private double a;

	private double b;

	public SimplePi(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		return 1.0 / (1 + Math.pow(x - a / b, 2));
	}
}