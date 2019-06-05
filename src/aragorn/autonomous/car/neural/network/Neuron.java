package aragorn.autonomous.car.neural.network;

import aragorn.util.MathVector;

public abstract class Neuron {

	protected abstract double getOutput(MathVector input_vector);

	protected abstract void randomizeWeights();

	public abstract String toString();
}