package edu.sjsu.whiteboard.models;

import edu.sjsu.whiteboard.ModelListener;
import edu.sjsu.whiteboard.shapes.DText;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by danil on 11/11/17.
 */
public class DTextModel extends DShapeModel {
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle2D bounds;
    private ArrayList<ModelListener> listOfListeners = new ArrayList<>();

    private String text = "Hello"; // Initially
    private String fontNameModel = "Dialog"; // Initially
    private Font font = new Font(fontNameModel,Font.PLAIN,1);

    public DTextModel(){
        setBounds(10,10,60,20);
    }


    public String getFontNameModel() {
        return fontNameModel;
    }

    public void setFontNameModel(String fontName) {
        this.fontNameModel = fontName;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

}
