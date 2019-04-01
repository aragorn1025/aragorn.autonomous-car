package aragorn.autonomous.car.zold.fuzzy.defuzzifier;

public interface PseudoDefuzzifier {

	public double deffuzzifier();

	public double mu(double y);
}
