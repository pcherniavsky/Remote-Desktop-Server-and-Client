package sec.keychain;

import java.io.*;
import java.util.*;
import java.security.interfaces.*;
import javax.swing.JOptionPane;

/**
 * This is a class that stores a list of asymmetric cipher info and manages it.
 *
 * @author Peter
 */
public class AsymmetricCipherList  {
    /**
     * The public key info list. Volatile because it is modified by multiple threads.
     */
    private static volatile ArrayList<AsymmetricCipherInfo> aciList;
    
    static {
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream("keydb");
            try {
                ois = new ObjectInputStream(fis);
            
                aciList = (ArrayList<AsymmetricCipherInfo>) ois.readObject();
                fis.close();
            }
            catch (InvalidClassException | StreamCorruptedException | EOFException e) {
                switch(JOptionPane.showConfirmDialog(null, "There is already a file named keydb that is not of the right"
                        + " format This program needs that filename to continue. Press YES to delete the file and NO to exit.",
                             "keydb Already Present", JOptionPane.YES_NO_OPTION, 
                             JOptionPane.WARNING_MESSAGE)) {
                    case JOptionPane.YES_OPTION:
                        if (ois instanceof ObjectInputStream) {
                            ois.close();
                        }
                        fis.close();
                        File file = new File("keydb");
                        file.delete();
                        break;
                    default:
                        System.exit(0);
                }
                aciList = new ArrayList<AsymmetricCipherInfo>();
            }
            catch (Exception e) {
                System.exit(-1);
            }
        }
        catch (FileNotFoundException e) {
            aciList = new ArrayList<AsymmetricCipherInfo>();
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }
    
    /**
     * Checks if the key is already in the list.
     *
     * @param publicKey The public key to be checked.
     * @param ip The IP.
     * @return True if in the list and false otherwise.
     */
    public static boolean isKnownKey(RSAPublicKey publicKey, String ip) {
        AsymmetricCipherInfo aci = new AsymmetricCipherInfo(publicKey, ip);
        
        for (int i = 0; i < aciList.size(); i++) {
            if (aciList.get(i).equals(aci)) {
                return (true);
            }
        }
        
        return (false);
    }
    
    /**
     * Adds a key to the array list.
     *
     * @param publicKey The public key to be added.
     * @param ip The IP.
     * @return True of successful and false otherwise.
     */
    public static boolean addKey(RSAPublicKey publicKey, String ip) {
        if (!isKnownKey(publicKey, ip)) {
            aciList.add(new AsymmetricCipherInfo(publicKey, ip));
            try {
                new ObjectOutputStream(new FileOutputStream("keydb")).writeObject(aciList);
            }
            catch (Exception e) {
                System.exit(-1);
            }
            return (true);
        }
        
        return (false);
    }
    
    
    /**
     * Removes the key from the array list.
     *
     * @param stringRep The string rep that will be checked with the existing list.
     */
    public static void removeKey(String stringRep) {
        for (int i = 0; i < aciList.size(); i++) {
            if (aciList.get(i).toString().equals(stringRep)) {
                aciList.remove(i);
                try {
                    new ObjectOutputStream(new FileOutputStream("keydb")).writeObject(aciList);
                }
                catch (Exception e) {
                    System.exit(-1);
                }
                return;
            }
        }
    }
    
    /**
     * Gets the string version of the array list that would work with the JList class.
     *
     * @return The string array rep.
     */
    public static String [] getStrRep() {
        String [] strRep = new String[aciList.size()];
        
        for (int i = 0; i < aciList.size(); i++) {
            strRep[i] = aciList.get(i).toString();
        }
        
        return (strRep);
    }
}