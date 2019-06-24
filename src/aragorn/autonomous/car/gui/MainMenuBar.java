package aragorn.autonomous.car.gui;

import javax.swing.JMenuBar;
import aragorn.autonomous.car.algorithm.fuzzy.system.FuzzySystem;
import aragorn.autonomous.car.algorithm.genetic.algorithm.GeneticAlgorithm;
import aragorn.autonomous.car.algorithm.particle.swarm.optimization.ParticleSwarmOptimization;
import aragorn.autonomous.car.object.CircularCar;
import aragorn.autonomous.car.object.DefaultMaze;
import aragorn.autonomous.car.object.RandomGridMaze;
import aragorn.autonomous.car.object.RectangularCar;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenu;
import aragorn.gui.GuiMenuItem;
import aragorn.gui.action.listener.CloseGuiFrameActionListener;
import aragorn.gui.action.listener.PauseGuiFrameActionListener;
import aragorn.gui.action.listener.PlayGuiFrameActionListener;

@SuppressWarnings("serial")
class MainMenuBar extends JMenuBar {

	private static GuiMenu getAlgorithmMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Algorithm");
		menu.add(new GuiMenuItem("Fuzzy System", '1'));
		menu.getItem(0).addActionListener(new AutonomousCarActionListener.ChangeAlgorithm(frame, autonomous_system, FuzzySystem.class));
		menu.add(new GuiMenuItem("Genetic Algorithm", '2'));
		menu.getItem(1).addActionListener(new AutonomousCarActionListener.ChangeAlgorithm(frame, autonomous_system, GeneticAlgorithm.class));
		menu.add(new GuiMenuItem("ParticleSwarmOptimization", '3'));
		menu.getItem(2).addActionListener(new AutonomousCarActionListener.ChangeAlgorithm(frame, autonomous_system, ParticleSwarmOptimization.class));
		return menu;
	}

	private static GuiMenu getCarMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Car");
		menu.add(new GuiMenuItem("Change to a circular car", '1'));
		menu.getItem(0).addActionListener(new AutonomousCarActionListener.ChangeCar(frame, autonomous_system, CircularCar.class));
		menu.add(new GuiMenuItem("Change to a rectangular car", '2'));
		menu.getItem(1).addActionListener(new AutonomousCarActionListener.ChangeCar(frame, autonomous_system, RectangularCar.class));
		return menu;
	}

	private static GuiMenu getFileExportMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Save");
		menu.add(new GuiMenuItem("Save Linear Maze", '\0'));
		menu.getItem(0).addActionListener(new FileIoActionListener(new AutonomousCarFileChooser.Save.LinearMaze(frame, autonomous_system)));
		menu.addSeparator();
		menu.add(new GuiMenuItem("Save Car Track (4D)", '\0'));
		menu.getItem(2).addActionListener(new FileIoActionListener(new AutonomousCarFileChooser.Save.CarTrack4D(frame, autonomous_system)));
		menu.add(new GuiMenuItem("Save Car Track (6D)", '\0'));
		menu.getItem(3).addActionListener(new FileIoActionListener(new AutonomousCarFileChooser.Save.CarTrack6D(frame, autonomous_system)));
		return menu;
	}

	private static GuiMenu getFileImportMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Open");
		menu.add(new GuiMenuItem("Open Linear Maze", '\0'));
		menu.getItem(0).addActionListener(new FileIoActionListener(new AutonomousCarFileChooser.Open.LinearMaze(frame, autonomous_system)));
		return menu;
	}

	private static GuiMenu getFileMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("File");
		menu.add(getFileImportMenu(frame, autonomous_system));
		menu.add(getFileExportMenu(frame, autonomous_system));
		// menu.add(new JSeparator());
		// menu.add(new JMenuItem("Export All"));
		menu.addSeparator();
		menu.add(new GuiMenuItem("Exit", 'X'));
		menu.getItem(3).addActionListener(new CloseGuiFrameActionListener(frame));
		return menu;
	}

	private static GuiMenu getMazeMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Maze");
		menu.add(new GuiMenuItem("Change to the default maze", '0'));
		menu.getItem(0).addActionListener(new AutonomousCarActionListener.ChangeMaze(frame, autonomous_system, DefaultMaze.class));
		menu.add(new GuiMenuItem("Change to a random grid maze", '1'));
		menu.getItem(1).addActionListener(new AutonomousCarActionListener.ChangeMaze(frame, autonomous_system, RandomGridMaze.class));
		return menu;
	}

	private static GuiMenu getObjectMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Object");
		menu.add(MainMenuBar.getCarMenu(frame, autonomous_system));
		menu.add(MainMenuBar.getMazeMenu(frame, autonomous_system));
		return menu;
	}

	private static GuiMenu getPlayMenu(GuiFrame frame, AutonomousSystem autonomous_system) {
		GuiMenu menu = new GuiMenu("Play");
		menu.add(new GuiMenuItem("Play", 'Z'));
		menu.getItem(0).addActionListener(new PlayGuiFrameActionListener(frame));
		menu.add(new GuiMenuItem("Pause", 'X'));
		menu.getItem(1).addActionListener(new PauseGuiFrameActionListener(frame));
		menu.addSeparator();
		menu.add(new GuiMenuItem("Reset", 'C'));
		menu.getItem(3).addActionListener(new AutonomousCarActionListener.Reset(frame, autonomous_system));
		return menu;
	}

	MainMenuBar(GuiFrame frame, AutonomousSystem autonomous_system) {
		add(MainMenuBar.getFileMenu(frame, autonomous_system));
		add(MainMenuBar.getAlgorithmMenu(frame, autonomous_system));
		add(MainMenuBar.getPlayMenu(frame, autonomous_system));
		add(MainMenuBar.getObjectMenu(frame, autonomous_system));
	}
}