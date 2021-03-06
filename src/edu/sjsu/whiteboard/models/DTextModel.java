package edu.sjsu.whiteboard.models;

import edu.sjsu.whiteboard.ModelListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
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
    private Font font = new Font(fontNameModel,Font.PLAIN,10);

    public DTextModel(){
        setBounds(10,10,50,20);
    }


    public String getFontNameModel() {
        return fontNameModel;
    }

    public void setFontNameModel(String fontName) {
        this.fontNameModel = fontName;
        Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
        Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }
    }

    @Override
    public void mimic (DShapeModel other) {
        this.setBounds(other.getX(),other.getY(),other.getWidth(),other.getHeight());
        this.setText(((DTextModel)other).getText());
        this.setFontNameModel(((DTextModel)other).getFontNameModel());
        this.setFont(((DTextModel)other).getFont());
        this.setColor(((DTextModel)other).getColor());
        this.setHeight(((DTextModel)other).getHeight());
        this.setWidth(((DTextModel)other).getWidth());
    }
}
