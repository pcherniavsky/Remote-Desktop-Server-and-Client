package sec;

import javax.crypto.*;
import javax.crypto.spec.*;
import data.crypto.SymmetricCipherInfo;

/**
 *
 * @author Peter Cherniavsky
 */
public class SymmetricCipher implements Cryptable {
    /**
     * This is the cipher used for encryption. It is shard data.
     */
    private Cipher encrypt;
    /**
     * This is the cipher used for decryption. It is shard data.
     */
    private Cipher decrypt;
    
    /**
     * The constructor sets up a symmetric cipher.
     * 
     * @param sci The info needed to set up the Cipher.
     */
    public SymmetricCipher(SymmetricCipherInfo sci) {
        try {
            // Creates the secrete key.
            SecretKey key = new SecretKeySpec(sci.getKeyEncoded(), 0, sci.getKeyEncoded().length, "AES");
            
            // Sets up the initialization vector.
            IvParameterSpec ivSpec = new IvParameterSpec(sci.getIv());
            
            // Sets up the Ciphers.
            encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decrypt.init(Cipher.DECRYPT_MODE, key, ivSpec);
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * This function encrypts data.
     * 
     * @param data the data to be encrypted.
     * @return The encrypted data.
     */
    @Override public byte[] encrypt(byte[] data) {
        try {
            return (encrypt.doFinal(data));
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (null);
    }
    
    /**
     * This function decrypts data.
     * 
     * @param data the data to be decrypted.
     * @return The decrypted data.
     */
    @Override public byte [] decrypt(byte[] data) {
        try {
            return (decrypt.doFinal(data));
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (null);
    }
}
