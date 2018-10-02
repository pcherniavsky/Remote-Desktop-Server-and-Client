package data;

import java.io.Serializable;

/**
 * This class holds data used for moving the mouse.
 * 
 * @author Peter
 */
public class MouseMoveData implements Serializable {
    /**
     * The x cord.
     */
    private final int x;
    /**
     * The y cord.
     */
    private final int y;
    
    /**
     * The constructor used to create the object.
     * 
     * @param x The x cord.
     * @param y The y cord.
     */
    public MouseMoveData(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x cord.
     * 
     * @return The x cord.
     */
    public int getX() {
        return (x);
    }
    
    /**
     * Gets the y cord.
     * 
     * @return The y cord.
     */
    public int getY() {
        return (y);
    }
}
