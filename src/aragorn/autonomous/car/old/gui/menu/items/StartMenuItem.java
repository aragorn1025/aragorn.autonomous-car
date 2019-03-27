package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import aragorn.gui.GUIFrame;

public class StartMenuItem extends JMenuItem implements ActionListener {
	private GUIFrame frame;

	public StartMenuItem(GUIFrame frame) {
		super("Start");
		this.frame = frame;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.start();
	}
}