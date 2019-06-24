package aragorn.autonomous.car.algorithm.particle.swarm.optimization;

import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetworkWeight;

class Individual implements Comparable<Individual>, Cloneable {

	private RadialBasisFunctionNetworkWeight weight;

	private RadialBasisFunctionNetworkWeight best_weight;

	private double error;

	private double best_error;

	public Individual(RadialBasisFunctionNetworkWeight weight) {
		this.weight = (RadialBasisFunctionNetworkWeight) weight.clone();
		this.best_weight = (RadialBasisFunctionNetworkWeight) weight.clone();
		this.error = 0;
		this.best_error = 0;
	}

	@Override
	public Object clone() {
		try {
			Individual val = (Individual) super.clone();
			val.weight = (RadialBasisFunctionNetworkWeight) weight.clone();
			val.best_weight = (RadialBasisFunctionNetworkWeight) best_weight.clone();
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

	public double getBestError() {
		return best_error;
	}

	public RadialBasisFunctionNetworkWeight getBestWeight() {
		return best_weight;
	}

	public double getError() {
		return error;
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