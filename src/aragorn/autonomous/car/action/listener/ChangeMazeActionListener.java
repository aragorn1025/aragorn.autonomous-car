package aragorn.autonomous.car.action.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFrame;

public class ChangeMazeActionListener implements ActionListener {

	private GuiFrame frame;

	private AutonomousSystem autonomous_system;

	private Class<? extends LinearMaze> clazz;

	public ChangeMazeActionListener(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends LinearMaze> clazz) {
		this.frame = frame;
		this.autonomous_system = autonomous_system;
		this.clazz = clazz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			frame.pause();
			autonomous_system.setMaze(clazz.newInstance());
			autonomous_system.reset();
			frame.repaint();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}