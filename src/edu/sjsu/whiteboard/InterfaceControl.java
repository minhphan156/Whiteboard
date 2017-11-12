package edu.sjsu.whiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox scriptChooser = new JComboBox(fonts);
        scriptChooser.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String selectedValue = scriptChooser.getSelectedItem().toString();
                System.out.print("\nUser selected: "+selectedValue);

            }
        });
        JButton setColor = new JButton("Set Color");
        JButton colorButton = new JButton("");
        colorButton.setBackground(Color.GRAY);
        colorButton.setOpaque(true);
        colorButton.setBorderPainted(false);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Color initialBackground = setColor.getBackground();
                Color color = JColorChooser.showDialog(null, "JColorChooser", initialBackground);
                colorButton.setBackground(color);
                colorButton.setOpaque(true);
                colorButton.setBorderPainted(false);

            }
        };
        setColor.addActionListener(actionListener);
        JTextField textField = new JTextField("");
        setColorHorizontalBox.add(textField);
        setColorHorizontalBox.add(scriptChooser);
        setColorHorizontalBox.add(setColor);
        setColorHorizontalBox.add(colorButton);

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
        Box tableHBox = Box.createHorizontalBox();
        JTable table = new JTable(data, columns);
        tableHBox.add(table);
        JScrollPane sp = new JScrollPane(table);

        verticalBoxMain.add(shapesHorizontalBox);
        verticalBoxMain.add(setColorHorizontalBox);
        verticalBoxMain.add(toolsHBox);
        verticalBoxMain.add(tableHBox);
        for (Component comp : verticalBoxMain.getComponents()) { ((JComponent)comp).setAlignmentX(Box.CENTER_ALIGNMENT);
        }
        this.add(verticalBoxMain);
        this.add(sp);

    }

}
