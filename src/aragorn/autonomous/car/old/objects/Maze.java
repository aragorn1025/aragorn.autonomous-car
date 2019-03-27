package aragorn.autonomous.car.old.objects;

import java.awt.Dimension;
import java.awt.Rectangle;
import aragorn.autonomous.car.old.math.operation.GeometryParallelogram;
import aragorn.autonomous.car.old.math.operation.GeometryPolyline;
import aragorn.gui.Coordinate2D;
import aragorn.gui.Paintable;

public interface Maze extends Paintable {
	public Rectangle getBounds();

	public double getBoundsHypotenuse();

	public GeometryParallelogram getEndArea();

	public GeometryPolyline getEndWall();

	public Coordinate2D getFitCoordinate(int panelWidth, int panelHeight, int margin);

	public default Coordinate2D getFitCoordinate(Dimension panelSize, int margin) {
		return getFitCoordinate((int) panelSize.getWidth(), (int) panelSize.getHeight(), margin);
	}

	public GeometryPolyline getLeftWall();

	public GeometryPolyline getRightWall();
}