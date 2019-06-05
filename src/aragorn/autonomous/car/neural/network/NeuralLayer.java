package aragorn.autonomous.car.neural.network;

import aragorn.util.MathVector;

public abstract class NeuralLayer {

	private Neuron[] neurons;

	protected NeuralLayer(int neuron_number) {
		neurons = new Neuron[neuron_number];
	}

	protected Neuron getNeuron(int i) {
		return neurons[i];
	}

	protected int getNeuronNumber() {
		return neurons.length;
	}

	MathVector getOutput(MathVector input_vector) {
		MathVector val = new MathVector(getNeuronNumber());
		for (int i = 0; i < getNeuronNumber(); i++) {
			val.setComponent(i, neurons[i].getOutput(input_vector));
		}
		return val;
	}

	void randomizeWeight() {
		for (Neuron neuron : neurons) {
			neuron.randomizeWeights();
		}
	}

	protected void setNeuron(int i, Neuron neuron) {
		this.neurons[i] = neuron;
	}

	@Override
	public String toString() {
		String val = "";
		for (Neuron neuron : neurons) {
			val += String.format("%s%n", neuron.toString());
		}
		return String.format("\t%s [%n%s\t]", getClass().getSimpleName(), val);
	}
}