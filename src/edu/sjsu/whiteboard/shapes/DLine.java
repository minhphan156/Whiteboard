package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.models.DLineModel;
import edu.sjsu.whiteboard.models.DShapeModel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by danil on 11/11/17.
 */
public class DLine extends DShape{

    public DLine(){}

    DLineModel myModel = new DLineModel();

    double x1 = myModel.getP1().getX();
    double y1 = myModel.getP1().getY();
    double x2 = myModel.getP2().getX();
    double y2 = myModel.getP2().getY();



    public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
        super.setPointerToDShapeModel(pointerToDShapeModel); // call to set dShapeModel in parent class
    }

    public DShapeModel getPointerToDShapeModel() {
        return super.getDShapeModel();// call to get dShapeModel in parent class
    }

    @Override
    public void modelChanged(DShapeModel pointerToDShapeModel) {
        System.out.println("redraw line");
        getCanvasReferencel().repaint();
    }

    @Override
    public void setKnobs(){
        // TODO: Change from 4 knobs to 2 knobs
        //each time model changes, need a new ArrayList...
        //otherwise, it will keep adding points...
        knobPoints = new ArrayList<>();
        knobs =  new ArrayList<>();
        //System.out.println("the size of knobPoints is " + knobPoints.size());
        int dModelX = getDShapeModel().getX();
        int dModelY = getDShapeModel().getY();
        int dModelWidth = getDShapeModel().getWidth();
        int dModelHeight = getDShapeModel().getHeight();
        Point upLeft = new Point(dModelX, dModelY);
        knobPoints.add(upLeft);
        knobs.add(new Knob(upLeft));
//        Point upRight = new Point(dModelX + dModelWidth, dModelY);
//        knobPoints.add(upRight);
//        knobs.add(new Knob(upRight));
        Point downRight = new Point(dModelX + dModelWidth, dModelY + dModelHeight);
        knobPoints.add(downRight);
        knobs.add(new Knob(downRight));
//        Point downLeft = new Point(dModelX, dModelY + dModelHeight);
//        knobPoints.add(downLeft);
//        knobs.add(new Knob(downLeft));
    }


    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //Line2D line = new Line2D.Double(x1,y1,x2,y2);
        g2.drawLine(super.getDShapeModel().getX(),super.getDShapeModel().getY(),super.getDShapeModel().getWidth(),
                super.getDShapeModel().getHeight());
        super.drawKnobs(g2);
    }


}
