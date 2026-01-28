public class Parser {

    public static String getCommandWord(String input) {
        return input.trim().split("\\s+")[0];
    }
}
