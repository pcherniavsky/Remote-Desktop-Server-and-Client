package server;

import sec.keychain.AsymmetricCipherManager;
import sec.Password;
import gui.*;
import exceptions.*;
import java.awt.Panel;
import java.awt.event.*;
import java.net.ServerSocket;

/**
 * The user interface class. This is the class that contains the main method.
 *
 * @author Peter Cherniavsky
 */
public class UserInterface {
    /**
     * Holds the server on status.
     */
    private static boolean serverOn = false;
    /**
     * Holds the SeverMain object.
     */
    private static ServerMain server = null;
    /**
     * Holds the label that contains the server on/off status.
     */
    private static LabelP serverInfo;
    /**
     * Holds the port number.
     */
    private static TextFieldP port;
    /**
     * Holds the password.
     */
    private static PasswordFieldP password;
    /**
     * Holds the label that contains the servers connections. Shared data so it is volatile.
     */
    private static volatile LabelP numConnections;
    /**
     * Holds the label that contains the servers sessions. Shared data so it is volatile.
     */
    private static volatile LabelP numSessions;
    /**
     * The field where you can enter the key field.
     */
    private static TextFieldP keyName;
    /**
     * This field is used to store the keys info.
     */
    private static LabelP keyInfo1;
    /**
     * This field is used to store the keys info.
     */
    private static LabelP keyInfo2;
    /**
     * This field is used to store the keys info.
     */
    private static LabelP keyInfo3;
    /**
     * This field just deals with error info.
     */
    private static LabelP keyInfo4;
    /**
     * The IP.
     */
    // private static LabelP ipLabel;
    
    /**
     * The main method that gets called first.
     *
     * @param args Arguments that don't matter.
     */
    public static void main(String[] args) {
        // Setts up the GUI.
        FrameP f = new FrameP(new SizeLoc(700, 600, 200, 200), "Server Interface");
        Panel p = new Panel(null);
        ButtonP start = new ButtonP(new SizeLoc(100, 100, 0, 0), "Start Server"),
                stop = new ButtonP(new SizeLoc(100, 100, 150, 0), "Stop Server"),
                generateKey = new ButtonP(new SizeLoc(120, 100, 150, 250), "Generate Key Pair"),
                saveKey = new ButtonP(new SizeLoc(100, 100, 270, 250), "Save Key Pair"),
                retrieveKey = new ButtonP(new SizeLoc(100, 100, 370, 250), "Retrive Key Pair");
        LabelP portLabel = new LabelP(new SizeLoc(100, 50, 275, 0), "Port:"),
                passwordLabel = new LabelP(new SizeLoc(100, 50, 275, 100), "Password:"),
                keyLabel = new LabelP(new SizeLoc(100, 50, 250, 200), "Key Pair Management:"),
                keyNameLabel = new LabelP(new SizeLoc(100, 50, 200, 350), "Key Pair Name:");
        serverInfo = new LabelP(new SizeLoc(150, 50, 50, 150), "Server is off.");
        numConnections = new LabelP(new SizeLoc(100, 50, 50, 200), "Connections: 0");
        numSessions = new LabelP(new SizeLoc(100, 50, 50, 250), "Sessions: 0");
        port = new TextFieldP(new SizeLoc(100, 50, 275, 50), "13694");
        password = new PasswordFieldP(new SizeLoc(100, 50, 275, 150));
        keyName = new TextFieldP(new SizeLoc(100, 50, 300, 350));
        keyInfo1 = new LabelP(new SizeLoc(100, 50, 200, 400), "Key Pair Info 1.");
        keyInfo2 = new LabelP(new SizeLoc(500, 50, 200, 450), "Key Pair Info 2.");
        keyInfo3 = new LabelP(new SizeLoc(500, 50, 200, 500), "Key Pair Info 3.");
        keyInfo4 = new LabelP(new SizeLoc(300, 50, 400, 400), "Key Pair Error Info.");
        // ipLabel = new LabelP(new SizeLoc(150, 50, 0, 300), "Server IP: ");
        
        start.addMouseListener(new ButtonInstructions("on"));
        stop.addMouseListener(new ButtonInstructions("off"));
        generateKey.addMouseListener(new KeyInstruction("generate"));
        saveKey.addMouseListener(new KeyInstruction("save"));
        retrieveKey.addMouseListener(new KeyInstruction("retrieve"));
        p.add(start);
        p.add(stop);
        p.add(serverInfo);
        p.add(numConnections);
        p.add(numSessions);
        p.add(port);
        p.add(portLabel);
        p.add(password);
        p.add(passwordLabel);
        p.add(keyLabel);
        p.add(generateKey);
        p.add(saveKey);
        p.add(retrieveKey);
        p.add(keyName);
        p.add(keyNameLabel);
        p.add(keyInfo1);
        p.add(keyInfo2);
        p.add(keyInfo3);
        p.add(keyInfo4);
        // p.add(ipLabel);
        
        f.addWindowListener(new WindowInstruction());
        f.add(p);
        f.setVisible(true);
    }
    
