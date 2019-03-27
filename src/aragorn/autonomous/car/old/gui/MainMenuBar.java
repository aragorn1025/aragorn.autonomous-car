package aragorn.autonomous.car.old.gui;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.gui.menu.items.ChangeCarMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ChangeMazeMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ExitMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ExportCarTrack4DMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ExportCarTrack6DMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ExportLinearMazeMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ImportLinearMazeMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.ResetMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.StartMenuItem;
import aragorn.autonomous.car.old.gui.menu.items.StopMenuItem;
import aragorn.autonomous.car.old.objects.CircularCar;
import aragorn.autonomous.car.old.objects.DefaultMaze;
import aragorn.autonomous.car.old.objects.RandomGridMaze;
import aragorn.autonomous.car.old.objects.RectangularCar;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenu;
import aragorn.gui.GuiPanel;

class MainMenuBar extends JMenuBar {
	private JFileChooser fileChooser = new JFileChooser(Main.DESKTOP);

	private GuiFrame			frame;
	private AutonomousSystem	autonomousSystem;
	private GuiPanel[]			panels;

	MainMenuBar(GuiFrame frame, AutonomousSystem autonomousSystem, GUIPanel... panels) {
		this.frame = frame;
		this.autonomousSystem = autonomousSystem;
		this.panels = panels;
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
		getMenu(0).add(new ExitMenuItem(frame));
		add(new GuiMenu("Play"));
		getMenu(1).add(new StartMenuItem(frame));
		getMenu(1).add(new StopMenuItem(frame));
		getMenu(1).addSeparator();
		getMenu(1).add(new ResetMenuItem(frame, autonomousSystem, panels));
		add(new GuiMenu("Maze"));
		getMenu(2).add(new ChangeMazeMenuItem('1', frame, autonomousSystem, DefaultMaze.class, panels));
		getMenu(2).add(new ChangeMazeMenuItem('2', frame, autonomousSystem, RandomGridMaze.class, panels));
		add(new GuiMenu("Car"));
		getMenu(3).add(new ChangeCarMenuItem('1', frame, autonomousSystem, CircularCar.class, panels));
		getMenu(3).add(new ChangeCarMenuItem('2', frame, autonomousSystem, RectangularCar.class, panels));
	}
}