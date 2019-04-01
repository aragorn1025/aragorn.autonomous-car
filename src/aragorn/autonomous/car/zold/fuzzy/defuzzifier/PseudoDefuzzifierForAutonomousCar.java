package aragorn.autonomous.car.zold.fuzzy.defuzzifier;

import aragorn.autonomous.car.object.CarStatus;

public interface PseudoDefuzzifierForAutonomousCar extends PseudoDefuzzifier {

	public default long getL() {
		return (long) (Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE - CarStatus.MIN_WHEEL_ANGLE) / 5.0);
	}

	public default double getYMax() {
		return Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE);
	}

	public default double getYMin() {
		return Math.toDegrees(CarStatus.MIN_WHEEL_ANGLE);
	}

	public default double y(long i) {
		return (getYMin() * (getL() + 0.5 - i) - getYMax() * (0.5 - i)) / getL();
	}
}
