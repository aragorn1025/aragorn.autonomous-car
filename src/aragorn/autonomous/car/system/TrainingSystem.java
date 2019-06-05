package aragorn.autonomous.car.system;

import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.LinearMaze;

public class TrainingSystem extends ControlSystem {

	public TrainingSystem(Algorithm algorithm, LinearMaze maze, Car car) {
		super(algorithm, maze, car);
	}

	public ControlCode run() {
		return null; // TODO
	}
}