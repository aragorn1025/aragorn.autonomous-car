package aragorn.autonomous.car.fuzzy.implication;

import aragorn.autonomous.car.zold.fuzzy.system.FuzzyOperator;

interface ProductImplication extends AAndBImplication {

	@Override
	public default double tnorms(double mu_a, double mu_b) {
		return FuzzyOperator.algebraicProductTnorms(mu_a, mu_b);
	}
}