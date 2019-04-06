package aragorn.autonomous.car.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import aragorn.autonomous.car.object.CircularCar;
import aragorn.autonomous.car.object.DefaultMaze;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.zold.fuzzy.system.FuzzyAutonomousSystem;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
public class MainFrame extends GuiFrame {

	private AutonomousSystem autonomous_system;

	private MazePanel maze_panel;

	private InfoPanel info_panel;

	public MainFrame(String title) {
		super(new Dimension(800, 450), true, 100);

		autonomous_system = new FuzzyAutonomousSystem(new DefaultMaze(), new CircularCar());
		maze_panel = new MazePanel(autonomous_system);
		info_panel = new InfoPanel(autonomous_system);

		setTitle(title + " - by Fuzzy System");
		setJMenuBar(new MainMenuBar(this, autonomous_system));

		GuiPanel content_pane = new GuiPanel();
		content_pane.addComponent(maze_panel, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		content_pane.addComponent(info_panel, 1, 0, 1, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL);
		setContentPane(content_pane);
	}

	@Override
	public void repaint() {
		super.repaint();
		maze_panel.repaint();
		info_panel.repaint();
	}

	@Override
	protected void run() {
		boolean toStop = autonomous_system.control();
		autonomous_system.addCarTrack();
		info_panel.reset();
		if (toStop) {
			pause();
			echo("The car gets ends.", GuiFrame.INFORMATION_MESSAGE);
		}
	}
}