package aragorn.autonomous.car.object;

import java.awt.geom.Point2D;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;

public class DefaultMaze extends LinearMaze {

	public static final CarStatus CAR_STATUS = new CarStatus(new Point2D.Double(0, 0), Math.toRadians(90), 0);

	public static final LineSegment2D END_LINE = new LineSegment2D(new Point2D.Double(18, 40), new Point2D.Double(30, 37));

	public static final Polygon2D WALL = new Polygon2D(new Point2D.Double(-6, -3), new Point2D.Double(-6, 22), new Point2D.Double(18, 22), new Point2D.Double(18, 50),
			new Point2D.Double(30, 50), new Point2D.Double(30, 10), new Point2D.Double(6, 10), new Point2D.Double(6, -3), new Point2D.Double(-6, -3));

	public DefaultMaze() {
		super(DefaultMaze.CAR_STATUS, DefaultMaze.END_LINE, DefaultMaze.WALL);
	}
}