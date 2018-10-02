package data.crypto;

import data.crypto.CIOData.CryptoData;
import sec.*;
import java.io.*;

/**
 * This class deals with packing and sending Crypto Data.
 * 
 * @author Peter Cherniavsky
 */
public class CryptoOOS {
    /**
     * The output stream associated with the object.
     */
    private final OutputStream outputStream;
    /**
     * The ObjectOutputStream being used.
     */
    private final ObjectOutputStream out;
    /**
     * The cipher associated with this object.
     */
    private Cryptable cipher;
    
    /**
     * This constructor simply does exactly what the parent would do.
     * 
     * @param out The OutputStream
     * @param cipher The cipher being used.
     * @throws IOException 
     */
    public CryptoOOS(OutputStream out, Cryptable cipher) throws IOException {
        this.outputStream = out;
        this.out = new ObjectOutputStream(out);
        this.cipher = cipher;
    }
    
    /**
     * This function gets the output stream associated with this object.
     * 
     * @return The output stream.
     */
    public OutputStream getOutputStream() {
        return (outputStream);
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
     * This function packs and write CryptoData.
     * 
     * @param trans The data to be packed in CryptoData.
     * @throws IOException 
     */
    public void cryptoWriteObject(Serializable trans) throws IOException {
        out.writeObject(new CryptoData(trans, cipher));
    }
}
