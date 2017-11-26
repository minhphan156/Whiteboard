package edu.sjsu.whiteboard.models;

import java.awt.*;

/**
 * Created by danil on 11/11/17.
 */
public class DLineModel extends DShapeModel {


    Point p1 = new Point(10,10);
    Point p2 = new Point(30,30);


    public DLineModel(){
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

}
