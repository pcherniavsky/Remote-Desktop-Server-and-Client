package data;

import java.io.Serializable;

/**
 * This class holds data used to scroll the mouse.
 * @author Peter
 */
public class MouseScrollData implements Serializable {
    /**
     * The scroll value.
     */
    private final int scrollVal;
    
    /**
     * The constructor used to create the object.
     * 
     * @param scrollVal The scroll value.
     */
    public MouseScrollData(int scrollVal) {
        this.scrollVal = scrollVal;
    }
    
    /**
     * Gets the scroll value.
     * 
     * @return The scroll value.
     */
    public int getScrollVal() {
        return (scrollVal);
    }
}