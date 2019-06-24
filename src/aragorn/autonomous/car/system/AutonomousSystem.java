package aragorn.autonomous.car.system;

import java.util.ArrayList;
import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.object.LinearMaze;

public class AutonomousSystem extends ControlSystem {

	private ArrayList<CarStatus> tracks = new ArrayList<>();

	private ArrayList<Double> front = new ArrayList<>();

	private ArrayList<Double> left = new ArrayList<>();

	private ArrayList<Double> right = new ArrayList<>();

	public AutonomousSystem(Algorithm algorithm, LinearMaze maze, Car car) {
		super(algorithm, maze, car);
	}

	private void addCarTrack() {
		tracks.add(getCar().getCloneStatus());
		front.add(detectFront());
		left.add(detectLeft());
		right.add(detectRight());
	}

	public CarStatus getCarTracks(int index) {
		return tracks.get(index);
	}

	public int getCarTracksNumber() {
		if (tracks.size() != front.size() || tracks.size() != right.size() || tracks.size() != left.size())
			throw new UnknownError("Arraylist size is not equal.");
		return tracks.size();
	}

	public String getSensor(int index) {
		return String.format("%.7f %.7f %.7f", front.get(index), right.get(index), left.get(index));
	}

	@Override
	public void reset() {
		super.reset();
		front.clear();
		left.clear();
		right.clear();
		tracks.clear();
		addCarTrack();
	}

	public ControlCode run() {
		double angle = getAlgorithm().getOutput(detectLeft(), detectFront(), detectRight());
		getCar().move(angle);
		addCarTrack();
		if (isTouchWall())
			return ControlCode.TOUCHES_WALL;
		if (isReachEnd())
			return ControlCode.REACHES_END;
		return ControlCode.RUNNING;
	}
}