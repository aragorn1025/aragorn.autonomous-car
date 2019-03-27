package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenuItem;
import aragorn.gui.GuiPanel;

public class ResetMenuItem extends GuiMenuItem implements ActionListener {
	private GuiFrame			frame;
	private GuiPanel[]			panels;
	private AutonomousSystem	autonomousSystem;

	public ResetMenuItem(GuiFrame frame, AutonomousSystem autonomousSystem, GUIPanel[] panels) {
		super("Reset");
		this.frame = frame;
		this.autonomousSystem = autonomousSystem;
		this.panels = panels;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.stop();
		autonomousSystem.reset();
		for (int i = 0; i < panels.length; i++) {
			panels[i].reset();
		}
	}
}