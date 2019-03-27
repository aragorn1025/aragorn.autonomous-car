package aragorn.autonomous.car.old.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.security.InvalidParameterException;
import javax.swing.border.LineBorder;
import aragorn.autonomous.car.old.fuzzy.system.AutonomousSystem;
import aragorn.gui.Coordinate2D;
import aragorn.gui.GUIPanel;

class MazePanel extends GUIPanel {
	private AutonomousSystem autonomousSystem;

	private int printStep;

	MazePanel(AutonomousSystem autonomousSystem) {
		super();
		setAutonomousSystem(autonomousSystem);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK));
		setOpaque(true);
		setPrintStep(1);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Coordinate2D c = autonomousSystem.getMaze().getFitCoordinate(getWidth(), getHeight(), 30);
		for (int i = 0; i < autonomousSystem.getCarTracksNumber(); i += printStep) {
			autonomousSystem.getCarTracks(i).paintCarShadow(g, c);
		}
		autonomousSystem.getCar().paint(g, c);
		autonomousSystem.getMaze().paint(g, c);
	}

	@Override
	public void reset() {
		autonomousSystem.reset();
		repaint();
	}

	private void setAutonomousSystem(AutonomousSystem autonomousSystem) {
		this.autonomousSystem = autonomousSystem;
		reset();
	}

	void setPrintStep(int printStep) {
		if (printStep < 0) {
			throw new InvalidParameterException();
		}
		this.printStep = printStep;
	}
}