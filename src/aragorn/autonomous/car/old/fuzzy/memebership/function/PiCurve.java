package aragorn.autonomous.car.old.fuzzy.memebership.function;

import java.security.InvalidParameterException;
import aragorn.autonomous.car.old.math.operation.MathOperation;

class PiCurve implements MembershipFunction {
	private double	mid;
	private SCurve	sf;
	private ZCurve	zf;

	public PiCurve(double a, double b) {
		mid = b;
		sf = new SCurve(b - a, b);
		zf = new ZCurve(b, a + b);
	}

	@Override
	public double f(double x) {
		if (Double.isNaN(x)) {
			throw new InvalidParameterException("x should not be NaN.");
		}
		if (MathOperation.valueInRange(x, String.format("(%s, %f)", "-infinity", mid))) {
			return sf.f(x);
		} else if (MathOperation.valueInRange(x, String.format("[%f, %f]", mid, mid))) {
			return 1.0;
		} else if (MathOperation.valueInRange(x, String.format("(%f, %s)", mid, "+infinity"))) {
			return zf.f(x);
		} else {
			throw new UnknownError();
		}
	}
}