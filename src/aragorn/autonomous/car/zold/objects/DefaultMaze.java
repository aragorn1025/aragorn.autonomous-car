package aragorn.autonomous.car.zold.objects;

import java.awt.geom.Point2D;
import aragorn.util.MathGeometryPolyline2D;

public class DefaultMaze extends LinearMaze {

	public DefaultMaze() {
		super(new MathGeometryPolyline2D(new Point2D.Double(-6, 0), new Point2D.Double(-6, 22), new Point2D.Double(18, 22), new Point2D.Double(18, 37)),
				new MathGeometryPolyline2D(new Point2D.Double(6, 0), new Point2D.Double(6, 10), new Point2D.Double(30, 10), new Point2D.Double(30, 37)),
				new MathGeometryPolyline2D(new Point2D.Double(18, 37), new Point2D.Double(18, 50), new Point2D.Double(30, 50), new Point2D.Double(30, 37)));
	}
}