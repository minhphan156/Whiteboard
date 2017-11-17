package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DOvalModel;
import edu.sjsu.whiteboard.models.DRectModel;
import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.shapes.DOval;
import edu.sjsu.whiteboard.shapes.DShape;

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
    private Canvas canvas;

    public static Color getSelectedColor(){
        return selectedColor;
    }

    public InterfaceControl(Controller controller,Canvas canvas){
        this.canvas = canvas;
        this.controller = controller;
        this.setPreferredSize(size);

        Box verticalBoxMain = Box.createVerticalBox(); // Create main box that contains all horizontal boxes

        Box shapesHorizontalBox = Box.createHorizontalBox(); // Creates horizontal box that stores Rect, Oval, Line, Text buttons
        JButton rect = new JButton("Rect"); // Create Rect button
        JButton oval = new JButton("Oval"); // Create Oval button
        JButton line = new JButton("Line"); // Create Line button
        JButton text = new JButton("Text"); // Create Text button
        shapesHorizontalBox.add(rect); // Add Rect button to shapesHorizaontalBox
        shapesHorizontalBox.add(oval); // Add Oval button to shapesHorizaontalBox
        shapesHorizontalBox.add(line); // Add Line button to shapesHorizaontalBox
        shapesHorizontalBox.add(text); // Add Text button to shapesHorizaontalBox

        Box setColorHorizontalBox = Box.createHorizontalBox(); // Horizontal box that store Set Color button and button that displays selected color
        JButton setColor = new JButton("Set Color"); // Creates set Color Buttonn
        JButton colorButton = new JButton(""); // Create an empty button that serves as selected color display
        Dimension colorButtonSize = new Dimension(1,1);
        colorButton.setSize(colorButtonSize); // Size of the button
        colorButton.setBackground(Color.GRAY); // Initially colorButton will display Gray color
        colorButton.setOpaque(true);
        colorButton.setBorderPainted(false);

        ActionListener actionListener = new ActionListener() { // Listener for colorButton
            public void actionPerformed(ActionEvent actionEvent) {
                Color initialBackground = setColor.getBackground();
                Color color = JColorChooser.showDialog(null, "JColorChooser", initialBackground);
                selectedColor = color; // Set selectedColor object to selected color that the user chose

               canvas.getSelectedShape().getDShapeModel().setColor(selectedColor); // shape that need to change color
               /*CHANGE COLOR OF EXISTING SHAPES  !!!!!!!!! */

                colorButton.setBackground(color);
                colorButton.setOpaque(true);
                colorButton.setBorderPainted(false);
            }
        };
        setColor.addActionListener(actionListener);
        setColorHorizontalBox.add(setColor); // Add setColor button to horizontal box setColorHorizontalBox
        setColorHorizontalBox.add(colorButton); // Add setColor button to horizontal box setColorHorizontalBox

        Box textHBox = Box.createHorizontalBox(); // Horizontal box that contains Text Field and Font chooser
        JTextField textField = new JTextField(""); // Text field to get user input for text
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); // Gets all system fonts
        JComboBox scriptChooser = new JComboBox(fonts); // Combo Box filled with String array of system fonts
        scriptChooser.addActionListener (new ActionListener () { // Listener for change in font selection
            public void actionPerformed(ActionEvent e) {
                String selectedValue = scriptChooser.getSelectedItem().toString(); // Contains selected font
                System.out.print("\nUser selected: "+selectedValue);

            }
        });
        textHBox.add(textField); // Adds textField object to horizontal box textHBox
        textHBox.add(scriptChooser); // Adds Font chooser object to horizontal box textHBox

        Box toolsHBox = Box.createHorizontalBox(); // Horizontal box contains following buttons: Move to Front, Move to Back, Remove shape
        JButton moveToFront = new JButton("Move to Front");
        JButton moveToBack = new JButton("Move to Back");
        JButton removeShape = new JButton("Remove Shape");
        toolsHBox.add(moveToFront); // Adds move to front button in the horizontal box toolsHBox
        toolsHBox.add(moveToBack); // Adds move to back button in the horizontal box toolsHBox
        toolsHBox.add(removeShape); // Adds remove shape button to horizontal box toolsHBox

        // Columns and Data String arrays for table
        String[] columns = new String[] {
                "X", "Y", "Width", "Height"
        };

        Object[][] data = new Object[][] {
                {10, 10, 111, 58 },
                {56, 52, 221, 56 },
                {18, 148, 361, 120 },
        };
        JTable table = new JTable(data, columns);

        // Add horizontal boxes to the main
        verticalBoxMain.add(shapesHorizontalBox);
        verticalBoxMain.add(setColorHorizontalBox);
        verticalBoxMain.add(textHBox);
        verticalBoxMain.add(toolsHBox);
        verticalBoxMain.add(table.getTableHeader());
        verticalBoxMain.add(table);

        for (Component comp : verticalBoxMain.getComponents()) { ((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }
        this.add(verticalBoxMain);



        //listener for Rect JButton
        rect.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                    draw("rect");
            }
        }) ;

        // Listener for Oval button
        oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                draw("oval");
            }
        });

        // Listener for Text button
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = "text";
                draw(text);
            }
        });



        removeShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Delete selected shape
            }
        });
    }

    public void draw(String type){
        if(type.equals("oval")){
            DShapeModel temp = new DOvalModel();
            temp.setColor(selectedColor);
            controller.getdShapeModels().add(temp); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
        }
        else if(type.equals("rect")){
            DShapeModel temp = new DRectModel();
            temp.setColor(selectedColor);
            controller.getdShapeModels().add(temp); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
        }
        int indexOfNewShape = controller.getdShapeModels().size()- 1; //index of new DShapeModel in arraylist of DShapeModel in Controller

        Canvas canvasReference = (Canvas)controller.getWhiteboard().getComponentAt("canvas");
        (canvasReference).addShape(controller.getdShapeModels().get(indexOfNewShape), type);
        (canvasReference).paintComponent();
    }

    @Override
    public String getName() {
        return name;
    }
}
