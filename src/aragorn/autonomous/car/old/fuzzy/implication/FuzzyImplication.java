package aragorn.autonomous.car.old.fuzzy.implication;

interface FuzzyImplication {
	public double mu_a(double x_a);

	public double mu_b(double x_b);

	public double mu_r(double x_a, double x_b);
}