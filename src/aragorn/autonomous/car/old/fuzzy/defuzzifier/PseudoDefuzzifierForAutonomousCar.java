package aragorn.autonomous.car.old.fuzzy.defuzzifier;

import aragorn.autonomous.car.old.objects.Car;

public interface PseudoDefuzzifierForAutonomousCar extends PseudoDefuzzifier {
	public default long getL() {
		return (long) ((Car.MAX_WHEEL_ANGLE - Car.MIN_WHEEL_ANGLE) / 5.0);
	}

	public default double y(long i) {
		return (getYMin() * (getL() + 0.5 - i) - getYMax() * (0.5 - i)) / getL();
	}

	public default double getYMax() {
		return Car.MAX_WHEEL_ANGLE;
	}

	public default double getYMin() {
		return Car.MIN_WHEEL_ANGLE;
	}
}
