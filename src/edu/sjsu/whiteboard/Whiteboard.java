package edu.sjsu.whiteboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * @author Minh Phan minh.phan@sjsu.edu
 * @author Yulan Jin yulan.jin@sjsu.edu
 * CS 151 Term Project - Whiteboard
 */


/**
 * Whiteboard class is a container class for Canvas and Interface Control. It also contain the menu bar.
 */

public class Whiteboard extends JFrame {

    private Controller controller;

    public Whiteboard(Controller controller){
        super("Whiteboard");
        Canvas canvas =  new Canvas(controller);
        JMenuBar menubar = new JMenuBar(); // Initialize the menubar

        // Icons
        ImageIcon iconNew = new ImageIcon("res/reset.png");
        ImageIcon iconOpen = new ImageIcon("res/open.png");
        ImageIcon iconSave = new ImageIcon("res/save.png");
        ImageIcon iconSavePNG = new ImageIcon("res/save.png");
        ImageIcon iconExit = new ImageIcon("exit.png");
        ImageIcon iconAuthors = new ImageIcon("res/info.png");
        ImageIcon iconAuthorsDialog = new ImageIcon("res/infoDialog.png");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenu editMenu = new JMenu("Edit");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenu networkingMenu = new JMenu("Networking");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem moveToFront = new JMenuItem(new MenuItemAction("Move to Front", null,
                KeyEvent.VK_N));

        moveToFront.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveToFront();
            }
        });

        JMenuItem moveToBack = new JMenuItem(new MenuItemAction("Move to Back", null,
                KeyEvent.VK_N));

        moveToBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveToBack();
            }
        });
        JMenuItem removeShape = new JMenuItem(new MenuItemAction("Remove Shape", null,
                KeyEvent.VK_N));
        removeShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.deleteShape();
            }
        });

        // Add controls to editMenu
        editMenu.add(moveToFront);
        editMenu.add(moveToBack);
        editMenu.addSeparator();
        editMenu.add(removeShape);

        JMenuItem startServ = new JMenuItem(new MenuItemAction("Start Server", null,
                KeyEvent.VK_N));
        JMenuItem startClient = new JMenuItem(new MenuItemAction("Start Client", null,
                KeyEvent.VK_N));

        // Add sub menus to networking menu
        networkingMenu.add(startServ);
        networkingMenu.add(startClient);


        JMenu aboutMenu = new JMenu("About");
        JMenuItem authorsMI = new JMenuItem(new MenuItemAction("Authors", iconAuthors,
                KeyEvent.VK_N));
        aboutMenu.add(authorsMI);

        authorsMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(authorsMI,"SJSU CS 151 Term Project - Whiteboard"+"\nDanil Kolesnikov danil.kolesnikov@sjsu.edu" +
                        "\nMinh Phan minh.phan@sjsu.edu" +
                        "\nYulan Jin yulan.jin@sjsu.edu","Authors",1,iconAuthorsDialog);
            }
        });


        JMenuItem newMi = new JMenuItem(new MenuItemAction("Reset", iconNew,
                KeyEvent.VK_N));

        // Reset the canvas
        newMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.clearCanvas();
            }
        });

        JMenuItem openMi = new JMenuItem(new MenuItemAction("Open", iconOpen,
                KeyEvent.VK_O));
        JMenuItem saveMi = new JMenuItem(new MenuItemAction("Save", iconSave,
                KeyEvent.VK_S));
        JMenuItem savePNG = new JMenuItem(new MenuItemAction("Save PNG", iconSavePNG,
                KeyEvent.VK_S));

        // Exit button
        JMenuItem exitMi = new JMenuItem("Exit", iconExit);
        exitMi.setMnemonic(KeyEvent.VK_E);
        exitMi.setToolTipText("Exit whiteboard");
        exitMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                ActionEvent.CTRL_MASK));

        exitMi.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        // Add sub menus in the File menu
        fileMenu.add(newMi);
        fileMenu.add(openMi);
        fileMenu.add(saveMi);
        fileMenu.add(savePNG);
        fileMenu.addSeparator();
        fileMenu.add(exitMi);

        // Add 4 sub menus in the menu bar
        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(networkingMenu);
        menubar.add(aboutMenu);

        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.add(new InterfaceControl(controller,canvas), BorderLayout.WEST);
        this.add(menubar,BorderLayout.NORTH);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // Some helper function
    private class MenuItemAction extends AbstractAction {

        public MenuItemAction(String text, ImageIcon icon, Integer mnemonic) {
            super(text);
            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand()); // Prints the name of the button pressed
        }
    }

    // return either Canvas or InterfaceControl inside whiteboard
    public JPanel getComponentAt(String name){
        Component comp = null;
        for (Component child : this.getContentPane().getComponents()) {
            if(child.getName()==null){
                System.out.println("\nWeird error in getComponent() in Whiteboard.java");
            }
            else if (child.getName().equals(name)) {
                comp = child;
            }
        }
        return (JPanel)comp;
    }
}
