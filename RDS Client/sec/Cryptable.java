package sec;

/**
 * This interface is used in Cipher classes.
 * 
 * @author Peter
 */
public interface Cryptable {
    /**
     * Encrypts data.
     * 
     * @param data Data to be encrypted.
     * @return The encrypted data.
     * @throws Exception if used improperly.
     */
    public byte[] encrypt(byte [] data) throws Exception;
    /**
     * Decrypts data.
     * 
     * @param data Data to be decrypted.
     * @return The decrypted data.
     * @throws Exception if used improperly.
     */
    public byte[] decrypt(byte [] data) throws Exception;
}
