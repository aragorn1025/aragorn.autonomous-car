package aragorn.autonomous.car.algorithm.genetic.algorithm;

import java.io.File;
import java.util.ArrayList;
import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.algorithm.EvolutionaryTrainable;
import aragorn.autonomous.car.algorithm.TrainingDataSet;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetwork;
import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetworkWeight;
import aragorn.util.MathVector;

public class GeneticAlgorithm implements Algorithm, EvolutionaryTrainable {

	private static ArrayList<Individual> crossover(ArrayList<Individual> mating_pool_, double rate) {
		ArrayList<Individual> mating_pool = new ArrayList<Individual>();
		for (int i = 0; i < mating_pool_.size(); i++) {
			mating_pool.add((Individual) mating_pool_.get(i).clone());
		}

		ArrayList<Individual> mutating_pool = new ArrayList<Individual>();
		while (mating_pool.size() >= 2) {
			RadialBasisFunctionNetworkWeight w_0 = mating_pool.remove((int) (Math.random() * mating_pool.size())).get();
			RadialBasisFunctionNetworkWeight w_1 = mating_pool.remove((int) (Math.random() * mating_pool.size())).get();
			if (Math.random() <= rate) {
				double temp = 0;
				for (int j = 0; j < w_0.getMSize(); j++) {
					for (int i = 0; i < w_0.getMDimension(); i++) {
						temp = (w_0.getM(j, i) - w_1.getM(j, i)) * Math.random() * 0.9;
						w_0.setM(j, i, w_0.getM(j, i) + temp);
						w_1.setM(j, i, w_1.getM(j, i) - temp);
					}
				}
				for (int j = 0; j < w_0.getSigmaSize(); j++) {
					temp = (w_0.getSigma(j) - w_1.getSigma(j)) * Math.random() * 0.9;
					w_0.setSigma(j, w_0.getSigma(j) + temp);
					w_1.setSigma(j, w_0.getSigma(j) - temp);
				}
				for (int j = 0; j < w_0.getWSize(); j++) {
					for (int i = 0; i < w_0.getWDimension(); i++) {
						temp = (w_0.getW(j, i) - w_1.getW(j, i)) * Math.random() * 0.9;
						w_0.setW(j, i, w_0.getW(j, i) + temp);
						w_1.setW(j, i, w_0.getW(j, i) - temp);
					}
				}
			}
			mutating_pool.add(new Individual(w_0));
			mutating_pool.add(new Individual(w_1));
		}

		while (mating_pool.size() > 0) {
			mutating_pool.add((Individual) mating_pool.remove(0).clone());
		}
		return mutating_pool;
	}

	private static double getError(RadialBasisFunctionNetwork network, TrainingDataSet training_data_set, Individual individual) {
		double error = 0;
		network.load(individual.get());
		for (int j = 0; j < training_data_set.getDataNumber(); j++) {
			error += MathVector.add(EvolutionaryTrainable.normalize(network.getOutput(training_data_set.getInput(j))), training_data_set.getExpectedOutput(j))
					.getLength();
		}
		return error;
	}

	private static ArrayList<Individual> mutate(ArrayList<Individual> mutating_pool, double rate) {
		ArrayList<Individual> val = new ArrayList<Individual>();
		for (int k = 0; k < mutating_pool.size(); k++) {
			val.add((Individual) mutating_pool.get(k).clone());
			if (Math.random() <= rate) {
				RadialBasisFunctionNetworkWeight w = val.get(k).get();
				for (int j = 0; j < w.getMSize(); j++) {
					for (int i = 0; i < w.getMDimension(); i++) {
						w.setM(j, i, w.getM(j, i) * (Math.random() * 0.2 - 0.1 + 1));
					}
				}
				for (int j = 0; j < w.getSigmaSize(); j++) {
					w.setSigma(j, w.getSigma(j) * (Math.random() * 0.2 - 0.1 + 1));
				}
				for (int j = 0; j < w.getWSize(); j++) {
					for (int i = 0; i < w.getWDimension(); i++) {
						w.setW(j, i, w.getW(j, i) * (Math.random() * 0.2 - 0.1 + 1));
					}
				}
			}
		}
		return val;
	}

