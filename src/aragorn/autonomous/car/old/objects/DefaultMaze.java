package aragorn.autonomous.car.old.objects;

import java.awt.geom.Point2D;
import aragorn.autonomous.car.old.math.operation.GeometryPolyline;

public class DefaultMaze extends LinearMaze {
	public DefaultMaze() {
		super(new GeometryPolyline(new Point2D.Double[] {	new Point2D.Double(-6, 0), new Point2D.Double(-6, 22),
															new Point2D.Double(18, 22), new Point2D.Double(18, 37) },
				4),
				new GeometryPolyline(new Point2D.Double[] {	new Point2D.Double(6, 0), new Point2D.Double(6, 10),
															new Point2D.Double(30, 10), new Point2D.Double(30, 37) },
						4),
				new GeometryPolyline(new Point2D.Double[] {	new Point2D.Double(18, 37), new Point2D.Double(18, 50),
															new Point2D.Double(30, 50), new Point2D.Double(30, 37) },
						4));
	}
}