package aragorn.autonomous.car.algorithm.genetic.algorithm;

import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.neural.network.radial.basis.function.network.RadialBasisFunctionNetwork;

public class GeneticAlgorithm implements Algorithm {

	private RadialBasisFunctionNetwork network;

	public GeneticAlgorithm(int neuron_number) {
		network = new RadialBasisFunctionNetwork(3, 1, neuron_number);
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

	@Override
	public void train(int epoches) {
		// TODO
	}
}