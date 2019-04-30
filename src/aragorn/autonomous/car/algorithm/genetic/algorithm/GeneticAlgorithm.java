package aragorn.autonomous.car.algorithm.genetic.algorithm;

import aragorn.autonomous.car.algorithm.Algorithm;

public class GeneticAlgorithm implements Algorithm {

	public GeneticAlgorithm() {
	}

	@Override
	public String getName() {
		return "Genetic Algorithm";
	}

	@Override
	public double getOutput(double detect_left, double detect_front, double detect_right) {
		System.out.println(getName());
		return 0; // TODO
	}
}