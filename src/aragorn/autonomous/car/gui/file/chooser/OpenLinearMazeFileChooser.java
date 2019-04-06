package aragorn.autonomous.car.gui.file.chooser;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFileChooser;
import aragorn.gui.GuiFrame;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;
import aragorn.math.geometry.Polyline2D;

@SuppressWarnings("serial")
public class OpenLinearMazeFileChooser extends GuiFileChooser.Open {

	private AutonomousSystem autonomous_system;

	public OpenLinearMazeFileChooser(GuiFrame parent, AutonomousSystem autonomous_system) {
		super(parent);
		this.autonomous_system = autonomous_system;

		setCurrentDirectory(GuiFileChooser.USER_DESKTOP);
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
			autonomous_system.setMaze(new LinearMaze(car_initial_status, end_line, new Polygon2D(wall)));
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