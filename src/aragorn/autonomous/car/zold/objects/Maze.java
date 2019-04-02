package aragorn.autonomous.car.zold.objects;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import aragorn.gui.GuiCoordinate2D;
import aragorn.gui.GuiPaintable;
import aragorn.util.MathGeometryParallelogram2D;
import aragorn.util.MathGeometryPolyline2D;

public interface Maze extends GuiPaintable {

	public Rectangle2D.Double getBounds();

	public double getBoundsHypotenuse();

	public MathGeometryParallelogram2D getEndArea();

	public MathGeometryPolyline2D getEndWall();

	public default GuiCoordinate2D getFitCoordinate(Dimension panelSize, int margin) {
		return getFitCoordinate((int) panelSize.getWidth(), (int) panelSize.getHeight(), margin);
	}

	public GuiCoordinate2D getFitCoordinate(int panelWidth, int panelHeight, int margin);

	public MathGeometryPolyline2D getLeftWall();

	public MathGeometryPolyline2D getRightWall();
}