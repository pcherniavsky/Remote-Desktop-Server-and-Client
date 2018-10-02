package data;

import java.io.Serializable;

/**
 * This class holds data used to press the mouse.
 * @author Peter
 */
public class MousePressData implements Serializable {
    /**
     * The button value.
     */
    private final int buttons;
    
    /**
     * Sets up the object.
     * 
     * @param buttons The button value.
     */
    public MousePressData(int buttons) {
        this.buttons = buttons;
    }
    
    /**
     * Gets the button value.
     * 
     * @return The button value.
     */
    public int getButtons() {
        return (buttons);
    }
}
