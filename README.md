# Whiteboard
> Primitive but functional drawing application with networking capabilities

![](Canvas.png)

# Description
 Whiteboard application in Java with Swing GUI framework allows to draw multiple shapes (Rectangle, Oval, Line, Text), resize, drag and remove methods are supported. Networking features consist of a setting one instance to server mode and another instance to client mode. Server acts as a broadcaster while client receives all of the actions performed by server but can't make changes.    


## Known Bugs
* Text disabling/enabling during - SOLVED
* Double rows insertion in the table - SOLVED
* In networking mode: If delete all shapes, can't add again
* Networking: Change in text isn't reflected
* Table: If there are only 2 shapes on the canvas and you press delete: all rows will be deleted. Has to do with vector
* Table: Deleting the shape at index 0 causes weird exception
* Networking: moving to front/back doesn't seem to reflect in the table 
* Networking is not working consistently
* Table: networking is actually not working. If run to separate Whiteboards, table changes won't be reflected 

## Usage

Click on the executable Whiteboard.jar

## Meta

Please feel free to reach out:)

Danil Kolesnikov – danil.kolesnikov@sjsu.edu

Distributed under the MIT license.
