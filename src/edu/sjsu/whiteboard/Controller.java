package edu.sjsu.whiteboard;


import edu.sjsu.whiteboard.models.DShapeModel;

import java.util.ArrayList;

public class Controller {
    private Whiteboard whiteboard;
    private ArrayList<DShapeModel> dShapeModels;

    Controller(){
        whiteboard = new Whiteboard(this);
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
