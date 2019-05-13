package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.NeuralLayer;

class OutputLayer extends NeuralLayer {

	OutputLayer(int input_dimension, int output_dimension) {
		super(output_dimension);
		for (int i = 0; i < getNeuronNumber(); i++) {
			neurons[i] = new OutputNeuron(input_dimension);
		}
	}
}
