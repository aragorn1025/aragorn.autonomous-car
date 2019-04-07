package aragorn.autonomous.car.fuzzy.implication;

interface NotAOrBImplication extends FuzzyImplication {

	public double complement(double mu_a);

	@Override
	public default double mu_r(double x_a, double x_b) {
		double mu_a = mu_a(x_a);
		double mu_b = mu_b(x_b);
		return tconorms(complement(mu_a), mu_b);
	}

	public double tconorms(double mu_a, double mu_b);
}