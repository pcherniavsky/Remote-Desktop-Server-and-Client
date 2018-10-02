package server;

import data.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

/**
 * This class is simply a collection of methods to process IO data.
 * @author Peter
 */
public class IOProcessor {
    /**
     * The one robot class used to apply the instructions sent from the client.
     */
    private static final Robot IO;
    /**
     * The cursor image being used.
     */
    private static final BufferedImage cursor;
    /**
     * The image screen shot variable.
     */
    private static volatile BufferedImage screenShot;
    /**
     * The screen dimension used to get the screen shot.
     */
    private static final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * The object used to make the BufferdImage into a byte array.
     */
    private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    /**
     * The raw data of the BufferdImage.
     */
    private static volatile byte[] imageInByte;
    
    // This intilizes the static varaibles in this class.
    static {
        Robot tmp1;
        BufferedImage tmp2;
        
        try {
            tmp1 = new Robot();
            tmp2 = ImageIO.read(new File("cursor.bmp"));
        }
        
        catch (Exception e) {
            tmp1 = null;
            tmp2 = null;
            System.exit(-1);
        }
        IO = tmp1;
        cursor = tmp2;
        
    }
    
    /**
     * Used to move the mouse as instructed.
     * 
     * @param MMD The moving instructions.
     * @return true
     */
    private static boolean mouseMoveProcess(MouseMoveData MMD) {
        IO.mouseMove(MMD.getX(), MMD.getY());
        return (true);
    }
    
    /**
     * Used to press the mouse.
     * 
     * @param MPD The mouse pressing data.
     * @return true of it works and false otherwise.
     */
    private static boolean mousePressProcess(MousePressData MPD) {
        try {
            IO.mousePress(MPD.getButtons());
            return (true);
        }
        catch (Exception e) {
            return (false);
        }
    }
    
    /**
     * Used to release the mouse.
     * 
     * @param MRD The mouse release instructions.
     * @return true if it works and false otherwise.
     */
    private static boolean mouseReleaseProcess(MouseReleaseData MRD) {
        try {
            IO.mouseRelease(MRD.getButtons());
            return (true);
        }
        catch (Exception e) {
            return (false);
        }
    }
    
    /**
     * Used to scroll the mouse.
     * 
     * @param MCD The mouse scroll data.
     * @return true
     */
    private static boolean mouseScrollProcess(MouseScrollData MCD) {
        IO.mouseWheel(MCD.getScrollVal());
        return (true);
    }
    
    /**
     * Used to press the key.
     * 
     * @param KPD The key press data.
     * @return true if it works and false otherwise.
     */
    private static boolean keyPressProcess(KeyPressData KPD) {
        try {
            IO.keyPress(KPD.getKeyCode());
            return (true);
        }
        catch (Exception e) {
            return (false);
        }
    }
    
    /**
     * Used to release the key.
     * 
     * @param KRD The key release data.
     * @return true if it works and false otherwise.
     */
    private static boolean keyReleaseProcess(KeyReleaseData KRD) {
        try {
            IO.keyRelease(KRD.getKeyCode());
            return (true);
        }
        catch (Exception e) {
            return (false);
        }
    }
    
    /**
     * Used to process Input Data
     * 
     * @param Data The object contain should contain the Input Data.
     * @return true if it works and false otherwise.
     */
    public static synchronized boolean Processor(Object Data) {
        if (Data instanceof MouseMoveData) {
            return (mouseMoveProcess((MouseMoveData) Data));
        }
        else if (Data instanceof MousePressData) {
            return (mousePressProcess((MousePressData) Data));
        }
        else if (Data instanceof MouseReleaseData) {
            return (mouseReleaseProcess((MouseReleaseData) Data));
        }
        else if (Data instanceof MouseScrollData) {
            return (mouseScrollProcess((MouseScrollData) Data));
        }
        else if (Data instanceof KeyPressData) {
            return (keyPressProcess((KeyPressData) Data));
        }
        else if (Data instanceof KeyReleaseData) {
            return (keyReleaseProcess((KeyReleaseData) Data));
        }
        else {
            return (false);
        }
    }
    
    /**
     * The function gets a screen shot with the right dimensions and puts it in the right type of class.
     * 
     * @return The screenshotData class.
     */
    public static synchronized ScreenshotData getScreenshot() {
        try {
            // Sets up the objects to be used later on.
            screenShot = IO.createScreenCapture(new Rectangle(d.width, d.height));
            
            // Inserts the cursor into the screenshot.
            screenShot.createGraphics().drawImage(cursor, 
                    MouseInfo.getPointerInfo().getLocation().x, 
                    MouseInfo.getPointerInfo().getLocation().y, 8, 8, null);
            
            // Converts the image into bytes.
            ImageIO.write(screenShot, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.reset();
            
            return (new ScreenshotData(imageInByte));
        }
        catch (Exception e) {
            System.exit(-1);
        }
        
        return (null);
    }
}