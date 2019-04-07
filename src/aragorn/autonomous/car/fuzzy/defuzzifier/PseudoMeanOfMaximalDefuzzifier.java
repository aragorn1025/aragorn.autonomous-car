package aragorn.autonomous.car.fuzzy.defuzzifier;

public abstract class PseudoMeanOfMaximalDefuzzifier extends PseudoDefuzzifier {

	@Override
	public double deffuzzifier() {
		double yi, mu, height = Double.NEGATIVE_INFINITY, n = 0, sigma = 0;
		for (long i = 0; i < i_max; i++) {
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