package edu.sjsu.whiteboard.shapes;



import edu.sjsu.whiteboard.*;
import edu.sjsu.whiteboard.Canvas;
import edu.sjsu.whiteboard.models.DShapeModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

public abstract class DShape implements ModelListener {
	private DShapeModel pointerToDShapeModel;// hold the reference to DRectModel to get data for painting
	private Canvas canvasReferencel; //hold the canvas reference to call paintComponent() later
	private final int KNOB_SIDE_LENGTH = 9;
	 ArrayList<Point> knobPoints;
	 ArrayList<Knob> knobs;
	private Knob clickedKnob;
	private int clickedKnobIndex;
	private Knob anchorKnob;
	private int anchorKnobIndex;
	private boolean isSelected = false;
	
	public DShape()
	{
		knobPoints = new ArrayList<>();
		knobs = new ArrayList<>();
		
	}
	
	public abstract void draw(Graphics g);

	public Canvas getCanvasReferencel() {
		return canvasReferencel;
	}

	public void setCanvasReferencel(Canvas canvasReferencel) {
		this.canvasReferencel = canvasReferencel;
	}

	public void setDShapeModel(DShapeModel pointerToDShapeModel)
	{
		this.pointerToDShapeModel = pointerToDShapeModel;
	}

	public void setPointerToDShapeModel(DShapeModel pointerToDShapeModel) {
		this.pointerToDShapeModel = pointerToDShapeModel;
	}

	public DShapeModel getDShapeModel()
	{
		return pointerToDShapeModel;
	}
	
	

	//getKnobs() returns an ArrayList of four corner points got from DShapeModel
	public void setKnobs()
	{
		//each time model changes, need a new ArrayList...
		//otherwise, it will keep adding points...
		knobPoints = new ArrayList<>();
		knobs =  new ArrayList<>();
		//System.out.println("the size of knobPoints is " + knobPoints.size());
		int dModelX = getDShapeModel().getX();
		int dModelY = getDShapeModel().getY();
		int dModelWidth = getDShapeModel().getWidth();
		int dModelHeight = getDShapeModel().getHeight();
		Point upLeft = new Point(dModelX, dModelY);
		knobPoints.add(upLeft);
		knobs.add(new Knob(upLeft));
		Point upRight = new Point(dModelX + dModelWidth, dModelY);
		knobPoints.add(upRight);
		knobs.add(new Knob(upRight));
		Point downRight = new Point(dModelX + dModelWidth, dModelY + dModelHeight);
		knobPoints.add(downRight);
		knobs.add(new Knob(downRight));
		Point downLeft = new Point(dModelX, dModelY + dModelHeight);
		knobPoints.add(downLeft);
		knobs.add(new Knob(downLeft));
	}
	
	public ArrayList<Point> getKnobs()
	{
		//get central points of knobs
		return knobPoints;
	}

	public void showKnobs(){
		//drawKnobs();
	}

	public void hideKnobs(){
		knobPoints.clear();
	}
	
	public int getKnobSideLength()
	{
		return KNOB_SIDE_LENGTH;
	}
	
	public void drawKnobs(Graphics g)
	{
		//draw knobs of one shape
		setKnobs();
		//System.out.println("draw knobs now");
		for(int i = 0; i < knobs.size(); i++)
		{
			//System.out.println("draw a knob now");
			knobs.get(i).drawKnob(g); //let each knob draw itself
//			int knobUpLeftX = knobPoints.get(i).x - KNOB_SIDE_LENGTH / 2;
//			int knobUpLeftY = knobPoints.get(i).y - KNOB_SIDE_LENGTH / 2;
//			Graphics2D g2 = (Graphics2D) g;
//			g2.setColor(Color.BLACK);
//			g2.fillRect(knobUpLeftX, knobUpLeftY, KNOB_SIDE_LENGTH, KNOB_SIDE_LENGTH);
		}
	}
	
	public boolean isInKnobs(Point clickedPoint)
	{
		for(int i = 0; i < knobs.size() ; i++)
		{
			Knob currKnob = knobs.get(i);
			if(currKnob.containsInKnob(clickedPoint))
			{
				clickedKnobIndex = i;
				clickedKnob = currKnob;
				anchorKnobIndex = (i + knobs.size() / 2) % (knobs.size());
				anchorKnob = knobs.get(anchorKnobIndex);
				return true;
			}
		}
		return false;
	} 
	
	public int[] resize(int dx, int dy) {
		int [] newResizedShapeInfo = new int[4]; //return x, y, width, height
		
		//newClickedPoint is the new position of the clicked knob
		Point newClickedPoint = new Point (clickedKnob.getCentralPoint().x+dx,clickedKnob.getCentralPoint().y + dy);
		clickedKnob = new Knob(newClickedPoint);
		
		if(!(this instanceof DLine))
		{
			int newWidth = Math.abs( newClickedPoint.x - anchorKnob.getCentralPoint().x);
			int newHeight = Math.abs( newClickedPoint.y - anchorKnob.getCentralPoint().y);
			int newX;
			int newY;
			
			//compare newClickedPoint and the position of central point of the anchorKnob(which doesn't change)
			//the up left corner of new bound has the smallerX of these two points and the smallerY of these two points.
			if(newClickedPoint.x < anchorKnob.getCentralPoint().x) {
				newX = newClickedPoint.x;
			}
			else {
				newX = anchorKnob.getCentralPoint().x;
			}
			
			if(newClickedPoint.y < anchorKnob.getCentralPoint().y) {
				newY = newClickedPoint.y;
			}
			else {
				newY = anchorKnob.getCentralPoint().y;
			}
			newResizedShapeInfo[2] = newWidth;
			newResizedShapeInfo[3] = newHeight;
			newResizedShapeInfo[0] = newX;
			newResizedShapeInfo[1] = newY;
	
			return newResizedShapeInfo;
		}
		else
		{
			//Different content in the returning array for DLine
			//1st point: anchorKnob.centralPoint
			//2nd point: clickedKnob.centralPoint
			//the order of points doesn't matter
			newResizedShapeInfo[0] = anchorKnob.getCentralPoint().x;
			newResizedShapeInfo[1] = anchorKnob.getCentralPoint().y;;
			newResizedShapeInfo[2] = clickedKnob.getCentralPoint().x;
			newResizedShapeInfo[3] = clickedKnob.getCentralPoint().y;
			return newResizedShapeInfo;
		}
	}
	
	public void setIsSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}
	
	public boolean getIsSelected()
	{
		return isSelected;
	}
//	public Knob getClickedKnob()
//	{
//		return clickedKnob;
//	}
//	
//	public Knob getAnchorKnob()
//	{
//		return anchorKnob;
//	}
//	public Rectangle2D.Double getBounds()
//	{
//		return pointerToDShapeModel.getBounds();
//	}
}
