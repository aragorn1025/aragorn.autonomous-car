package aragorn.autonomous.car.old.gui;

import java.io.File;
import aragorn.gui.GuiFrame;

public class Main {

	public static final File DESKTOP = new File(System.getProperty("user.home") + "/Desktop");

	private final static String TITLE = new String("Fuzzy System - Computer Simulating Autonomous Car");

	public static void main(String[] args) {
		GuiFrame.setDefaultLookAndFeelDecorated(true);
		MainFrame frame = new MainFrame(TITLE);
		frame.setVisible(true);
	}
}