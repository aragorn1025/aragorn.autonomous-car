package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.NeuralNetwork;

public class RadialBasisFunctionNetwork extends NeuralNetwork {

	public RadialBasisFunctionNetwork(int input_dimension, int output_dimension, int neuron_number) {
		super(2);
		neural_layers[0] = new RadialBasisFunctionLayer(input_dimension, neuron_number);
		neural_layers[1] = new OutputLayer(neuron_number, output_dimension);
	}
}