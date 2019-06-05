package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.NeuralLayer;

class OutputLayer extends NeuralLayer {

	OutputLayer(int input_dimension, int output_dimension) {
		super(output_dimension);
		for (int i = 0; i < getNeuronNumber(); i++) {
			setNeuron(i, new OutputNeuron(input_dimension));
		}
	}

	void setW(int j, int i, double w_j_i) {
		getNeuron(j).setW(i, w_j_i);
	}

	@Override
	protected OutputNeuron getNeuron(int i) {
		return (OutputNeuron) super.getNeuron(i);
	}
}