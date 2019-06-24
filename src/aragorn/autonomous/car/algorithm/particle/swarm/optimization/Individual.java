package aragorn.autonomous.car.algorithm.particle.swarm.optimization;

import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetworkWeight;

class Individual implements Comparable<Individual>, Cloneable {

	private RadialBasisFunctionNetworkWeight weight;

	private RadialBasisFunctionNetworkWeight best_weight;

	private RadialBasisFunctionNetworkWeight v;

	private double error;

	private double best_error;

	public Individual(RadialBasisFunctionNetworkWeight weight) {
		this.weight = (RadialBasisFunctionNetworkWeight) weight.clone();
		this.best_weight = (RadialBasisFunctionNetworkWeight) weight.clone();
		this.v = new RadialBasisFunctionNetworkWeight(weight.getMDimension(), weight.getWSize(), weight.getMSize());
		this.error = 0;
		this.best_error = 0;
	}

	@Override
	public Object clone() {
		try {
			Individual val = (Individual) super.clone();
			val.weight = (RadialBasisFunctionNetworkWeight) weight.clone();
			val.best_weight = (RadialBasisFunctionNetworkWeight) best_weight.clone();
			val.v = (RadialBasisFunctionNetworkWeight) v.clone();
//			val.error = 0;
			return val;
		} catch (CloneNotSupportedException e) {
			throw new InternalError("Unknown error.");
		}
	}

	@Override
	public int compareTo(Individual compared) {
		return (this.error - compared.error) >= 0 ? 1 : -1;
	}

	public RadialBasisFunctionNetworkWeight get() {
		return weight;
	}

	public RadialBasisFunctionNetworkWeight getBest() {
		return best_weight;
	}

	public double getBestError() {
		return best_error;
	}

	public double getError() {
		return error;
	}

	public RadialBasisFunctionNetworkWeight getV() {
		return v;
	}

	public void next(RadialBasisFunctionNetworkWeight p_g, double lo_i, double lo_g) {
		for (int j = 0; j < v.getMSize(); j++) {
			for (int i = 0; i < v.getMDimension(); i++) {
				v.setM(j, i, v.getM(j, i) + lo_i * (best_weight.getM(j, i) - weight.getM(j, i)) + lo_g * (p_g.getM(j, i) - weight.getM(j, i)));
				weight.setM(j, i, weight.getM(j, i) + v.getM(j, i));
			}
		}
		for (int j = 0; j < v.getSigmaSize(); j++) {
			v.setSigma(j, v.getSigma(j) + lo_i * (best_weight.getSigma(j) - weight.getSigma(j)) + lo_g * (p_g.getSigma(j) - weight.getSigma(j)));
			weight.setSigma(j, weight.getSigma(j) + v.getSigma(j));
		}
		for (int j = 0; j < v.getWSize(); j++) {
			for (int i = 0; i < v.getWDimension(); i++) {
				v.setW(j, i, v.getW(j, i) + lo_i * (best_weight.getW(j, i) - weight.getW(j, i)) + lo_g * (p_g.getW(j, i) - weight.getW(j, i)));
				weight.setW(j, i, weight.getW(j, i) + v.getW(j, i));
			}
		}
	}

	public void setBestError(double best_error) {
		this.best_error = best_error;
	}

	public void setBestWeight(RadialBasisFunctionNetworkWeight best_weight) {
		this.best_weight = best_weight;
	}

	public void setError(double error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return String.format("%.8f", error);
	}
}