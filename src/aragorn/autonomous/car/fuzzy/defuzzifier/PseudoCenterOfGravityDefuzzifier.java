package aragorn.autonomous.car.fuzzy.defuzzifier;

public abstract class PseudoCenterOfGravityDefuzzifier extends PseudoDefuzzifier {

	@Override
	public double deffuzzifier() {
		double yi, mu, a = 0, b = 0;
		for (long i = 0; i < i_max; i++) {
			yi = y(i);
			mu = mu(yi);
			a += mu * yi;
			b += mu;
		}
		return (b == 0) ? 0 : (a / b);
	}
}