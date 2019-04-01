package aragorn.autonomous.car.zold.fuzzy.system;

import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.zold.objects.Maze;

public interface AutonomousSystemOld {

	public void addCarTrack();

	public boolean control();

	public double detect(double angle);

	public double detectFront();

	public double detectLeft();

	public double detectRight();

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