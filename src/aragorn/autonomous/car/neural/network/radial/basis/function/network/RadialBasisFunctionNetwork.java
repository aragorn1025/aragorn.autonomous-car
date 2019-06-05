package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.NeuralNetwork;

public class RadialBasisFunctionNetwork extends NeuralNetwork {

	public RadialBasisFunctionNetwork(int input_dimension, int output_dimension, int neuron_number) {
		super(2);
		setNeuralLayer(0, new RadialBasisFunctionLayer(input_dimension, neuron_number));
		setNeuralLayer(1, new OutputLayer(neuron_number, output_dimension));
	}

	private OutputLayer getOutputLayer() {
		return (OutputLayer) super.getNeuralLayer(1);
	}

	private RadialBasisFunctionLayer getRadialBasisFunctionLayer() {
		return (RadialBasisFunctionLayer) super.getNeuralLayer(0);
	}

	public void setM(int j, int i, double m_j_i) {
		getRadialBasisFunctionLayer().setM(j, i, m_j_i);
	}

	public void setSigma(int j, double sigma) {
		getRadialBasisFunctionLayer().setSigma(j, sigma);
	}

	public void setW(int j, int i, double w_j_i) {
		getOutputLayer().setW(j, i, w_j_i);
	}
}