package aragorn.autonomous.car.old.fuzzy.defuzzifier;

public abstract class PseudoModifiedMeanOfMaximalDefuzzifier implements PseudoDefuzzifierForAutonomousCar {

	@Override
	public double deffuzzifier() {
		double yi, mu, height = 0, max_mu_y_min = getYMin(), max_mu_y_max = getYMin();
		for (long i = 0; i < getL(); i++) {
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
