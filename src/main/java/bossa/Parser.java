package bossa;
public class Parser {

/**
 * A utility class for parsing user input commands.
 * 
 * <p>This class provides methods to extract the command word
 * from a full input string entered by the user.</p>
 */
    public static String getCommandWord(String input) {
        return input.trim().split("\\s+")[0];
    }
}
