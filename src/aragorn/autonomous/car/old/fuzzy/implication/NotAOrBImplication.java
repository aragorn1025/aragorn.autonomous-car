package aragorn.autonomous.car.old.fuzzy.implication;

interface NotAOrBImplication extends FuzzyImplication {
	@Override
	public default double mu_r(double x_a, double x_b) {
		double mu_a = mu_a(x_a);
		double mu_b = mu_b(x_b);
		return tconorms(complement(mu_a), mu_b);
	}

	public double complement(double mu_a);

	public double tconorms(double mu_a, double mu_b);
}