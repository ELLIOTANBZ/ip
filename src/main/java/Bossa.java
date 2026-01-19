import java.util.Scanner;

public class Bossa {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Good day Boss. I'm Bossa.");
        System.out.println("How may I be of service?");
        System.out.println("____________________________________________________________");


        while (true) {
            String input = scanner.nextLine();

            // bye
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Best regards,");
                System.out.println("Bossa.");
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

            //ToDo
            if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new ToDo(description);

                System.out.println("____________________________________________________________");
                System.out.println("Got it Boss, added this task:");
                System.out.println(" " + tasks[taskCount]);
                taskCount++;
                System.out.println("You now have " + taskCount + " tasks in your list.");
                System.out.println("____________________________________________________________");
                continue;
            }

            //Deadline
            if (input.startsWith("deadline ")) {
                String[] description = input.substring(9).split(" /by ");
                tasks[taskCount] = new Deadline(description[0], description[1]);

                System.out.println("____________________________________________________________");
                System.out.println("Got it Boss, added this task:");
                System.out.println(" " + tasks[taskCount]);
                taskCount++;
                System.out.println("You now have " + taskCount + " tasks in your list.");
                System.out.println("____________________________________________________________");
                continue;
            }

            //Event
            if (input.startsWith("event ")) {
                String[] description = input.substring(6).split(" /from | /to ");
                tasks[taskCount] = new Event(description[0], description[1], description[2]);

                System.out.println("____________________________________________________________");
                System.out.println("Got it Boss, added this task:");
                System.out.println(" " + tasks[taskCount]);
                taskCount++;
                System.out.println("You now have " + taskCount + " tasks in your list.");
                System.out.println("____________________________________________________________");
                continue;
            }

        }

        scanner.close();
    }
}
