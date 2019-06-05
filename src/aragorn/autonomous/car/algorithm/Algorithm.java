package aragorn.autonomous.car.algorithm;

public interface Algorithm {
	
	public void train(int epoches);

	public String getName();
	
	public double getOutput(double detect_left, double detect_front, double detect_right);
}