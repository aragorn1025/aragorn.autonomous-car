package aragorn.autonomous.car.algorithm;

public interface EvolutionaryTrainable {

	public void train();

	public default void train(int epoches) {
		for (int i = 0; i < epoches; i++) {
			System.out.printf("Epoches[%d]%n", i);
			train();
		}
	}
}