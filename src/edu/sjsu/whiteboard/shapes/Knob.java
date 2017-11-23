package edu.sjsu.whiteboard.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Knob {
	private Point centralPoint;
	private final int KNOB_SIDE_LENGTH = 9;
	private int x;
	private int y;
	private int sideLength;

	public Knob() {

	}

	//each knob is based on the central point, which is the corner point of the bound of a shape
	public Knob(Point centralPoint) {
		this.centralPoint = centralPoint;
		x = centralPoint.x - KNOB_SIDE_LENGTH / 2;
		y = centralPoint.y - KNOB_SIDE_LENGTH / 2;
		this.sideLength = KNOB_SIDE_LENGTH;
	}

	public boolean containsInKnob(Point p) {
		if ((p.x >= this.x && p.x <= (this.x + sideLength)) && (p.y >= this.y && p.y <= (this.y + sideLength))) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getSideLength() {
		return sideLength;
	}
	
	public Point getCentralPoint()
	{
		return centralPoint;
	}

	public void setCentralPoint(Point centraPoint)
	{
		this.centralPoint = centralPoint;
	}
	
	void drawKnob(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(x, y, sideLength, sideLength);
	}

}
