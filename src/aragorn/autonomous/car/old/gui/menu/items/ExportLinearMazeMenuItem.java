package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.gui.Main;
import aragorn.autonomous.car.old.math.operation.GeometryPolyline;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenuItem;

@SuppressWarnings("serial")
public class ExportLinearMazeMenuItem extends GuiMenuItem implements ActionListener {
	private GuiFrame			frame;
	private JFileChooser		fileChooser;
	private AutonomousSystem	autonomousSystem;

	private static final String	FILE_EXPORT_CANCELLED_MESSAGE	= new String("File export for maze has been cancelled.");
	private static final String	FILE_EXPORT_DONE_MESSAGE		= new String("File export for maze is done.");
	private static final String	FILE_OVERWRITE_MESSAGE			= new String("The file will be overwrite.");
	private static final String	FILE_READ_ONLY_MESSAGE			= new String("The file is read only.");

	public ExportLinearMazeMenuItem(GuiFrame frame, JFileChooser fileChooser, AutonomousSystem autonomousSystem) {
		super("Export Linear Maze", '\0');
		this.frame = frame;
		this.fileChooser = fileChooser;
		this.autonomousSystem = autonomousSystem;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.setDialogTitle("Export Linear Maze");
		fileChooser.setCurrentDirectory(Main.DESKTOP);
		fileChooser.setSelectedFile(new File("Maze.txt"));
		switch (fileChooser.showSaveDialog(frame)) {
			case JFileChooser.APPROVE_OPTION:
				try {
					File file = fileChooser.getSelectedFile();
					if (!file.exists()) {
						file.createNewFile();
					} else if (file.canWrite()) {
						if (JOptionPane.showConfirmDialog(frame, FILE_OVERWRITE_MESSAGE, "Warning",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							file.delete();
							file.createNewFile();
						} else {
							JOptionPane.showMessageDialog(frame, FILE_EXPORT_CANCELLED_MESSAGE, "Cancel", JOptionPane.CANCEL_OPTION);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(frame, FILE_READ_ONLY_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					PrintWriter output = new PrintWriter(file);
					GeometryPolyline wall;
					for (int j = 0; j < 3; j++) {
						switch (j) {
							case 0:
								wall = autonomousSystem.getMaze().getLeftWall();
								break;
							case 1:
								wall = autonomousSystem.getMaze().getRightWall();
								break;
							case 2:
								wall = autonomousSystem.getMaze().getEndWall();
								break;
							default:
								throw new UnknownError("Unknown error for file chooser.");
						}
						output.printf("%s%n", wall.getPointsNumber());
						for (int i = 0; i < wall.getPointsNumber(); i++) {
							output.printf("(%.7f, %.7f)%n", wall.getPoint(i).getX(), wall.getPoint(i).getY());
						}
					}
					output.flush();
					output.close();
					JOptionPane.showMessageDialog(frame, FILE_EXPORT_DONE_MESSAGE, "Message", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			case JFileChooser.CANCEL_OPTION:
				JOptionPane.showMessageDialog(frame, FILE_EXPORT_CANCELLED_MESSAGE, "Cancel", JOptionPane.CANCEL_OPTION);
				return;
			default:
				throw new UnknownError("Unknown error for file chooser.");
		}
	}
}