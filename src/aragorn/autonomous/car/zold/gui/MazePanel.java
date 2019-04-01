package aragorn.autonomous.car.zold.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.security.InvalidParameterException;
import javax.swing.border.LineBorder;
import aragorn.autonomous.car.zold.fuzzy.system.AutonomousSystemOld;
import aragorn.gui.GuiCoordinate2D;
import aragorn.gui.GuiPanel;

@SuppressWarnings("serial")
class MazePanel extends GuiPanel {

	private AutonomousSystemOld autonomousSystem;

	private int printStep;

	MazePanel(AutonomousSystemOld autonomousSystem) {
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
		GuiCoordinate2D c = autonomousSystem.getMaze().getFitCoordinate(getWidth(), getHeight(), 30);
		for (int i = 0; i < autonomousSystem.getCarTracksNumber(); i += printStep) {
			autonomousSystem.getCar().drawShadow(g, c, autonomousSystem.getCarTracks(i));
		}
		autonomousSystem.getCar().draw(g, c);
		autonomousSystem.getMaze().draw(g, c);
	}

	public void reset() {
		autonomousSystem.reset();
		repaint();
	}

	private void setAutonomousSystem(AutonomousSystemOld autonomousSystem) {
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