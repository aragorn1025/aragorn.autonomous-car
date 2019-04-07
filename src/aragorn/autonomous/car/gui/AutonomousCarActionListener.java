package aragorn.autonomous.car.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.gui.GuiFrame;

public abstract class AutonomousCarActionListener implements ActionListener {

	public static class ChangeCar extends AutonomousCarActionListener {

		private Class<? extends Car> clazz;

		public ChangeCar(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends Car> clazz) {
			super(frame, autonomous_system);
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				frame.pause();
				if (autonomous_system.getCar().getClass() != clazz) {
					autonomous_system.setCar(clazz.newInstance());
				}
				autonomous_system.reset();
				frame.repaint();
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
	}

	public static class ChangeMaze extends AutonomousCarActionListener {

		private Class<? extends LinearMaze> clazz;

		public ChangeMaze(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends LinearMaze> clazz) {
			super(frame, autonomous_system);
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				frame.pause();
				autonomous_system.setMaze(clazz.newInstance());
				autonomous_system.reset();
				frame.repaint();
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
	}

	public static class Reset extends AutonomousCarActionListener {

		public Reset(GuiFrame frame, AutonomousSystem autonomous_system) {
			super(frame, autonomous_system);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			frame.pause();
			autonomous_system.reset();
			frame.repaint();
		}
	}

	protected GuiFrame frame;

	protected AutonomousSystem autonomous_system;

	protected AutonomousCarActionListener(GuiFrame frame, AutonomousSystem autonomous_system) {
		this.frame = frame;
		this.autonomous_system = autonomous_system;
	}
}