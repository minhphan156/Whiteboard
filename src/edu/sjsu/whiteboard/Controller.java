package edu.sjsu.whiteboard;


import edu.sjsu.whiteboard.models.*;
import edu.sjsu.whiteboard.shapes.DShape;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */

public class Controller {
    private Whiteboard whiteboard;
    private ClientHandler clientHandler;
    private ServerAccepter serverAccepter;
    private java.util.List<ObjectOutputStream> outputs =
            new ArrayList<ObjectOutputStream>();
    private ArrayList<DShapeModel> dShapeModels;

    public Controller(){
        whiteboard = new Whiteboard(this);
        dShapeModels = new ArrayList<DShapeModel>();
    }

    public static void main(String[] args) {
        Controller controllerServer = new Controller();
    }

    public void deleteModel(int index){
        dShapeModels.remove(index);
    }
    public Whiteboard getWhiteboard() {
        return whiteboard;
    }
    ArrayList<DShapeModel> getdShapeModels() {
        return dShapeModels;
    }

    // Server thread accepts incoming client connections
    class ServerAccepter extends Thread {
        private int port;
        ServerAccepter(int port) {
            this.port = port;
        }
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket toClient = null;
                    // this blocks, waiting for a Socket to the client
                    toClient = serverSocket.accept();
                    System.out.println("server: got client");
                    // Get an output stream to the client, and add it to
                    // the list of outputs
                    // (our server only uses the output stream of the connection)
                    addOutput(new ObjectOutputStream(toClient.getOutputStream()));
                    sendRemoteBeggining("add",dShapeModels);
                }
            }

            catch (java.net.BindException ex) {
                System.out.println("Closing application because of server collision!");
                System.exit(0);
                ex.printStackTrace();
            }

            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    // Adds an object stream to the list of outputs
    // (this and sendToOutputs() are synchronzied to avoid conflicts)
    private synchronized void addOutput(ObjectOutputStream out) {
        outputs.add(out);
    }

    // Client runs this to handle incoming messages
    // (our client only uses the inputstream of the connection)
    private class ClientHandler extends Thread {
        private String name;
        private int port;
        ClientHandler(String name, int port) {
            this.name = name;
            this.port = port;
        }
        // Connect to the server, loop getting messages
        public void run() {
            try {
                // make connection to the server name/port
                Socket toServer = new Socket(name, port);
                // get input stream to read from server and wrap in object input stream
                ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
                System.out.println("client: connected!");

                // we could do this if we wanted to write to server in addition
                // to reading
                // out = new ObjectOutputStream(toServer.getOutputStream());
                while (true) {
                    // Get the xml string, decode to a Message object.
                    // Blocks in readObject(), waiting for server to send something.
                    // String verb = (String) in.readObject(); // **************************receive from Server
                    String xmlString = (String) in.readObject();

                    //this part debug from xmlString to message object
                    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));

                    String command = (String) decoder.readObject();
                    DShapeModel tempDShapeModel = (DShapeModel) decoder.readObject();

                    System.out.println("client read: " + command); //client read xml string on console
                    System.out.println("client read: " + tempDShapeModel); //client read xml string on console
                    whiteboard.getCanvas().setSelectedShape2(tempDShapeModel);

                    if(command.equals("add")) {
                        if (tempDShapeModel instanceof DRectModel) {
                            whiteboard.getCanvas().addShape(tempDShapeModel, "rect");
                        } else if (tempDShapeModel instanceof DOvalModel) {
                            whiteboard.getCanvas().addShape(tempDShapeModel, "oval");
                        } else if (tempDShapeModel instanceof DLineModel) {
                            whiteboard.getCanvas().addShape(tempDShapeModel, "line");
                        } else if (tempDShapeModel instanceof DTextModel) {
                            whiteboard.getCanvas().addShape(tempDShapeModel, "text");
                        }
                        dShapeModels.add(tempDShapeModel);
                        InterfaceControl.getICtable().fireTableDataChanged();
                    }

                    else if (command.equals("remove")){
                        whiteboard.getCanvas().deleteShape();
                        InterfaceControl.getICtable().fireTableDataChanged();
                    }
                    else if (command.equals("front")){
                        whiteboard.getCanvas().moveToFront();
                        InterfaceControl.getICtable().fireTableDataChanged();
                    }
                    else if (command.equals("back")){
                        whiteboard.getCanvas().moveToBack();
                        InterfaceControl.getICtable().fireTableDataChanged();
                    }

                    else if (command.equals("change")){
                        if(whiteboard.getCanvas().getSelectedShape().getDShapeModel() instanceof DTextModel){
                            System.out.println("Making a change to DTextModel from Controller.java");
                            String hi = ((DTextModel)tempDShapeModel).getText();
                            System.out.println(hi);
                            whiteboard.getCanvas().getSelectedShape().getDShapeModel().mimic(((DTextModel)tempDShapeModel));
                        }
                        else{
                            System.out.println("Making a change to a NON DTextModel from Controller.java");
                            whiteboard.getCanvas().getSelectedShape().getDShapeModel().mimic(tempDShapeModel);
                        }
                    }
                }
            }

            // IOException and ClassNotFoundException
            catch (Exception ex) {
                ex.printStackTrace();
            }
            // Could null out client ptr.
            // Note that exception breaks out of the while loop,
            // thus ending the thread.
        }
    }

    // Starts the sever accepter to catch incoming client connections.
    // Wired to Server button.
    void doServer() {
        String result = JOptionPane.showInputDialog("Enter ip and port number you want to use (default is 39587):", "39587");
        if (result!=null) {
            System.out.println("server: start");
            serverAccepter = new ServerAccepter(Integer.parseInt(result.trim()));
            serverAccepter.start();
        }
    }

    // Runs a client handler to connect to a server.
    // Wired to Client button.
    void doClient() {
        String result = JOptionPane.showInputDialog("Enter ip and port number you want to use (default is 39587):", "127.0.0.1:39587");
        if (result!=null) {
            String[] parts = result.split(":");
            System.out.println("client: start");
            clientHandler = new ClientHandler(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            clientHandler.start();
        }
    }

    //NOTE: only client do input but server do input and output
    // Sends a message to all of the outgoing streams.
    // Writing rarely blocks, so doing this on the swing thread is ok,
    // although could fork off a worker to do it.
    synchronized void sendRemote(String command, DShapeModel dShapeModel) {
        // Convert the message object into an xml string.
        OutputStream memStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(memStream);
        encoder.writeObject(command);
        encoder.writeObject(dShapeModel);

        encoder.close();
        String xmlString = memStream.toString();
        // Now write that xml string to all the clients.
        //System.out.println(xmlString);
        Iterator<ObjectOutputStream> it = outputs.iterator();
        while (it.hasNext()) {
            ObjectOutputStream out = it.next();
            try {
                out.writeObject(xmlString); //********* send to client under xml
                out.flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                it.remove();
                // Cute use of iterator and exceptions --
                // drop that socket from list if have probs with it
            }
        }
    }

    private void sendRemoteBeggining(String command, ArrayList<DShapeModel> dShapeModels){

        Iterator<DShapeModel> itr = dShapeModels.iterator();
        while(itr.hasNext()) {

            System.out.println("\nserver send: remote data !!! ");

            // Convert the message object into an xml string.
            OutputStream memStream = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(memStream);
            encoder.writeObject(command);
            encoder.writeObject(itr.next());

            encoder.close();
            String xmlString = memStream.toString();
            // Now write that xml string to all the clients.
            //System.out.println(xmlString);
            Iterator<ObjectOutputStream> it = outputs.iterator();
            while (it.hasNext()) {
                ObjectOutputStream out = it.next();
                try {
                    out.writeObject(xmlString); //********* send to client under xml
                    out.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    it.remove();
                    // Cute use of iterator and exceptions --
                    // drop that socket from list if have probs with it
                }
            }
        }
    }

    void save(File file) {
        try {
            ArrayList<DShape> shapeList = whiteboard.getCanvas().getShapeList();
            XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
            DShapeModel[] dModelArrayWritten = new DShapeModel[shapeList.size()];
            for (int i = 0; i < shapeList.size(); i++) {
                System.out.println(shapeList.get(i).getDShapeModel());
                dModelArrayWritten[i] = shapeList.get(i).getDShapeModel();
                // xmlOut.writeObject(dModelArrayWritten[i]);
            }
            xmlOut.writeObject(dModelArrayWritten);
            xmlOut.writeObject("saved dModelArray into xml");
            xmlOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void open(File file) {
        Canvas canvas = whiteboard.getCanvas();
        canvas.clearCanvas();
        DShapeModel[] dModelArrayRead;
        try {
            XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
            dModelArrayRead = (DShapeModel[]) xmlIn.readObject();
            String test = (String) xmlIn.readObject();
            xmlIn.close();
            System.out.println(dModelArrayRead.length);
            for (int i = 0; i < dModelArrayRead.length; i++) {
                if (dModelArrayRead[i] instanceof DRectModel) {
                    canvas.addShape(dModelArrayRead[i], "rect");

                } else if (dModelArrayRead[i] instanceof DOvalModel) {
                    canvas.addShape(dModelArrayRead[i], "oval");
                } else if (dModelArrayRead[i] instanceof DLineModel) {
                    canvas.addShape(dModelArrayRead[i], "line");
                } else if (dModelArrayRead[i] instanceof DTextModel) {
                    canvas.addShape(dModelArrayRead[i], "text");
                }
                dShapeModels.add(dModelArrayRead[i]);
            }
            System.out.println(test);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
