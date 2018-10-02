package gui;

import java.awt.Button;

/**
 * This is a version of the Button class that has auto size and location.
 * 
 * @author Peter Cherniavsky
 */
public class ButtonP extends Button {
    /**
     * The SL version of the Button constructor.
     * 
     * @param SL Size and location data.
     */
    public ButtonP(SizeLoc SL) {
        super();
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Button constructor.
     * 
     * @param SL Size and location data.
     * @param label The label string.
     */
    public ButtonP(SizeLoc SL, String label) {
        super(label);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
}
