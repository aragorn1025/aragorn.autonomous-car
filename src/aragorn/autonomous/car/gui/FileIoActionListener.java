package aragorn.autonomous.car.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.gui.GuiFileChooser;

class FileIoActionListener implements ActionListener {

	private GuiFileChooser file_chooser;

	FileIoActionListener(GuiFileChooser file_chooser) {
		this.file_chooser = file_chooser;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		file_chooser.actionPerformed(event);
	}
}