import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("Good day Boss. I'm Bossa.");
        System.out.println("How may I be of service?");
        System.out.println("____________________________________________________________");
    }

    public void showBye() {
        System.out.println("____________________________________________________________");
        System.out.println("Best regards,");
        System.out.println("Bossa.");
        System.out.println("____________________________________________________________");
    }

    public void showDontUnderstand() {
        System.out.println("____________________________________________________________");
        System.out.println("My apologies Boss, I don't understand that command.");
        System.out.println("____________________________________________________________");
    }

    public void showMessage(String msg) {
        System.out.println("____________________________________________________________");
        System.out.println(msg);
        System.out.println("____________________________________________________________");
    }

    public void showAllTasks(TaskList tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("The tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    public void markAsDone(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Great job! Marked as Done!");
        System.out.println(" " + task);
        System.out.println("____________________________________________________________");
    }

    public void markAsUndone(Task task) {
        System.out.println("____________________________________________________________");
        System.out.println("Unmarked");
        System.out.println(" " + task);
        System.out.println("____________________________________________________________");
    }

    public void removeTask(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it Boss, removed this task:");
        System.out.println(" " + task);
        System.out.println("You now have " + taskCount + " tasks in your list.");
        System.out.println("____________________________________________________________");
    }

    public void addTask(Task task, int taskCount) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it Boss, added this task:");
        System.out.println(" " + task);
        System.out.println("You now have " + taskCount + " tasks in your list.");
        System.out.println("____________________________________________________________");
    }

    public String readCommand(){
        return scanner.nextLine();
    }

}
