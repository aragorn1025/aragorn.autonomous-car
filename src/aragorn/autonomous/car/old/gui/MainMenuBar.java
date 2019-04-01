package aragorn.autonomous.car.old.gui;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import aragorn.autonomous.car.action.listener.ChangeCarActionListener;
import aragorn.autonomous.car.action.listener.ChangeMazeActionListener;
import aragorn.autonomous.car.action.listener.ResetActionListener;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.gui.menu.items.ExportCarTrack4DMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ExportCarTrack6DMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ExportLinearMazeMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ImportLinearMazeMenuItem;
import aragorn.autonomous.car.old.objects.CircularCar;
import aragorn.autonomous.car.old.objects.DefaultMaze;
import aragorn.autonomous.car.old.objects.RandomGridMaze;
import aragorn.autonomous.car.old.objects.RectangularCar;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenu;
import aragorn.gui.GuiMenuItem;
import aragorn.gui.action.listener.CloseGuiFrameActionListener;
import aragorn.gui.action.listener.PauseGuiFrameActionListener;
import aragorn.gui.action.listener.PlayGuiFrameActionListener;

@SuppressWarnings("serial")
class MainMenuBar extends JMenuBar {

	private JFileChooser fileChooser = new JFileChooser(MainFrame.DESKTOP);

	private GuiFrame frame;

	private AutonomousSystem autonomousSystem;

	MainMenuBar(GuiFrame frame, AutonomousSystem autonomousSystem) {
		this.frame = frame;
		this.autonomousSystem = autonomousSystem;
		fileChooser.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
		this.editMenuBar();
	}

	private void editMenuBar() {
		removeAll();
		add(new GuiMenu("File"));
		getMenu(0).add(new GuiMenu("Import"));
		getMenu(0).getItem(0).add(new ImportLinearMazeMenuItem(frame, fileChooser, autonomousSystem));
		getMenu(0).add(new GuiMenu("Export"));
		getMenu(0).getItem(1).add(new ExportLinearMazeMenuItem(frame, fileChooser, autonomousSystem));
		getMenu(0).getItem(1).add(new ExportCarTrack4DMenuItem(frame, fileChooser, autonomousSystem));
		getMenu(0).getItem(1).add(new ExportCarTrack6DMenuItem(frame, fileChooser, autonomousSystem));
		// getMenu(0).getItem(1).add(new JSeparator());
		// getMenu(0).getItem(1).add(new JMenuItem("Export All"));
		getMenu(0).addSeparator();
		getMenu(0).add(new GuiMenuItem("Exit", 'X'));
		getMenu(0).getItem(3).addActionListener(new CloseGuiFrameActionListener(frame));

		add(new GuiMenu("Play"));
		getMenu(1).add(new GuiMenuItem("Play", 'Z'));
		getMenu(1).getItem(0).addActionListener(new PlayGuiFrameActionListener(frame));
		getMenu(1).add(new GuiMenuItem("Pause", 'X'));
		getMenu(1).getItem(1).addActionListener(new PauseGuiFrameActionListener(frame));
		getMenu(1).addSeparator();
		getMenu(1).add(new GuiMenuItem("Reset", 'C'));
		getMenu(1).getItem(3).addActionListener(new ResetActionListener(frame, autonomousSystem));

		add(new GuiMenu("Maze"));
		getMenu(2).add(new GuiMenuItem("Change to the default maze", '0'));
		getMenu(2).getItem(0).addActionListener(new ChangeMazeActionListener(frame, autonomousSystem, DefaultMaze.class));
		getMenu(2).add(new GuiMenuItem("Change to a random grid maze", '1'));
		getMenu(2).getItem(1).addActionListener(new ChangeMazeActionListener(frame, autonomousSystem, RandomGridMaze.class));

		add(new GuiMenu("Car"));
		getMenu(3).add(new GuiMenuItem("Change to a circular car", '1'));
		getMenu(3).getItem(0).addActionListener(new ChangeCarActionListener(frame, autonomousSystem, CircularCar.class));
		getMenu(3).add(new GuiMenuItem("Change to a rectangular car", '2'));
		getMenu(3).getItem(1).addActionListener(new ChangeCarActionListener(frame, autonomousSystem, RectangularCar.class));
	}
}