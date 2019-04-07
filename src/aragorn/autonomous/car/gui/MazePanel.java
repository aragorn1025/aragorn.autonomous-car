package aragorn.autonomous.car.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.border.LineBorder;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.gui.GuiPanel;
import aragorn.math.geometry.Coordinate2D;

@SuppressWarnings("serial")
class MazePanel extends GuiPanel {

	private AutonomousSystem autonomous_system;

	MazePanel(AutonomousSystem autonomous_system) {
		super();
		setAutonomousSystem(autonomous_system);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK));
		setOpaque(true);
		reset();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Coordinate2D c = autonomous_system.getMaze().getFitCoordinate(getWidth(), getHeight(), 30);
		for (int i = 0; i < autonomous_system.getCarTracksNumber(); i++) {
			autonomous_system.getCar().drawShadow(g, c, autonomous_system.getCarTracks(i));
		}
		autonomous_system.getCar().draw(g, c);
		autonomous_system.getMaze().draw(g, c);
	}

	public void reset() {
		autonomous_system.reset();
		repaint();
	}

	private void setAutonomousSystem(AutonomousSystem autonomousSystem) {
		this.autonomous_system = autonomousSystem;
		reset();
	}
}