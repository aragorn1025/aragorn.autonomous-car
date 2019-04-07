package aragorn.autonomous.car.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import aragorn.math.geometry.Coordinate2D;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Paintable;
import aragorn.math.geometry.Polygon2D;

public class LinearMaze implements Paintable {

	protected Polygon2D wall;

	protected LineSegment2D end_line;

	protected CarStatus car_initial_status;

	protected LinearMaze() {
		this(null, null, null);
	}

	public LinearMaze(CarStatus car_initial_status, LineSegment2D end_line, Polygon2D wall) {
		this.car_initial_status = car_initial_status;
		this.end_line = end_line;
		this.wall = wall;
	}

	@Override
	public void draw(Graphics g, Coordinate2D c) {
		g.setColor(Color.BLACK);
		wall.draw(g, c);
		g.setColor(Color.RED);
		end_line.draw(g, c);
	}

	public Rectangle2D.Double getBounds() {
		return getWall().getBounds();
	}

	public CarStatus getCarInitialStatus() {
		return car_initial_status;
	}

	public LineSegment2D getEndLine() {
		return end_line;
	}

	public Coordinate2D getFitCoordinate(Dimension panel_dimension, int margin) {
		double unit = Math.min((panel_dimension.width - 2.0 * margin) / getBounds().getWidth(), (panel_dimension.height - 2.0 * margin) / getBounds().getHeight());
		double ox = panel_dimension.width / 2.0 - unit * (getBounds().getWidth() / 2.0 + getBounds().getX());
		double oy = panel_dimension.height / 2.0 + unit * (getBounds().getHeight() / 2.0 + getBounds().getY());
		return new Coordinate2D(new Point2D.Double(ox, oy), unit, unit);
	}

	public Coordinate2D getFitCoordinate(int panel_width, int panel_height, int margin) {
		return getFitCoordinate(new Dimension(panel_width, panel_height), margin);
	}

	public Polygon2D getWall() {
		return wall;
	}
}