package aragorn.autonomous.car.zold.fuzzy.memebership.function;

import java.security.InvalidParameterException;

class GaussianFunction implements MembershipFunction {

	private double m;

	private double sigma;

	public GaussianFunction(double m, double sigma) {
		this.m = m;
		this.sigma = sigma;
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		return Math.exp(-Math.pow(x - m, 2) / (2 * Math.pow(sigma, 2)));
	}
}