package server;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * The class that contains and extends the function of a server.
 * 
 * @author Peter Cherniavsky
 */
public class ServerMain extends Thread {
    /**
     * This field is used to contain the ServerSocket.
     */
    private final ServerSocket server;
    /**
     * The list of clients that are connected to the server. The data is shared.
     */
    private volatile LinkedList<Client> clientList;
    /**
     * This variable is used to show that the server is running. The data is shared. The only shared data not modified
     * using the modifSharedData method.
     */
    private volatile AtomicBoolean isRunning;
    /**
     * The variable is used to track the number of sessions. The data is shared.
     */
    private volatile int numSessions;
    
    /**
     * The constructor.
     * 
     * @param server The ServerSocket that this class will be attached to.
     */
    public ServerMain(ServerSocket server) {
        this.server = server;
        this.clientList = new LinkedList<>();
        this.isRunning = new AtomicBoolean(true);
        this.numSessions = 0;
    }
    
    /**
     * The thread code.
     */
    @Override public void run() {
        Socket connection;
        
        // Adds a client if there is room.
        while (true) {
            try {
                connection = server.accept();
                if (!addClient(connection)) {
                    connection.close();
                }
            }
            catch (IOException e) {
                isRunning.set(false);
                return;
            }
        }
    }
    
    /**
     * Shuts down the server.
     * 
     * @return true if it works and false otherwise.
     */
    public boolean shutdownServer() {
        try {
            // Closes the ServerSocket.
            server.close();
            // Waits until the server stops accepting connections.
            while (isRunning.get()) {
                Thread.sleep(1000);
            }
            // Shuts down all the clients connected.
            modifySharedData(false, true, false, null);
            return (true);
        }
        catch (IOException e) {
            return (false);
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (false);
    }
    
    /**
     * Removes a client from the server.
     * 
     * @param client The client being removed.
     */
    public void removeClient(Client client) {
        if (isRunning.get()) {
            modifySharedData(false, false, false, client);
        }
    }
    
    /**
     * Adds a client.
     * 
     * @param connection Socket used to set up a Client.
     * @return true if successful and false otherwise.
     */
    public boolean addClient(Socket connection) {
        return(modifySharedData(false, false, true, new Client(connection, this)));
    }
    
    /**
     * Adds a session, Meaning post authentication Client.
     * 
     * @param client
     * @return true if successful and false otherwise.
     */
    public boolean addSession(Client client) {
        return(modifySharedData(true, false, false, client));
    }
    
    /**
     * A synchronized method meaning it runs each thread at a time. It is a badly written method used to modify shared data.
     * Modifies all but the isRunning variable.
     * 
     * @param addSessions True if you want to add a session.
     * @param removeAll True if you want to remove all connections.
     * @param add True if you want to add a client.
     * @param client False if you want to remove a client.
     * @return true if successful and false otherwise.
     */
    private synchronized boolean modifySharedData(boolean addSessions, boolean removeAll, boolean add, Client client) {
        // Removes each cliient via a loop.
        if (removeAll) {
            // Shuts down each client and removes it.
            while (clientList.size() > 0) {
                clientList.get(0).shutdownClient();
                clientList.remove(0);
            }
            // Sets sessions to zero.
            numSessions = 0;
            // Updates the stats on the UI.
            UserInterface.updateStats(0, 0);
            return(true);
        }
        // Incrments the session count.
        if (addSessions) {
            // Checks if there are too many sessions already.
            if (numSessions < 1) {
                // Increments the counter.
                numSessions++;
                // Sets the client to in session.
                client.setInSession();
                // Updates the stats on the UI.
                UserInterface.updateStats(clientList.size(), numSessions);
                return(true);
            }
            // If not returns false.
            else {
                return (false);
            }
        }
        // Adds a client.
        if (add) {
            // Checks if there are already too may connections.
            if (clientList.size() < 3) {
                // Adds a client to the list.
                clientList.add(client);
                // Starts the client.
                clientList.get(clientList.size() - 1).start();
                // Updates the stats.
                UserInterface.updateStats(clientList.size(), numSessions);
                return (true);
            }
            // Otherwise returns false.
            else {
                return (false);
            }
        }
        // Removes a client.
        else {
            // If it is in session it decrements the variable.
            if (client.isInSession()) {
                numSessions--;
            }
            // Shuts down the client.
            client.shutdownClient();
            // Removes it from the client list.
            clientList.remove(client);
            // Updates the stats in the UI.
            UserInterface.updateStats(clientList.size(), numSessions);
            return (true);
        }
    }
}