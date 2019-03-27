package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.gui.GUIFrame;
import aragorn.gui.GUIMenuItem;

public class ExitMenuItem extends GUIMenuItem implements ActionListener {
	private GUIFrame frame;

	public ExitMenuItem(GUIFrame frame) {
		super("Exit", 'X');
		this.frame = frame;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.close();
	}
}