import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
public abstract class DShape {
	private DShapeModel shapeModel;
	
	public abstract void draw(Graphics g);
}
