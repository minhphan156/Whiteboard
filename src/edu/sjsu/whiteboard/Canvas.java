package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.models.DLineModel;
import edu.sjsu.whiteboard.shapes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static javax.imageio.ImageIO.write;


/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

/**
 * Canvas class is responsible for drawing, dragging and resizing shapes
 */

public class Canvas extends JPanel  {

	private String name = "canvas";
	private Controller controller;
	private ArrayList<DShape> shapeList; // shapeList stores shapes currently displayed on the canvas
	private Dimension size = new Dimension(400, 400); // Dimensions of the canva plane
	private DShape selectedShape; // This shape contains currently selected shape
	private int indexOfSelected = -1; // -1 indicates no shapes is selected
	private Point mousePt; // where the mouse points
	private boolean isResizing; // if don't set isResizing to false, it will affect all other behaviors, like dragging and selecting a shape
	private TableModel icTableModel = InterfaceControl.getICtable(); // reference to the table in Interface Control class

	public ArrayList<DShape> getShapeList() {
		return shapeList;
	}

	public TableModel getIcTableModel() {
		return icTableModel;
	}

	public int getIndexOfSelected() {
		return indexOfSelected;
	}

	public void setIndexOfSelected(int indexOfSelected) {
		this.indexOfSelected = indexOfSelected;
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	@Override
	public String getName() {
		return name;
	}

	public Canvas(Controller controller) {

		this.controller = controller;
		shapeList = new ArrayList<>();
		this.setBackground(Color.WHITE);
		this.setPreferredSize(size);

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent event) {
				Object source = event.getSource();
				int x = event.getX();
				int y = event.getY();
				setSelectedShape(x, y);
				System.out.print("\nNew Selected shape at index: " + indexOfSelected);
			}

			/* mousePressed, if the pressedPoint is not in any shape, selected shape should not move
			now press a shape can set it to a selected shape, no need to click then move */
			public void mousePressed(MouseEvent event) {
				mousePt = event.getPoint();

				setSelectedShape(mousePt.x,mousePt.y);
				//	controller.sendRemote("change",getSelectedShape().getDShapeModel(),(Integer)indexOfSelected);
				if (selectedShape != null && selectedShape.isInKnobs(mousePt)) {
					isResizing = true; // otherwise, will have a null pointer exception
				}
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				isResizing = false; // moved isResizing to here, don't need to click to cancel behavior of resizing
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				int dx = event.getX() - mousePt.x;
				int dy = event.getY() - mousePt.y;

				// make sure that selectedShape != null to avoid errors
				// not resizing -> so moving the shape
				if (selectedShape != null && !isResizing) {

					if(!(selectedShape instanceof DLine))
					{
						selectedShape.getDShapeModel().setX(selectedShape.getDShapeModel().getX() + dx);
						selectedShape.getDShapeModel().setY(selectedShape.getDShapeModel().getY() + dy);
						mousePt = event.getPoint();

						controller.sendRemote("change",getSelectedShape().getDShapeModel());

						icTableModel.setValueAt(selectedShape.getDShapeModel().getX() + dx,indexOfSelected,0); // Set X value of a Non Line shape
						icTableModel.setValueAt(selectedShape.getDShapeModel().getY() + dy,indexOfSelected,1); // Set Y value of a Non Line shape
					}

					// Moving DLine Shape
					else
					{
						Point point1 = ((DLineModel)(selectedShape.getDShapeModel())).getP1();
						Point point2 = ((DLineModel)(selectedShape.getDShapeModel())).getP2();
						point1 = new Point(point1.x + dx, point1.y + dy);
						point2 = new Point(point2.x + dx, point2.y + dy);
						((DLineModel)(selectedShape.getDShapeModel())).setP1(point1);
						((DLineModel)(selectedShape.getDShapeModel())).setP2(point2);
						mousePt = event.getPoint();

						controller.sendRemote("change",getSelectedShape().getDShapeModel());

						icTableModel.setValueAt("Start X: "+((DLineModel)(selectedShape.getDShapeModel())).getP1().x,indexOfSelected,0); // X value(Point 1) of the  line
						icTableModel.setValueAt("Start Y: "+((DLineModel)(selectedShape.getDShapeModel())).getP1().y,indexOfSelected,1); // Y value(Point 1) of the line
						icTableModel.setValueAt("End X: "+((DLineModel)(selectedShape.getDShapeModel())).getP2().x,indexOfSelected,2); // X value(Point 2) of the line
						icTableModel.setValueAt("End Y: "+((DLineModel)(selectedShape.getDShapeModel())).getP2().y,indexOfSelected,3); // Y value(Point 2) of the line
					}
				}
				// Resizing
				else if(selectedShape != null && isResizing){

					// Resizing a shape
					if(!(selectedShape instanceof DLine))
					{
						int[] newShapeInfo = selectedShape.resize(dx, dy);
						selectedShape.getDShapeModel().setBounds(newShapeInfo[0], newShapeInfo[1], newShapeInfo[2], newShapeInfo[3]);
						mousePt = event.getPoint(); //always need to update

						controller.sendRemote("change",getSelectedShape().getDShapeModel());

						icTableModel.setValueAt(selectedShape.getDShapeModel().getWidth(),indexOfSelected,2); // Width
						icTableModel.setValueAt(newShapeInfo[3],indexOfSelected,3); // Height
					}

					// Resizing a DLine shape
					else
					{
						// after calculating info about resizing,
						//1stPoint.x 1stPoint.y 2ndPoint.x 2ndPoint.y in newShapeInfo[]
						int[] newShapeInfo = selectedShape.resize(dx, dy);
						((DLineModel)(selectedShape.getDShapeModel())).setP1(new Point(newShapeInfo[0],newShapeInfo[1]));
						((DLineModel)(selectedShape.getDShapeModel())).setP2(new Point(newShapeInfo[2],newShapeInfo[3]));
						mousePt = event.getPoint(); //always need to update

						controller.sendRemote("change",getSelectedShape().getDShapeModel());

						icTableModel.setValueAt("Start X "+((DLineModel)(selectedShape.getDShapeModel())).getP1().x,indexOfSelected,0); // X value(Point 1) of the  line
						icTableModel.setValueAt("Start Y: "+((DLineModel)(selectedShape.getDShapeModel())).getP1().y,indexOfSelected,1); // Y value(Point 1) of the line
						icTableModel.setValueAt("End X: "+((DLineModel)(selectedShape.getDShapeModel())).getP2().x,indexOfSelected,2); // X value(Point 2) of the line
						icTableModel.setValueAt("End Y: "+((DLineModel)(selectedShape.getDShapeModel())).getP2().y,indexOfSelected,3); // Y value(Point 2) of the line
					}
				}
			}
		});
	}

	// Paints shapes on the canvas
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
			setNewAddedShapeSelected();
			repaint();
		}
		else if (typeOfShape.equals("oval")) {
			DOval temp = new DOval();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
		else if(typeOfShape.equals("text")){
			DText temp = new DText();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
		else if(typeOfShape.equals("line")){
			DLine temp = new DLine();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
	}

	// Add comment
	public void setNewAddedShapeSelected()
	{
		if(indexOfSelected != -1)
		{
			//previously selected shape, set it to false(not selected)
			shapeList.get(indexOfSelected).setIsSelected(false);
		}
		//a new index for currently new shape, which is also a selected shape
		indexOfSelected = shapeList.size() - 1;
		shapeList.get(indexOfSelected).setIsSelected(true);
		selectedShape = shapeList.get(indexOfSelected);

		//controller.sendRemote("change",getSelectedShape().getDShapeModel(),(Integer)indexOfSelected);

	}

	// setSelectedShape() is the main function to find a shape selected on the canvas
	public boolean setSelectedShape(int x, int y) {

		//changed it to boolean to deal with clicked point or pressed point that is not in any one of the shapes
		int currIndex = shapeList.size() - 1;
		while (currIndex >= 0) {
			// back to front, the first found(which is on top) is the selected shape
			DShape currShape = shapeList.get(currIndex);
			if (currShape.getDShapeModel().containsInBound(x, y)) {
				//System.out.println("Selected a shape at: "+currIndex);
				if(currShape instanceof DText){
					System.out.println("\nDText selected");
					selectedShape = currShape;
					DShape temp = selectedShape;
					DText textTemp = (DText)temp;
					String currentText = textTemp.getText();
					String currentFont = textTemp.getFontName();
					// STATIC !
					InterfaceControl.enableScriptChooserTextFields(true); // Enable controls in the UI
					InterfaceControl.updateScriptChooserTextField(currentText, currentFont); // Update live change in text and font chooser

					// add comments
					if(indexOfSelected != -1)
					{
						//previously, we had a selected shape.
						shapeList.get(indexOfSelected).setIsSelected(false);

						//controller.sendRemote("change",selectedShape.getDShapeModel(),(Integer)indexOfSelected);
					}
					indexOfSelected = currIndex;
					shapeList.get(indexOfSelected).setIsSelected(true);

					//controller.sendRemote("change",selectedShape.getDShapeModel(),(Integer)indexOfSelected);




					repaint();
					return true;
				}
				// Any other shape
				else{
					InterfaceControl.enableScriptChooserTextFields(false); // Disable text controls
					selectedShape = currShape;

					if(indexOfSelected != -1)
					{
						shapeList.get(indexOfSelected).setIsSelected(false); //previously, we had a selected shape.

						//controller.sendRemote("change",selectedShape.getDShapeModel(),(Integer)indexOfSelected);

					}
					indexOfSelected = currIndex;
					shapeList.get(indexOfSelected).setIsSelected(true);

					//controller.sendRemote("change",selectedShape.getDShapeModel(),(Integer)indexOfSelected);

					repaint();
					return true;
				}

			}
			currIndex--;
		}

		//This time, the clicked point is not in any bounds of selected shape so, set the selected shape to null.
		if(indexOfSelected != -1)
		{
			//previously, we had a selected shape.
			if(shapeList.get(indexOfSelected).isInKnobs(new Point(x, y)))
			{
				/* The new clicked point is in knobs of previously selected shape,
				should not set previously selected shape to null.
				should not change anything of previously selected shape
				didn't discuss this situation early, because click a knob should not select a shape
				return true because there is a selected shape, keep the previously selected shape */


				return true;
			}

			//not in any shapes or any knobs
			shapeList.get(indexOfSelected).setIsSelected(false);

			//controller.sendRemote("change",selectedShape.getDShapeModel(),(Integer)indexOfSelected);

		}
		indexOfSelected = -1;
		selectedShape = null;

		//controller.sendRemote("change",selectedShape.getDShapeModel(),(Integer)indexOfSelected);

		repaint();
		return false;
	}
	public void setSelectedShape2(DShapeModel dShapeModel){
		for(int i = 0; i < shapeList.size(); i++){
			DShape tempDShap = shapeList.get(i);
			DShapeModel tempDSM = tempDShap.getDShapeModel();
			if(tempDSM.getId() == dShapeModel.getId()){
				selectedShape = tempDShap;
				indexOfSelected = i;
			}
		}
	}

	// moveToFront() moves the selected shape in front and modifies the table
	public void moveToFront() {
		if (selectedShape != null) {
			System.out.printf("Moving a shape at index %d in front", indexOfSelected);
			controller.sendRemote("front",getSelectedShape().getDShapeModel());
			Collections.swap(shapeList,indexOfSelected,shapeList.size()-1);
			icTableModel.moveToFront(indexOfSelected);
			indexOfSelected = shapeList.size() - 1;
			selectedShape = shapeList.get(indexOfSelected);
			repaint();
		}
	}

	// moveToBack() moves the selected shape in the back and modifies the table
	public void moveToBack() {
		if (selectedShape != null) {

			System.out.printf("Moving a shape at i=ndex %d in the back", indexOfSelected);
			controller.sendRemote("back",getSelectedShape().getDShapeModel());
			Collections.swap(shapeList,indexOfSelected,0);
			icTableModel.moveToBack(indexOfSelected);
			indexOfSelected = 0;
			selectedShape = shapeList.get(indexOfSelected);
			repaint();
		}
	}

	// deleteShape() removes the shape from the canvas and the table
	public void deleteShape() {
		if(selectedShape != null)
		{
			controller.sendRemote("remove",getSelectedShape().getDShapeModel());
			controller.getdShapeModels().remove(selectedShape.getDShapeModel());
			shapeList.remove(indexOfSelected); // remove from the list
			System.out.printf("\nDelete a shape at index: %d. Array size: %d", indexOfSelected, shapeList.size());
			selectedShape = null;
			repaint();
			// Order matters
			icTableModel.removeRow(indexOfSelected); // remove from the table
			indexOfSelected = -1; // selected shape is deleted,no selected shape now.
		}
	}

	// Update text of the Text shape on the canvas
	public void updateText(String str){
		if(selectedShape instanceof DText){
			DShape temp = selectedShape;
			DText textTemp = (DText)temp; // Cast
			textTemp.setText(str);
			controller.sendRemote("change",getSelectedShape().getDShapeModel());
			repaint();
		}
	}

	// Update the font of the Text Shape on the canvas
	public void updateFont(String fontName){
		if(selectedShape instanceof DText){
			DShape temp = selectedShape;
			DText textTemp = (DText)temp;
			textTemp.setFontName(fontName);
			controller.sendRemote("change",getSelectedShape().getDShapeModel());
			repaint();
		}
	}

	// Clears the canvas and table
	public void clearCanvas(){
		controller.getdShapeModels().clear(); // Clear list in the Controller class
		shapeList.clear(); // Clear the ArrayList in canvas class
		icTableModel.clear(); // Clear the table
		controller.sendRemote("change",getSelectedShape().getDShapeModel());
		InterfaceControl.enableScriptChooserTextFields(false); // Disable text controls
		InterfaceControl.updateScriptChooserTextField("", "Dialog");
		repaint();
	}

	public void saveImage(File file1) {
		// Create an image bitmap, same size as ourselves
		BufferedImage image = (BufferedImage) createImage(getWidth(), getHeight());
		// Get Graphics pointing to the bitmap, and call paintAll()
		// This is the RARE case where calling paint() is appropriate
		// (normally the system calls paint()/paintComponent())
		Graphics g = image.getGraphics();
		paintAll(g);
		g.dispose(); // Good but not required--
		// dispose() Graphics you create yourself when done with them.
		try {
			write(image, "PNG", file1);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}