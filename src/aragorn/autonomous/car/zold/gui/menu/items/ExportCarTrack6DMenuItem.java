package aragorn.autonomous.car.zold.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystemOld;
import aragorn.autonomous.car.zold.gui.MainFrame;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenuItem;

@SuppressWarnings("serial")
public class ExportCarTrack6DMenuItem extends GuiMenuItem implements ActionListener {

	private static final String FILE_EXPORT_CANCELLED_MESSAGE = new String("File export for car tracks has been cancelled.");

	private static final String FILE_EXPORT_DONE_MESSAGE = new String("File export for car tracks is done.");

	private static final String FILE_OVERWRITE_MESSAGE = new String("The file will be overwrite.");

	private static final String FILE_READ_ONLY_MESSAGE = new String("The file is read only.");

	private GuiFrame frame;

	private JFileChooser fileChooser;

	private AutonomousSystemOld autonomousSystem;

	public ExportCarTrack6DMenuItem(GuiFrame frame, JFileChooser fileChooser, AutonomousSystemOld autonomousSystem) {
		super("Export Car Track (6D)", '\0');
		this.frame = frame;
		this.fileChooser = fileChooser;
		this.autonomousSystem = autonomousSystem;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.setDialogTitle("Export Car Track");
		fileChooser.setCurrentDirectory(MainFrame.DESKTOP);
		fileChooser.setSelectedFile(new File("Car Tracks 6D.txt"));
		switch (fileChooser.showSaveDialog(frame)) {
			case JFileChooser.APPROVE_OPTION:
				try {
					File file = fileChooser.getSelectedFile();
					if (!file.exists()) {
						file.createNewFile();
					} else if (file.canWrite()) {
						if (JOptionPane.showConfirmDialog(frame, FILE_OVERWRITE_MESSAGE, "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
					for (int i = 0; i < autonomousSystem.getCarTracksNumber(); i++) {
						output.printf("%.7f %.7f %s %.7f%n", autonomousSystem.getCar().getStatus().getLocation().getX(),
								autonomousSystem.getCar().getStatus().getLocation().getY(), autonomousSystem.getSensor(i),
								autonomousSystem.getCarTracks(i).getWheelAngleOutput());
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