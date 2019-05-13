package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.Neuron;
import aragorn.util.MathVector;

class RadialBasisFunctionNeuron implements Neuron {

	private MathVector m;

	private double sigma;

	RadialBasisFunctionNeuron(int input_dimension) {
		this(input_dimension, 0);
	}

	RadialBasisFunctionNeuron(int input_dimension, double sigma) {
		this(new MathVector(input_dimension), sigma);
	}

	RadialBasisFunctionNeuron(MathVector m, double sigma) {
		this.m = (MathVector) m.clone();
		this.sigma = sigma;
	}

	@Override
	public double getOutput(MathVector x) {
		MathVector vector = MathVector.add(x, m.getNegative());
		return Math.exp(-0.5 * MathVector.getInnerProduct(vector, vector) / (sigma * sigma));
	}

	@Override
	public void randomizeWeight() {
		for (int i = 0; i < m.getDimension(); i++) {
			m.setComponent(i, Math.random());
		}
		sigma = Math.random();
	}

	@Override
	public String toString() {
		return String.format("\t\t%s [m = %s, sigma = %f]", getClass().getSimpleName(), m.toString(), sigma);
	}
}