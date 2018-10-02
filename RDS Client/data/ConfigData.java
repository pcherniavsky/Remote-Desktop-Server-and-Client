package data;

import java.io.Serializable;

/**
 * The purpose of this class is to hold data for the purpose of authentication and configuration.
 * @author Peter
 */
public class ConfigData implements Serializable {
    /**
     * The password in byte form.
     */
    private final byte [] password;
    /**
     * A string message.
     */
    private final String message;
    
    /**
     * The constructor.
     * 
     * @param password The password in byte form.
     * @param message The message in string form.
     */
    public ConfigData(byte [] password, String message) {
        this.password = password;
        this.message = message;
    }
    
    /**
     * Gets the password.
     * 
     * @return the password.
     */
    public byte [] getPassword() {
        return (password);
    }
    
    /**
     * Gets the message.
     * @return The string representation of the message.
     */
    public String getMessage() {
        return (message);
    }
}
