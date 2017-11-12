import models.DShapeModel;
import shapes.DShape;

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


    public Whiteboard getWhiteboard() {
        return whiteboard;
    }
    public ArrayList<DShapeModel> getdShapeModels() {
        return dShapeModels;
    }
}
