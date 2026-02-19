package bossa;

public class Parser {

    /**
    * A utility class for parsing user input commands.
    * 
    * <p>This class provides methods to extract the command word
    * from a full input string entered by the user.</p>
    */    
    public static String getCommandWord(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        return input.trim().split("\\s+")[0];
    }

    public static int parseIndex(String input) {
        String[] parts = input.trim().split("\\s+");

        if (parts.length < 2) {
            throw new NumberFormatException();
        }

        return Integer.parseInt(parts[1]) - 1;
    }

    public static String parseTodo(String input) {
        String[] parts = input.trim().split("\\s+", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Todo description cannot be empty.");
        }

        return parts[1].trim();
    }

    public static String[] parseDeadline(String input) {
        String[] parts = input.substring(8).split(" /by ");

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid deadline format.");
        }

        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    public static String[] parseEvent(String input) {
        String[] parts = input.substring(5).split(" /from | /to ");

        if (parts.length < 3 ||
                parts[1].trim().isEmpty() ||
                parts[2].trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid event format.");
        }

        return new String[]{
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim()
        };
    }

    public static String parseFind(String input) {
        String keyword = input.substring(4).trim();

        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("Find keyword cannot be empty.");
        }

        return keyword;
    }
}
