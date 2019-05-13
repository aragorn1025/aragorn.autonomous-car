package aragorn.autonomous.car.neural.network;

import aragorn.util.MathVector;

public interface Neuron {

	public double getOutput(MathVector input_vector);
	
	public void randomizeWeight();
	
	public String toString();
}