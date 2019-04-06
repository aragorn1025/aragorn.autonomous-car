package aragorn.autonomous.car.fuzzy.membership.function;

import java.security.InvalidParameterException;

public class Trapezoidal implements MembershipFunction {

	private double a;

	private double b;

	private double c;

	private double d;

	public Trapezoidal(double a, double b, double c, double d) {
		if (a > b)
			throw new InvalidParameterException("The first parameter should be smaller than the second parameter.");
		if (b > c)
			throw new InvalidParameterException("The second parameter should be smaller than the third parameter.");
		if (c > d)
			throw new InvalidParameterException("The third parameter should be smaller than the forth parameter.");
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override
	public double f(double x) {
		if (!Double.isFinite(x))
			throw new InvalidParameterException("x should be a finite number.");
		if (x <= a)
			return 0;
		if (x >= d)
			return 0;
		if (x >= b && x <= c)
			return 1;
		if (x > a && a < b)
			return (x - a) / (b - a);
		if (x > c && x < d)
			return (d - x) / (d - c);
		throw new UnknownError();
	}
}