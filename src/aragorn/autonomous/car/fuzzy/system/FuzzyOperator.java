package aragorn.autonomous.car.fuzzy.system;

import java.security.InvalidParameterException;

public class FuzzyOperator {

	public static double algebraicProductTnorms(double mu_a, double mu_b) {
		return mu_a * mu_b;
	}

	public static double algebraicSumTconorms(double mu_a, double mu_b) {
		return mu_a + mu_b - mu_a * mu_b;
	}

	public static double boundedProductTnorms(double mu_a, double mu_b) {
		return Math.max(0, mu_a + mu_b - 1);
	}

	public static double boundedSumTconorms(double mu_a, double mu_b) {
		return Math.min(1, mu_a + mu_b);
	}

	public static double drasticProductTnorms(double mu_a, double mu_b) {
		if (mu_b == 1)
			return mu_a;
		if (mu_a == 1)
			return mu_b;
		return 0;
	}

	public static double drasticSumTconorms(double mu_a, double mu_b) {
		if (mu_b == 0)
			return mu_a;
		if (mu_a == 0)
			return mu_b;
		return 1;
	}

	public static double maximumTconorms(double mu_a, double mu_b) {
		return Math.max(mu_a, mu_b);
	}

	public static double minimumTnorms(double mu_a, double mu_b) {
		return Math.min(mu_a, mu_b);
	}

	public static double negationComplement(double mu_a) {
		return 1 - mu_a;
	}

	public static double standardComplement(double mu_a) {
		return negationComplement(mu_a);
	}

	public static double standardTconorms(double mu_a, double mu_b) {
		return maximumTconorms(mu_a, mu_b);
	}

	public static double standardTnorms(double mu_a, double mu_b) {
		return minimumTnorms(mu_a, mu_b);
	}

	public static double SugenosComplement(double mu_a, double lambda) {
		if (lambda < -1)
			throw new InvalidParameterException("Lambda must in the range [-1, +INFINITY).");
		return (1 - mu_a) / (1 + lambda * mu_a);
	}

	public static double SugenosTconorms(double mu_a, double mu_b, double lambda) {
		if (lambda < -1)
			throw new InvalidParameterException("Lambda must in the range [-1, +INFINITY).");
		return Math.min(1, mu_a + mu_b - lambda * mu_a * mu_b);
	}

	public static double SugenosTnorms(double mu_a, double mu_b, double lambda) {
		if (lambda < -1)
			throw new InvalidParameterException("Lambda must in the range [-1, +INFINITY).");
		return Math.max(0, (lambda + 1) * (mu_a + mu_b - 1) - lambda * mu_a * mu_b);
	}

	public static double YagersComplement(double mu_a, double omega) {
		if (omega <= 0)
			throw new InvalidParameterException("Omega must in the range (0, +INFINITY).");
		return Math.pow(1 - Math.pow(mu_a, omega), 1.0 / omega);
	}

	public static double YagersTconorms(double mu_a, double mu_b, double omega) {
		if (omega <= 0)
			throw new InvalidParameterException("Omega must in the range (0, +INFINITY).");
		return Math.min(1, Math.pow(Math.pow(mu_a, omega) + Math.pow(mu_b, omega), 1 / omega));
	}

	public static double YagersTnorms(double mu_a, double mu_b, double omega) {
		if (omega <= 0)
			throw new InvalidParameterException("Omega must in the range (0, +INFINITY).");
		return 1 - Math.min(1, Math.pow(Math.pow(1 - mu_a, omega) + Math.pow(1 - mu_b, omega), 1 / omega));
	}
}