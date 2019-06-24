package aragorn.autonomous.car.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import aragorn.util.MathVector;

public class TrainingDataSet {

	private ArrayList<MathVector> inputs = new ArrayList<>();

	private ArrayList<MathVector> expected_outputs = new ArrayList<>();

	public TrainingDataSet(File file) {
		this(file, 3, 1);
	}

	public TrainingDataSet(File file, int input_dimension, int expected_output_dimension) {
		try {
			Scanner scanner = new Scanner(file);
			MathVector input = new MathVector(3);
			MathVector expected_output = new MathVector(1);
			while (scanner.hasNext()) {
				for (int i = 0; i < input_dimension; i++) {
					input.setComponent(i, scanner.nextDouble());
				}
				for (int i = 0; i < expected_output_dimension; i++) {
					expected_output.setComponent(i, scanner.nextDouble());
				}
				add(input, expected_output);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void add(MathVector input, MathVector expected_output) {
		if (getDataNumber() != 0) {
			if (input.getDimension() != inputs.get(0).getDimension())
				throw new IllegalArgumentException("The dimension of inputs should be the same.");
			if (expected_output.getDimension() != expected_outputs.get(0).getDimension())
				throw new IllegalArgumentException("The dimension of expected outputs should be the same.");
		}
		inputs.add((MathVector) input.clone());
		expected_outputs.add((MathVector) expected_output.clone());
	}

	public int getDataNumber() {
		if (inputs.size() != expected_outputs.size())
			throw new InternalError("Unknown error.");
		return inputs.size();
	}

	public MathVector getExpectedOutput(int index) {
		return expected_outputs.get(index);
	}

	public MathVector getInput(int index) {
		return inputs.get(index);
	}
}