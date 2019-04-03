package aragorn.autonomous.car.object;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import aragorn.gui.GuiCoordinate2D;
import aragorn.gui.GuiPaintable;
import aragorn.math.geometry.Polyline2D;
import aragorn.math.geometry.ConvexQuadrilateral2D;

public interface Maze extends GuiPaintable {

	public default Rectangle2D.Double getBounds() {
		return getWall().getBounds();
	}

	public default double getBoundsHypotenuse() {
		return Math.sqrt(Math.pow(getBounds().getWidth(), 2) + Math.pow(getBounds().getHeight(), 2));
	}

	public ConvexQuadrilateral2D getEndArea(); // TODO should be polygon

	public Polyline2D getWall(); // TODO should be polygon

	public GuiCoordinate2D getFitCoordinate(Dimension panelSize, int margin);

	public default GuiCoordinate2D getFitCoordinate(int panel_width, int panel_height, int margin) {
		return getFitCoordinate(new Dimension(panel_width, panel_height), margin);
	}

	public Polyline2D getEndLine(); // TODO should be line segment
}