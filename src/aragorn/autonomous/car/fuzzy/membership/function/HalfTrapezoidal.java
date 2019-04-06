package aragorn.autonomous.car.fuzzy.membership.function;

import java.security.InvalidParameterException;

public class HalfTrapezoidal implements MembershipFunction {

	private double a;

	private double b;

	private double c;

	public HalfTrapezoidal(double a, double b, String c) {
		if (!c.toLowerCase().equals("+infinity") && !c.toLowerCase().equals("+inf"))
			throw new InvalidParameterException("The third parameter should be \"+infinity\", \"+inf\" or real number.");
		if (a > b)
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		this.a = a;
		this.b = b;
		this.c = Double.POSITIVE_INFINITY;
	}

	public HalfTrapezoidal(String a, double b, double c) {
		if (!a.toLowerCase().equals("-infinity") && !a.toLowerCase().equals("-inf"))
			throw new InvalidParameterException("The first parameter should be \"-infinity\", \"-inf\" or real number.");
		if (b > c)
			throw new InvalidParameterException("The second parameter should be smaller than the third parameter.");
		this.a = Double.NEGATIVE_INFINITY;
		this.b = b;
		this.c = c;
	}

	@Override
	public double f(double x) {
		if (!Double.isFinite(x))
			throw new InvalidParameterException("x should be a finite number.");
		if (Double.isInfinite(a)) {
			if (x <= b)
				return 1;
			if (x >= c)
				return 0;
			return (c - x) / (c - b);
		}
		if (Double.isInfinite(c)) {
			if (x <= a)
				return 0;
			if (x >= b)
				return 1;
			return (x - a) / (b - a);
		}
		throw new InternalError("Unknown error.");
	}
}