package aragorn.autonomous.car.old.objects;

import java.awt.Dimension;
import java.awt.Rectangle;
import aragorn.gui.Coordinate2D;
import aragorn.gui.Paintable;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathGeometryPolyline2D;

public interface Maze extends Paintable {

	public Rectangle getBounds();

	public double getBoundsHypotenuse();

	public MathGeometryParallelogram2D getEndArea();

	public MathGeometryPolyline2D getEndWall();

	public default Coordinate2D getFitCoordinate(Dimension panelSize, int margin) {
		return getFitCoordinate((int) panelSize.getWidth(), (int) panelSize.getHeight(), margin);
	}

	public Coordinate2D getFitCoordinate(int panelWidth, int panelHeight, int margin);

	public MathGeometryPolyline2D getLeftWall();

	public MathGeometryPolyline2D getRightWall();
}