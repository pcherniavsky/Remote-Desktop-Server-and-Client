package client.events;

import client.UserInterface;
import java.awt.Frame;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class used for the window instruction. Closing.
 * 
 * @author Peter
 */
public class WindowInstruction extends WindowAdapter {
    Frame f;
    Socket s;
    AtomicBoolean on;
    
    /**
     * The constructor.
     * 
     * @param f The frame.
     * @param s The socket.
     * @param on The boolean variable used to check if something changed.
     */
    public WindowInstruction(Frame f, Socket s, AtomicBoolean on) {
        this.f = f;
        this.s = s;
        this.on = on;
    }
    
    /**
     * If the window closes this function handles the event.
     * 
     * @param e The window event.
     */
    @Override public void windowClosing(WindowEvent e) {
        if (!on.get()) {
            return;
        }
        try {
            // Closes the socket, shuts down the window, and tells the UI that the client is done running.
            s.close();
            f.dispose();
            UserInterface.doneRunning();
        }
        catch (IOException E) {
            System.exit(-1);
        }
    }
}