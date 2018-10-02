package client.events;

import data.*;
import data.crypto.CryptoOOS;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

/**
 * Deals with mouse instructions.
 * 
 * @author Peter
 */
public class MouseInstruction extends MouseAdapter {
    /**
     * The output stream.
     */
    private final CryptoOOS out;
    /**
     * The socket.
     */
    private final Socket s;
    
    /**
     * The constructor.
     * 
     * @param out The output stream.
     * @param s The socket.
     */
    public MouseInstruction(CryptoOOS out, Socket s) {
        this.out = out;
        this.s = s;
    }
    
    /**
     * Sends the mouse location to the server.
     *
     * @param e The mouse event.
     */
    @Override public void mouseMoved(MouseEvent e) {
        try {
            out.cryptoWriteObject(new MouseMoveData(e.getX(), e.getY()));
        }
        catch (IOException E) {
            return;
        }
    }
    
    /**
     * Sends the mouse location to the server.
     *
     * @param e The mouse event.
     */
    @Override public void mouseDragged(MouseEvent e) {
        try {
            out.cryptoWriteObject(new MouseMoveData(e.getX(), e.getY()));
        }
        catch (IOException E) {
            return;
        }
    }
    
    /**
     * Sends the mouse press instructions.
     *
     * @param e The mouse event.
     */
    @Override public void mousePressed(MouseEvent e) {
        try {
            // Converts the mouse press instructions to the ones that can be used by Robot and sends them.
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    out.cryptoWriteObject(new MousePressData(InputEvent.BUTTON1_DOWN_MASK));
                    break;
                case MouseEvent.BUTTON2:
                    out.cryptoWriteObject(new MousePressData(InputEvent.BUTTON2_DOWN_MASK));
                    break;
                case MouseEvent.BUTTON3:
                    out.cryptoWriteObject(new MousePressData(InputEvent.BUTTON3_DOWN_MASK));
                    break;
                default:
                    break;
            }
        }
        catch (IOException E) {
            return;
        }
    }
    
    /**
     * Sends the mouse release instructions.
     *
     * @param e The mouse event.
     */
    @Override public void mouseReleased(MouseEvent e) {
        try {
            // Converts the mouse press instructions to the ones that can be used by Robot and sends them.
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    out.cryptoWriteObject(new MouseReleaseData(InputEvent.BUTTON1_DOWN_MASK));
                    break;
                case MouseEvent.BUTTON2:
                    out.cryptoWriteObject(new MouseReleaseData(InputEvent.BUTTON2_DOWN_MASK));
                    break;
                case MouseEvent.BUTTON3:
                    out.cryptoWriteObject(new MouseReleaseData(InputEvent.BUTTON3_DOWN_MASK));
                    break;
                default:
                    break;
            }
        }
        catch (Exception E) {
            return;
        }
    }
    
    /**
     * Sends the mouse wheel instructions.
     *
     * @param e The mouse event.
     */
    @Override public void mouseWheelMoved(MouseWheelEvent e) {
        try {
            out.cryptoWriteObject(new MouseScrollData(e.getWheelRotation()));
        }
        catch (IOException E) {
            return;
        }
    }
}
