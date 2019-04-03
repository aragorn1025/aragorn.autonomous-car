package aragorn.autonomous.car.zold.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import aragorn.autonomous.car.object.Maze;
import aragorn.gui.GuiCoordinate2D;
import aragorn.math.geometry.Polyline2D;
import aragorn.math.geometry.ConvexQuadrilateral2D;

public class LinearMaze implements Maze {

	private Polyline2D wall;

	private Polyline2D end_line;

	public LinearMaze() {
		this(new Polyline2D(), new Polyline2D());
	}

	public LinearMaze(Polyline2D wall, Polyline2D end_line) {
		this.wall = wall;
		this.end_line = end_line;
	}

	@Override
	public void draw(Graphics g, GuiCoordinate2D c) {
		g.setColor(Color.BLACK);
		wall.draw(g, c);
		g.setColor(Color.RED);
		end_line.draw(g, c);
	}

	@Override
	public Polyline2D getEndLine() {
		return end_line;
	}

	@Override
	public ConvexQuadrilateral2D getEndArea() {
		return null; // TODO undone
		// new MathGeometryParallelogram2D(endWall.getPoint(0), new MathVector2D(endWall.getPoint(0), endWall.getPoint(1)),
		// new MathVector2D(endWall.getPoint(1), endWall.getPoint(2)));
	}

	@Override
	public Polyline2D getWall() {
		return wall;
	}

	@Override
	public GuiCoordinate2D getFitCoordinate(Dimension panel_dimension, int margin) {
		double unit = Math.min((panel_dimension.width - 2.0 * margin) / getBounds().getWidth(), (panel_dimension.height - 2.0 * margin) / getBounds().getHeight());
		double ox = panel_dimension.width / 2.0 - unit * (getBounds().getWidth() / 2.0 + getBounds().getX());
		double oy = panel_dimension.height / 2.0 + unit * (getBounds().getHeight() / 2.0 + getBounds().getY());
		return new GuiCoordinate2D(new Point2D.Double(ox, oy), unit, unit);
	}
}