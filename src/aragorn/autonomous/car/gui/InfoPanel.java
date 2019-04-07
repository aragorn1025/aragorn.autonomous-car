package aragorn.autonomous.car.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
class InfoPanel extends GuiPanel {

	private static final Font DEFAULT_FONT = new Font(null, Font.PLAIN, 15);

	private static final Font SMALL_FONT = new Font(null, Font.PLAIN, 12);

	private static final String[] TITLES = new String[] { "X", "Y", "Direction", "Wheel", "Front", "Left", "Right" };

	private AutonomousSystem autonomous_system;

	private JTextField[] fields = new JTextField[TITLES.length];

	InfoPanel(AutonomousSystem autonomous_system) {
		super("Info");
		setDefaultMargin(5);
		this.autonomous_system = autonomous_system;

		// initial
		for (int i = 0; i < TITLES.length; i++) {
			// initial label
			JLabel[] labels = new JLabel[TITLES.length];
			labels[i] = new JLabel(TITLES[i]);
			labels[i].setFont(SMALL_FONT);
			labels[i].setHorizontalAlignment(JTextField.TRAILING);
			addComponent(labels[i], 0, i, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

			// initial field
			fields[i] = new JTextField();
			fields[i].setColumns(5);
			fields[i].setEditable(false);
			fields[i].setToolTipText(TITLES[i]);
			addComponent(fields[i], 1, i, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		}

		// reset
		reset();
	}

	public void reset() {
		setText(fields[0], autonomous_system.getCar().getStatus().getLocation().getX());
		setText(fields[1], autonomous_system.getCar().getStatus().getLocation().getY());
		setText(fields[2], autonomous_system.getCar().getStatus().getDirectionOutput());
		setText(fields[3], autonomous_system.getCar().getStatus().getWheelAngleOutput());
		setText(fields[4], autonomous_system.detectFront());
		setText(fields[5], autonomous_system.detectRight());
		setText(fields[6], autonomous_system.detectLeft());
	}

	private void setText(JTextField field, double value) {
		if (Double.isFinite(value)) {
			field.setFont(DEFAULT_FONT);
			field.setHorizontalAlignment(JTextField.TRAILING);
			field.setText(String.format("%.2f", value));
		} else {
			field.setFont(SMALL_FONT);
			field.setHorizontalAlignment(JTextField.CENTER);
			field.setText("INFINITY");
		}
	}
}