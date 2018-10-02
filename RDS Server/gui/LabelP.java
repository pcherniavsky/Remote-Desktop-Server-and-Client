package gui;

import java.awt.Label;

/**
 * This is a version of the Label class that has auto size and location.
 * 
 * @author Peter Cherniavsky
 */
public class LabelP extends Label {
    /**
     * The SL version of the Label constructor.
     * 
     * @param SL Size and location data.
     */
    public LabelP(SizeLoc SL) {
        super();
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Label constructor.
     * 
     * @param SL Size and location data.
     * @param text The text in the label.
     */
    public LabelP(SizeLoc SL, String text) {
        super(text);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Label constructor.
     * 
     * @param SL Size and location data.
     * @param text The text in the label.
     * @param alignment The alignment of the text.
     */
    public LabelP(SizeLoc SL, String text, int alignment) {
        super(text);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
}