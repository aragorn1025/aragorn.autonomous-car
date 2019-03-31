package aragorn.autonomous.car.old.fuzzy.memebership.function;

import java.security.InvalidParameterException;
import aragorn.autonomous.car.old.math.operation.MathOperation;

public class Trapezoidal implements MembershipFunction {

	private double a;

	private double b;

	private double c;

	private double d;

	public Trapezoidal(double a, double b, double c, double d) {
		if (a > b) {
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		} else if (b > c) {
			throw new InvalidParameterException("The second parameter should be smaller than the third parameter.");
		} else if (c > d) {
			throw new InvalidParameterException("The third parameter should be smaller than the forth parameter.");
		}
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		if (MathOperation.valueInRange(x, String.format("(%s, %f]", "-infinity", a))) {
			return 0;
		} else if (MathOperation.valueInRange(x, String.format("(%f, %f)", a, b))) {
			return (x - a) / (b - a);
		} else if (MathOperation.valueInRange(x, String.format("[%f, %f]", b, c))) {
			return 1;
		} else if (MathOperation.valueInRange(x, String.format("(%f, %f)", c, d))) {
			return (d - x) / (d - c);
		} else if (MathOperation.valueInRange(x, String.format("[%f, %s)", d, "+infinity"))) {
			return 0;
		} else {
			throw new UnknownError();
		}
	}
}