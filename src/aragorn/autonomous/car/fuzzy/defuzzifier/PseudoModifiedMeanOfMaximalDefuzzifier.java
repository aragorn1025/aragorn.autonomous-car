package aragorn.autonomous.car.fuzzy.defuzzifier;

public abstract class PseudoModifiedMeanOfMaximalDefuzzifier extends PseudoDefuzzifier {

	@Override
	public double deffuzzifier() {
		double yi, mu, height = Double.NEGATIVE_INFINITY, max_mu_y_min = y_min, max_mu_y_max = y_min;
		for (long i = 0; i < i_max; i++) {
			yi = y(i);
			mu = mu(yi);
			if (height < mu) {
				height = mu;
				max_mu_y_min = yi;
				max_mu_y_max = yi;
			} else if (height == mu) {
				max_mu_y_max = yi;
			}
		}
		return (max_mu_y_min + max_mu_y_max) / 2.0;
	}
}