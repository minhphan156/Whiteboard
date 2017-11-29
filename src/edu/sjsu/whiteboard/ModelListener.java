package edu.sjsu.whiteboard;

import edu.sjsu.whiteboard.models.DShapeModel;


/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

/**
 Interface to listen for shape change notifications.
 The modelChanged() notification includes a pointer to
 the model that changed. There is not detail about
 what the exact change
was. */
public interface ModelListener {
    public void modelChanged(DShapeModel model);




}
