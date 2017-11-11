package edu.sjsu.whiteboard.models;

import javax.swing.*;
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
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
}
