package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.*;
import edu.sjsu.whiteboard.Canvas;
import edu.sjsu.whiteboard.models.DShapeModel;

import java.awt.*;

public class DRect extends DShape {
	//private DShapeModel pointerToDShapeModel; // hold the reference to DRectModel to get data for painting
	public DRect(){
	}
	public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
		super.setPointerToDShapeModel(pointerToDShapeModel); // call to set dShapeModel in parent class
	}

	public DShapeModel getPointerToDShapeModel() {
		return super.getDShapeModel();// call to get dShapeModel in parent class
	}

	@Override
	public void modelChanged(DShapeModel pointerToDShapeModel) {
		System.out.println(" redraw rectangle");
		getCanvasReferencel().paintComponent();
		// Paint
	}

	// called to draw Rectangle
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(super.getDShapeModel().getColor()); // setColor method must be before fillRect()
		g2.fillRect(super.getDShapeModel().getX(),super.getDShapeModel().getY(),super.getDShapeModel().getWidth(),super.getDShapeModel().getHeight());
	}
}
