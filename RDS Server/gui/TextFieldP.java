package gui;

import java.awt.TextField;

/**
 * This is a version of the TextField class that has auto size and location.
 * 
 * @author Peter
 */
public class TextFieldP extends TextField {
    /**
     * The SL version of the TextField constructor.
     * 
     * @param SL Size and location data.
     */
    public TextFieldP(SizeLoc SL) {
        super();
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the TextField constructor.
     * 
     * @param SL Size and location data.
     * @param columns The columns.
     */
    public TextFieldP(SizeLoc SL, int columns) {
        super(columns);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the TextField constructor.
     * 
     * @param SL Size and location data.
     * @param text The default text in the field.
     */
    public TextFieldP(SizeLoc SL, String text) {
        super(text);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the TextField constructor.
     * 
     * @param SL Size and location data.
     * @param text The default text in the field.
     * @param columns The columns.
     */
    public TextFieldP(SizeLoc SL, String text, int columns) {
        super(text, columns);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
}
