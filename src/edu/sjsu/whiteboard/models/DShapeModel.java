package edu.sjsu.whiteboard.models;

import edu.sjsu.whiteboard.ModelListener;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

public abstract class DShapeModel {
	private static int countOfObject = 0;
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle2D bounds;
	private Color color;
    private ArrayList<ModelListener> listOfListeners = new ArrayList<>();

    public DShapeModel()

	{
		System.out.println("we creating objects !!!!!!!!!!!!");
		countOfObject++;
		id = 0;
		x = 10;
		y = 10;
		width = 20;// set initial size for the shape
		height = 20;// set initial size for the shape

		bounds = new Rectangle2D.Double(x, y, width, height);
        color = Color.GRAY;
    }

	public static int getCountOfObject() {
		return countOfObject;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setBounds(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle2D.Double(x,y,width,height);
		
		Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
            }
	}

    public ArrayList<ModelListener> getListOfListeners() {
        return listOfListeners;
    }

    public void setListOfListeners(ModelListener listener) {
        listOfListeners.add(listener);

    }
	public Rectangle2D getBounds()
	{
		return bounds;
	}

	public int getX() {
		return x;
	}

	public void setX(int x)
	{
		setBounds(x,y, width,height);
		this.x = x;
		Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y)
	{
		setBounds(x,y, width,height);
		this.y = y;
		Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }

	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

    public void setColor(Color color)
    {
        this.color = color;
        Iterator<ModelListener> itr = listOfListeners.iterator();
        while (itr.hasNext()){
            itr.next().modelChanged(this);
        }
    }
	
	public Color getColor()
	{
		return color;
	}

	public String toString(){
		return  x + "x--" + y + "y--" + width + "width--" + height + "height--"+ id + "id--";
	}

    public boolean containsInBound(int x, int y)
    {
    	//Because we update bounds whenever we update x, y, point1, point2, 
    	//just check parameters of the bounds, which is a Rectangle2D object.    	
        if((x >= bounds.getX() && x <= (bounds.getX() + bounds.getWidth())) && (y >= bounds.getY() && y <= (bounds.getY() + bounds.getHeight())))
        {
            return true;
        }
        else{
            return false;
        }
    }

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	public void mimic (DShapeModel other){
		this.setBounds(other.getX(),other.getY(),other.getWidth(),other.getHeight());
		this.setX(other.getX());
		this.setY(other.getY());
		this.setHeight(other.getHeight());
		this.setWidth(other.getWidth());
		this.setColor(other.getColor());
	}

}
