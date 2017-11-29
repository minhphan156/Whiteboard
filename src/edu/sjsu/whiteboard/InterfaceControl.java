package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

/**
 * InterfaceControl class handles user's input and the UI elements on the left part of the JFrame
 */

public class InterfaceControl extends JPanel {

    private String name = "interfacecontrol";
    private Controller controller;
    private Dimension size = new Dimension(400,400);
    private Canvas canvas; // pointer
    private static JComboBox scriptChooser; // STATIC Combo Box filled with String array of system fonts
    private static JTextField textField; // STATIC Text field to get user input for text
    private static TableModel tableValues = new TableModel();
    public static TableModel getICtable(){
        return tableValues;
    }

    @Override
    public String getName() {
        return name;
    }

    public InterfaceControl(Controller controller, Canvas canvas){
        this.canvas = canvas;
        this.controller = controller;
        this.setPreferredSize(size);

        Box verticalBoxMain = Box.createVerticalBox(); // Create main box that contains all horizontal boxes

        Box shapesHorizontalBox = Box.createHorizontalBox(); // Creates horizontal box that stores Rect, Oval, Line, Text buttons
        JLabel addShapesText = new JLabel(" Add Shapes: ");
        Font interfaceControlFont = new Font("Calibri", Font.ITALIC, 18);
        addShapesText.setFont(interfaceControlFont);
        JButton rect = new JButton("Rect"); // Create Rect button
        JButton oval = new JButton("Oval"); // Create Oval button
        JButton line = new JButton("Line"); // Create Line button
        JButton text = new JButton("Text"); // Create Text button
        shapesHorizontalBox.add(addShapesText);
        shapesHorizontalBox.add(rect); // Add Rect button to shapesHorizaontalBox
        shapesHorizontalBox.add(oval); // Add Oval button to shapesHorizaontalBox
        shapesHorizontalBox.add(line); // Add Line button to shapesHorizaontalBox
        shapesHorizontalBox.add(text); // Add Text button to shapesHorizaontalBox

        Box setColorHorizontalBox = Box.createHorizontalBox(); // Horizontal box that store Set Color button and button that displays selected color
        JLabel setShapesColor = new JLabel(" Set Shapes Color: ");
        setShapesColor.setFont(interfaceControlFont);

        JButton setColor = new JButton("Set Color"); // Creates set Color Buttonn
//        JButton colorButton = new JButton(""); // Create an empty button that serves as selected color display
//        Dimension colorButtonSize = new Dimension(1,1);
//        colorButton.setSize(colorButtonSize); // Size of the button
//        colorButton.setBackground(Color.GRAY); // Initially colorButton will display Gray color
//        colorButton.setOpaque(true);
//        colorButton.setBorderPainted(false);

        ActionListener actionListener = new ActionListener() { // Listener for colorButton
            public void actionPerformed(ActionEvent actionEvent) {
                Color initialBackground = setColor.getBackground();
                Color color = JColorChooser.showDialog(null, "JColorChooser", initialBackground);
                if(color != null){
                    canvas.getSelectedShape().getDShapeModel().setColor(color); // Set selectedColor object to selected color that the user chose
                    canvas.revalidate();
                    canvas.repaint();
                }
//                colorButton.setBackground(color);
//                colorButton.setOpaque(true);
//                colorButton.setBorderPainted(false);
            }
        };
        setColor.addActionListener(actionListener);
        setColorHorizontalBox.add(setShapesColor);
        setColorHorizontalBox.add(setColor); // Add setColor button to horizontal box setColorHorizontalBox
        //setColorHorizontalBox.add(colorButton); // Add setColor button to horizontal box setColorHorizontalBox

        Box textHBox = Box.createHorizontalBox(); // Horizontal box that contains Text Field and Font chooser
        textField = new JTextField("");
        Dimension size = new Dimension(1,1);
        textField.setPreferredSize(size);// this prevents resize issue
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Update the text of the chosen shape in real life
                canvas.updateText(textField.getText());
                canvas.revalidate();
                canvas.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Update the text of the chosen shape in real life
                canvas.updateText(textField.getText());
                canvas.revalidate();
                canvas.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not sure whats that for
            }
        });

        JLabel editText = new JLabel(" Edit text: ");
        editText.setFont(interfaceControlFont); //
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); // Gets all system fonts
        scriptChooser = new JComboBox(fonts);
        scriptChooser.addActionListener (new ActionListener () { // Listener for change in font selection
            public void actionPerformed(ActionEvent e) {
                String selectedValue = scriptChooser.getSelectedItem().toString(); // Contains selected font
                canvas.updateFont(selectedValue); // Sets font of the selected shape to new font
                canvas.revalidate();
                canvas.repaint();
            }
        });
        textHBox.add(editText); // Add prompt text to the textHBox
        textHBox.add(textField); // Adds textField object to horizontal box textHBox
        textHBox.add(scriptChooser); // Adds Font chooser object to horizontal box textHBox
        scriptChooser.setEnabled(false); // Initially  disable set text controls
        textField.setEnabled(false);

        // Horizontal box contains following buttons: Move to Front, Move to Back, Remove shape
        Box toolsHBox = Box.createHorizontalBox();
        JButton moveToFront = new JButton("Move to Front");
        JButton moveToBack = new JButton("Move to Back");
        JButton removeShape = new JButton("Remove Shape");
        toolsHBox.add(moveToFront); // Adds move to front button in the horizontal box toolsHBox
        toolsHBox.add(moveToBack); // Adds move to back button in the horizontal box toolsHBox
        toolsHBox.add(removeShape); // Adds remove shape button to horizontal box toolsHBox

        moveToFront.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveToFront(); // Works but needs more testing
            }
        });

        moveToBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveToBack();
            }
        });

        removeShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.deleteShape();
            }
        });

        JTable table = new JTable(tableValues); // Initialize table mode
        // Add all horizontal boxes in the main vertical box
        verticalBoxMain.add(shapesHorizontalBox);
        verticalBoxMain.add(setColorHorizontalBox);
        verticalBoxMain.add(textHBox);
        verticalBoxMain.add(toolsHBox);
        verticalBoxMain.add(table.getTableHeader());
        verticalBoxMain.add(table);

        // Align all components of the main box to the left
        for (Component comp : verticalBoxMain.getComponents()) {
            ((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }
        this.add(verticalBoxMain); // add the vertical box to self

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
                draw("text");
            }
        });
        // Listener for Line button
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                draw("line");
            }
        });

    }

    // Draw function that calls controller -> MVC patters
    public void draw(String type){
        if(type.equals("oval")){
            DShapeModel temp = new DOvalModel();
            controller.getdShapeModels().add(temp); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
        }
        else if(type.equals("rect")){
            DShapeModel temp = new DRectModel();
            controller.getdShapeModels().add(temp); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
        }
        else if(type.equals("text")){
            DShapeModel temp = new DTextModel();
            controller.getdShapeModels().add(temp); // Creates new DTextModel in the ArrayList of DShapeModel in Controller class
        }
        else if(type.equals("line")){
            DShapeModel temp = new DLineModel();
            controller.getdShapeModels().add(temp); // Creates new DLineModel in the ArrayList of DShapeModel in Controller class
        }

        int indexOfNewShape = controller.getdShapeModels().size()- 1; //index of new DShapeModel in arraylist of DShapeModel in Controller
        Canvas canvasReference = (Canvas)controller.getWhiteboard().getComponentAt("canvas");
        (canvasReference).addShape(controller.getdShapeModels().get(indexOfNewShape), type); // add the shape
        (canvasReference).repaint();
    }

    // Static method to enable/disable text controls. Called from canvas class
    public static void enableScriptChooserTextFields(Boolean bool){
        scriptChooser.setEnabled(bool);
        textField.setEnabled(bool);
    }

    // Static method that updates the content of JTextField and Font chooser from canvas class
    public static void updateScriptChooserTextField(String str, String font) {
        textField.setText(str); // set the text
        scriptChooser.getModel().setSelectedItem(font); // Scriptchooser
    }

}
