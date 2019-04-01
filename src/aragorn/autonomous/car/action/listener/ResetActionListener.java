package aragorn.autonomous.car.action.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystemOld;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenuItem;

@SuppressWarnings("serial")
public class ResetActionListener extends GuiMenuItem implements ActionListener {

	private GuiFrame frame;

	private AutonomousSystemOld autonomous_system;

	public ResetActionListener(GuiFrame frame, AutonomousSystemOld autonomous_system) {
		super("Reset");
		this.frame = frame;
		this.autonomous_system = autonomous_system;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.pause();
		autonomous_system.reset();
		frame.repaint();
	}
}