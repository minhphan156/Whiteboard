package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.InterfaceControl;
import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.models.DTextModel;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class DText extends DShape {

    DTextModel textModel = new DTextModel();

    public DText(){

    }

    public void setText(String str){
        textModel.setText(str);
    }

    public void setFontName(String fontName){
        textModel.setFontNameModel(fontName);
    }



    public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
        super.setPointerToDShapeModel(pointerToDShapeModel); // call to set dShapeModel in parent class
    }

    public DShapeModel getPointerToDShapeModel() {
        return super.getDShapeModel();// call to get dShapeModel in parent class
    }

    @Override
    public void modelChanged(DShapeModel pointerToDShapeModel) {
        System.out.println("\nRedraw text component");
        getCanvasReferencel().repaint();
    }

    // called to draw Rectangle
    public void draw(Graphics g)
    {

        Graphics2D g2 = (Graphics2D) g;
        /* Clip is to not allow the text be drawn outside the bounds */
        Shape clip = g2.getClip(); // Get the current clip

        g2.setFont(computeFont());
        g2.setColor(Color.GRAY);
        g2.setClip(clip.getBounds().createIntersection(super.getDShapeModel().getBounds())); // Intersect the clip with the text shape bounds i.e. we won't lay down any pixels that fall outside our bounds
        g2.drawString(textModel.getText(),super.getDShapeModel().getX(),super.getDShapeModel().getY()+super.getDShapeModel().getHeight()-10);
        g2.setClip(clip); // Restore the old clip
        super.drawKnobs(g2);

    }

    public Font computeFont(){
        int height = (super.getDShapeModel().getHeight());

        FontMetrics fm = new FontMetrics(textModel.getFont()) {
            @Override
            public int getHeight() {
                return super.getHeight();
            }
        };

        int sizeInt = fm.getHeight();
        double size = (double) sizeInt;

        do {
            size = (size * 1.10)+1; // Formula to computer font size: should be less than height of DModelShape
        } while (size < height);


        int finalSize = (int)size; // Convert double to float
        Font finalFont = new Font(textModel.getFontNameModel(),Font.PLAIN,finalSize);


        System.out.print("\nFont size computed: "+finalFont.getSize());
        System.out.print("\nFont name of finalFont: "+finalFont.getName());

        return finalFont;
    }

}
