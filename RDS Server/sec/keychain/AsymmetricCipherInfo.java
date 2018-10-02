package sec.keychain;

import sec.Hash;
import java.security.*;
import java.io.*;
import java.util.*;

/**
 * The object that stores info about a keyPair.
 * 
 * @author Peter Cherniavsky
 */
class AsymmetricCipherInfo implements Serializable {
    /**
     * The key pair.
     */
    private final KeyPair kp;
    /**
     * The name of the key.
     */
    private String name;
    /**
     * The date of the keys creation.
     */
    private final Date date;
    /**
     * The md5 digest of the public key.
     */
    private final byte [] publicKeyDigest;
    
    /**
     * The constructor.
     * 
     * @param kp The key pair.
     * @param name The name.
     */
    public AsymmetricCipherInfo(KeyPair kp, String name) {
        this.kp = kp;
        this.name = name.toLowerCase();
        this.date = new Date();
        this.publicKeyDigest = Hash.digest(kp.getPublic().getEncoded());
    }
    
    /**
     * Gets the key pair.
     * 
     * @return The key pair.
     */
    KeyPair getKeyPair() {
        return (kp);
    }
    
    /**
     * Gets the name.
     * 
     * @return The name.
     */
    String getName() {
        return (name);
    }
    
    /**
     * Sets the name.
     * 
     * @param name The name.
     */
    void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the string version of the date.
     * 
     * @return The date.
     */
    private String getDate() {
        return (date.toString());
    }
    
    /**
     * Gets the string version of the public key digest.
     * 
     * @return The public key digest.
     */
    private String getPublicKeyDigest() {
        return (Arrays.toString(publicKeyDigest));
    }
    
    /**
     * Gets the string version of the object.
     * 
     * @return The string version of the object.
     */
    String getStringRep() {
        return ("Name: " + getName() + "\n Date Created: " + getDate() + "\n Digest: " + getPublicKeyDigest());
    }
}