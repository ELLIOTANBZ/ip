import java.util.Scanner;

public class Bossa {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Hello! I'm Bossa");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");


        while (true) {
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(" " + input);
            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}
