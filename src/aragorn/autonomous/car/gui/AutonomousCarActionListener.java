package aragorn.autonomous.car.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aragorn.autonomous.car.algorithm.Algorithm;
import aragorn.autonomous.car.object.Car;
import aragorn.autonomous.car.object.LinearMaze;
import aragorn.autonomous.car.system.AutonomousSystem;
import aragorn.gui.GuiFrame;

abstract class AutonomousCarActionListener implements ActionListener {

	static class ChangeAlgorithm extends AutonomousCarActionListener {

		private Class<? extends Algorithm> clazz;

		ChangeAlgorithm(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends Algorithm> clazz) {
			super(frame, autonomous_system);
			this.clazz = clazz;
		}

		@SuppressWarnings("unlikely-arg-type")
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				getFrame().pause();
				if (!getAutonomousSystem().getAlgorithm().equals(clazz)) {
					getAutonomousSystem().setAlgorithm(clazz.newInstance());
				}
				getAutonomousSystem().reset();
				getFrame().setTitle(getAutonomousSystem().getAlgorithm().getName());
				getFrame().repaint();
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
	}

	static class ChangeCar extends AutonomousCarActionListener {

		private Class<? extends Car> clazz;

		ChangeCar(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends Car> clazz) {
			super(frame, autonomous_system);
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				getFrame().pause();
				if (getAutonomousSystem().getCar().getClass() != clazz) {
					getAutonomousSystem().setCar(clazz.newInstance());
				}
				getAutonomousSystem().reset();
				getFrame().repaint();
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
	}

	static class ChangeMaze extends AutonomousCarActionListener {

		private Class<? extends LinearMaze> clazz;

		ChangeMaze(GuiFrame frame, AutonomousSystem autonomous_system, Class<? extends LinearMaze> clazz) {
			super(frame, autonomous_system);
			this.clazz = clazz;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				getFrame().pause();
				getAutonomousSystem().setMaze(clazz.newInstance());
				getAutonomousSystem().reset();
				getFrame().repaint();
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
	}

	static class Reset extends AutonomousCarActionListener {

		Reset(GuiFrame frame, AutonomousSystem autonomous_system) {
			super(frame, autonomous_system);
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			getFrame().pause();
			getAutonomousSystem().reset();
			getFrame().repaint();
		}
	}

	private GuiFrame frame;

	private AutonomousSystem autonomous_system;

	protected AutonomousCarActionListener(GuiFrame frame, AutonomousSystem autonomous_system) {
		this.frame = frame;
		this.autonomous_system = autonomous_system;
	}

	protected AutonomousSystem getAutonomousSystem() {
		return autonomous_system;
	}

	protected GuiFrame getFrame() {
		return frame;
	}
}