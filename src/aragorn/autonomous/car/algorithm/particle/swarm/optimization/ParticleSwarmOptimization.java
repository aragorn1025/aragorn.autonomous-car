package aragorn.autonomous.car.algorithm.particle.swarm.optimization;

import java.io.File;
import java.util.ArrayList;
import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.algorithm.EvolutionaryTrainable;
import aragorn.autonomous.car.algorithm.TrainingDataSet;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetwork;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetworkWeight;
import aragorn.util.MathVector;

public class ParticleSwarmOptimization implements Algorithm, EvolutionaryTrainable {

	private static double getError(RadialBasisFunctionNetwork network, TrainingDataSet training_data_set, Individual individual) {
		double error = 0;
		network.load(individual.get());
		for (int j = 0; j < training_data_set.getDataNumber(); j++) {
			error += MathVector.add(network.getOutput(training_data_set.getInput(j)), training_data_set.getExpectedOutput(j)).getLength();
		}
		return error;
	}

	private RadialBasisFunctionNetwork network;

	private Individual best_individual;

	private Individual[] individuals;

	private TrainingDataSet training_data_set;

	public ParticleSwarmOptimization() {
		this(8);
		setTrainingDataSet(new TrainingDataSet(new File("./data/track/train4d_all.txt")));
		resetIndividuals(5000);
		train(100);
	}

	public ParticleSwarmOptimization(int neuron_number) {
		network = new RadialBasisFunctionNetwork(3, 1, neuron_number);
	}

	@Override
	public String getName() {
		return "Particle Swarm Optimization";
	}

	@Override
	public double getOutput(double detect_left, double detect_front, double detect_right) {
		MathVector input = new MathVector(3);
		input.setComponent(2, detect_left);
		input.setComponent(0, detect_front);
		input.setComponent(1, detect_right);
		network.load(best_individual.get());
		System.out.println(network.getOutput(input).getComponent(0));
		return network.getOutput(input).getComponent(0);
	}

	private void resetIndividuals(int individual_number) {
		individuals = new Individual[individual_number];
		RadialBasisFunctionNetworkWeight weight = new RadialBasisFunctionNetworkWeight(network);
		for (int i = 0; i < individual_number; i++) {
			individuals[i] = new Individual((RadialBasisFunctionNetworkWeight) weight.clone());
			individuals[i].get().randomized();
		}
	}

	void setTrainingDataSet(TrainingDataSet training_data_set) {
		this.training_data_set = training_data_set;
	}

	@Override
	public void train() {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i].setError(getError(network, training_data_set, individuals[i]));
		}

		// TODO

		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = (Individual) new_individuals.get(i).clone();
			individuals[i].setError(getError(network, training_data_set, individuals[i]));
			if (best_individual == null || individuals[i].getError() < best_individual.getError()) {
				best_individual = (Individual) individuals[i].clone();
				best_individual.setError(getError(network, training_data_set, individuals[i]));
				System.out.println(best_individual.getError());
			}
		}
	}
}