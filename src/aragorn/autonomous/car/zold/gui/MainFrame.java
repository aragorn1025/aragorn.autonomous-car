package aragorn.autonomous.car.zold.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.File;
import javax.swing.JOptionPane;
import aragorn.autonomous.car.object.CircularCar;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystemOld;
import aragorn.autonomous.car.zold.fuzzy.system.FuzzyAutonomousSystem;
import aragorn.autonomous.car.zold.objects.DefaultMaze;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
public class MainFrame extends GuiFrame {

	public static final File DESKTOP = new File(System.getProperty("user.home") + "/Desktop");

	private AutonomousSystemOld autonomousSystem;

	private MazePanel mazePanel;

	private InfoPanel infoPanel;

	public MainFrame(String title) {
		super(new Dimension(800, 450), true, 100);

		autonomousSystem = new FuzzyAutonomousSystem(new DefaultMaze(), new CircularCar());
		mazePanel = new MazePanel(autonomousSystem);
		infoPanel = new InfoPanel(autonomousSystem);

		setTitle(title + " - by Fuzzy System");
		setJMenuBar(new MainMenuBar(this, autonomousSystem));

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

	@Override
	public void repaint() {
		super.repaint();
		mazePanel.repaint();
		infoPanel.repaint();
	}
}