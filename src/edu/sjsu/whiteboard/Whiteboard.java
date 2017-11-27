package edu.sjsu.whiteboard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Whiteboard extends JFrame {
    private Controller controller;


    public Whiteboard(Controller controller){
        super("Whiteboard");

        JMenuBar menubar = new JMenuBar();

        ImageIcon iconNew = new ImageIcon("new.png");
        ImageIcon iconOpen = new ImageIcon("open.png");
        ImageIcon iconSave = new ImageIcon("save.png");
        ImageIcon iconExit = new ImageIcon("exit.png");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu networkingMenu = new JMenu("Networking");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem startServ = new JMenuItem(new MenuItemAction("Start Server", iconNew,
                KeyEvent.VK_N));

        JMenuItem startClient = new JMenuItem(new MenuItemAction("Start Client", iconNew,
                KeyEvent.VK_N));

        networkingMenu.add(startServ);
        networkingMenu.add(startClient);

        JMenuItem newMi = new JMenuItem(new MenuItemAction("New canvas", iconNew,
                KeyEvent.VK_N));

        newMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //canvas.clearCanvas();
            }
        });

        JMenuItem openMi = new JMenuItem(new MenuItemAction("Open", iconOpen,
                KeyEvent.VK_O));

        JMenuItem saveMi = new JMenuItem(new MenuItemAction("Save", iconSave,
                KeyEvent.VK_S));

        JMenuItem savePNG = new JMenuItem(new MenuItemAction("Save PNG", iconSave,
                KeyEvent.VK_S));

        JMenuItem exitMi = new JMenuItem("Exit", iconExit);
        exitMi.setMnemonic(KeyEvent.VK_E);
        exitMi.setToolTipText("Exit whiteboard");
        exitMi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                ActionEvent.CTRL_MASK));

        exitMi.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        fileMenu.add(newMi);
        fileMenu.add(openMi);
        fileMenu.add(saveMi);
        fileMenu.add(savePNG);
        fileMenu.addSeparator();
        fileMenu.add(exitMi);


        menubar.add(fileMenu);
        menubar.add(networkingMenu);

        this.controller = controller;
        this.setLayout(new BorderLayout());
        Canvas temp =  new Canvas(controller);
        this.add(temp, BorderLayout.CENTER);
        this.add(new InterfaceControl(controller,temp), BorderLayout.WEST);
        this.add(menubar,BorderLayout.NORTH);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);



    }

    private class MenuItemAction extends AbstractAction {

        public MenuItemAction(String text, ImageIcon icon,
                              Integer mnemonic) {
            super(text);

            putValue(SMALL_ICON, icon);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(e.getActionCommand());
        }
    }

    // return either Canvas or InterfaceControl inside whiteboard
    public JPanel getComponentAt( String name){
        Component comp = null;
        for (Component child : this.getContentPane().getComponents()) {
            if(child.getName()==null){
                System.out.print("Weird error");
            }
            else if (child.getName().equals(name)) {
                comp = child;
            }
        }
        return (JPanel)comp;
    }
}
