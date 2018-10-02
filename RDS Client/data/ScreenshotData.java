package data;

import java.io.Serializable;

/**
 * This object holds the screen shot byte data.
 * 
 * @author Peter Cherniavsky
 */
public class ScreenshotData implements Serializable {
    /**
     * The data.
     */
    private final byte[] data;
    
    /**
     * Sets the data of the object.
     * 
     * @param data The data.
     */
    public ScreenshotData(byte[] data) {
        this.data = data;
    }
    
    /**
     * Returns the data.
     * 
     * @return The data.
     */
    public byte[] getData() {
        return (data);
    }
}
