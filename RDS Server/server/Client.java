package server;

import sec.AsymmetricCipher;
import data.crypto.*;
import data.ConfigData;
import java.io.IOException;
import sec.*;
import java.net.*;
import sec.Password;

/**
 * This class represents one client. It is it's own thread.
 * @author Peter Cherniavsky
 */
public class Client extends Thread {
    /**
     * This field holds the socket connected to the client. Shared data.
    */
    private volatile Socket connection;
    /**
     * This field holds the server that this Client is connected to.
    */
    private final ServerMain server;
    /**
     * This field holds a boolean variable that says whether or not this client has become a session.
     * In session means that it has not just connected but also passed the authentication phase.
     * Besides the constructor this field is ONLY modified outside of the class.
     */
    private volatile boolean inSession;
    
    /**
     * This is the constructor that sets the field values of the client class.
     * @param connection The Socket connection the client is tied to.
     * @param server  The Server the client is tied to.
     */
    public Client(Socket connection, ServerMain server) {
        this.connection = connection;
        this.server = server;
        this.inSession = false;
    }
       
   /**
    * This is the new thread that is run via this class. It deals with the back and forth exchange of the connection.
    * It deals with receiving and sending data
    */
   @Override public void run() {
        try {
            // Sets up the variables used throughout the function.
            Object o;

            CryptoOOS out = new CryptoOOS(connection.getOutputStream(), new ZeroCipher());
            CryptoOIS in = new CryptoOIS(connection.getInputStream(), new ZeroCipher());
            
            if (!exchangeCryptoInfo(in, out)) {
                server.removeClient(this);
                return;
            }
            
            if (!authenticate(in, out)) {
                server.removeClient(this);
                return;
            }
            
            // Checks if there is any room left.
            if (!server.addSession(this)) {
                server.removeClient(this);
                return;
            }
            
            // Creates the ssperate thread that deals with just sending the screenshot.
            new ScreenshotSender(out).start();

            // Recives and interprutes instructions sent from the client.
            
            while (true) {
                o = in.cryptoReadObject();
                if (o == null) {
                    break;
                }
                if (!IOProcessor.Processor(o)) {
                    break;
                }
            }
            
            // Cleans up the Client assuming a graceful exit.
            server.removeClient(this);
                
        }
        
        // Cleans up the Client assuming a non graceful exit.
        catch (IOException e) {
            server.removeClient(this);
            return;
        }
   }
   
    /**
     * Function exchanges key info and sets the cipher to the shared symmetric key.
     * 
     * @param in The input stream.
     * @param out The output stream.
     * @return true if successful and false otherwise.
     */
    private boolean exchangeCryptoInfo(CryptoOIS in, CryptoOOS out) {
        try {
            // Sets up the variables.
            AsymmetricCipher ac = new AsymmetricCipher();
            SymmetricCipherInfo sci;
            out.cryptoWriteObject(ac.getKey());
            
            // Changes the cipher and recives thedecryed key info.
            in.changeCipher(ac);
            sci = (SymmetricCipherInfo) in.cryptoReadObject();
            
            in.changeCipher(new SymmetricCipher(sci));
            out.changeCipher(new SymmetricCipher(sci));
            
            return (true);
        }
        catch (IOException e) {
            return (false);
        }
   }
   
    /**
     * @param in The Input Stream used to receive data.
     * @param out The Input Stream used to send data.
     * @return true if everything works out fine and false otherwise.
     */
    private boolean authenticate(CryptoOIS in, CryptoOOS out) {
        try {
            ConfigData cd;
            
            cd = (ConfigData) in.cryptoReadObject() ;
            
            if (!Password.checkPassword(cd.getPassword())) {
                out.cryptoWriteObject(new ConfigData(null, "bad"));
                return (false);
            }
            else {
                out.cryptoWriteObject(new ConfigData(null, "good"));
                return (true);
            }
        }
        catch (IOException e) {
            return (false);
        }
   }
   
    /**
     * This function shuts the client down forcing a non graceful exit. This is only called from the ServerMain class.
     * @return If it was successful it return true. Otherwise false.
     */
    public boolean shutdownClient() {
        try {
            connection.close();
            return (true);
        }
        catch (IOException e) {
            return (false);
        }
    }
    
    /**
     * This function checks if the Client is in session. This is only called from the ServerMain class.
     * @return If the client is in session
     */
    public boolean isInSession() {
        if (inSession) {
            return (true);
        }
        else {
            return (false);
        }
    }
    
    /**
     * This function changes the status of Client to inSession.
     */
    public void setInSession() {
        inSession = true;
    }
    
    /**
     * This class just sends screen shots.
     */  
    private static class ScreenshotSender extends Thread {
       /**
        * This field holds the output stream needed to send data.
        */
        private final CryptoOOS out;
        
        /**
         * The constructor just sets the output stream that you are using to send data.
         * @param out 
         */
        public ScreenshotSender(CryptoOOS  out) {
            this.out = out;
        }
        
        /**
         * The thread that sends the screen shots.
         */
        @Override public void run() {
            try {
                // Sends the screnshots until the Client is disconnected.
                while (true) {
                    out.cryptoWriteObject(IOProcessor.getScreenshot());
                }
            }
            catch (IOException e) {
                return;
            }
        }
    }
}