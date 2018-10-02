package data;

import java.io.Serializable;

/**
 * This class holds data used in a key press.
 * 
 * @author Peter
 */
public class KeyPressData implements Serializable {
    /**
     * The key code.
     */
    private final int keyCode;
    
    /**
     * Constructor that sets up the object and the key code.
     * 
     * @param keyCode The key code.
     */
    public KeyPressData(int keyCode) {
        this.keyCode = keyCode;
    }
    
    /**
     * Gets the key code.
     * 
     * @return The key code.
     */
    public int getKeyCode() {
        return (keyCode);
    }
}