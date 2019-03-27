package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import aragorn.gui.GUIFrame;

public class StopMenuItem extends JMenuItem implements ActionListener {
	private GUIFrame frame;

	public StopMenuItem(GUIFrame frame) {
		super("Stop");
		this.frame = frame;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.stop();
	}
}