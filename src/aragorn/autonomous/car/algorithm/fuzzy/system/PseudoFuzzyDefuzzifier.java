package aragorn.autonomous.car.algorithm.fuzzy.system;

import aragorn.autonomous.car.object.CarStatus;

abstract class PseudoFuzzyDefuzzifier {

	abstract static class CenterOfGravity extends PseudoFuzzyDefuzzifier {

		@Override
		public double deffuzzifier() {
			double yi, mu, a = 0, b = 0;
			for (long i = 0; i < getIMax(); i++) {
				yi = y(i);
				mu = mu(yi);
				a += mu * yi;
				b += mu;
			}
			return (b == 0) ? 0 : (a / b);
		}
	}

	abstract static class MeanOfMaximal extends PseudoFuzzyDefuzzifier {

		@Override
		public double deffuzzifier() {
			double yi, mu, height = Double.NEGATIVE_INFINITY, n = 0, sigma = 0;
			for (long i = 0; i < getIMax(); i++) {
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

	abstract static class ModifiedMeanOfMaximal extends PseudoFuzzyDefuzzifier {

		@Override
		public double deffuzzifier() {
			double yi, mu, height = Double.NEGATIVE_INFINITY, max_mu_y_min = getYMin(), max_mu_y_max = getYMin();
			for (long i = 0; i < getIMax(); i++) {
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

	private long i_max;

	private double y_max;

	private double y_min;

	protected PseudoFuzzyDefuzzifier() {
		this(Math.toDegrees(CarStatus.MIN_WHEEL_ANGLE), Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE),
				(long) (Math.toDegrees(CarStatus.MAX_WHEEL_ANGLE - CarStatus.MIN_WHEEL_ANGLE) / 5.0));
	}

	private PseudoFuzzyDefuzzifier(double y_min, double y_max, long i_max) {
		this.y_min = y_min;
		this.y_max = y_max;
		this.i_max = i_max;
	}

	protected abstract double deffuzzifier();

	protected long getIMax() {
		return i_max;
	}

	protected double getYMax() {
		return y_max;
	}

	protected double getYMin() {
		return y_min;
	}

	protected abstract double mu(double y);

	protected double y(long i) {
		return y_min + (y_max - y_min) * i / (i_max - 1);
	}
}