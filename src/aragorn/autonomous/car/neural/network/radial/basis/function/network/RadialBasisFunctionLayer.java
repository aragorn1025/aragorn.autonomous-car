package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.NeuralLayer;

class RadialBasisFunctionLayer extends NeuralLayer {

	RadialBasisFunctionLayer(int input_dimension, int output_dimension) {
		super(output_dimension);
		for (int i = 0; i < getNeuronNumber(); i++) {
			neurons[i] = new RadialBasisFunctionNeuron(input_dimension);
		}
	}
}
