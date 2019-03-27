package aragorn.autonomous.car.old.gui;

import java.awt.Dimension;
import java.io.File;
import aragorn.gui.GUIFrame;

public class Main {
	public static final File		DESKTOP			= new File(System.getProperty("user.home") + "/Desktop");
	private final static String		TITLE			= new String("Fuzzy System - Computer Simulating Autonomous Car");
	private final static Dimension	SIZE			= new Dimension(800, 450);
	private final static boolean	TO_MAXIMIZE		= false;
	private final static int		UPDATING_PERIOD	= 100;

	public static void main(String[] args) {
		GUIFrame.setDefaultLookAndFeelDecorated(true);
		MainFrame frame = new MainFrame(TITLE, SIZE, TO_MAXIMIZE, UPDATING_PERIOD);
		frame.setVisible(true);
	}
}