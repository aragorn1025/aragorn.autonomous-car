package aragorn.autonomous.car.action.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFileChooser;
import aragorn.gui.GuiFrame;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;
import aragorn.math.geometry.Polyline2D;

public class ImportLinearMazeActionListener implements ActionListener {

	private GuiFrame frame;

	private GuiFileChooser.Open file_chooser;

	private AutonomousSystem autonomous_system;

	public ImportLinearMazeActionListener(GuiFrame frame, AutonomousSystem autonomous_system) {
		this.frame = frame;
		this.autonomous_system = autonomous_system;

		file_chooser = new GuiFileChooser.Open(frame);
		file_chooser.setCurrentDirectory(GuiFileChooser.USER_DESKTOP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		file_chooser.setDialogTitle("Import Linear Maze...");
		file_chooser.actionPerformed(e);
		Scanner input = null;
		try {
			input = new Scanner(file_chooser.getSelectedFile());
			input.useDelimiter(",|\r\n");

			// TODO should set car status
			double car_status_0 = input.nextDouble();
			double car_status_1 = input.nextDouble();
			double car_status_2 = input.nextDouble();

			Point2D.Double starting_point = new Point2D.Double(input.nextDouble(), input.nextDouble());
			Point2D.Double ending_point = new Point2D.Double(input.nextDouble(), input.nextDouble());
			LineSegment2D end_line = new LineSegment2D(starting_point, ending_point);

			Polyline2D wall = new Polyline2D();
			while (input.hasNext()) {
				wall.addPoint(new Point2D.Double(input.nextDouble(), input.nextDouble()));
			}

			input.close();
			autonomous_system.setMaze(new LinearMaze(new Polygon2D(wall), end_line));
		} catch (FileNotFoundException | NullPointerException exception) {
			return;
		} catch (NoSuchElementException exception) {
			if (input != null) {
				input.close();
			}
			frame.echo("The input file is not in the format.", GuiFrame.ERROR_MESSAGE);
		}
	}
}