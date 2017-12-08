package edu.sjsu.whiteboard;

import com.sun.tools.internal.ws.processor.model.Model;

import javax.swing.table.AbstractTableModel;
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
    private Vector data = new Vector();



    public TableModel(){
    }

    public TableModel getTableModel(){
        return this;
    }


    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        try{
            return ((Vector) data.get(row)).get(col);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Array Index Out Of Bound in getValueAt from TableModel.java");
            e.printStackTrace();
        }
        return null;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

    void moveToFront(int row){
        Collections.swap(data,row,getRowCount()-1);
        fireTableDataChanged();
    }

    void moveToBack(int row){
        Collections.swap(data,row,0);
        fireTableDataChanged();
    }


    public void setValueAt(Object value, int row, int col){
        ((Vector) data.get(row)).setElementAt(value, col);
        fireTableCellUpdated(row,col);
    }

    void insertData(Object[] values){
        data.add(new Vector());
        for(int i = 0; i<values.length; i++){
            ((Vector) data.get(data.size()-1)).add(values[i]);
        }
        fireTableDataChanged();
    }

    void removeRow(int row){
        data.removeElementAt(0);
        fireTableDataChanged();
    }

    void clear(){
        data.clear();
        fireTableDataChanged();
    }


}
