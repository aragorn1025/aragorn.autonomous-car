package aragorn.autonomous.car.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import aragorn.autonomous.car.algorithm.fuzzy.system.FuzzySystem;
import aragorn.autonomous.car.object.CircularCar;
import aragorn.autonomous.car.object.DefaultMaze;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.autonomous.car.system.ControlCode;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
public class MainFrame extends GuiFrame {

	private AutonomousSystem autonomous_system;

	private MazePanel maze_panel;

	private InfoPanel info_panel;
	
	private String title;

	public MainFrame(String title) {
		super(new Dimension(800, 450), true, 100);

		autonomous_system = new AutonomousSystem(new FuzzySystem(), new DefaultMaze(), new CircularCar());
		maze_panel = new MazePanel(autonomous_system);
		info_panel = new InfoPanel(autonomous_system);

		this.title = title;
		setTitle(autonomous_system.getAlgorithm().getName());
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
		ControlCode control_code = autonomous_system.control();
		info_panel.reset();
		switch (control_code) {
			case REACHES_END:
				pause();
				echo("The car reaches ends.", GuiFrame.INFORMATION_MESSAGE);
				break;
			case TOUCHES_WALL:
				pause();
				echo("The car touches walls.", GuiFrame.ERROR_MESSAGE);
				break;
			case RUNNING:
				break;
			default:
				throw new InternalError("Unknown error.");
		}
	}
	
	@Override
	public void setTitle(String title) {
		super.setTitle(this.title + " - by " + title);
	}
}