package sec;

import java.security.interfaces.*;
import javax.crypto.*;
import sec.keychain.AsymmetricCipherManager;

/**
 *
 * @author Peter Cherniavsky
 */
public class AsymmetricCipher implements Cryptable {
    /**
     * This is the cipher used for decryption. It is shard data.
     */
    private Cipher decrypt;
    /**
     * The public key to be sent to a client.
     */
    private RSAPublicKey publicKey;
    
    /**
     * The constructor for the object.
     */
    public AsymmetricCipher() {
        try {            
            // Gets public key.
            publicKey = AsymmetricCipherManager.getPublic();
            
            // Sets up the decrypt cipher.
            decrypt = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            decrypt.init(Cipher.DECRYPT_MODE, AsymmetricCipherManager.getPrivate());
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * This function decrypts data. It deals with shared data so it is synchronized.
     * 
     * @param data the data to be decrypted.
     * @return The decrypted data.
     */
    @Override public byte[] decrypt(byte[] data) {
        try {
            return (decrypt.doFinal(data));
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (null);
    }
    
    @Override public byte[] encrypt(byte[] data) throws Exception {
        throw new Exception("Do not use this function");
    }
    
    /**
     * Functions returns the public key.
     * 
     * @return The public key.
     */
    public RSAPublicKey getKey() {
        return (publicKey);
    }
}