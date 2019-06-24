package aragorn.autonomous.car.algorithm;

import aragorn.neural.network.radial.basis.function.network.RadialBasisFunctionNetworkWeight;

public class Individual implements Comparable<Individual>, Cloneable {

	private RadialBasisFunctionNetworkWeight weight;

	private double error;

	public Individual(RadialBasisFunctionNetworkWeight weight) {
		this.weight = (RadialBasisFunctionNetworkWeight) weight.clone();
		this.error = 0;
	}

	@Override
	public Object clone() {
		try {
			Individual val = (Individual) super.clone();
			val.weight = (RadialBasisFunctionNetworkWeight) weight.clone();
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

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return String.format("%.8f", error);
	}
}