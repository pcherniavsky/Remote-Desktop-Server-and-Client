package data.crypto;

import java.io.*;
import java.security.*;
import javax.crypto.*;

/**
 * This class is used to contain info needed for the creation of a symmetric cipher.
 * 
 * @author Peter
 */
public class SymmetricCipherInfo implements Serializable {
    /**
     * The key used for the AES cipher.
     */
    private byte [] keyEncoded;
    /**
     * The iv needed to for CBC type encryption.
     */
    private byte[] iv;
    
    /**
     * The default constructor that is used for a new set of info.
     */
    public SymmetricCipherInfo() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, new SecureRandom());
            this.keyEncoded = kg.generateKey().getEncoded();
            
            iv = new byte[16];
            new SecureRandom().nextBytes(iv);
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }

    /**
     * Returns the key.
     * 
     * @return The key.
     */
    public byte[] getKeyEncoded() {
        return (keyEncoded);
    }

    /**
     * Returns the iv.
     * 
     * @return The iv.
     */
    public byte[] getIv() {
        return (iv);
    }
}