package aragorn.autonomous.car.gui.file.chooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.filechooser.FileNameExtensionFilter;
import aragorn.autonomous.car.object.CarStatus;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFileChooser;
import aragorn.gui.GuiFrame;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;

@SuppressWarnings("serial")
public class SaveLinearMazeFileChooser extends GuiFileChooser.Save {

	private AutonomousSystem autonomous_system;

	public SaveLinearMazeFileChooser(GuiFrame parent, AutonomousSystem autonomous_system) {
		super(parent);
		this.autonomous_system = autonomous_system;

		setCurrentDirectory(GuiFileChooser.USER_DESKTOP);
		setDialogTitle("Save linear maze file...");
		setFileFilter(new FileNameExtensionFilter("text file", "txt"));
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
	public void actionPerformed(ActionEvent e) {
		setSelectedFile(new File("maze.txt"));
		super.actionPerformed(e);
	}
}