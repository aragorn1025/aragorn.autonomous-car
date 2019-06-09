package aragorn.autonomous.car.algorithm.genetic.algorithm;

import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.algorithm.EvolutionaryTrainable;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetwork;
import aragorn.util.MathVector;

public class GeneticAlgorithm implements Algorithm, EvolutionaryTrainable {

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
		MathVector input = new MathVector(3);
		input.setComponent(0, detect_left);
		input.setComponent(1, detect_front);
		input.setComponent(2, detect_right);
		return network.getOutput(input).getComponent(0);
	}

	@Override
	public void train(int individual_number, int epoches) {
		// TODO
	}
}