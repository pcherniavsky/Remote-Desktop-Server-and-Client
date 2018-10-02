package sec.keychain;

import sec.Hash;
import java.security.interfaces.*;
import java.io.*;
import java.util.Arrays;

/**
 * This class holds the info associated with an asymmetric cipher public key.
 * 
 * @author Peter
 */
public class AsymmetricCipherInfo implements Serializable {
    /**
     * The public key.
     */
    private final RSAPublicKey publicKey;
    /**
     * The public key hash.
     */
    private final byte [] publicKeyHash;
    /**
     * The related IP.
     */
    private final String ip;
    
    /**
     * The constructor. Just takes the public key.
     * 
     * @param publicKey The public key.
     * @param ip The IP.
     */
    public AsymmetricCipherInfo(RSAPublicKey publicKey, String ip) {
        this.publicKey = publicKey;
        this.publicKeyHash = Hash.digest(publicKey.getEncoded());
        this.ip = ip;
    }
    
    /**
     * Checks if two info objects are equal.
     * 
     * @param object The object to be checked.
     * @return True if equal and false otherwise.
     */
    @Override public boolean equals(Object object) {
        AsymmetricCipherInfo aci;
        byte [] currentKeyEncoded, passedKeyEncoded;
        
        // Checks if they are event he same type.
        if (!(object instanceof AsymmetricCipherInfo)) {
            return (false);
        }
        
        aci = (AsymmetricCipherInfo) object;
        
        // Checks their key encoded value.
        currentKeyEncoded = getEncoded();
        passedKeyEncoded = aci.getEncoded();
        
        // Checks the length and then the actual values.
        if (currentKeyEncoded.length != passedKeyEncoded.length) {
            return (false);
        }
        
        // Checks the public key.
        for (int i = 0; i < currentKeyEncoded.length; i++) {
            if (currentKeyEncoded[i] != passedKeyEncoded[i]) {
                return (false);
            }
        }
        
        // Checks the IP.
        if (!aci.getIP().equals(getIP())) {
            return (false);
        }
        
        return (true);
    }
    
    /**
     * Gets the encoded value.
     * 
     * @return The encoded value.
     */
    private byte[] getEncoded() {
        return (publicKey.getEncoded());
    }
    
    /**
     * Gets the IP.
     * 
     * @return The related IP String.
     */
    private String getIP() {
        return (ip);
    }
    
    /**
     * The hashed string.
     * 
     * @return String version of the public key hash.
     */
    String getHashed() {
        return (Arrays.toString(publicKeyHash));
    }
    
    /**
     * Gets the string version of the object.
     * 
     * @return The string version of the object.
     */
    @Override public String toString() {
        return ("IP: " + getIP() + " Hash: " + getHashed());
    }
}