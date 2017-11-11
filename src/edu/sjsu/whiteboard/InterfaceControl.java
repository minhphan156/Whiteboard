package edu.sjsu.whiteboard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by danil on 11/11/17.
 */
public class InterfaceControl extends JPanel {
    private Dimension size = new Dimension(400,400);

    public InterfaceControl(){
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

    }

}
