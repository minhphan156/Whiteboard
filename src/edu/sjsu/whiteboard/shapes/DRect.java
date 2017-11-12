package shapes;


import models.DRectModel;
import models.DShapeModel;

import java.awt.*;

public class DRect extends DShape {
	private DShapeModel pointerToDShapeModel; // hold the reference to DRectModel to get data for painting

	public DRect (DShapeModel pointerToDShapeModel){
		this.pointerToDShapeModel = pointerToDShapeModel;
	}

	// called to draw Rectangle
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawRect(pointerToDShapeModel.getX(),pointerToDShapeModel.getY(),pointerToDShapeModel.getWidth(),pointerToDShapeModel.getHeight());
	}
}
