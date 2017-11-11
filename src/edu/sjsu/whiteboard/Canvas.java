package edu.sjsu.whiteboard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by danil on 11/7/17.
 */
public class Canvas extends JPanel {
    private Dimension size = new Dimension(400,400);

    public Canvas() {
        this.setBackground(Color.white);
        this.setPreferredSize(size);
       // this.setVisible(true);
    }
}