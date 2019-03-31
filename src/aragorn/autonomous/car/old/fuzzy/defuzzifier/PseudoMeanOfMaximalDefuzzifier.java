package aragorn.autonomous.car.old.fuzzy.defuzzifier;

public abstract class PseudoMeanOfMaximalDefuzzifier implements PseudoDefuzzifierForAutonomousCar {

	@Override
	public double deffuzzifier() {
		double yi, mu, height = 0, n = 0, sigma = 0;
		for (long i = 0; i < getL(); i++) {
			yi = y(i);
			mu = mu(yi);
			if (height < mu) {
				height = mu;
				n = 1;
				sigma = yi;
			} else if (height == mu) {
				n++;
				sigma += yi;
			}
		}
		return (n == 0) ? 0 : (sigma / n);
	}
}
