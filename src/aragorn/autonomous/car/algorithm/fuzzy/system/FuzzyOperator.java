package aragorn.autonomous.car.algorithm.fuzzy.system;

import java.security.InvalidParameterException;

class FuzzyOperator {

	static double algebraicProductTnorms(double mu_a, double mu_b) {
		return mu_a * mu_b;
	}

	static double algebraicSumTconorms(double mu_a, double mu_b) {
		return mu_a + mu_b - mu_a * mu_b;
	}

	static double boundedProductTnorms(double mu_a, double mu_b) {
		return Math.max(0, mu_a + mu_b - 1);
	}

	static double boundedSumTconorms(double mu_a, double mu_b) {
		return Math.min(1, mu_a + mu_b);
	}

	static double drasticProductTnorms(double mu_a, double mu_b) {
		if (mu_b == 1)
			return mu_a;
		if (mu_a == 1)
			return mu_b;
		return 0;
	}

	static double drasticSumTconorms(double mu_a, double mu_b) {
		if (mu_b == 0)
			return mu_a;
		if (mu_a == 0)
			return mu_b;
		return 1;
	}

	static double maximumTconorms(double mu_a, double mu_b) {
		return Math.max(mu_a, mu_b);
	}

	static double minimumTnorms(double mu_a, double mu_b) {
		return Math.min(mu_a, mu_b);
	}

	static double negationComplement(double mu_a) {
		return 1 - mu_a;
	}

	static double standardComplement(double mu_a) {
		return negationComplement(mu_a);
	}

	static double standardTconorms(double mu_a, double mu_b) {
		return maximumTconorms(mu_a, mu_b);
	}

	static double standardTnorms(double mu_a, double mu_b) {
		return minimumTnorms(mu_a, mu_b);
	}

	static double SugenosComplement(double mu_a, double lambda) {
		if (lambda < -1)
			throw new InvalidParameterException("Lambda must in the range [-1, +INFINITY).");
		return (1 - mu_a) / (1 + lambda * mu_a);
	}

	static double SugenosTconorms(double mu_a, double mu_b, double lambda) {
		if (lambda < -1)
			throw new InvalidParameterException("Lambda must in the range [-1, +INFINITY).");
		return Math.min(1, mu_a + mu_b - lambda * mu_a * mu_b);
	}

	static double SugenosTnorms(double mu_a, double mu_b, double lambda) {
		if (lambda < -1)
			throw new InvalidParameterException("Lambda must in the range [-1, +INFINITY).");
		return Math.max(0, (lambda + 1) * (mu_a + mu_b - 1) - lambda * mu_a * mu_b);
	}

	static double YagersComplement(double mu_a, double omega) {
		if (omega <= 0)
			throw new InvalidParameterException("Omega must in the range (0, +INFINITY).");
		return Math.pow(1 - Math.pow(mu_a, omega), 1.0 / omega);
	}

	static double YagersTconorms(double mu_a, double mu_b, double omega) {
		if (omega <= 0)
			throw new InvalidParameterException("Omega must in the range (0, +INFINITY).");
		return Math.min(1, Math.pow(Math.pow(mu_a, omega) + Math.pow(mu_b, omega), 1 / omega));
	}

	static double YagersTnorms(double mu_a, double mu_b, double omega) {
		if (omega <= 0)
			throw new InvalidParameterException("Omega must in the range (0, +INFINITY).");
		return 1 - Math.min(1, Math.pow(Math.pow(1 - mu_a, omega) + Math.pow(1 - mu_b, omega), 1 / omega));
	}
}