package aragorn.autonomous.car.old.fuzzy.implication;

interface AAndBThenOrNotAImplication extends FuzzyImplication {
	@Override
	public default double mu_r(double x_a, double x_b) {
		double mu_a = mu_a(x_a);
		double mu_b = mu_b(x_b);
		return tconorms(tnorms(mu_a, mu_b), complement(mu_a));
	}

	public double complement(double mu_a);

	public double tnorms(double mu_a, double mu_b);

	public double tconorms(double mu_a, double mu_b);
}