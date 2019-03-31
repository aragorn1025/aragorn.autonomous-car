package aragorn.autonomous.car.old.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JOptionPane;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.fuzzy.system.FuzzyAutonomousSystem;
import aragorn.autonomous.car.old.objects.CircularCar;
import aragorn.autonomous.car.old.objects.DefaultMaze;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
class MainFrame extends GuiFrame {

	private final static Dimension DIMENSION = new Dimension(800, 450);

	private final static boolean IS_MAXIMIZED_WHILE_LAUNCH = false;

	private final static int UPDATING_PERIOD = 100;

	private AutonomousSystem autonomousSystem;

	private MazePanel mazePanel;

	private InfoPanel infoPanel;

	MainFrame(String title) {
		super(DIMENSION, IS_MAXIMIZED_WHILE_LAUNCH, UPDATING_PERIOD);

		autonomousSystem = new FuzzyAutonomousSystem(new DefaultMaze(), new CircularCar());
		mazePanel = new MazePanel(autonomousSystem);
		infoPanel = new InfoPanel(autonomousSystem);

		setTitle(title);
		setJMenuBar(new MainMenuBar(this, autonomousSystem, mazePanel, infoPanel));

		GuiPanel content_pane = new GuiPanel();
		content_pane.addComponent(mazePanel, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		content_pane.addComponent(infoPanel, 1, 0, 1, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL);
		setContentPane(content_pane);
	}

	@Override
	protected void run() {
		boolean toStop = autonomousSystem.control();
		autonomousSystem.addCarTrack();
		infoPanel.reset();
		if (toStop) {
			pause();
			JOptionPane.showMessageDialog(this, "The car gets ends.", "Timer stop", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}