package aragorn.autonomous.car.action.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystemOld;
import aragorn.autonomous.car.zold.objects.Maze;
import aragorn.gui.GuiFrame;

public class ChangeMazeActionListener implements ActionListener {

	private GuiFrame frame;

	private AutonomousSystemOld autonomous_system;

	private Class<? extends Maze> clazz;

	public ChangeMazeActionListener(GuiFrame frame, AutonomousSystemOld autonomous_system, Class<? extends Maze> clazz) {
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