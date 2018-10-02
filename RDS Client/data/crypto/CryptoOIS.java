package data.crypto;

import data.crypto.CIOData.CryptoData;
import sec.*;
import java.io.*;

/**
 * This class automatically deals with the unpacking of CryptoData.
 * 
 * @author Peter Cherniavsky
 */
public class CryptoOIS {
    /**
     * The input Stream associated with the object.
     */
    private final InputStream inputStream;
    /**
     * The ObjectInputStream being used.
     */
    private final ObjectInputStream in;
    /**
     * The cipher associated with this object.
     */
    private Cryptable cipher;
    
    /**
     * This constructor simply does exactly what the parent would do.
     * 
     * @param in The InputStream
     * @param cipher The cipher to be used.
     * @throws IOException 
     */
    public CryptoOIS(InputStream in, Cryptable cipher) throws IOException {
        this.inputStream = in;
        this.in = new ObjectInputStream(in);
        this.cipher = cipher;
    }
    
    /**
     * Gets the input stream;
     * 
     * @return The input stream.
     */
    public InputStream getInputStream() {
        return (inputStream);
    }
    
    /**
     * This changes the Cipher used for encryption in this object.
     * 
     * @param cipher 
     */
    public void changeCipher(Cryptable cipher) {
        this.cipher = cipher;
    }
    
    /**
     * This function reads in CryptoData and unpacks it.
     * 
     * @return The unpacked CryptoData
     * @throws IOException
     */
    public Serializable cryptoReadObject() throws IOException {
        try {
            CryptoData cd = (CryptoData) in.readObject();
            return (cd.getCryptoObject(cipher));
        }
        catch (ClassNotFoundException e) {
            System.exit(-1);
        }
        return (null);
    }
}
