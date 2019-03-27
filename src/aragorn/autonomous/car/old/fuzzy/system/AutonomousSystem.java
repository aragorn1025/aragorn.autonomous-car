package aragorn.autonomous.car.old.fuzzy.system;

import aragorn.autonomous.car.old.objects.Car;
import aragorn.autonomous.car.old.objects.CircularCar;
import aragorn.autonomous.car.old.objects.Maze;
import aragorn.autonomous.car.old.objects.RectangularCar;

public interface AutonomousSystem {
	public void addCarTrack();

	public boolean control();

	public double detect(double angle);

	public default double detectFront() {
		return detect(0) - getCar().getLength() / 2;
	}

	public default double detectLeft() {
		if (getCar().getClass() == CircularCar.class) {
			return detect(45) - getCar().getLength() / 2;
		} else if (getCar().getClass() == RectangularCar.class) {
			return detect(45) - Math.min(getCar().getLength(), getCar().getWidth()) / Math.sqrt(2);
		}
		return detect(45);
	}

	public default double detectRight() {
		if (getCar().getClass() == CircularCar.class) {
			return detect(-45) - getCar().getLength() / 2;
		} else if (getCar().getClass() == RectangularCar.class) {
			return detect(-45) - Math.min(getCar().getLength(), getCar().getWidth()) / Math.sqrt(2);
		}
		return detect(-45);
	}

	public Car getCar();

	public Car getCarTracks(int index);

	public int getCarTracksNumber();

	public Maze getMaze();

	public String getSensor(int index);

	public boolean isReachEnd();

	public void reset();

	public void setCar(Car car);

	public void setMaze(Maze maze);
}