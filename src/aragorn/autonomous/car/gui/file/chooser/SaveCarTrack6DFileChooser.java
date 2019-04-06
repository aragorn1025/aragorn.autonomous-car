package aragorn.autonomous.car.gui.file.chooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFileChooser;
import aragorn.gui.GuiFrame;

@SuppressWarnings("serial")
public class SaveCarTrack6DFileChooser extends GuiFileChooser.Save {

	private AutonomousSystem autonomous_system;

	public SaveCarTrack6DFileChooser(GuiFrame parent, AutonomousSystem autonomous_system) {
		super(parent);
		this.autonomous_system = autonomous_system;

		setCurrentDirectory(GuiFileChooser.USER_DESKTOP);
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
	public void actionPerformed(ActionEvent event) {
		setSelectedFile(new File("train-6d.txt"));
		super.actionPerformed(event);
	}
}