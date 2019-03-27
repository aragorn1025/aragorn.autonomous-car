package aragorn.autonomous.car.old.fuzzy.defuzzifier;

public interface PseudoDefuzzifier {
	public double deffuzzifier();

	public double mu(double y);
}
