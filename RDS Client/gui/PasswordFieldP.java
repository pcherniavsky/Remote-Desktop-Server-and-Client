package gui;

import javax.swing.JPasswordField;
import javax.swing.text.Document;

/**
 * This is a version of the JPasswordField class that has auto size and location.
 * 
 * @author Peter
 */
public class PasswordFieldP extends JPasswordField {
    /**
     * The SL version of the JPasswordField constructor.
     * 
     * @param SL Size and location data.
     */
    public PasswordFieldP(SizeLoc SL) {
        super();
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the JPasswordField constructor.
     * 
     * @param SL Size and location data.
     * @param columns The amount of columns.
     */
    public PasswordFieldP(SizeLoc SL, int columns) {
        super(columns);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the JPasswordField constructor.
     * 
     * @param SL Size and location data.
     * @param text The default text in the field.
     */
    public PasswordFieldP(SizeLoc SL, String text) {
        super(text);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the JPasswordField constructor.
     * 
     * @param SL Size and location data.
     * @param text The default text in the field.
     * @param columns The amount of columns.
     */
    public PasswordFieldP(SizeLoc SL, String text, int columns) {
        super(text, columns);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the JPasswordField constructor.
     * 
     * @param SL Size and location data.
     * @param doc The document parameter.
     * @param text The default text in the field.
     * @param columns The amount of columns.
     */
    public PasswordFieldP(SizeLoc SL, Document doc, String text, int columns) {
        super(doc, text, columns);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
}
