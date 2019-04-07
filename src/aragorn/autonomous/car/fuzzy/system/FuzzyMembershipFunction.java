package aragorn.autonomous.car.fuzzy.system;

import java.security.InvalidParameterException;

public interface FuzzyMembershipFunction {

	public double f(double x);

	public static class HalfTrapezoidal implements FuzzyMembershipFunction {

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

	public static class Trapezoidal implements FuzzyMembershipFunction {

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
			if (x > a && x < b)
				return (x - a) / (b - a);
			if (x > c && x < d)
				return (d - x) / (d - c);
			throw new UnknownError();
		}
	}

	public static class Triangular implements FuzzyMembershipFunction {

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
}