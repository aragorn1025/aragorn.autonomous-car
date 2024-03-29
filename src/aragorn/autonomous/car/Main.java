package aragorn.autonomous.car;

import aragorn.autonomous.car.gui.MainFrame;
import aragorn.gui.GuiFrame;

class Main {

	private final static String TITLE = new String("Computer Simulating Autonomous Car");

	public static void main(String[] args) {
		GuiFrame.setDefaultLookAndFeelDecorated(true);
		MainFrame frame = new MainFrame(TITLE);
		frame.setVisible(true);
	}
}