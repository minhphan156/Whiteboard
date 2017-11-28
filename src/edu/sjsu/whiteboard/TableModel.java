package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DShapeModel;
import javafx.scene.control.Tab;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by danil on 11/27/17.
 */
public class TableModel extends AbstractTableModel {

    private String[] columnNames = { "X", "Y", "Width", "Height" };
    private Object[][] values = {};
    private Vector data = new Vector();


    public TableModel(){
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
        return ((Vector) data.get(row)).get(col);
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }

    public void moveToFront(int row){

    }
    public void moveToBack(int row){

    }

    public void setValueAt(Object value, int row, int col){
        ((Vector) data.get(row)).setElementAt(value, col);
        fireTableCellUpdated(row,col);
    }

    public void insertData(Object[] values){
        data.add(new Vector());
        for(int i = 0; i<values.length; i++){
            ((Vector) data.get(data.size()-1)).add(values[i]);
        }
        fireTableDataChanged();
        System.out.print("ACCESSED");
    }

    public void removeRow(int row){
        data.removeElementAt(row);
        fireTableDataChanged();
    }


}
