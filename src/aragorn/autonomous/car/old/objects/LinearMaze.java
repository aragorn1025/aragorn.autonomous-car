package aragorn.autonomous.car.old.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import aragorn.autonomous.car.old.math.operation.GeometryParallelogram;
import aragorn.autonomous.car.old.math.operation.GeometryPolyline;
import aragorn.autonomous.car.old.math.operation.MathOperation;
import aragorn.autonomous.car.old.math.operation.MathVector;
import aragorn.gui.Coordinate2D;

public class LinearMaze implements Maze {
	private GeometryPolyline	endWall;
	private GeometryPolyline	leftWall;
	private GeometryPolyline	rightWall;

	public LinearMaze() {
		this(new GeometryPolyline(), new GeometryPolyline(), new GeometryPolyline());
	}

	public LinearMaze(GeometryPolyline leftWall, GeometryPolyline rightWall, GeometryPolyline endWall) {
		this.leftWall = leftWall;
		this.rightWall = rightWall;
		this.endWall = endWall;
	}

	@Override
	public Rectangle getBounds() {
		int xmin = (int) MathOperation.min(leftWall.getBounds().getX(), rightWall.getBounds().getX(), endWall.getBounds().getX());
		int ymin = (int) MathOperation.min(leftWall.getBounds().getY(), rightWall.getBounds().getY(), endWall.getBounds().getY());
		int xmax = (int) MathOperation.max(leftWall.getBounds().getX() + leftWall.getBounds().getWidth(),
				rightWall.getBounds().getX() + rightWall.getBounds().getWidth(),
				endWall.getBounds().getX() + endWall.getBounds().getWidth());
		int ymax = (int) MathOperation.max(leftWall.getBounds().getY() + leftWall.getBounds().getHeight(),
				rightWall.getBounds().getY() + rightWall.getBounds().getHeight(),
				endWall.getBounds().getY() + endWall.getBounds().getHeight());
		return new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
	}

	@Override
	public double getBoundsHypotenuse() {
		return Math.sqrt(Math.pow(getBounds().getWidth(), 2) + Math.pow(getBounds().getHeight(), 2));
	}

	@Override
	public Coordinate2D getFitCoordinate(int panelWidth, int panelHeight, int margin) {
		double unit = Math.min((panelWidth - 2.0 * margin) / getBounds().getWidth(),
				(panelHeight - 2.0 * margin) / getBounds().getHeight());
		double ox = panelWidth / 2.0 - unit * (getBounds().getWidth() / 2.0 + getBounds().getX());
		double oy = panelHeight / 2.0 + unit * (getBounds().getHeight() / 2.0 + getBounds().getY());
		return new Coordinate2D(ox, oy, unit, unit);
	}

	@Override
	public GeometryParallelogram getEndArea() {
		return new GeometryParallelogram(endWall.getPoint(0), new MathVector._2D(endWall.getPoint(0), endWall.getPoint(1)),
				new MathVector._2D(endWall.getPoint(1), endWall.getPoint(2)));
	}

	@Override
	public GeometryPolyline getEndWall() {
		return endWall;
	}

	@Override
	public GeometryPolyline getLeftWall() {
		return leftWall;
	}

	@Override
	public GeometryPolyline getRightWall() {
		return rightWall;
	}

	@Override
	public void paint(Graphics g, Coordinate2D c) {
		g.setColor(Color.BLACK);
		leftWall.paint(g, c);
		rightWall.paint(g, c);
		g.setColor(Color.LIGHT_GRAY);
		endWall.paint(g, c);
	}
}