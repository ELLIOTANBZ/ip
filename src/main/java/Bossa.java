import java.util.Scanner;

public class Bossa {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm Bossa");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");


        while (true) {
            String input = scanner.nextLine();

            // bye
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            // list
            if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("The tasks in your list: ");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            //mark task as done
            if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks[index].markAsDone();
                
                System.out.println("____________________________________________________________");
                System.out.println("Great job! Marked as Done!");
                System.out.println(" " + tasks[index]);
                System.out.println("____________________________________________________________");
                continue;
            }

            //unmark task
            if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks[index].markAsNotDone();
                
                System.out.println("____________________________________________________________");
                System.out.println("Unmarked");
                System.out.println(" " + tasks[index]);
                System.out.println("____________________________________________________________");
                continue;
            }

            tasks[taskCount] = new Task(input);
            taskCount++;
            System.out.println("____________________________________________________________");
            System.out.println("added: " + input);
            System.out.println("____________________________________________________________");
        }

        scanner.close();
    }
}
