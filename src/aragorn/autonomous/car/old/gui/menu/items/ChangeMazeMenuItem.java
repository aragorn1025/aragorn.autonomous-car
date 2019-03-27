package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.autonomous.car.old.objects.Maze;
import aragorn.gui.GUIFrame;
import aragorn.gui.GUIMenuItem;
import aragorn.gui.GUIPanel;

public class ChangeMazeMenuItem extends GUIMenuItem implements ActionListener {
	private GUIFrame				frame;
	private AutonomousSystem		autonomousSystem;
	private Class<? extends Maze>	c;
	private GUIPanel[]				panels;

	public ChangeMazeMenuItem(char mnemonic, GUIFrame frame, AutonomousSystem autonomousSystem, Class<? extends Maze> c,
			GUIPanel[] panels) {
		super(getTitleName(c), mnemonic);
		this.frame = frame;
		this.autonomousSystem = autonomousSystem;
		this.c = c;
		this.panels = panels;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.stop();
		try {
			autonomousSystem.setMaze(c.newInstance());
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		autonomousSystem.reset();
		for (int i = 0; i < panels.length; i++) {
			panels[i].reset();
		}
	}

	private static String getTitleName(Class<? extends Maze> c) {
		String parentName = c.getSuperclass().getInterfaces()[0].getSimpleName();
		String typeName = c.getSimpleName().replaceAll(parentName, "");
		String article;
		switch (typeName.toUpperCase().charAt(0)) {
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				article = "an";
				break;
			case 'D':
				article = (typeName.toLowerCase().equals("default")) ? "the" : "a";
				break;
			default:
				article = "a";
				break;
		}
		return String.format("Change to %s %s %s", article, typeName, parentName);
	}
}