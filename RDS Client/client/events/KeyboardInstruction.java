package client.events;

import data.*;
import data.crypto.CryptoOOS;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

/**
 * The class deals with keyboard instructions.
 * 
 * @author Peter
 */
public class KeyboardInstruction extends KeyAdapter {
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
     * @param out Output stream.
     * @param s The socket.
     */
    public KeyboardInstruction(CryptoOOS out, Socket s) {
        this.out = out;
        this.s = s;
    }
        
    /**
     * Sends key press instructions to the server.
     * 
     * @param e The key event.
     */
    @Override public void keyPressed(KeyEvent e) {
        try {
            out.cryptoWriteObject(new KeyPressData(e.getKeyCode()));
        }
        catch (IOException E) {
            return;
        }
    }
        
    /**
     * Sends the key release instruction to the server,
     * 
     * @param e The key event.
     */
    @Override public void keyReleased(KeyEvent e) {
        try {
            // This is here to compensate for alt tab not being received by KeyEvent.
            // You need alt tab to undo some of the effects of reconnecting to the server after you have already done so.
            if (e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
                out.cryptoWriteObject(new KeyPressData(KeyEvent.VK_ALT));
                out.cryptoWriteObject(new KeyPressData(KeyEvent.VK_CONTROL));
                out.cryptoWriteObject(new KeyPressData(KeyEvent.VK_TAB));
                out.cryptoWriteObject(new KeyReleaseData(KeyEvent.VK_ALT));
                out.cryptoWriteObject(new KeyReleaseData(KeyEvent.VK_CONTROL));
                out.cryptoWriteObject(new KeyReleaseData(KeyEvent.VK_TAB));
                return;
            }
            out.cryptoWriteObject(new KeyReleaseData(e.getKeyCode()));
        }
        catch (IOException E) {
            return;
        }
    }
}
