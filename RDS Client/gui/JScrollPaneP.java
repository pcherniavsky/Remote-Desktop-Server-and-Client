package gui;

import java.awt.Component;
import javax.swing.JScrollPane;

/**
 * This is a version of the JScrollPane class that has auto size and location.
 * 
 * @author Peter Cherniavsky
 */
public class JScrollPaneP extends JScrollPane {
    /**
     * The SL version of the Button constructor.
     * 
     * @param SL Size and location data.
     */
    public JScrollPaneP(SizeLoc SL) {
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
     */
    public JScrollPaneP(SizeLoc SL, Component view) {
        super(view);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Button constructor.
     * 
     * @param SL Size and location data.
     */
    public JScrollPaneP(SizeLoc SL, Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Button constructor.
     * 
     * @param SL Size and location data.
     * @param vsbPolicy The vsbPolicy parameter.
     * @param hsbPolicy The hsbPolicy parameter.
     */
    public JScrollPaneP(SizeLoc SL, int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
}