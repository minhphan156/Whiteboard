package edu.sjsu.whiteboard;


import edu.sjsu.whiteboard.models.DShapeModel;

import java.util.ArrayList;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

public class Controller {
    private Whiteboard whiteboard;
    private Whiteboard whiteboard2; // For networking
    private ArrayList<DShapeModel> dShapeModels;

    Controller(){
        whiteboard = new Whiteboard(this); // For networking mode we need 2 JFrames
        //whiteboard2 = new Whiteboard(this);
        dShapeModels = new ArrayList<DShapeModel>();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    public void deleteModel(int index){
        dShapeModels.remove(index);
    }
    public Whiteboard getWhiteboard() {
        return whiteboard;
    }
    public ArrayList<DShapeModel> getdShapeModels() {
        return dShapeModels;
    }
}
