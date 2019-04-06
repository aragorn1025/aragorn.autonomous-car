package aragorn.autonomous.car.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import aragorn.math.geometry.ConvexQuadrilateral2D;
import aragorn.math.geometry.Coordinate2D;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Paintable;
import aragorn.math.geometry.Polygon2D;
import aragorn.util.MathUtilities;

public class LinearMaze implements Paintable {

	protected Polygon2D wall;

	protected LineSegment2D end_line;

	private CarStatus car_initial_status;

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

	public double getBoundsHypotenuse() {
		return Math.sqrt(Math.pow(getBounds().getWidth(), 2) + Math.pow(getBounds().getHeight(), 2));
	}

	public ConvexQuadrilateral2D getEndArea() {
		int first_index = 0;
		for (int i = 1; i < wall.getPointNumber(); i++) {
			if (MathUtilities.isApproachZero(wall.getLineSegment(i - 1).getDistance(end_line.getPoints()[0]))) {
				first_index = i;
				break;
			}
		}
		if (first_index == 0)
			throw new InternalError("The points of the wall are illegal.");
		int second_index = first_index + 1;
		if (first_index == wall.getPointNumber() + 1)
			second_index = 0;
		if (MathUtilities.isApproachZero(wall.getLineSegment(second_index).getDistance(end_line.getPoints()[1])))
			return new ConvexQuadrilateral2D(end_line.getPoints()[0], wall.getPoint(first_index), wall.getPoint(second_index), end_line.getPoints()[1]);
		throw new InternalError("The points of the wall are illegal.");
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

	public CarStatus getCarInitialStatus() {
		return car_initial_status;
	}
}