package edu.sjsu.whiteboard.models;


import edu.sjsu.whiteboard.InterfaceControl;

import java.awt.*;
import java.awt.geom.*;

public abstract class DShapeModel {
	private int x;
	private int y;
	private int width;
	private int height;
	private Rectangle2D bounds;
	private Color color;

	
	public DShapeModel()
	{
		x = 111; // set initial size for the shape
		y = 111;// set initial size for the shape
		width = 200;// set initial size for the shape
		height = 200;// set initial size for the shape

		bounds = new Rectangle2D.Double(x, y, width, height);
        setColor();
    }
	
	public void setBounds(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle2D.Double(x,y,width,height);
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setColor()
	{
		this.color = InterfaceControl.getSelectedColor();
	}
	
	public Color getColor()
	{
		return color;
	}

	public String toString(){
		return  x + "x--" + y + "y--" + width + "width--" + height + "height";
	}
}
