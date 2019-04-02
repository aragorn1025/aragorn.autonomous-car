package aragorn.autonomous.car.zold.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystem;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
class InfoPanel extends GuiPanel {

	private static Font DEFAULT_FONT = new Font(null, Font.PLAIN, 15);

	private static Font SMALL_FONT = new Font(null, Font.PLAIN, 12);

	private static void initialField(JTextField field) {
		field.setColumns(5);
		field.setEditable(false);
	}

	private static void initialLabel(JLabel label) {
		label.setFont(SMALL_FONT);
		label.setHorizontalAlignment(JTextField.TRAILING);
	}

	private AutonomousSystem autonomousSystem;

	private final String[] STRINGS = new String[] { "X", "Y", "Direction", "Wheel", "Front", "Left", "Right" };

	private JLabel[] labels = new JLabel[STRINGS.length];

	private JTextField[] fields = new JTextField[STRINGS.length];

	InfoPanel(AutonomousSystem autonomousSystem) {
		super("Info");
		setDefaultMargin(5);
		setAutonomousSystem(autonomousSystem);
		for (int i = 0; i < STRINGS.length; i++) {
			labels[i] = new JLabel(STRINGS[i]);
			addComponent(labels[i], 0, i, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
			fields[i] = new JTextField();
			addComponent(fields[i], 1, i, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		}
		initial();
		reset();
	}

	private void initial() {
		for (int i = 0; i < STRINGS.length; i++) {
			initialLabel(labels[i]);
			initialField(fields[i]);
			fields[i].setToolTipText(STRINGS[i]);
		}
	}

	public void reset() {
		setText(fields[0], autonomousSystem.getCar().getStatus().getLocation().getX());
		setText(fields[1], autonomousSystem.getCar().getStatus().getLocation().getY());
		setText(fields[2], autonomousSystem.getCar().getStatus().getDirectionOutput());
		setText(fields[3], autonomousSystem.getCar().getStatus().getWheelAngleOutput());
		setText(fields[4], autonomousSystem.detectFront());
		setText(fields[5], autonomousSystem.detectRight());
		setText(fields[6], autonomousSystem.detectLeft());
	}

	private void setAutonomousSystem(AutonomousSystem autonomousSystem) {
		this.autonomousSystem = autonomousSystem;
	}

	private void setText(JTextField field, double value) {
		if (value >= autonomousSystem.getMaze().getBoundsHypotenuse() * 2.0) {
			field.setFont(SMALL_FONT);
			field.setHorizontalAlignment(JTextField.CENTER);
			field.setText("INFINITY");
		} else {
			field.setFont(DEFAULT_FONT);
			field.setHorizontalAlignment(JTextField.TRAILING);
			field.setText(String.format("%.2f", value));
		}
	}
}