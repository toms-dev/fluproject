package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Provides usefull functions to manage user inputs.
 * 
 * @author Loïc GAILLARD
 */
public class InputReader {
    /**
     * Displays a message and reads an integer on the System input.
     * @author Tom GUILLERMIN
     * @param message The message displayed to the user.
     * @return The input value
     */
    public static int readInteger(String message){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(true){
            System.out.println(message);
            try {
                return Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e){
                System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a valid value !"));
            } catch (IOException e){}
        }
    }
    
    /**
     * Displays a message and reads a positive integer on the System input.
     * The user input will be asked until the entered value is positive.
     * @author Loïc GAILLARD
     * @param message The message displayed to the user.
     * @return The input value
     */
    public static int readPositiveInteger(String message) {
        int input;
        while((input = readInteger(message)) < 0) {
            System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter a positive number !"));
        }
        return input;
    }
    
    /**
     * Displays a message and reads an integer in a range on the System input.
     * The user's input will be asked until the entered value is in range.
     * @author Loïc GAILLARD
     * @param message The message displayed to the user.
     * @param min The minimum value of the input.
     * @param max The maximum value of the input.
     * @return The input value.
     */
    public static int readIntegerInRange(String message, int min, int max){
        int input = readInteger(message);
        while(input < min || input > max){
            System.out.println(PrettyConsole.HeaderTextBox("Error", "You must enter a value between "+min+" and "+max+"."));
            input = readInteger(message);
        }
        return input ;
    }
    
    /**
     * Displays a message and reads a boolean on the System input.
     * @author Loïc GAILLARD
     * @param message The message displayed to the user.
     * @return The input value
     */
    public static boolean readBooleanYN(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println(message+" (Y/n)");
            try {
                String input = reader.readLine();
                if("Y".equals(input) || "y".equals(input)) return true;
                if("N".equals(input) || "n".equals(input)) return false;
            } catch(IOException e){}
        }
    }
    
    /**
     * Displays a message and reads a valid String on the System input.
     * The user's input will be asked until the String is valid.
     * @param message The message displayed to the user.
     * @param validStrings The array of valid Strings
     * @return The valid user's String
     */
    public static String readStringArray(String message, String[] validStrings){
        String input ;
        
        String join = "" ;
        for(int i = 0 ; i < validStrings.length ; i++ ){
            join += validStrings[i] ;
            if( i < validStrings.length-1 ) join += ", ";
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(true){
            System.out.println(message+" (Accepted values : "+join+")");
            try {
                input = reader.readLine();
                for(String s : validStrings){
                    if (s.equals(input)) {
                        return input ;
                    }
                }
                System.out.println(PrettyConsole.HeaderTextBox("Error", "Please enter one of these value : "+join));
            } catch(IOException e){}
        }
    }
}
