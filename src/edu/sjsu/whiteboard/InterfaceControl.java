package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DOvalModel;
import edu.sjsu.whiteboard.models.DRectModel;
import edu.sjsu.whiteboard.shapes.DOval;

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
    private static Color selectedColor = Color.GRAY;

    public static Color getSelectedColor(){
        return selectedColor;
    }

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

        JButton setColor = new JButton("Set Color");
        JButton colorButton = new JButton("");
        colorButton.setBackground(Color.GRAY);
        colorButton.setOpaque(true);
        colorButton.setBorderPainted(false);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Color initialBackground = setColor.getBackground();
                Color color = JColorChooser.showDialog(null, "JColorChooser", initialBackground);
                selectedColor = color;
                colorButton.setBackground(color);
                colorButton.setOpaque(true);
                colorButton.setBorderPainted(false);
            }
        };
        setColor.addActionListener(actionListener);
        setColorHorizontalBox.add(setColor);
        setColorHorizontalBox.add(colorButton);

        Box textHBox = Box.createHorizontalBox();
        JTextField textField = new JTextField("");
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox scriptChooser = new JComboBox(fonts);
        scriptChooser.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String selectedValue = scriptChooser.getSelectedItem().toString();
                System.out.print("\nUser selected: "+selectedValue);

            }
        });
        textHBox.add(textField);
        textHBox.add(scriptChooser);


        Box toolsHBox = Box.createHorizontalBox();
        JButton moveToFront = new JButton("Move to Front");
        JButton moveToBack = new JButton("Move to Back");
        JButton removeShape = new JButton("Remove Shape");
        toolsHBox.add(moveToFront);
        toolsHBox.add(moveToBack);
        toolsHBox.add(removeShape);
        String[] columns = new String[] {
                "X", "Y", "Width", "Height"
        };

        Object[][] data = new Object[][] {
                {10, 10, 111, 58 },
                {56, 52, 221, 56 },
                {18, 148, 361, 120 },
        };
        JTable table = new JTable(data, columns);

        verticalBoxMain.add(shapesHorizontalBox);
        verticalBoxMain.add(setColorHorizontalBox);
        verticalBoxMain.add(toolsHBox);
        verticalBoxMain.add(textHBox);
        verticalBoxMain.add(table.getTableHeader());
        verticalBoxMain.add(table);

        for (Component comp : verticalBoxMain.getComponents()) { ((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }
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

        // Liste
        oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getdShapeModels().add(new DOvalModel()); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
                int indexOfNewShape = controller.getdShapeModels().size()- 1; //index of new DShapeModel in arraylist of DShapeModel in Controller

                Canvas canvasReference = (Canvas)controller.getWhiteboard().getComponentAt("canvas");
                (canvasReference).addShape(controller.getdShapeModels().get(indexOfNewShape), "oval");
                (canvasReference).paintComponent();

            }
        });
    }

    @Override
    public String getName() {
        return name;
    }
}
