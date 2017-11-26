package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.models.DTextModel;
import edu.sjsu.whiteboard.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by danil on 11/7/17.
 */
public class Canvas extends JPanel  {
	private String name = "canvas";
	private Controller controller;
	private ArrayList<DShape> shapeList; // store current shapes
	private Dimension size = new Dimension(700, 600);
	private DShape selectedShape;
	private int indexOfSelected;
	private boolean foundSelected;
	private Point mousePt;
	private boolean isResizing;

	private boolean isSelectedShapeText;
	public void setIsSelectedShapeText(boolean isSelectedShapeText) {
		this.isSelectedShapeText = isSelectedShapeText;
		InterfaceControl.updateScriptChooser(isSelectedShapeText); // STATIC
	}

	public Canvas(Controller controller) {
		this.controller = controller;
		shapeList = new ArrayList<>();
		this.setBackground(Color.WHITE);
		this.setPreferredSize(size);
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent event) {
				isResizing = false;
				// if don't set isResizing to false, it will affect all other behaviors, like
				// dragging and selecting a shape

				Object source = event.getSource();
				int x = event.getX();
				int y = event.getY();
				System.out.printf("\n(%d, %d)", x, y);
				setSelectedShape(x, y);
				System.out.print("new Selected shape at index: " + indexOfSelected);
			}

			public void mousePressed(MouseEvent event) {
				mousePt = event.getPoint();
				System.out.printf("\n(%d, %d)", mousePt.x, mousePt.y);
				if (selectedShape.isInKnobs(mousePt)) {
					// in knobs... so resize;

					isResizing = true;
				}
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {

			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				int dx = event.getX() - mousePt.x;
				int dy = event.getY() - mousePt.y;

				if (!isResizing) {
					System.out.println("dragging now ");
					// not resizing, so moving the shape
					selectedShape.getDShapeModel().setX(selectedShape.getDShapeModel().getX() + dx);
					selectedShape.getDShapeModel().setY(selectedShape.getDShapeModel().getY() + dy);
					mousePt = event.getPoint();

				} else {
					System.out.println("resizing now ");
					int[] newShapeInfo = selectedShape.resize(dx, dy);
					// resizing
					selectedShape.getDShapeModel().setBounds(newShapeInfo[0], newShapeInfo[1], newShapeInfo[2],
							newShapeInfo[3]);
					mousePt = event.getPoint(); //always need to update
				}
			}
		});
	}

	// called will paint the shape on the canvas
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		Iterator<DShape> itr = shapeList.iterator();
		while (itr.hasNext()) {
			DShape shapeToPaint = itr.next();
			shapeToPaint.draw(g2d);
		}
	}

	// add the shape to shapeList before calling paintComponent()
	public void addShape(DShapeModel dShapeModel, String typeOfShape) {
		if (typeOfShape.equals("rect")) {
			DRect temp = new DRect();
			temp.setCanvasReferencel(this); // put canvas reference inside DRect object
			dShapeModel.setListOfListeners(temp); // put DRect reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DRect object
			shapeList.add(temp);
			repaint();
		}
		else if (typeOfShape.equals("oval")) {
			DOval temp = new DOval();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			shapeList.add(temp);
			repaint();
		}
		else if(typeOfShape.equals("text")){
			DText temp = new DText();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			shapeList.add(temp);
			repaint();
		}
		else if(typeOfShape.equals("line")){
			DLine temp = new DLine();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			shapeList.add(temp);
			repaint();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public void setSelectedShape(int x, int y) {
		int currIndex = shapeList.size() - 1;
		foundSelected = false;
		indexOfSelected = -1;
		while (currIndex >= 0 && !foundSelected) {
			// back to front, the first found(which is on top) is the selected shape
			System.out.println("List size: " + shapeList.size());
			DShape currShape = shapeList.get(currIndex);

			// a bound Class may be needed
			if (currShape.getDShapeModel().containsInBound(x, y)) {
				System.out.println("Selected a shape;");
				if(currShape.getClass().getSimpleName().equals("DText")){
					System.out.print("DTEXT SELECTED");
					setIsSelectedShapeText(true);
					selectedShape = currShape;
					//selectedShape.showKnobs();
					foundSelected = true;
					indexOfSelected = currIndex;
				}
				else{
					setIsSelectedShapeText(false);
					selectedShape = currShape;
					//selectedShape.showKnobs();
					foundSelected = true;
					indexOfSelected = currIndex;
				}

			}
			currIndex--;
		}
		moveToFront(); // Move selected shape in front
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	public void moveToFront() {
		if (foundSelected) {
			System.out.printf("Moving a shape at index %d in front", indexOfSelected);
			shapeList.remove(indexOfSelected);
			shapeList.add(getSelectedShape());
			indexOfSelected = shapeList.size() - 1;
			repaint();
		}
	}

	public void moveToBack() {
		if (foundSelected) {
			System.out.printf("Moving a shape at index %d in the back", indexOfSelected);
			shapeList.remove(indexOfSelected);
			shapeList.add(0, getSelectedShape());
			indexOfSelected = 0;
			repaint();
		}
	}

	public void deleteShape() {
		shapeList.remove(indexOfSelected);
		System.out.printf("Delete a shape at index: %d. Array size: %d", indexOfSelected, shapeList.size());
		repaint();
	}

	public void updateText(String str){
		if(selectedShape.getClass().getSimpleName().equals("DText")){
			System.out.print("Changing the text of the DText");
			DShape temp = selectedShape;
			DText textTemp = (DText)temp;
			textTemp.setText(str);
			repaint();
		}
	}

	public void updateFont(String fontName){
		if(selectedShape.getClass().getSimpleName().equals("DText")){
			System.out.print("\nChanging the font of the DText");
			DShape temp = selectedShape;
			DText textTemp = (DText)temp;
			textTemp.setFontName(fontName);
			repaint();
		}
	}
}