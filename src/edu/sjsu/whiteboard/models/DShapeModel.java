package edu.sjsu.whiteboard.models;


import edu.sjsu.whiteboard.InterfaceControl;
import edu.sjsu.whiteboard.ModelListener;
import edu.sjsu.whiteboard.TableModel;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class DShapeModel {
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle2D bounds;
	private Color color;
    private ArrayList<ModelListener> listOfListeners = new ArrayList<>();

    public DShapeModel()
	{

		x = 10;
		y = 10;
//		x = (int)(Math.random() * 400); // set initial size for the shape
//		y = (int)(Math.random() * 400);// set initial size for the shape
		width = 20;// set initial size for the shape
		height = 20;// set initial size for the shape

		bounds = new Rectangle2D.Double(x, y, width, height);
        color = Color.GRAY;
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
		return  x + "x--" + y + "y--" + width + "width--" + height + "height";
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

}
