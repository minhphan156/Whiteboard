package edu.sjsu.whiteboard;




import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.shapes.DOval;
import edu.sjsu.whiteboard.shapes.DRect;
import edu.sjsu.whiteboard.shapes.DShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private DShape selectedShape;


    public Canvas(Controller controller) {
        this.controller = controller;
        shapeList = new ArrayList<>();
        this.setBackground(Color.WHITE);
        this.setPreferredSize(size);
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent event) {
                Object source = event.getSource();
                int x = event.getX();
                int y = event.getY();
                System.out.printf("\n(%d, %d)", x, y);
                setSelectedShape(x, y);
            }

            public void mouseEntered(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {

            }

            public void mouseReleased(MouseEvent arg0) {

            }
        });
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
            DRect temp = new DRect();
            temp.setCanvasReferencel(this); // put canvas reference inside DRect object
            dShapeModel.setListOfListeners(temp); // put DRect reference in listOfListener in DShapeModel
            temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DRect object
            shapeList.add(temp);


        }
        else if(typeOfShape.equals("oval")){
            DOval temp = new DOval();
            temp.setCanvasReferencel(this); // put canvas reference inside DOval object
            dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
            temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
            shapeList.add(temp);
        }
    }

    @Override
    public String getName() {
        return name;
    }


    public void setSelectedShape(int x, int y)
    {
        int currIndex = shapeList.size() - 1;
        boolean foundSelected = false;
        int indexOfSelected = -1;
        while(currIndex >= 0 && !foundSelected)
        {
            //back to front, the first found(which is on top) is the selected shape
            System.out.println("List size"+shapeList.size());
            DShape currShape = shapeList.get(currIndex);

            //a bound Class may be needed
            if(currShape.getDShapeModel().containsInBound(x, y))
            {
                System.out.println("inside last shape");
                selectedShape = currShape;
                foundSelected = true;
                indexOfSelected = currIndex;
            }
            currIndex--;
        }

        if(foundSelected)
        {
            shapeList.remove(indexOfSelected);
            shapeList.add(selectedShape);;
            paintComponent();
        }
    }

    public DShape getSelectedShape()
    {
        return selectedShape;
    }


}