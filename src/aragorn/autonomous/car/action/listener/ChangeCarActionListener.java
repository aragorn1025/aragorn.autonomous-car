package aragorn.autonomous.car.action.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFrame;

public class ChangeCarActionListener implements ActionListener {

	private GuiFrame frame;

	private AutonomousSystem autonomous_system;

	private Class<? extends Car> clazz;

	public ChangeCarActionListener(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends Car> clazz) {
		this.frame = frame;
		this.autonomous_system = autonomous_system;
		this.clazz = clazz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			frame.pause();
			if (autonomous_system.getCar().getClass() != clazz) {
				autonomous_system.setCar(clazz.newInstance());
			}
			autonomous_system.reset();
			frame.repaint();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}
}