package edu.sjsu.whiteboard.shapes;

import edu.sjsu.whiteboard.models.DShapeModel;

import java.awt.*;

public abstract class DShape {
	private DShapeModel shapeModel;
	
	public abstract void draw(Graphics g);
}
