package sec;

/**
 *
 * @author Peter Cherniavsky
 * 
 * This class is used to store passwords. It automatically hashes a password you set it to.
 */
public class Password {
    /**
     * This is the hashed password. It is represented in bytes since that is what the hashing function returns.
     */
    private static byte [] password;
    
    /**
     * This function converts the plain text password to a hashed byte array and stores it.
     * 
     * @param password The charter array representation of the password.
     * @return true if it works and false otherwise.
     * @throws java.lang.Exception If given an empty password.
     */
    public static boolean setPassword(char [] password) throws Exception {
        byte [] passwordB = new byte [password.length];
        byte conversionNumber = (byte) -128;
        
        if (password.length == 0) {
            throw new Exception("Empty Password");
        }
        else {
            for (int i = 0; i < password.length; i++) {
                passwordB[i] = (byte) password[i];
                passwordB[i] = (byte) (passwordB[i] - conversionNumber);
                password[i] = 0;
            }
            Password.password = Hash.hashPassword(passwordB);
            
            for (int i = 0; i < passwordB.length; i++) {
                passwordB[i] = 0;
            }
            
            return (true);
        }
    }
    
    /**
     * This function simply gets the hashed password.
     * @return The password or null if it has not been set.
     */
    public static byte [] getPassword() {
        return (Password.password);
    }
    
    /**
     * 
     * @param password Checks if the password, in byte form, is the same as the password stored. 
     * It clears the passed password from memory. This deals with shared data so it is synchronized.
     * @return true if same and false otherwise. 
     */
    public static synchronized boolean checkPassword(byte [] password) {
        boolean isCorrect = true;
        
        // If the password is not set, then it is automaticly false.
        if (Password.password == null) {
            return (false);
        }
        
        if (Password.password.length != password.length) {
            return (false);
        }
        
        for (int i = 0; i < password.length; i++) {
            if (Password.password[i] != password[i]) {
                isCorrect = false;
            }
            
            password[i] = 0;
        }
        
        return (isCorrect);
    }
    
}