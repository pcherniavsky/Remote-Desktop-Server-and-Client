package sec;

import java.security.*;

/**
 * This class will be used by the rest of the programs to hash data.
 * 
 * @author Peter Cherniavsky
 */
public class Hash {
    /**
     * The object that holds the hash object for messages.
     */
    private static MessageDigest digest;
    /**
     * The object that holds the hash object for passwords.
     */
    private static MessageDigest password;
    
    // This sets up the hasing object.
    static {
        try {
            password = MessageDigest.getInstance("sha-512");
            digest = MessageDigest.getInstance("MD5");
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * This function hashes the byte array that is passed to it.
     * 
     * @param password The password to be hashed.
     * @return Returns the hashed password.
     */
    public static synchronized byte [] hashPassword(byte [] password) {
        return(Hash.password.digest(password));
    }
    
    /**
     * This function hashes the byte array that is passed to it.
     * 
     * @param data The data to be hashed.
     * @return The hashed data.
     */
    public static synchronized byte [] digest(byte [] data) {
        return(digest.digest(data));
    }
}