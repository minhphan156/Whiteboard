package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.InterfaceControl;
import edu.sjsu.whiteboard.models.DShapeModel;

import java.awt.*;

public class DRect extends DShape {
	private DShapeModel pointerToDShapeModel; // hold the reference to DRectModel to get data for painting

	public DRect(){
	}
	public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
		this.pointerToDShapeModel = pointerToDShapeModel;
	}

	public DShapeModel getPointerToDShapeModel() {
		return pointerToDShapeModel;
	}
	// called to draw Rectangle
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(pointerToDShapeModel.getColor()); // setColor method must be before fillRect()
		g2.fillRect(pointerToDShapeModel.getX(),pointerToDShapeModel.getY(),pointerToDShapeModel.getWidth(),pointerToDShapeModel.getHeight());
	}
}
