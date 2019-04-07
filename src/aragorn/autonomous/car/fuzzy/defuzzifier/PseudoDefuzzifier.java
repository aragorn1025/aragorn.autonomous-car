package aragorn.autonomous.car.fuzzy.defuzzifier;

import aragorn.autonomous.car.object.CarStatus;

public abstract class PseudoDefuzzifier {

	protected long i_max;

	protected double y_max;

	protected double y_min;

	public PseudoDefuzzifier() {
		this(Math.toDegrees(CarStatus.MIN_WHEEL_ANGLE), Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE),
				(long) (Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE - CarStatus.MIN_WHEEL_ANGLE) / 5.0));
	}

	private PseudoDefuzzifier(double y_min, double y_max, long i_max) {
		this.y_min = y_min;
		this.y_max = y_max;
		this.i_max = i_max;
	}

	public abstract double deffuzzifier();

	public abstract double mu(double y);

	public double y(long i) {
		return y_min + (y_max - y_min) * i / (i_max - 1);
	}
}