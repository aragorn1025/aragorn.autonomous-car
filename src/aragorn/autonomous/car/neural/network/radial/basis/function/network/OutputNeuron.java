package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.Neuron;
import aragorn.util.MathVector;

class OutputNeuron implements Neuron {

	private MathVector w;

	OutputNeuron(int input_dimension) {
		this(new MathVector(input_dimension + 1));
	}

	OutputNeuron(MathVector w) {
		this.w = (MathVector) w.clone();
	}

	@Override
	public double getOutput(MathVector input_vector) {
		if (1 + input_vector.getDimension() != w.getDimension())
			throw new IllegalArgumentException("The dimension of the input vector mismatched.");
		MathVector concatenated_vector = MathVector.concatenate(new MathVector(1), input_vector);
		concatenated_vector.setComponent(0, 1);
		return MathVector.getInnerProduct(concatenated_vector, w);
	}

	@Override
	public void randomizeWeight() {
		for (int i = 0; i < w.getDimension(); i++) {
			w.setComponent(i, Math.random());
		}
	}

	@Override
	public String toString() {
		return String.format("\t\t%s [w = %s]", getClass().getSimpleName(), w.toString());
	}
}
