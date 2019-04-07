package aragorn.autonomous.car.fuzzy.implication;

interface AAndBImplication extends FuzzyImplication {

	@Override
	public default double mu_r(double x_a, double x_b) {
		double mu_a = mu_a(x_a);
		double mu_b = mu_b(x_b);
		return tnorms(mu_a, mu_b);
	}

	public double tnorms(double mu_a, double mu_b);
}