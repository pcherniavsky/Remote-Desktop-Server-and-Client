package sec;

/**
 * This class in the public key reception stage of the key exchange.
 * 
 * @author Peter
 */
public class ZeroCipher implements Cryptable {
    /**
     * Just returns what it takes in.
     * 
     * @param data The data.
     * @return  The same data.
     */
    @Override public byte[] encrypt(byte[] data) {
        return (data);
    }
    
    /**
     * Just returns what it takes in.
     * 
     * @param data The data.
     * @return The same data.
     */
    @Override public byte [] decrypt(byte[] data) {
        return (data);
    }
}
