package edu.sjsu.whiteboard.models;

import java.awt.*;

/**
 * Created by danil on 11/11/17.
 */
public class DTextModel extends DShapeModel {

    private String text = "Hello"; // Initially
    private String fontNameModel = "Calibri";
    private Font font = new Font(fontNameModel,Font.PLAIN,12);



    public String getFontNameModel() {
        return fontNameModel;
    }

    public void setFontNameModel(String fontName) {
        this.fontNameModel = fontName;
    }


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
