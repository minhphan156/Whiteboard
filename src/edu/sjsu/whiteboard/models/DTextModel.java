package edu.sjsu.whiteboard.models;

import java.awt.*;

/**
 * Created by danil on 11/11/17.
 */
public class DTextModel extends DShapeModel {

    private String text = "Hello";
    private Font font = new Font(Font.DIALOG, Font.PLAIN, 12); // First time

    public DTextModel(){
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
