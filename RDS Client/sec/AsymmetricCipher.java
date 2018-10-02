package sec;

import java.security.interfaces.*;
import javax.crypto.*;

/**
 *
 * @author Peter Cherniavsky
 */
public class AsymmetricCipher implements Cryptable {
    /**
     * This is the cipher used for encryption. It is shard data.
     */
    private Cipher encrypt;
    
    /**
     * Sets up the Asymmetric Cipher so that it can be used for encryption.
     * 
     * @param publicKey The public key used for encryption.
     */
    public AsymmetricCipher(RSAPublicKey publicKey) {
        try {
            // Sets up the encrypt and decrypt ciphers.
            encrypt = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, publicKey);
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * This function encrypts data. It deals with shard data so it is synchronized.
     * 
     * @param data the data to be encrypted.
     * @return The encrypted data.
     */
    @Override public byte[] encrypt(byte[] data) {
        try {
            // Max length can only be 90 byts.
            return (encrypt.doFinal(data));
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (null);
    }
    
    /**
     * Do not use this function.
     * 
     * @param data
     * @return Nothing.
     * @throws Exception 
     */
    @Override public byte[] decrypt(byte[] data) throws Exception {
        throw new Exception("You can not use this function");
    }
}
