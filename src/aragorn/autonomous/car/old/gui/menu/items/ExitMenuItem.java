package aragorn.autonomous.car.old.gui.menu.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.gui.GuiFrame;
import aragorn.gui.GuiMenuItem;

@SuppressWarnings("serial")
public class ExitMenuItem extends GuiMenuItem implements ActionListener {

	private GuiFrame frame;

	public ExitMenuItem(GuiFrame frame) {
		super("Exit", 'X');
		this.frame = frame;
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.close();
	}
}