    /**
     * The instructions needed to close the window.
     */
    private static class WindowInstruction extends WindowAdapter {
        /**
         * Function handles the window closing event.
         *
         * @param e The event.
         */
        @Override public void windowClosing(WindowEvent e) {
            if (serverOn) {
                server.shutdownServer();
                serverOn = false;
            }
            System.exit(0);
        }
    }
    
    /**
     * This is the class that deals with the key generation instructions.
     * 
     */
    private static class KeyInstruction extends MouseAdapter {
        /**
         * This is the string that tells the class which actions to interpret.
         */
        private final String type;
        
        /**
         * The constructor that sets the type.
         * 
         * @param type The button type.
         */
        public KeyInstruction(String type) {
            this.type = type;
        }
        
        /**
         * The function that processes the mouse being pressed.
         * 
         * @param e The mouse event.
         */
        @Override public void mousePressed(MouseEvent e) {
            String text = keyName.getText();
            
            if (serverOn) {
                setKeyText("Server is on");
                return;
            }
            
            if (!type.equals("save") && text.length() == 0) {
                setKeyText("Empty String");
                return;
            }
            
            if (type.equals("generate")) {
                AsymmetricCipherManager.generate(text);
                setKeyText("");
            }
            else if (type.equals("save")) {
                if (keyName.getText().length() == 0) {
                    try {
                        AsymmetricCipherManager.save();
                        setKeyText("");
                    }
                    catch (KeyProcessException E) {
                        setKeyText(E.getMessage());
                    }
                }
                else {
                    try {
                        AsymmetricCipherManager.save(text);
                        setKeyText("");
                    }
                    catch (KeyProcessException E) {
                        setKeyText(E.getMessage());
                    }
                }
            }
            else if (type.equals("retrieve")) {
                try { 
                    AsymmetricCipherManager.retrieve(text);
                    setKeyText("");
                }
                catch (KeyProcessException E) {
                    setKeyText(E.getMessage());
                }
            }
        }
        
        /**
         * Full string sets it to that string. Empty string sets it to the ACM contents.
         * 
         * @param text The message text.
         */
        private void setKeyText(String text) {
            if (text.length() == 0) {
                String info = AsymmetricCipherManager.getStringRep();
                
                keyInfo1.setText(info.split("\n")[0]);
                keyInfo2.setText(info.split("\n")[1]);
                keyInfo3.setText(info.split("\n")[2]);
                keyInfo4.setText("");
            }
            else {
                keyInfo4.setText("Error: " + text);
            }
        }
    }
    
    /**
     * The instruction for the on button.
     */
    private static class ButtonInstructions extends MouseAdapter {
        private String type;
        
        public ButtonInstructions(String type) {
            this.type = type;
        }
        /**
         * Function handles the mouse being pressed.
         *
         * @param e The event.
         */
        @Override public void mousePressed(MouseEvent e) {
            if (AsymmetricCipherManager.getStringRep() == null) {
                serverInfo.setText("No key Pair Loaded.");
                return;
            }
            
            if (type.equals("on")) {
                if (!serverOn) {
                    try {
                        password.setVisible(false);
                        Password.setPassword(password.getPassword());
                        ServerSocket ss = new ServerSocket(Integer.parseInt(port.getText()));
                        // ipLabel.setText("Server IP: " + ss.getInetAddress().getHostAddress());
                        server = new ServerMain(ss);
                        server.start();
                        serverOn = true;
                        serverInfo.setText("Server is on.");
                    }
                    catch (NumberFormatException E) {
                        serverInfo.setText("Invalid Port.");
                        password.setVisible(true);
                    }
                    catch (IllegalArgumentException E) {
                        serverInfo.setText("Invalid Port.");
                        password.setVisible(true);
                    }
                    catch (Exception E) {
                        if (E.getMessage().equals("Empty Password")) {
                            serverInfo.setText("Empty Password.");
                        }
                        else {
                            serverInfo.setText("Error.");
                        }
                        password.setVisible(true);
                    }
                }
            }
            else if (type.equals("off")) {
                if (serverOn) {
                    server.shutdownServer();
                    serverOn = false;
                    serverInfo.setText("Server is off.");
                    password.setVisible(true);
                }
            }
        }
    }
    
    /**
     * The function used to update the label statistics.
     *
     * @param numConnections The number of connections to the server.
     * @param numSessions The number of sessions in the server.
     */
    public static void updateStats(int numConnections, int numSessions) {
        UserInterface.numConnections.setText("Connections: " + numConnections);
        UserInterface.numSessions.setText("Sessions: " + numSessions);
    }
}