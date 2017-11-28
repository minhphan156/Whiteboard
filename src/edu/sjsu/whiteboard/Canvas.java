package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.models.DLineModel;
import edu.sjsu.whiteboard.shapes.*;
import javafx.scene.control.Tab;

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
	private int indexOfSelected = -1;
	private Point mousePt;
	private boolean isResizing;

	public Canvas(Controller controller) {
		this.controller = controller;
		shapeList = new ArrayList<>();
		this.setBackground(Color.WHITE);
		this.setPreferredSize(size);
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent event) {

				// if don't set isResizing to false, it will affect all other behaviors, like
				// dragging and selecting a shape

				Object source = event.getSource();
				int x = event.getX();
				int y = event.getY();
				//System.out.printf("\n(%d, %d)", x, y);
				setSelectedShape(x, y);
				System.out.print("new Selected shape at index: " + indexOfSelected);
			}

			public void mousePressed(MouseEvent event) {
				mousePt = event.getPoint();
				//System.out.printf("\n(%d, %d)", mousePt.x, mousePt.y);
				//mousePressed, if the pressedPoint is not in any shape, selected shape should not move
				//now press a shape can set it to a selected shape, no need to click then move
				setSelectedShape(mousePt.x,mousePt.y);
				if (selectedShape != null && selectedShape.isInKnobs(mousePt)) {

					// in knobs... so resize;
					//check this: indexOfSelected != -1,
					//otherwise, will have a null pointer exception
					isResizing = true;
				}
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
				//moved isResizing to here, don't need to click to cancel behavior of resizing
				isResizing = false;
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent event) {
				int dx = event.getX() - mousePt.x;
				int dy = event.getY() - mousePt.y;

				//make sure that selectedShape != null to avoid errors...
				if (selectedShape != null && !isResizing) {
					//System.out.println("dragging now ");
					// not resizing, so moving the shape
					if(!(selectedShape instanceof DLine))
					{
						selectedShape.getDShapeModel().setX(selectedShape.getDShapeModel().getX() + dx);
						selectedShape.getDShapeModel().setY(selectedShape.getDShapeModel().getY() + dy);
						mousePt = event.getPoint();
						InterfaceControl.tableValues.setValueAt(selectedShape.getDShapeModel().getX() + dx,indexOfSelected,0);
						InterfaceControl.tableValues.setValueAt(selectedShape.getDShapeModel().getY() + dy,indexOfSelected,1);
					}
					else
					{
						//for DLine
						//((DLineModel)(selectedShape.getDShapeModel()))
						Point point1 = ((DLineModel)(selectedShape.getDShapeModel())).getP1();
						Point point2 = ((DLineModel)(selectedShape.getDShapeModel())).getP2();
						point1 = new Point(point1.x + dx, point1.y + dy);
						point2 = new Point(point2.x + dx, point2.y + dy);
						((DLineModel)(selectedShape.getDShapeModel())).setP1(point1);
						((DLineModel)(selectedShape.getDShapeModel())).setP2(point2);
						mousePt = event.getPoint();
					}
				} else if(selectedShape != null && isResizing){
					//System.out.println("resizing now ");
					if(!(selectedShape instanceof DLine))
					{
						int[] newShapeInfo = selectedShape.resize(dx, dy);
						// resizing
						selectedShape.getDShapeModel().setBounds(newShapeInfo[0], newShapeInfo[1], newShapeInfo[2],
								newShapeInfo[3]);
						mousePt = event.getPoint(); //always need to update
						InterfaceControl.tableValues.setValueAt(selectedShape.getDShapeModel().getWidth(),indexOfSelected,2); // Width
						InterfaceControl.tableValues.setValueAt(newShapeInfo[3],indexOfSelected,3); // Height
					}
					else
					{
						//after calculating infor about resizing,
						//1stPoint.x 1stPoint.y 2ndPoint.x 2ndPoint.y in newShapeInfo[]
						int[] newShapeInfo = selectedShape.resize(dx, dy);
						((DLineModel)(selectedShape.getDShapeModel())).setP1(new Point(newShapeInfo[0],newShapeInfo[1]));
						((DLineModel)(selectedShape.getDShapeModel())).setP2(new Point(newShapeInfo[2],newShapeInfo[3]));
						mousePt = event.getPoint(); //always need to update
					}
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
			Object[] value = {temp.getDShapeModel().getX(),temp.getDShapeModel().getY(),temp.getDShapeModel().getWidth(),temp.getDShapeModel().getHeight()};
			InterfaceControl.tableValues.insertData(value);
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
		else if (typeOfShape.equals("oval")) {
			DOval temp = new DOval();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			Object[] value = {temp.getDShapeModel().getX(),temp.getDShapeModel().getY(),temp.getDShapeModel().getWidth(),temp.getDShapeModel().getHeight()};
			InterfaceControl.tableValues.insertData(value);
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
		else if(typeOfShape.equals("text")){
			DText temp = new DText();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			Object[] value = {temp.getDShapeModel().getX(),temp.getDShapeModel().getY(),temp.getDShapeModel().getWidth(),temp.getDShapeModel().getHeight()};
			InterfaceControl.tableValues.insertData(value);
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
		else if(typeOfShape.equals("line")){
			DLine temp = new DLine();
			temp.setCanvasReferencel(this); // put canvas reference inside DOval object
			dShapeModel.setListOfListeners(temp); // put DOval reference in listOfListener in DShapeModel
			temp.setPointerToDShapeModel(dShapeModel);// put dShapeModel reference inside DOval object
			Object[] value = {temp.getDShapeModel().getX(),temp.getDShapeModel().getY(),temp.getDShapeModel().getWidth(),temp.getDShapeModel().getHeight()};
			InterfaceControl.tableValues.insertData(value);
			shapeList.add(temp);
			setNewAddedShapeSelected();
			repaint();
		}
	}

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
	}

	@Override
	public String getName() {
		return name;
	}

	public boolean setSelectedShape(int x, int y) {
		//changed it to boolean to deal with clicked point or pressed point
		//that is not in any one of the shapes
		int currIndex = shapeList.size() - 1;
		while (currIndex >= 0) {
			// back to front, the first found(which is on top) is the selected shape
			System.out.println("List size: " + shapeList.size());
			DShape currShape = shapeList.get(currIndex);

			// a bound Class may be needed
			if (currShape.getDShapeModel().containsInBound(x, y)) {
				System.out.println("Selected a shape at: "+currIndex);
				if(selectedShape instanceof DText){
					System.out.print("DTEXT SELECTED");
					selectedShape = currShape;
					DShape temp = selectedShape;
					DText textTemp = (DText)temp;
					String currentText = textTemp.getText();
					String currentFont = textTemp.getFontName();
					InterfaceControl.enableScriptChooserTextFields(true);
					InterfaceControl.updateScriptChooserTextField(currentText, currentFont); // STATIC
					//selectedShape.showKnobs();
					if(indexOfSelected != -1)
					{
						//previously, we had a selected shape.
						shapeList.get(indexOfSelected).setIsSelected(false);
					}
					indexOfSelected = currIndex;
					shapeList.get(indexOfSelected).setIsSelected(true);
					repaint();
					return true;
				}
				else{
					InterfaceControl.enableScriptChooserTextFields(false);
					selectedShape = currShape;
					//selectedShape.showKnobs();
					if(indexOfSelected != -1)
					{
						//previously, we had a selected shape.
						shapeList.get(indexOfSelected).setIsSelected(false);
					}
					indexOfSelected = currIndex;
					shapeList.get(indexOfSelected).setIsSelected(true);
					repaint();
					return true;
				}

			}
			currIndex--;
		}
		//This time, the clicked point is not in any bounds of selected shape...
		//so, set the selected shape to null.
		if(indexOfSelected != -1)
		{
			//previously, we had a selected shape.
			if(shapeList.get(indexOfSelected).isInKnobs(new Point(x, y)))
			{
				//the new clicked point is in knobs of previously selected shape,
				//should not set previously selected shape to null.
				//should not change anything of previously selected shape
				//didn't discuss this situation early, because click a knob should not select a shape
				//return true because there is a selected shape, keep the previously selected shape
				return true;
			}
			//not in any shapes or any knobs
			shapeList.get(indexOfSelected).setIsSelected(false);
		}
		indexOfSelected = -1;
		selectedShape = null;
		repaint();
		return false;
		//selected shape is not moved to front in teacher's jar file
//		moveToFront(); // Move selected shape in front
	}

	public DShape getSelectedShape() {
		return selectedShape;
	}

	public void moveToFront() {
		if (selectedShape != null) {
			System.out.printf("Moving a shape at index %d in front", indexOfSelected);
			shapeList.remove(indexOfSelected);
			shapeList.add(getSelectedShape());
			//indexOfSelected = shapeList.size() - 1;
			repaint();
		}
	}

	public void moveToBack() {
		if (selectedShape != null) {
			System.out.printf("Moving a shape at index %d in the back", indexOfSelected);
			shapeList.remove(indexOfSelected);
			shapeList.add(0, getSelectedShape());
			indexOfSelected = 0;
			repaint();
		}
	}

	public void deleteShape() {
		shapeList.remove(indexOfSelected);
		System.out.print("\nIndex of the shape to delete: "+indexOfSelected);
		InterfaceControl.tableValues.removeRow(indexOfSelected);
		System.out.printf("Delete a shape at index: %d. Array size: %d", indexOfSelected, shapeList.size());
		repaint();
	}

	public void updateText(String str){
		if(selectedShape instanceof DText){
			System.out.print("Changing the text of the DText");
			DShape temp = selectedShape;
			DText textTemp = (DText)temp;
			textTemp.setText(str);
			repaint();
		}
	}

	public void updateFont(String fontName){
		if(selectedShape instanceof DText){
			System.out.print("\nChanging the font of the DText");
			DShape temp = selectedShape;
			DText textTemp = (DText)temp;
			textTemp.setFontName(fontName);
			repaint();
		}
	}

	public void clearCanvas(){
		controller.getdShapeModels().clear();
		shapeList.clear();
		repaint();
	}
}