	private static ArrayList<Individual> reproduction(Individual[] individuals) {
		return tournament_selection(individuals);
	}

	@SuppressWarnings("unused")
	private static ArrayList<Individual> roulette_wheel_selection(Individual[] individuals) {
//		System.out.println("Individuals");
//		Arrays.sort(individuals);
//		for (int i = 0; i < individuals.length; i++) {
//			System.out.println(individuals[i].getError());
//		}

		ArrayList<Individual> mating_pool = new ArrayList<>();
		double error_max = 0.0;
		for (int i = 0; i < individuals.length; i++) {
			if (error_max < individuals[i].getError()) {
				error_max = individuals[i].getError();
			}
		}
		double f_bar = 0.0;
		for (int i = 0; i < individuals.length; i++) {
			f_bar += error_max - individuals[i].getError();
		}
		f_bar /= individuals.length;
		for (int i = 0; i < individuals.length; i++) {
			int n = (int) Math.round((error_max - individuals[i].getError()) / f_bar);
			while (n > 0) {
				mating_pool.add((Individual) individuals[i].clone());
				n--;
			}
		}
		while (mating_pool.size() > individuals.length) {
			mating_pool.remove((int) (Math.random() * mating_pool.size()));
		}
		while (mating_pool.size() < individuals.length) {
			mating_pool.add((Individual) individuals[(int) (Math.random() * individuals.length)].clone());
		}

//		System.out.println("Mating pool");
//		Collections.sort(mating_pool);
//		for (int i = 0; i < mating_pool.size(); i++) {
//			System.out.println(mating_pool.get(i).getError());
//		}
//		System.out.println("End");
		return mating_pool;
	}

	private static ArrayList<Individual> tournament_selection(Individual[] individuals_) {
		ArrayList<Individual> individuals = new ArrayList<Individual>();
		for (int i = 0; i < individuals_.length; i++) {
			individuals.add((Individual) individuals_[i].clone());
		}

		ArrayList<Individual> mating_pool = new ArrayList<>();
		while (individuals.size() >= 2) {
			Individual i_0 = individuals.remove((int) (Math.random() * individuals.size()));
			Individual i_1 = individuals.remove((int) (Math.random() * individuals.size()));
			if (i_0.getError() <= i_1.getError()) {
				mating_pool.add((Individual) i_0.clone());
				mating_pool.add((Individual) i_0.clone());
			} else {
				mating_pool.add((Individual) i_1.clone());
				mating_pool.add((Individual) i_1.clone());
			}
		}
		while (individuals.size() > 0) {
			mating_pool.add((Individual) individuals.remove(0).clone());
		}
		return mating_pool;
	}

	private RadialBasisFunctionNetwork network;

	private Individual best_individual;

	private Individual[] individuals;

	private TrainingDataSet training_data_set;

	public GeneticAlgorithm() {
		this(5);
		setTrainingDataSet(new TrainingDataSet(new File("./data/track/train4d_all.txt")));
		resetIndividuals(300);
		train(50);
	}

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
		input.setComponent(2, detect_left);
		input.setComponent(0, detect_front);
		input.setComponent(1, detect_right);
		network.load(best_individual.get());
		System.out.println(EvolutionaryTrainable.normalize(network.getOutput(input)).getComponent(0));
		return EvolutionaryTrainable.normalize(network.getOutput(input)).getComponent(0);
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

		ArrayList<Individual> mating_pool = GeneticAlgorithm.reproduction(individuals);
		ArrayList<Individual> mutating_pool = GeneticAlgorithm.crossover(mating_pool, 0.80);
		ArrayList<Individual> new_individuals = mutate(mutating_pool, 0.50);

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