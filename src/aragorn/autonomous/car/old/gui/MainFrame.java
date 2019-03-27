package aragorn.autonomous.car.old.gui;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.fuzzy.system.FuzzyAutonomousSystem;
import aragorn.autonomous.car.old.objects.CircularCar;
import aragorn.autonomous.car.old.objects.DefaultMaze;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiPanel;

class MainFrame extends GuiFrame {
	private AutonomousSystem	autonomousSystem;
	private MazePanel			mazePanel;
	private InfoPanel			infoPanel;

	MainFrame(String title, Dimension size, boolean toMaximize, int updatingPeriod) {
		super(title, size, toMaximize, updatingPeriod);
		setJMenuBar(new MainMenuBar(this, autonomousSystem, mazePanel, infoPanel));
	}

	@Override
	protected void addTimerTaskAction() {
		boolean toStop = autonomousSystem.control();
		autonomousSystem.addCarTrack();
		infoPanel.reset();
		if (toStop) {
			this.stop();
			JOptionPane.showMessageDialog(this, "The car gets ends.", "Timer stop", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected void editContentPane() {
		autonomousSystem = new FuzzyAutonomousSystem(new DefaultMaze(), new CircularCar());
		mazePanel = new MazePanel(autonomousSystem);
		infoPanel = new InfoPanel(autonomousSystem);
		this.getContentPane().addComponent(mazePanel, 0, 0, 1, 1, 1, 1, GUIPanel.CENTER, GUIPanel.BOTH);
		this.getContentPane().addComponent(infoPanel, 1, 0, 1, 1, 0, 1, GUIPanel.CENTER, GUIPanel.VERTICAL);
	}
}