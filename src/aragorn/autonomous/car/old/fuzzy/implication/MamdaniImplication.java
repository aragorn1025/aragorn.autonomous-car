package aragorn.autonomous.car.old.fuzzy.implication;

import aragorn.autonomous.car.old.fuzzy.system.FuzzyOperator;

interface MamdaniImplication extends AAndBImplication {
	@Override
	public default double tnorms(double mu_a, double mu_b) {
		return FuzzyOperator.minimumTnorms(mu_a, mu_b);
	}
}