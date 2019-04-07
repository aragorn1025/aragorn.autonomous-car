package aragorn.autonomous.car.gui;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.filechooser.FileNameExtensionFilter;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.gui.GuiFileChooser;
import aragorn.gui.GuiFrame;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;
import aragorn.math.geometry.Polyline2D;

@SuppressWarnings("serial")
public class AutonomousCarFileChooser {

	public static class Open extends GuiFileChooser.Open {

		public static class LinearMaze extends AutonomousCarFileChooser.Open {

			public LinearMaze(GuiFrame parent, AutonomousSystem autonomous_system) {
				super(parent, autonomous_system);
				setDialogTitle("Open linear maze file...");
			}

			@Override
			protected void action() {
				Scanner input = null;
				try {
					input = new Scanner(getSelectedFile());
					input.useDelimiter(",|\r\n");

					Point2D.Double car_initial_location = new Point2D.Double(input.nextDouble(), input.nextDouble());
					CarStatus car_initial_status = new CarStatus(car_initial_location, Math.toRadians(input.nextDouble()), 0);

					Point2D.Double starting_point = new Point2D.Double(input.nextDouble(), input.nextDouble());
					Point2D.Double ending_point = new Point2D.Double(input.nextDouble(), input.nextDouble());
					LineSegment2D end_line = new LineSegment2D(starting_point, ending_point);

					Polyline2D wall = new Polyline2D();
					while (input.hasNext()) {
						wall.addPoint(new Point2D.Double(input.nextDouble(), input.nextDouble()));
					}

					input.close();
					autonomous_system.setMaze(new aragorn.autonomous.car.object.LinearMaze(car_initial_status, end_line, new Polygon2D(wall)));
				} catch (FileNotFoundException | NullPointerException exception) {
					parent.echo("Unknown error: " + exception.toString(), GuiFrame.ERROR_MESSAGE);
					return;
				} catch (NoSuchElementException exception) {
					if (input != null) {
						input.close();
					}
					parent.echo("The input file is not in the format.", GuiFrame.ERROR_MESSAGE);
				}
			}
		}

		protected AutonomousSystem autonomous_system;

		private Open(GuiFrame parent, AutonomousSystem autonomous_system) {
			super(parent);
			this.autonomous_system = autonomous_system;

			setCurrentDirectory(GuiFileChooser.USER_DESKTOP);
			setFileFilter(new FileNameExtensionFilter("text file", "txt"));
		}
	}

	public static abstract class Save extends GuiFileChooser.Save {

		public static class CarTrack4D extends AutonomousCarFileChooser.Save {

			public CarTrack4D(GuiFrame parent, AutonomousSystem autonomous_system) {
				super(parent, autonomous_system);
				setDialogTitle("Save car track 4D file...");
			}

			@Override
			protected void action() {
				try {
					PrintWriter output = new PrintWriter(getSelectedFile());
					for (int i = 0; i < autonomous_system.getCarTracksNumber(); i++) {
						output.printf("%s %.7f%n", autonomous_system.getSensor(i), autonomous_system.getCarTracks(i).getWheelAngleOutput());
					}
					output.flush();
					output.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}

			@Override
			protected String getDefaultFileName() {
				return "train-4d.txt";
			}
		}

		public static class CarTrack6D extends AutonomousCarFileChooser.Save {

			public CarTrack6D(GuiFrame parent, AutonomousSystem autonomous_system) {
				super(parent, autonomous_system);
				setDialogTitle("Save car track 6D file...");
			}

			@Override
			protected void action() {
				try {
					PrintWriter output = new PrintWriter(getSelectedFile());
					for (int i = 0; i < autonomous_system.getCarTracksNumber(); i++) {
						output.printf("%.7f %.7f %s %.7f%n", autonomous_system.getCar().getStatus().getLocation().getX(),
								autonomous_system.getCar().getStatus().getLocation().getY(), autonomous_system.getSensor(i),
								autonomous_system.getCarTracks(i).getWheelAngleOutput());
					}
					output.flush();
					output.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}

			@Override
			protected String getDefaultFileName() {
				return "train-6d.txt";
			}
		}

		public static class LinearMaze extends AutonomousCarFileChooser.Save {

			public LinearMaze(GuiFrame parent, AutonomousSystem autonomous_system) {
				super(parent, autonomous_system);
				setDialogTitle("Save linear maze file...");
			}

			@Override
			protected void action() {
				try {
					PrintWriter output = new PrintWriter(getSelectedFile());

					autonomous_system.reset();

					CarStatus car_status = autonomous_system.getCar().getStatus();
					output.printf("%.7f,%.7f,%.7f%n", car_status.getLocation().getX(), car_status.getLocation().getY(), car_status.getDirectionOutput());

					LineSegment2D end_line = autonomous_system.getMaze().getEndLine();
					output.printf("%.7f,%.7f%n", end_line.getPoints()[0].getX(), end_line.getPoints()[0].getY());
					output.printf("%.7f,%.7f%n", end_line.getPoints()[1].getX(), end_line.getPoints()[1].getY());

					Polygon2D wall = autonomous_system.getMaze().getWall();
					for (int i = 0; i < wall.getPointNumber() - 1; i++) {
						output.printf("%.7f,%.7f%n", wall.getPoint(i).getX(), wall.getPoint(i).getY());
					}

					output.flush();
					output.close();
				} catch (FileNotFoundException | NullPointerException exception) {
					parent.echo("Unknown error: " + exception.toString(), GuiFrame.ERROR_MESSAGE);
					return;
				}
			}

			@Override
			protected String getDefaultFileName() {
				return "maze.txt";
			}
		}

		protected AutonomousSystem autonomous_system;

		private Save(GuiFrame parent, AutonomousSystem autonomous_system) {
			super(parent);
			this.autonomous_system = autonomous_system;

			setCurrentDirectory(GuiFileChooser.USER_DESKTOP);
			setFileFilter(new FileNameExtensionFilter("text file", "txt"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedFile(new File(getDefaultFileName()));
			super.actionPerformed(e);
		}

		protected abstract String getDefaultFileName();
	}
}