package edu.sjsu.whiteboard.models;

import java.awt.*;
import java.util.Iterator;

import edu.sjsu.whiteboard.ModelListener;

/**
 * Created by danil on 11/11/17.
 */
public class DLineModel extends DShapeModel {
	private Point p1;
	private Point p2;

 
    public DLineModel(){
    	p1 = new Point(10,10);
    	p2 = new Point(50,50);
    	//setBounds after setting two points
    	computeBounds();
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
        //setBounds after setting two points
        computeBounds();
        Iterator<ModelListener> itr = (super.getListOfListeners()).iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);      
        }        
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
        //setBounds after setting two points
        computeBounds();
        Iterator<ModelListener> itr = (super.getListOfListeners()).iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }
    }
    
    public void computeBounds()
    {
    	//compute bounds of a line, still a rectangle, when DShapeModel checks containsInBound,
    	//checking with four parameters of the bound.
    	int minX;
        int minY;
        if((this.p1).x < (this.p2).x)
        {
        	minX = (this.p1).x;
        }
        else
        {
        	minX = (this.p2).x;
        }
        
        if((this.p1).y < (this.p2).y)
        {
        	minY = (this.p1).y;
        }
        else
        {
        	minY = (this.p2).y;
        }
        super.setBounds(minX, minY, Math.abs(this.p1.x - this.p2.x), Math.abs(this.p1.y - this.p2.y));
    }

}
