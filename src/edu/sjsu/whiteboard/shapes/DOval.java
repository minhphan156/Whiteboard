package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.InterfaceControl;
import edu.sjsu.whiteboard.models.DShapeModel;

import java.awt.*;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

public class DOval extends DShape {
   // private DShapeModel pointerToDShapeModel; // hold the reference to DRectModel to get data for painting
    public DOval(){
    }

    public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
        super.setPointerToDShapeModel(pointerToDShapeModel);
    }

    public DShapeModel getPointerToDShapeModel() {
        return super.getDShapeModel();
    }

    @Override
    public void modelChanged(DShapeModel pointerToDShapeModel) {
        if(getCanvasReferencel() != null)
        {
            getCanvasReferencel().repaint();
        }
    }

    // called to draw an Oval
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(super.getDShapeModel().getColor()); // setColor method must be before fillOval()
        g2.fillOval(super.getDShapeModel().getX(),super.getDShapeModel().getY(),super.getDShapeModel().getWidth(),super.getDShapeModel().getHeight());
		if(super.getIsSelected())
		{
			super.drawKnobs(g2);
		}
    }
}
