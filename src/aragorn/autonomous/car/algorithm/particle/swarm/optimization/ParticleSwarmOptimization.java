package aragorn.autonomous.car.algorithm.particle.swarm.optimization;

import java.io.File;
import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.algorithm.EvolutionaryTrainable;
import aragorn.autonomous.car.algorithm.TrainingDataSet;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetwork;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetworkWeight;
import aragorn.util.MathVector;

public class ParticleSwarmOptimization implements Algorithm, EvolutionaryTrainable {

	private static double getError(RadialBasisFunctionNetwork network, TrainingDataSet training_data_set, Individual individual) {
		double error = 0;
		network.load(individual.get());
		for (int j = 0; j < training_data_set.getDataNumber(); j++) {
			error += MathVector.add(EvolutionaryTrainable.normalize(network.getOutput(training_data_set.getInput(j))), training_data_set.getExpectedOutput(j))
					.getLength();
		}
		return error;
	}

	private RadialBasisFunctionNetwork network;

	private Individual global_best_individual;

	private Individual[] individuals;

	private TrainingDataSet training_data_set;

	public ParticleSwarmOptimization() {
		this(5);
		setTrainingDataSet(new TrainingDataSet(new File("./data/track/train4d_all.txt")));
		resetIndividuals(300);
		train(50);
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
		network.load(global_best_individual.get());
		System.out.println(EvolutionaryTrainable.normalize(network.getOutput(input)).getComponent(0));
		return EvolutionaryTrainable.normalize(network.getOutput(input)).getComponent(0);
	}

	private void resetIndividuals(int individual_number) {
		individuals = new Individual[individual_number];
		RadialBasisFunctionNetworkWeight weight = new RadialBasisFunctionNetworkWeight(network);
		double error;
		for (int i = 0; i < individual_number; i++) {
			individuals[i] = new Individual((RadialBasisFunctionNetworkWeight) weight.clone());
			individuals[i].get().randomized();
			error = getError(network, training_data_set, individuals[i]);
			individuals[i].setError(error);
			individuals[i].setBestError(error);
			if (global_best_individual == null || global_best_individual.getError() > error) {
				global_best_individual = (Individual) individuals[i].clone();
				global_best_individual.setError(error);
				System.out.println(global_best_individual.getError());
			}
		}
	}

	void setTrainingDataSet(TrainingDataSet training_data_set) {
		this.training_data_set = training_data_set;
	}

	@Override
	public void train() {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i].next(global_best_individual.get(), 0.5, 0.5);
		}
		double error;
		for (int i = 0; i < individuals.length; i++) {
			error = getError(network, training_data_set, individuals[i]);
			individuals[i].setError(error);
			if (error < individuals[i].getBestError()) {
				individuals[i].setBestWeight(individuals[i].get());
				individuals[i].setBestError(error);
			}
			if (error < global_best_individual.getError()) {
				global_best_individual = (Individual) individuals[i].clone();
				global_best_individual.setError(error);
				System.out.println(global_best_individual.getError());
			}
		}
	}
}