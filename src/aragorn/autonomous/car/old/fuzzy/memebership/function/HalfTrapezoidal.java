package aragorn.autonomous.car.old.fuzzy.memebership.function;

import java.security.InvalidParameterException;
import aragorn.autonomous.car.old.math.operation.MathOperation;

public class HalfTrapezoidal implements MembershipFunction {

	private String as;

	private String cs;

	private double a;

	private double b;

	private double c;

	public HalfTrapezoidal(double a, double b, String c) {
		if (!c.toLowerCase().equals("+infinity") && !c.toLowerCase().equals("+inf")) {
			throw new InvalidParameterException("The third parameter should be \"+infinity\", \"+inf\" or real number.");
		}
		if (a > b) {
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		}

		this.a = a;
		this.b = b;
		this.cs = c;
		this.as = null;
	}

	public HalfTrapezoidal(String a, double b, double c) {
		if (!a.toLowerCase().equals("-infinity") && !a.toLowerCase().equals("-inf")) {
			throw new InvalidParameterException("The first parameter should be \"-infinity\", \"-inf\" or real number.");
		}
		if (b > c) {
			throw new InvalidParameterException("The second parameter should be smaller than the third parameter.");
		}

		this.as = a;
		this.b = b;
		this.c = c;
		this.cs = null;
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		if (cs == null) {
			if (MathOperation.valueInRange(x, String.format("(%s, %f]", as, b))) {
				return 1;
			} else if (MathOperation.valueInRange(x, String.format("(%f, %f)", b, c))) {
				return (c - x) / (c - b);
			} else if (MathOperation.valueInRange(x, String.format("[%f, %s)", c, "+infinity"))) {
				return 0;
			} else {
				throw new UnknownError();
			}
		} else if (as == null) {
			if (MathOperation.valueInRange(x, String.format("(%s, %f]", "-infinity", a))) {
				return 0;
			} else if (MathOperation.valueInRange(x, String.format("(%f, %f)", a, b))) {
				return (x - a) / (b - a);
			} else if (MathOperation.valueInRange(x, String.format("[%f, %s)", b, cs))) {
				return 1;
			} else {
				throw new UnknownError();
			}
		} else {
			throw new UnknownError();
		}
	}
}