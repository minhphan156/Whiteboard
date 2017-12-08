package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.models.DLineModel;
import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.models.DTextModel;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

public class DText extends DShape {


    public DText(){

    }

    public void setText(String str){
        ((DTextModel)super.getDShapeModel()).setText(str);
    }
    public String getText(){
        return ((DTextModel)super.getDShapeModel()).getText();

    }
    public String getFontName(){
        return ((DTextModel)super.getDShapeModel()).getFontNameModel();
    }

    public void setFontName(String fontName){
        ((DTextModel)super.getDShapeModel()).setFontNameModel(fontName);
    }

    public DShapeModel getPointerToDShapeModel() {
        return super.getDShapeModel();// call to get dShapeModel in parent class
    }
    @Override
    public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
        if(pointerToDShapeModel instanceof DTextModel)
        {
            super.setPointerToDShapeModel((DTextModel)pointerToDShapeModel); // call to set dShapeModel in parent class
        }
    }
    @Override
    public void modelChanged(DShapeModel pointerToDShapeModel) {
        getCanvasReferencel().repaint();
    }

    // called to draw Rectangle
    public void draw(Graphics g)
    {

        Graphics2D g2 = (Graphics2D) g;
        /* Clip is to not allow the text be drawn outside the bounds */

        g2.setFont(computeFont());
        g2.setColor(super.getDShapeModel().getColor());
        FontMetrics fm = g2.getFontMetrics(g2.getFont());
        Rectangle2D rect = fm.getStringBounds(((DTextModel)super.getDShapeModel()).getText(), g2);
        int textHeight = (int)(rect.getHeight());
        int textX = super.getDShapeModel().getX();
        int textY = super.getDShapeModel().getY() + (super.getDShapeModel().getHeight() - textHeight)/2 + fm.getAscent();
        Shape clip = g2.getClip(); // Get the current clip
        g2.setClip(clip.getBounds().createIntersection(super.getDShapeModel().getBounds())); // Intersect the clip with the text shape bounds i.e. we won't lay down any pixels that fall outside our bounds
        g2.drawString(((DTextModel)super.getDShapeModel()).getText(),textX,textY);
        g2.setClip(clip); // Restore the old clip
        if(super.getIsSelected())
		{
			super.drawKnobs(g2);
		}

    }

    public Font computeFont(){
        int height = super.getDShapeModel().getHeight();

        FontMetrics fm = new FontMetrics(((DTextModel)super.getDShapeModel()).getFont()) {
            @Override
            public int getHeight() {
                return super.getHeight();
            }
        };

        int sizeInt = fm.getHeight();
        double size = (double) sizeInt;

        while (size < height){
            size = (size * 1.10)+1; // Formula to computer font size: should be less than height of DModelShape
        }

        int finalSize = (int)(size/1.5); // Convert double to float and devide by 1.5 -> this way letters y and p will fit
        Font finalFont = new Font(((DTextModel)super.getDShapeModel()).getFontNameModel(),Font.LAYOUT_LEFT_TO_RIGHT,finalSize);

        return finalFont;
    }

}
