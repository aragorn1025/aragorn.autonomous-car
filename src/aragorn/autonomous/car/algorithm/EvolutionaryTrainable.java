package aragorn.autonomous.car.algorithm;

public interface EvolutionaryTrainable {

	public default void train(int epoches) {
		for (int i = 0; i < epoches; i++) {
			System.out.printf("Epoches[%d]%n", i);
			train();
		}
	}

	public void train();
}