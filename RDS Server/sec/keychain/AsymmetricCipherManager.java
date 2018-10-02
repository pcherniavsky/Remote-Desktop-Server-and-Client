package sec.keychain;

import exceptions.*;
import java.security.*;
import java.security.interfaces.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Peter Cherniavsky
 */
public class AsymmetricCipherManager {
    /**
     * The key info loaded.
     */
    private static volatile AsymmetricCipherInfo aci;
    
    /**
     * This method generates a key.
     * 
     * @param name The name.
     * @return true if it works and false otherwise.
     */
    public static boolean generate(String name) {
        try {
            // Generates the key.
            KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
            kg.initialize(2048);
            
            // Generates the key pair.
            aci = new AsymmetricCipherInfo(kg.genKeyPair(), name);
            return (true);
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (false);
    }
    
    /**
     * This method retrieves the key from a file.
     * 
     * @param name The name of the key to be retrieved.
     * @throws exceptions.KeyProcessException When a key Process error occurs.
     */
    public static void retrieve(String name) throws KeyProcessException {
        try {
            FileInputStream fin = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(fin);
            
            aci = (AsymmetricCipherInfo) in.readObject();
            fin.close();
        }
        catch (FileNotFoundException e) {
            throw new KeyProcessException("File Not Found.");
        }
        catch(InvalidClassException | StreamCorruptedException | EOFException e) {
            throw new KeyProcessException("Invalid File Found.");
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * The function saves the current key as is. It also checks for an already existing file.
     * 
     * @throws KeyProcessException If there is no key loaded or you are unable to save it.
     */
    public static void save() throws KeyProcessException {
        if (aci == null) {
            throw new KeyProcessException("No key loaded.");
        }
        
        byte[] tempACIByte, aciByte;
        File f = new File(aci.getName());
        
        if(f.exists() && !f.isDirectory()) { 
            try {
                tempACIByte = Files.readAllBytes(f.toPath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(baos);
                
                out.writeObject(aci);
                out.flush();
                aciByte = baos.toByteArray();
                out.close();
                baos.close();
                
                
                if (!Arrays.equals(tempACIByte, aciByte)) {
                    switch(JOptionPane.showConfirmDialog(null, "There is a file with diffrent "
                            + "conent already here. Press YES to overwrite it and pres NO to do nothing.", 
                            "The Same File Already Exists", 
                            JOptionPane.YES_NO_OPTION, 
                                JOptionPane.WARNING_MESSAGE)) {
                        case JOptionPane.YES_OPTION:
                            break;
                        default:
                            return;
                    }
                }
                else {
                    switch(JOptionPane.showConfirmDialog(null, "There is a file with the same "
                            + "conent already here. Press YES to overwrite it and pres NO to do nothing.", 
                            "The Same File Already Exists", 
                            JOptionPane.YES_NO_OPTION, 
                                JOptionPane.INFORMATION_MESSAGE)) {
                        case JOptionPane.YES_OPTION:
                            break;
                        default:
                            return;
                    }
                }
                
            }
            catch (InvalidPathException e) {
                throw new KeyProcessException("Invalid file name.");
            }
            catch (Exception e) {
                System.exit(-1);
            }
        }
        try {
            FileOutputStream fout = new FileOutputStream(aci.getName());
            ObjectOutputStream out = new ObjectOutputStream(fout);
            
            out.writeObject(aci);
            fout.close();
        }
        catch (FileNotFoundException e) {
            throw new KeyProcessException("Invalid file name.");
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * Same as the other save method except the name is custom name. It also checks for an already existing file.
     * 
     * @param name The name to save the key as.
     * @throws KeyProcessException If there is no key loaded or you are unable to save it.
     */
    public static void save(String name) throws KeyProcessException {
        if (aci == null) {
            throw new KeyProcessException("No key loaded.");
        }
        
        aci.setName(name);
        
        save();
    }
    
    /**
     * This function returns the string representation of the key. Null if not initialized.
     * 
     * @return The string representation.
     */
    public static String getStringRep() {
        if (aci == null) {
            return (null);
        }
        return(aci.getStringRep());
    }
    
    /**
     * This function returns the public key. Null if not initialized.
     * 
     * @return The public key.
     */
    public synchronized static RSAPublicKey getPublic() {
        if (aci == null) {
            return (null);
        }
        return ((RSAPublicKey) aci.getKeyPair().getPublic());
    }
    
    /**
     * This function returns the private key. Null if not initialized.
     * 
     * @return The private key.
     */
    public synchronized static RSAPrivateKey getPrivate() {
        if (aci == null) {
            return (null);
        }
        return ((RSAPrivateKey) aci.getKeyPair().getPrivate());
    }
}