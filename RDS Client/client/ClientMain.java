package client;

import data.crypto.*;
import data.*;
import sec.*;
import client.events.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.concurrent.atomic.*;
import java.security.interfaces.RSAPublicKey;

/**
 * This represents the client window.
 *
 * @author Peter Cherniavsky
 */
public class ClientMain extends Thread {
    /**
     * The IP.
     */
    private final String IP;
    /**
     * The port.
     */
    private final String port;
    /**
     * The variable that is checked before making any thread unsafe calls to the UI control methods or to the frame methods.
     */
    private final AtomicBoolean on;
    
    /**
     * The constructor for the class.
     *
     * @param IP The IP string.
     * @param port The port number.
     */
    public ClientMain(String IP, String port) {
        this.IP = IP;
        this.port = port;
        this.on = new AtomicBoolean(false);
    }
    
    /**
     * The code run by the thread.
     */
    @Override public void run() {
        try {
            // Sets up the variables.
            UserInterface.updateStatus("Connecting");
            Socket s = new Socket(IP, Integer.parseInt(port));
            
            CryptoOIS in = new CryptoOIS(s.getInputStream(), new ZeroCipher());
            CryptoOOS out = new CryptoOOS(s.getOutputStream(), new ZeroCipher());
            
            if (!exchangeCryptoInfo(in, out)) {
                s.close();
                UserInterface.doneRunningError();
                return;
            }
            
            // Authenticates the client.
            if (!authenticate(in, out)) {
                s.close();
                UserInterface.doneRunningError();
                return;
            }
            
            // Sets up the frame.
            Frame f = new Frame();
            
            f.setSize(1920,1080);
            f.setLocation(0,0);
            f.addMouseListener(new MouseInstruction(out, s));
            f.addMouseMotionListener(new MouseInstruction(out,s));
            f.addMouseWheelListener(new MouseInstruction(out,s));
            f.addKeyListener(new KeyboardInstruction(out, s));
            f.addWindowListener(new WindowInstruction(f, s, on));
            f.setUndecorated(true);
            f.setVisible(true);
            
            // Runs the screen shot recvier.
            new ScreenshotReceiver(in, f, f.getGraphics()).start();
            
            // Closes the UI window and lets it know that the Client is running and allows the closing of the current UI.
            UserInterface.startRunning();
            on.set(true);
        }
        catch (IOException | IllegalArgumentException e) {
            // Tells the UI that the client is done running and that it fails.
            UserInterface.doneRunningError();
        }
    }
    
    /**
     * This changes the crypto information.
     *
     * @param in The input stream.
     * @param out The output stream.
     * @return true if successful and false otherwise.
     */
    private boolean exchangeCryptoInfo(CryptoOIS in, CryptoOOS out) {
        try {
            // Sets up the variables.
            SymmetricCipherInfo sci = new SymmetricCipherInfo();
            RSAPublicKey publicKey = (RSAPublicKey) in.cryptoReadObject();
            
            // Checks to see if the key is new and asks the user if they want to continue.
            if (!UserInterface.addKey(publicKey, IP)) {
                return (false);
            }
            
            // Sets up the asymmetric cipher.
            AsymmetricCipher ac = new AsymmetricCipher(publicKey);
            
            // Changes the cipher and sends the encrypted key info.
            out.changeCipher(ac);
            out.cryptoWriteObject(sci);
            
            // Sets the symmetric cipher to the new key info.
            out.changeCipher(new SymmetricCipher(sci));
            in.changeCipher(new SymmetricCipher(sci));
            
            return (true);
        }
        catch (IOException e) {
            return (false);
        }
    }
    
    /**
     * @param in The Input Stream used to receive data.
     * @param out The OUtput Stream used to send data.
     * @return true if everything works out fine and false otherwise.
     */
    private boolean authenticate(CryptoOIS in, CryptoOOS out) {
        try {
            ConfigData cd;
            
            // Updates the status.
            UserInterface.updateStatus("Authenticating");
            
            // Authenticates.
            out.cryptoWriteObject(new ConfigData(Password.getPassword(), ""));
            
            cd = (ConfigData) in.cryptoReadObject();
            
            if (!cd.getMessage().equals("good")) {
                return (false);
            }
            else {
                return (true);
            }
        }
        catch (IOException e) {
            return (false);
        }
    }
    
    /**
     * This class deals with receiving screen shots.
     */
    private static class ScreenshotReceiver extends Thread {
        /**
         * The input stream that receives the screen shots.
         */
        private final CryptoOIS in;
        /**
         * The Frame that gets painted.
         */
        private final Frame f;
        /**
         * The graphics object that gets attached the frame.
         */
        private final Graphics g;
        
        /**
         * The constructor for the object.
         *
         * @param in Input stream.
         * @param f Frame.
         * @param g Graphics.
         */
        public ScreenshotReceiver(CryptoOIS in, Frame f, Graphics g) {
            this.in = in;
            this.g = g;
            this.f = f;
            f.paint(g);
        }
        
        /**
         * The thread code.
         */
        @Override public void run() {
            ScreenshotData SD;
            
            // Loop that recives the screenshot.
            try {
                while (true) {
                    SD = (ScreenshotData) in.cryptoReadObject();
                    g.drawImage(ImageIO.read(new ByteArrayInputStream(SD.getData())), 0, 0, 1920, 1080, Color.black, null);
                }
            }
            catch (IOException e) {
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}