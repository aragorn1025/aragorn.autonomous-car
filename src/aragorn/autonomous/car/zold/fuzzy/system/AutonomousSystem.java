package aragorn.autonomous.car.zold.fuzzy.system;

import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.zold.objects.Maze;

public interface AutonomousSystem {

	public void addCarTrack();

	public boolean control();

	public double detect(double angle);

	public default double detectFront() {
		return detect(0) - getCar().getFrontSensorOffset();
	}

	public default double detectLeft() {
		return detect(45) - getCar().getLeftSensorOffset();
	}

	public default double detectRight() {
		return detect(-45) - getCar().getRightSensorOffset();
	}

	public Car getCar();

	public CarStatus getCarTracks(int index);

	public int getCarTracksNumber();

	public Maze getMaze();

	public String getSensor(int index);

	public boolean isReachEnd();

	public void reset();

	public void setCar(Car car);

	public void setMaze(Maze maze);
}