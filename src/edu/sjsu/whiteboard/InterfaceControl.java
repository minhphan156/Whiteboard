import models.DRectModel;
import models.DShapeModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by danil on 11/11/17.
 */
public class InterfaceControl extends JPanel {
    private String name = "interfacecontrol";
    private Controller controller;
    private Dimension size = new Dimension(400,400);

    public InterfaceControl(Controller controller){
        this.controller = controller;
        this.setPreferredSize(size);

        Box verticalBoxMain = Box.createVerticalBox();

        Box shapesHorizontalBox = Box.createHorizontalBox();
        JButton rect = new JButton("Rect");
        JButton oval = new JButton("Oval");
        JButton line = new JButton("Line");
        JButton text = new JButton("Text");
        shapesHorizontalBox.add(rect);
        shapesHorizontalBox.add(oval);
        shapesHorizontalBox.add(line);
        shapesHorizontalBox.add(text);

        Box setColorHorizontalBox = Box.createHorizontalBox();
        JButton scriptChooser = new JButton("Script");
        JButton setColor = new JButton("Set Color");
        JTextField textField = new JTextField("");
        setColorHorizontalBox.add(textField);
        setColorHorizontalBox.add(scriptChooser);
        setColorHorizontalBox.add(setColor);

        Box toolsHBox = Box.createHorizontalBox();
        JButton moveToFront = new JButton("Move to Front");
        JButton moveToBack = new JButton("Move to Back");
        JButton removeShape = new JButton("Remove Shape");
        toolsHBox.add(moveToFront);
        toolsHBox.add(moveToBack);
        toolsHBox.add(removeShape);

        verticalBoxMain.add(shapesHorizontalBox);
        verticalBoxMain.add(setColorHorizontalBox);
        verticalBoxMain.add(toolsHBox);
        this.add(verticalBoxMain);

        //listener for Rect JButton
        rect.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                controller.getdShapeModels().add(new DRectModel()); //create new DRectModel in arraylist of DShapeModel in Controller

                int indexOfNewShape = controller.getdShapeModels().size()- 1; //index of new DShapeModel in arraylist of DShapeModel in Controller

                Canvas canvasReference = (Canvas)controller.getWhiteboard().getComponentAt("canvas"); // get canvas object's reference

                (canvasReference).addShape(controller.getdShapeModels().get(indexOfNewShape),"rect"); // add the shape to shapeList before calling paintComponent()

                (canvasReference).paintComponent(); // call to paint the shape
            }
        }) ;
    }

    @Override
    public String getName() {
        return name;
    }
}
