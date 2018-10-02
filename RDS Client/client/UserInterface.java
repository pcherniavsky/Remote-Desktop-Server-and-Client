package client;

import gui.*;
import sec.Password;
import sec.keychain.*;
import java.awt.Panel;
import java.awt.event.*;
import java.security.interfaces.RSAPublicKey;
import javax.swing.*;
import javax.swing.event.*;

/**
 * This is the UI class.
 * 
 * @author Peter
 */
public class UserInterface {
    /**
     * The Frame for the UI.
     */
    private static FrameP f;
    /**
     * The text field used for the input.
     */
    private static TextFieldP IP;
    /**
     * The text field used for the input.
     */
    private static TextFieldP port;
    /**
     * The password field used for the password.
     */
    private static PasswordFieldP password;
    /**
     * The label used to display the status.
     */
    private static LabelP status;
    /**
     * True if a session is in progress.
     */
    private static boolean inProgress = false;
    /**
     * The list used for string the key strings. Volatile because it is modified by multiple threads.
     */
    private static final JList<String> keyList = new JList<String>(AsymmetricCipherList.getStrRep());
    
    /**
     * The main method that gets jumped into at the start of the program.
     * @param args String argument that does not matter.
     */
    public static void main(String[] args) {
        // Sets up the UI.
        f = new FrameP(new SizeLoc(750, 600, 200, 200), "Client Start Screen");
        Panel p = new Panel(null);
        ButtonP connect = new ButtonP(new SizeLoc(100, 100, 0, 0), "Connect");
        LabelP IPLabel = new LabelP(new SizeLoc(100, 50, 0, 100), "IP: "), portLabel = new LabelP(new SizeLoc(100, 50, 0, 200), "Port: "),
                passwordLabel = new LabelP(new SizeLoc(100, 50, 300, 0), "Password: "),
                keyScrollListLabel = new LabelP(new SizeLoc(150, 50, 300, 100), "List of Trusted Keys:");
        JScrollPaneP keyScrollList = new JScrollPaneP(new SizeLoc(600, 350, 100, 150), keyList);
        password = new PasswordFieldP(new SizeLoc(100, 50, 300, 50));
        IP = new TextFieldP(new SizeLoc(100, 50, 0, 150), "192.168.1.154");
        port = new TextFieldP(new SizeLoc(100, 50, 0, 250), "13694");
        status = new LabelP(new SizeLoc(200, 50, 100, 0), "Status: Disconnected");
        
        connect.addMouseListener(new ConnectButtonMouse(f));
        keyList.getSelectionModel().addListSelectionListener(new KeyListInstruction());
        p.add(connect);
        p.add(status);
        p.add(IPLabel);
        p.add(IP);
        p.add(portLabel);
        p.add(port);
        p.add(passwordLabel);
        p.add(password);
        p.add(keyScrollList);
        p.add(keyScrollListLabel);
        
        f.addWindowListener(new WindowInstruction());
        f.add(p);
        f.setVisible(true);
    }
    
    /**
     * The list instructions.
     */
    private static class KeyListInstruction implements ListSelectionListener {
        /**
         * Called if there is a value change.
         * @param e The data associated with the click.
         */
        @Override public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() || keyList.isSelectionEmpty()) {
                return;
            }
            
            switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " +
                    keyList.getSelectedValue() + "?",
                    "Key Removal Conformation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
                case JOptionPane.YES_OPTION:
                    AsymmetricCipherList.removeKey(keyList.getSelectedValue());
                    keyList.setListData(AsymmetricCipherList.getStrRep());
                    break;
                default:
                    break;
            }
            
            keyList.clearSelection();
       }
    }
    
    /**
     * The instructions for closing the window.
     */
    private static class WindowInstruction extends WindowAdapter {
        /**
         * Called if the window is closing.
         * 
         * @param e The associated data.
         */
        @Override public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
    
    /**
     * Starts the process for connecting the client.
     */
    private static class ConnectButtonMouse extends MouseAdapter {
        FrameP f;
        
        public ConnectButtonMouse(FrameP f) {
            this.f = f;
        }
        
        @Override public void mousePressed(MouseEvent e) {
            if (!inProgress) {
                try {
                    Password.setPassword(password.getPassword());
                }
                catch (Exception E) {
                    if (E.getMessage().equals("Empty Password")) {
                        doneRunningError();
                        return;
                    }
                    else {
                        System.exit(-1);
                    }
                }
                inProgress = true;
                new ClientMain(IP.getText(),port.getText()).start();
            }
        }
    }
    
    /**
     * Hides the UI in favor of the client.
     */
    public static void startRunning() {
        updateStatus("Connected");
        f.setVisible(false);
    }
    
    /**
     * Shows the UI now that the client is closed.
     */
    public static void doneRunning() {
        updateStatus("Disconnected");
        f.setVisible(true);
        inProgress = false;
    }
    
    /**
     * Shows the UI now that the client is closed due to an error.
     */
    public static void doneRunningError() {
        doneRunning();
        updateStatus("Error");
    }
    
    /**
     * Updates the status label in the UI.
     * 
     * @param message The message for the status.
     */
    public static void updateStatus(String message) {
        status.setText("Status: " + message);
    }
    
    /**
     * Updates the key list.
     * 
     * @param publicKey The public key to be added.
     * @param ip The IP.
     * @return true if the connection should go forward and false otherwise.
     */
    public static boolean addKey(RSAPublicKey publicKey, String ip) {
        if (!AsymmetricCipherList.isKnownKey(publicKey, ip)) {
            switch(JOptionPane.showConfirmDialog(null,
             "<html>A new key with the " + new AsymmetricCipherInfo(publicKey, ip).toString() + " " +
                     "has been recived.<br>Press Yes to store the key and connect. " +
                     "Press Cancel to connect but not store the key. Press No to not store the key "
                     + "and disconnect.</html>",
                             "Unknown Public Key", JOptionPane.YES_NO_CANCEL_OPTION, 
                             JOptionPane.WARNING_MESSAGE)) {
                case JOptionPane.YES_OPTION: {
                    AsymmetricCipherList.addKey(publicKey, ip);
                    keyList.setListData(AsymmetricCipherList.getStrRep());
                    return (true);
                }
                case JOptionPane.CANCEL_OPTION:
                    return (true);
                default:
                    return (false);
            }
        }
        else {
            return (true);
        }
    }
}