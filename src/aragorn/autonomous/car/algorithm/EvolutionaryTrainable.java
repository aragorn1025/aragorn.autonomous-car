package aragorn.autonomous.car.algorithm;

import aragorn.autonomous.car.object.CarStatus;
import aragorn.util.MathVector;

public interface EvolutionaryTrainable {

	public static MathVector normalize(MathVector y) {
		return y;
		// return normalize(y, 0.0000000008);
	}

	public static MathVector normalize(MathVector y, double c) {
		MathVector val = new MathVector(y.getDimension());
		for (int i = 0; i < y.getDimension(); i++) {
			val.setComponent(i, (1 - Math.exp(-c * y.getComponent(i))) / (1 + Math.exp(-c * y.getComponent(i))) * Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE));
		}
		return val;
	}

	public void train();

	public default void train(int epoches) {
		for (int i = 0; i < epoches; i++) {
			System.out.printf("Epoches[%d]%n", i);
			train();
		}
	}
}