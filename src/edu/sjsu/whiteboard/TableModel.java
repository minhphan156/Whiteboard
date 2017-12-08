package edu.sjsu.whiteboard;

import com.sun.tools.internal.ws.processor.model.Model;
import edu.sjsu.whiteboard.models.DShapeModel;
import edu.sjsu.whiteboard.shapes.DShape;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

/**
 * TableModel class contains all the necessary methods for whiteboard table. Uses Vector class for data storage
 */

public class TableModel extends AbstractTableModel {

    private String[] columnNames = { "X", "Y", "Width", "Height" };
    //private Vector data = new Vector();
    private ArrayList<DShape> shapeList;

    public TableModel( ArrayList<DShape> shapeList){
        this.shapeList = shapeList;
    }

    public TableModel getTableModel(){
        return this;
    }


    public ArrayList<DShape> getShapeList() {
        return shapeList;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return shapeList.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
//        try{
//            return ((Vector) data.get(row)).get(col);
//        }
//        catch (ArrayIndexOutOfBoundsException e){
//            System.out.println("Array Index Out Of Bound in getValueAt from TableModel.java");
//            e.printStackTrace();
//        }
//        return null;

        // Return X coord
        if(col == 0){
            return shapeList.get(row).getDShapeModel().getX();
        }

        // Return Y coord
        else if(col == 1){
            return shapeList.get(row).getDShapeModel().getY();
        }

        // Return Width
        else if(col == 2){
            return shapeList.get(row).getDShapeModel().getWidth();
        }

        // Return Height
        else if(col == 3){
            return shapeList.get(row).getDShapeModel().getHeight();
        }

        return shapeList.get(row).getDShapeModel().getHeight();
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

    void moveToFront(int row){
        //Collections.swap(shapeList,row,getRowCount()-1);
        fireTableDataChanged();
    }

    void moveToBack(int row){
        //Collections.swap(data,row,0);
        fireTableDataChanged();
    }


//    public void setValueAt(Object value, int row, int col){
//        //((Vector) data.get(row)).setElementAt(value, col);
//        fireTableCellUpdated(row,col);
//    }
//
//    void insertData(Object[] values){
//        data.add(new Vector());
//        for(int i = 0; i<values.length; i++){
//            ((Vector) data.get(data.size()-1)).add(values[i]);
//        }
//        fireTableDataChanged();
//    }
//
//
//
//    void removeRow(int row){
//        data.removeElementAt(row);
//        fireTableDataChanged();
//    }
//
//    void clear(){
//        data.clear();
//        fireTableDataChanged();
//    }


}
