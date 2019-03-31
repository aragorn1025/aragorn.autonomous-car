package aragorn.autonomous.car.old.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import aragorn.gui.Coordinate2D;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathGeometryPolyline2D;
import aragorn.util.MathUtilities;
import aragorn.util.MathVector2D;

public class LinearMaze implements Maze {

	private MathGeometryPolyline2D endWall;

	private MathGeometryPolyline2D leftWall;

	private MathGeometryPolyline2D rightWall;

	public LinearMaze() {
		this(new MathGeometryPolyline2D(), new MathGeometryPolyline2D(), new MathGeometryPolyline2D());
	}

	public LinearMaze(MathGeometryPolyline2D leftWall, MathGeometryPolyline2D rightWall, MathGeometryPolyline2D endWall) {
		this.leftWall = leftWall;
		this.rightWall = rightWall;
		this.endWall = endWall;
	}

	@Override
	public void draw(Graphics g, Coordinate2D c) {
		g.setColor(Color.BLACK);
		leftWall.draw(g, c);
		rightWall.draw(g, c);
		g.setColor(Color.LIGHT_GRAY);
		endWall.draw(g, c);
	}

	@Override
	public Rectangle getBounds() {
		int xmin = (int) MathUtilities.min(leftWall.getBounds().getX(), rightWall.getBounds().getX(), endWall.getBounds().getX());
		int ymin = (int) MathUtilities.min(leftWall.getBounds().getY(), rightWall.getBounds().getY(), endWall.getBounds().getY());
		int xmax = (int) MathUtilities.max(leftWall.getBounds().getX() + leftWall.getBounds().getWidth(), rightWall.getBounds().getX() + rightWall.getBounds().getWidth(),
				endWall.getBounds().getX() + endWall.getBounds().getWidth());
		int ymax = (int) MathUtilities.max(leftWall.getBounds().getY() + leftWall.getBounds().getHeight(),
				rightWall.getBounds().getY() + rightWall.getBounds().getHeight(), endWall.getBounds().getY() + endWall.getBounds().getHeight());
		return new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
	}

	@Override
	public double getBoundsHypotenuse() {
		return Math.sqrt(Math.pow(getBounds().getWidth(), 2) + Math.pow(getBounds().getHeight(), 2));
	}

	@Override
	public MathGeometryParallelogram2D getEndArea() {
		return new MathGeometryParallelogram2D(endWall.getPoint(0), new MathVector2D(endWall.getPoint(0), endWall.getPoint(1)),
				new MathVector2D(endWall.getPoint(1), endWall.getPoint(2)));
	}

	@Override
	public MathGeometryPolyline2D getEndWall() {
		return endWall;
	}

	@Override
	public Coordinate2D getFitCoordinate(int panelWidth, int panelHeight, int margin) {
		double unit = Math.min((panelWidth - 2.0 * margin) / getBounds().getWidth(), (panelHeight - 2.0 * margin) / getBounds().getHeight());
		double ox = panelWidth / 2.0 - unit * (getBounds().getWidth() / 2.0 + getBounds().getX());
		double oy = panelHeight / 2.0 + unit * (getBounds().getHeight() / 2.0 + getBounds().getY());
		return new Coordinate2D(new Point2D.Double(ox, oy), unit, unit);
	}

	@Override
	public MathGeometryPolyline2D getLeftWall() {
		return leftWall;
	}

	@Override
	public MathGeometryPolyline2D getRightWall() {
		return rightWall;
	}
}