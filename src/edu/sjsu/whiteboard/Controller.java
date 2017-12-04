package edu.sjsu.whiteboard;


import edu.sjsu.whiteboard.models.*;
import edu.sjsu.whiteboard.shapes.DRect;
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
    // private Whiteboard whiteboard2; // For networking
    private ClientHandler clientHandler;
    private ServerAccepter serverAccepter;
    private java.util.List<ObjectOutputStream> outputs =
            new ArrayList<ObjectOutputStream>();

    private ArrayList<DShapeModel> dShapeModels;

    Controller(){
        whiteboard = new Whiteboard(this); // For networking mode we need 2 JFrames
        //whiteboard2 = new Whiteboard(this);
        dShapeModels = new ArrayList<DShapeModel>();
    }

    public static void main(String[] args) {
        //Minh
        Controller controllerServer = new Controller();

        Controller controllerClient = new Controller();

        // controllerServer.doServer();

        // controllerClient.doClient();
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
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    // Adds an object stream to the list of outputs
    // (this and sendToOutputs() are synchronzied to avoid conflicts)
    public synchronized void addOutput(ObjectOutputStream out) {
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
                    //Integer indexOfSelected = (Integer) decoder.readObject();

                    System.out.println("client read: " + command); //client read xml string on console
                    System.out.println("client read: " + tempDShapeModel); //client read xml string on console
//                    System.out.println("client read: " + indexOfSelected); //client read xml string on console
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
                    }

                    else if (command.equals("remove")){
//                        Iterator<DShapeModel> itr = dShapeModels.iterator();
//
//                        while (itr.hasNext()){
//                            DShapeModel tempDSModel = itr.next();
//                            if( tempDSModel.getId() == tempDShapeModel.getId()){
//                                whiteboard.getCanvas().setSelectedShape2(tempDSModel);
//                            }
//                        }
                        whiteboard.getCanvas().deleteShape();
                    }
                    else if (command.equals("front")){


                        whiteboard.getCanvas().moveToFront();
                    }
                    else if (command.equals("back")){

                        //whiteboard.getCanvas().setSelectedShape(tempDShapeModel.getX(),tempDShapeModel.getY());
//                        Iterator<DShapeModel> itr = dShapeModels.iterator();
//
//                        while (itr.hasNext()){
//                            DShapeModel tempDSModel = itr.next();
//                            if( tempDSModel.getId() == tempDShapeModel.getId()){
//                                whiteboard.getCanvas().setSelectedShape2(tempDSModel);
//                            }
//                        }

                        whiteboard.getCanvas().moveToBack();

                    }
                    else if (command.equals("change")){
                        whiteboard.getCanvas().getSelectedShape().getDShapeModel().mimic(tempDShapeModel);
                        // whiteboard.getCanvas().setSelectedShape(tempDShapeModel.getX(),tempDShapeModel.getY());

                        // tempDShapeModel.getId();
//
//                      DShapeModel tempDSModel = itr.next();
//                     if( tempDSModel.getId() == tempDShapeModel.getId()){
//                         tempDSModel.mimic(tempDShapeModel);
//                     }
//                        Iterator<DShapeModel> itr = dShapeModels.iterator();
//
//                        while (itr.hasNext()){
//                            DShapeModel tempDSModel = itr.next();
//                           if( tempDSModel.getId() == tempDShapeModel.getId()){
//                               tempDSModel.mimic(tempDShapeModel);
//                           }
//                        }

                        // System.out.println("tempDshapeModel data: "+tempDShapeModel);

                        //whiteboard.getCanvas().setIndexOfSelected(indexOfSelected);


                        //DShape tempDShape = whiteboard.getCanvas().getShapeList().get(whiteboard.getCanvas().getIndexOfSelected());


                        //whiteboard.getCanvas().setSelectedShape(tempDShape.getDShapeModel().getX(),tempDShape.getDShapeModel().getY());

                        //System.out.println("old tempDShape data: "+tempDShape.getDShapeModel());

                        // current.mimic(passed)
                        //tempDShape.getDShapeModel().mimic(tempDShapeModel);

                        //  System.out.println("new tempDShape data: "+tempDShapeModel);



                    }


                    // Message message = (Message) decoder.readObject();

                    // invokeToGUI(message);
                }
            }
            catch (Exception ex) { // IOException and ClassNotFoundException
                ex.printStackTrace();
            }
            // Could null out client ptr.
            // Note that exception breaks out of the while loop,
            // thus ending the thread.
        }
    }

    // Starts the sever accepter to catch incoming client connections.
    // Wired to Server button.
    public void doServer() {
        //   status.setText("Start server");
        //TODO fix to take below instead after done

        String result = JOptionPane.showInputDialog("Enter ip and port number you want to use (default is 39587):", "39587");
        //TODO fix to take below instead after done

        if (result!=null) {
            System.out.println("server: start");
            //TODO fix to take below instead after done
            serverAccepter = new ServerAccepter(Integer.parseInt(result.trim()));
            //serverAccepter = new ServerAccepter(39587);
            serverAccepter.start();
            //TODO fix to take below instead after done

        }
    }

    // Runs a client handler to connect to a server.
    // Wired to Client button.
    public void doClient() {
        // status.setText("Start client");
        //TODO fix to take below instead after done

        String result = JOptionPane.showInputDialog("Enter ip and port number you want to use (default is 39587):", "127.0.0.1:39587");
        //TODO fix to take below instead after done

        if (result!=null) {
            //TODO fix to take below instead after done

            String[] parts = result.split(":");
            System.out.println("client: start");
            //TODO fix to take below instead after done
            clientHandler = new ClientHandler(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            // clientHandler = new ClientHandler("127.0.0.1",39587);
            clientHandler.start();
            //TODO fix to take below instead after done

        }
    }



    // Given a message, puts that message in the local GUI.
    // Can be called by any thread.
//    public void invokeToGUI(Message message) {
//        final Message temp = message;
//        SwingUtilities.invokeLater( new Runnable() {
//            public void run() {
//                status.setText("Client receive");
//                sendLocal(temp);
//            }
//        });
//    }



    //NOTE: only client do input but server do input and output
    // Sends a message to all of the outgoing streams.
    // Writing rarely blocks, so doing this on the swing thread is ok,
    // although could fork off a worker to do it.
    public synchronized void sendRemote(String command,DShapeModel dShapeModel) {

        System.out.println("\nserver send: remote data !!! ");

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

}
