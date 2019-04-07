package aragorn.autonomous.car.zold.fuzzy.implication;

import aragorn.autonomous.car.zold.fuzzy.system.FuzzyOperator;

interface ZadelImplication extends AAndBThenOrNotAImplication {

	@Override
	public default double complement(double mu_a) {
		return FuzzyOperator.negationComplement(mu_a);
	}

	@Override
	public default double tconorms(double mu_a, double mu_b) {
		return FuzzyOperator.maximumTconorms(mu_a, mu_b);
	}

	@Override
	public default double tnorms(double mu_a, double mu_b) {
		return FuzzyOperator.minimumTnorms(mu_a, mu_b);
	}
}