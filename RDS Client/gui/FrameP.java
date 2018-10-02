package gui;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;

/**
 * This is a version of the Frame class that has auto size and location.
 * 
 * @author Peter Cherniavsky
 */
public class FrameP extends Frame {
    /**
     * The SL version of the Frame constructor.
     * 
     * @param SL Size and location data.
     */
    public FrameP(SizeLoc SL) {
        super();
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Frame constructor.
     * 
     * @param SL Size and location data.
     * @param gc The GraphicsConfiguration data.
     */
    public FrameP(SizeLoc SL, GraphicsConfiguration gc) {
        super(gc);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Frame constructor.
     * 
     * @param SL Size and location data.
     * @param title The title of the frame.
     */
    public FrameP(SizeLoc SL, String title) {
        super(title);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
    
    /**
     * The SL version of the Frame constructor.
     * 
     * @param SL Size and location data.
     * @param title The title of the frame.
     * @param gc The GraphicsConfiguration data.
     */
    public FrameP(SizeLoc SL, String title, GraphicsConfiguration gc) {
        super(title, gc);
        
        if (SL != null) {
            super.setSize(SL.getWidth(), SL.getHeight());
            super.setLocation(SL.getxLoc(), SL.getyLoc());
        }
    }
}
