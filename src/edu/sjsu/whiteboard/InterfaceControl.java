package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.*;
import edu.sjsu.whiteboard.shapes.DLine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private Dimension size = new Dimension(520,400);
    private Canvas canvas; // pointer
    private static JComboBox scriptChooser; // STATIC Combo Box filled with String array of system fonts
    private static JTextField textField; // STATIC Text field to get user input for text
    private static TableModel tableValues;

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

        tableValues = new TableModel(canvas.getShapeList());
        this.setPreferredSize(size);

        //******************************************
        //*** Create Boxes for all Buttons ************
        //******************************************

        // Shapes Box
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

        // Text Box
        Box textHorizontalBox = Box.createHorizontalBox(); // Horizontal box that contains Text Field and Font chooser
        textField = new JTextField("");
        Dimension size = new Dimension(1,1);
        textField.setPreferredSize(size);// this prevents resize issue
        JLabel editText = new JLabel(" Edit text: ");
        editText.setFont(interfaceControlFont); //
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); // Gets all system fonts
        scriptChooser = new JComboBox(fonts);
        textHorizontalBox.add(editText); // Add prompt text to the textHorizontalBox
        textHorizontalBox.add(textField); // Adds textField object to horizontal box textHorizontalBox
        textHorizontalBox.add(scriptChooser); // Adds Font chooser object to horizontal box textHorizontalBox
        scriptChooser.setEnabled(false); // Initially  disable set text controls
        textField.setEnabled(false);

        // Set Color Box
        Box setColorHorizontalBox = Box.createHorizontalBox(); // Horizontal box that store Set Color button and button that displays selected color
        JLabel setShapesColor = new JLabel(" Set Shapes Color: ");
        setShapesColor.setFont(interfaceControlFont);
        JButton setColor = new JButton("Set Color"); // Creates set Color Buttonn
        setColorHorizontalBox.add(setShapesColor);
        setColorHorizontalBox.add(setColor); // Add setColor button to horizontal box setColorHorizontalBox

        // Edit Shapes Box
        Box editShapeHorizontalBox = Box.createHorizontalBox(); // Creates horizontal box that stores Rect, Oval, Line, Text buttons
        JLabel editShapeText = new JLabel(" Edit Shapes: ");
        editShapeText.setFont(interfaceControlFont);
        JButton sendToFront = new JButton("Send To Front"); // Create Start Server button
        JButton sendToBack = new JButton("Send To Back"); // Create Start Client button
        JButton removeThisShape = new JButton("Remove This Shape"); // Create Start Client button
        editShapeHorizontalBox.add(editShapeText);
        editShapeHorizontalBox.add(sendToFront);
        editShapeHorizontalBox.add(sendToBack);
        editShapeHorizontalBox.add(removeThisShape);

        // Save Content Box
        Box saveContentHorizontalBox = Box.createHorizontalBox(); // Creates horizontal box that stores Rect, Oval, Line, Text buttons
        JLabel saveContentText = new JLabel(" Edit/Save Content: ");
        saveContentText.setFont(interfaceControlFont);

        JButton save = new JButton("Save"); // Create Start Server button
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String result = JOptionPane.showInputDialog("Save File With The Name", "CanvasXML");
                String finalResult = result+".xml";
                File f = new File(finalResult);
                controller.save(f);

            }
        });

        JButton load = new JButton("Load"); // Create Start Client button
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String result = JOptionPane.showInputDialog("Open File With The Name: ", null);
                String finalResult = result+".xml";
                if (result != null) {
                    File f = new File(finalResult);
                    controller.open(f);
                }
                else{
                    JOptionPane.showMessageDialog(load,"Enter a file name!");
                }
            }
        });

        JButton saveAsPNG = new JButton("Save as PNG"); // Create Start Client button
        saveAsPNG.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String userInput = JOptionPane.showInputDialog("File Name", "Canvas");
                String finalName = userInput+".png";
                File f = new File(finalName);
                canvas.saveImage(f);
            }
        });
        // Add to Save/Open control box
        saveContentHorizontalBox.add(saveContentText);
        saveContentHorizontalBox.add(save);
        saveContentHorizontalBox.add(load);
        saveContentHorizontalBox.add(saveAsPNG);

        // NetWorking Box
        Box netWorkingHorizontalBox = Box.createHorizontalBox(); // Creates horizontal box that stores Rect, Oval, Line, Text buttons
        JLabel netWorkingText = new JLabel(" Networking: ");
        JLabel whatModeIsOn = new JLabel("");
        JButton startServer = new JButton("Start Server"); // Create Start Server button
        JButton startClient = new JButton("Start Client"); // Create Start Client button
        netWorkingHorizontalBox.add(netWorkingText);
        netWorkingHorizontalBox.add(startServer);
        netWorkingHorizontalBox.add(startClient);
        netWorkingHorizontalBox.add(whatModeIsOn);

        // Table Box
        JTable table = new JTable(tableValues); // Initialize table mode
        JScrollPane pane = new JScrollPane(table); // Make table scrollable
        add(pane, BorderLayout.CENTER);

        // Add all horizontal boxes in the main vertical box
        Box verticalBoxMain = Box.createVerticalBox(); // Create main box that contains all horizontal boxes

        verticalBoxMain.add(shapesHorizontalBox);
        verticalBoxMain.add(textHorizontalBox);
        verticalBoxMain.add(setColorHorizontalBox);
        verticalBoxMain.add(editShapeHorizontalBox);
        verticalBoxMain.add(saveContentHorizontalBox);
        verticalBoxMain.add(netWorkingHorizontalBox);
        verticalBoxMain.add(table.getTableHeader());
        verticalBoxMain.add(table);

        // Align all components of the main box to the left
        for (Component comp : verticalBoxMain.getComponents()) {
            ((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }
        this.add(verticalBoxMain); // add the vertical box to self


        //******************************************
        //*** Listeners for all Buttons ************
        //******************************************

        //listener for set color
        setColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color initialBackground = setColor.getBackground();
                Color color = JColorChooser.showDialog(null, "JColorChooser", initialBackground);
                if(color != null){
                    canvas.getSelectedShape().getDShapeModel().setColor(color); // Set selectedColor object to selected color that the user chose
                    controller.sendRemote("change",canvas.getSelectedShape().getDShapeModel()); //let client know to create shape
                    canvas.revalidate();
                    canvas.repaint();
                }
            }
        });

        // listener for script chooser
        scriptChooser.addActionListener (new ActionListener () { // Listener for change in font selection
            public void actionPerformed(ActionEvent e) {
                String selectedValue = scriptChooser.getSelectedItem().toString(); // Contains selected font
                canvas.updateFont(selectedValue); // Sets font of the selected shape to new font
                canvas.revalidate();
                canvas.repaint();
            }
        });

        //listener for text field
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Update the text of the chosen shape in real life
                scriptChooser.setEnabled(true); // Initially  disable set text controls
                textField.setEnabled(true);
                canvas.updateText(textField.getText());
                canvas.revalidate();
                canvas.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Update the text of the chosen shape in real life
                scriptChooser.setEnabled(true); // Initially  disable set text controls
                textField.setEnabled(true);
                canvas.updateText(textField.getText());
                canvas.revalidate();
                canvas.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not sure whats that for
            }
        });

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

        //listener for send to front
        sendToFront.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveToFront();
            }
        });

        // listener for send to back
        sendToBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveToBack();
            }
        });

        //listener for remove this shape
        removeThisShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                canvas.deleteShape();
            }
        });

        //listener for Start Server Button
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("start server here");
                whatModeIsOn.setText("Server mode, port 39587");
                controller.doServer();
                // Disable buttons after entering server mode
                startClient.setEnabled(false);
                startServer.setEnabled(false);
                controller.getWhiteboard().disableNetworking();
            }
        });

        //listener for Start Client Button
        startClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whatModeIsOn.setText("Client mode, port 39587");
                // disable all buttons in Client Mode
                rect.setEnabled(false);
                oval.setEnabled(false);
                line.setEnabled(false);
                text.setEnabled(false);
                textField.setEnabled(false);
                scriptChooser.setEnabled(false);
                setColor.setEnabled(false);
                sendToFront.setEnabled(false);
                sendToBack.setEnabled(false);
                removeThisShape.setEnabled(false);
                save.setEnabled(false);
                load.setEnabled(false);
                saveAsPNG.setEnabled(false);
                startServer.setEnabled(false);
                startClient.setEnabled(false);
                table.setEnabled(false);
                controller.getWhiteboard().disableNetworking();
                //set up client
                controller.doClient();
            }
        });

    }


    // Draw function that calls controller -> MVC patters
    public void draw(String type){
        if(type.equals("oval")){
            DShapeModel temp = new DOvalModel();
            temp.setId(DShapeModel.getCountOfObject());
            controller.getdShapeModels().add(temp); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
            controller.sendRemote("add",temp); //let client know to create shape
        }
        else if(type.equals("rect")){
            DShapeModel temp = new DRectModel();
            temp.setId(DShapeModel.getCountOfObject());
            controller.getdShapeModels().add(temp); // Creates new DOvalModel in the ArrayList of DShapeModel in Controller class
            controller.sendRemote("add",temp);//let client know to create shape
        }
        else if(type.equals("text")){
            DShapeModel temp = new DTextModel();
            temp.setId(DShapeModel.getCountOfObject());
            controller.getdShapeModels().add(temp); // Creates new DTextModel in the ArrayList of DShapeModel in Controller class
            controller.sendRemote("add",temp);//let client know to create shape
        }
        else if(type.equals("line")){
            DShapeModel temp = new DLineModel();
            temp.setId(DShapeModel.getCountOfObject());
            controller.getdShapeModels().add(temp); // Creates new DLineModel in the ArrayList of DShapeModel in Controller class
            controller.sendRemote("add",temp);//let client know to create shape
        }

        int indexOfNewShape = controller.getdShapeModels().size()- 1; //index of new DShapeModel in arraylist of DShapeModel in Controller
        Canvas canvasReference = (Canvas)controller.getWhiteboard().getComponentAt("canvas");
        (canvasReference).addShape(controller.getdShapeModels().get(indexOfNewShape), type); // add the shape
        (canvasReference).repaint();
    }


    // Static method to enable/disable text controls. Called from canvas class
    public static void enableScriptChooserTextFields(Boolean bool){
        //if(this.controller.getClass().getSimpleName().equals())
        scriptChooser.setEnabled(bool);
        textField.setEnabled(bool);
    }

    // Static method that updates the content of JTextField and Font chooser from canvas class
    public static void updateScriptChooserTextField(String str, String font) {
        textField.setText(str); // set the text
        scriptChooser.getModel().setSelectedItem(font); // Scriptchooser
    }

}
