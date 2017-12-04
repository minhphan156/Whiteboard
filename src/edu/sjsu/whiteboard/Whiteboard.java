package edu.sjsu.whiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */


/**
 * Whiteboard class is a container class for Canvas and Interface Control. It also contain the menu bar.
 */

public class Whiteboard extends JFrame {

    private Controller controller;
    private Canvas canvas;

    public Whiteboard(Controller controller){
        super("Whiteboard");
        canvas =  new Canvas(controller);
        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.add(new InterfaceControl(controller,canvas), BorderLayout.WEST);
        //this.add(menubar,BorderLayout.NORTH);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    // return either Canvas or InterfaceControl inside whiteboard
    public JPanel getComponentAt(String name){
        Component comp = null;
        for (Component child : this.getContentPane().getComponents()) {
            if(child.getName()==null){
                System.out.println("\nWeird error in getComponent() in Whiteboard.java");
            }
            else if (child.getName().equals(name)) {
                comp = child;
            }
        }
        return (JPanel)comp;
    }

}
