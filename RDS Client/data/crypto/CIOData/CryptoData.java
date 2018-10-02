package data.crypto.CIOData;

import java.io.*;
import sec.Cryptable;

/**
 * This class stores and encrypts objects.
 * 
 * @author Peter Cherniavsky
 */
public class CryptoData implements Serializable {
    /**
     * The encrypted byte array representation of the packed object.
     */
    private byte [] objectCryptoByte;
    
    /**
     * The constructor takes in a transmittable object and converts it to an encrypted byte array.
     * 
     * @param trans The object to be packed.
     * @param sc The cipher being used.
     */
    public CryptoData(Serializable trans, Cryptable sc) {
        // Sets up the byte array output stream.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Sets up the oos so that an object could be fed into the baos
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(trans);
            baos.flush();
            // Gets, encrypts, and stores the data.
            this.objectCryptoByte = sc.encrypt(baos.toByteArray());
            baos.close();
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * This function unpacks, decrypts and returns an object.
     * 
     * @return The unpacks object.
     * @param sc The cipher being used.
     */
    public Serializable getCryptoObject(Cryptable sc) {
        Serializable object;
        
        // Returns nothing if tehre is nothing to return.
        if (this.objectCryptoByte == null) {
            return (null);
        }
        
        // Sets up the bais.
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(sc.decrypt(this.objectCryptoByte));
            // Sets up the ois so the object can be read from the bais.
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = (Serializable) ois.readObject();
            return (object);
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (null);
    }
}