package edu.sjsu.whiteboard;

import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import java.awt.*;

public class Whiteboard extends JFrame {
    private String name = "Whiteboard";
    public Whiteboard(String name){
        super("Whiteboard");
        this.setLayout(new BorderLayout());
        this.add(new Canvas(), BorderLayout.CENTER);
        this.add(new InterfaceControl(), BorderLayout.WEST);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        Whiteboard board = new Whiteboard("Whiteboard");
	// write your code here
    }
}
