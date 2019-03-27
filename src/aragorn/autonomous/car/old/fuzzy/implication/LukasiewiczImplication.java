package aragorn.autonomous.car.old.fuzzy.implication;

import aragorn.autonomous.car.old.fuzzy.system.FuzzyOperator;

interface LukasiewiczImplication extends NotAOrBImplication {
	@Override
	public default double complement(double mu_a) {
		return FuzzyOperator.negationComplement(mu_a);
	}

	@Override
	public default double tconorms(double mu_a, double mu_b) {
		return FuzzyOperator.boundedSumTconorms(mu_a, mu_b);
	}
}