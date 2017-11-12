package edu.sjsu.whiteboard;

import javax.swing.*;
import java.awt.*;

public class Whiteboard extends JFrame {
    private Controller controller;


    public Whiteboard(Controller controller){
        super("Whiteboard");
        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.add(new Canvas(controller), BorderLayout.CENTER);
        this.add(new InterfaceControl(controller), BorderLayout.WEST);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // return either Canvas or InterfaceControl inside whiteboard
    public JPanel getComponentAt( String name) {
        Component comp = null;
        for (Component child : this.getContentPane().getComponents()) {
            if (child.getName().equals(name)) {
                comp = child;
            }
        }
        return (JPanel)comp;
    }
}
