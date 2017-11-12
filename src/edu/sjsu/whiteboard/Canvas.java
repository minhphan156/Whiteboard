package edu.sjsu.whiteboard;




import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.shapes.DRect;
import edu.sjsu.whiteboard.shapes.DShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by danil on 11/7/17.
 */
public class Canvas extends JPanel {
    private String name = "canvas";
    private Controller controller;
    private ArrayList<DShape> shapeList; // store current shapes
    private Dimension size = new Dimension(400,400);

    public Canvas(Controller controller) {
        this.controller = controller;
        shapeList = new ArrayList<>();
        this.setBackground(Color.white);
        this.setPreferredSize(size);
    }

    // called will paint the shape on the canvas
    public void paintComponent(){
        Graphics g = this.getGraphics();
        Iterator<DShape> itr = shapeList.iterator();
        while (itr.hasNext()){
            DShape shapeToPaint = itr.next();
            shapeToPaint.draw(g);
        }
    }

    // add the shape to shapeList before calling paintComponent()
    public void addShape(DShapeModel dShapeModel, String typeOfShape){
        if(typeOfShape.equals("rect")){
            shapeList.add(new DRect(dShapeModel));
        }
    }

    @Override
    public String getName() {
        return name;
    }
}