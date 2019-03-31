package aragorn.autonomous.car.old.fuzzy.memebership.function;

import java.security.InvalidParameterException;
import aragorn.autonomous.car.old.math.operation.MathOperation;

class SCurve implements MembershipFunction {

	private double a;

	private double b;

	public SCurve(double a, double b) {
		if (a > b) {
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		}
		this.a = a;
		this.b = b;
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		double mid = (a + b) / 2.0;
		if (MathOperation.valueInRange(x, String.format("(%s, %f]", "-infinity", a))) {
			return 0;
		} else if (MathOperation.valueInRange(x, String.format("(%f, %f)", a, mid))) {
			return 2.0 * Math.pow((x - a) / (b - a), 2);
		} else if (MathOperation.valueInRange(x, String.format("[%f, %f]", mid, mid))) {
			return 0.5;
		} else if (MathOperation.valueInRange(x, String.format("(%f, %f)", mid, b))) {
			return 1 - 2.0 * Math.pow((x - b) / (b - a), 2);
		} else if (MathOperation.valueInRange(x, String.format("[%f, %s)", b, "+infinity"))) {
			return 1;
		} else {
			throw new UnknownError();
		}
	}
}