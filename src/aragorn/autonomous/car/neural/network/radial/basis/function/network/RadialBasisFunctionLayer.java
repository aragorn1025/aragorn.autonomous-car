package aragorn.autonomous.car.neural.network.radial.basis.function.network;

import aragorn.autonomous.car.neural.network.NeuralLayer;

class RadialBasisFunctionLayer extends NeuralLayer {

	RadialBasisFunctionLayer(int input_dimension, int output_dimension) {
		super(output_dimension);
		for (int i = 0; i < getNeuronNumber(); i++) {
			setNeuron(i, new RadialBasisFunctionNeuron(input_dimension));
		}
	}
	
	void setM(int j, int i, double m_j_i) {
		getNeuron(j).setM(i, m_j_i);
	}

	void setSigma(int j, double sigma) {
		getNeuron(j).setSigma(sigma);
	}

	@Override
	protected RadialBasisFunctionNeuron getNeuron(int i) {
		return (RadialBasisFunctionNeuron) super.getNeuron(i);
	}
}