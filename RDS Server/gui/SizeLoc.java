package gui;

/**
 * The class that holds Size and Location data.
 * 
 * @author Peter Cherniavsky
 */
public class SizeLoc {
    /**
     * The data.
     */
    int width, height, xLoc, yLoc;

    /**
     * Constructor that sets up the data.
     * 
     * @param width The width.
     * @param height The height.
     * @param xLoc The x location.
     * @param yLoc The y location.
     */
    public SizeLoc(int width, int height, int xLoc, int yLoc) {
        this.width = width;
        this.height = height;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
    
    /**
     * Gets the width.
     * 
     * @return The width.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Gets the height.
     * 
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the x location.
     * 
     * @return The x location.
     */
    public int getxLoc() {
        return xLoc;
    }
    
    /**
     * Gets the y location.
     * 
     * @return The y location.
     */
    public int getyLoc() {
        return yLoc;
    }
    
}
