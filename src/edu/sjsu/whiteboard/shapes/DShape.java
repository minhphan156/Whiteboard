package edu.sjsu.whiteboard.shapes;



import edu.sjsu.whiteboard.*;
import edu.sjsu.whiteboard.Canvas;
import edu.sjsu.whiteboard.models.DShapeModel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class DShape implements ModelListener {
	private DShapeModel pointerToDShapeModel;// hold the reference to DRectModel to get data for painting
	private Canvas canvasReferencel; //hold the canvas reference to call paintComponent() later

	public abstract void draw(Graphics g);

	public Canvas getCanvasReferencel() {
		return canvasReferencel;
	}

	public void setCanvasReferencel(Canvas canvasReferencel) {
		this.canvasReferencel = canvasReferencel;
	}

	public void setDShapeModel(DShapeModel pointerToDShapeModel)
	{
		this.pointerToDShapeModel = pointerToDShapeModel;
	}

	public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
		this.pointerToDShapeModel = pointerToDShapeModel;
	}

	public DShapeModel getDShapeModel()
	{
		return pointerToDShapeModel;
	}

//	public Rectangle2D.Double getBounds()
//	{
//		return pointerToDShapeModel.getBounds();
//	}
}
