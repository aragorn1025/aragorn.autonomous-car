package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.gui.GUIFrame;
import aragorn.gui.GUIMenuItem;
import aragorn.gui.GUIPanel;

public class ResetMenuItem extends GUIMenuItem implements ActionListener {
	private GUIFrame			frame;
	private GUIPanel[]			panels;
	private AutonomousSystem	autonomousSystem;

	public ResetMenuItem(GUIFrame frame, AutonomousSystem autonomousSystem, GUIPanel[] panels) {
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