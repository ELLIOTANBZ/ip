package bossa;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public String showWelcome() {
        StringBuilder result = new StringBuilder();
        result.append("Good day Boss. I'm Bossa.").append("\n");
        result.append("How may I be of service?");
        return result.toString();
    }

    public String showBye() {
        StringBuilder result = new StringBuilder();
        result.append("Best regards,").append("\n");
        result.append("Bossa.");
        return result.toString();
    }

    public String showDontUnderstand() {
        StringBuilder result = new StringBuilder();
        result.append("My apologies Boss, I don't understand that command.");
        return result.toString();
    }

    public String showMessage(String msg) {
        StringBuilder result = new StringBuilder();
        result.append(msg);
        return result.toString();
    }

    public String showAllTasks(TaskList tasks) {
        StringBuilder result = new StringBuilder();
        result.append("The tasks in your list: ").append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            result.append(" ").append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return result.toString();
    }

    public String markAsDone(Task task) {
        StringBuilder result = new StringBuilder();
        result.append("Great job! Marked as Done!").append("\n");
        result.append(" ").append(task);

        return result.toString();
    }

    public String markAsUndone(Task task) {
        StringBuilder result = new StringBuilder();
        result.append("Unmarked:").append("\n");
        result.append(" ").append(task);

        return result.toString();
    }

    public String removeTask(Task task, int taskCount) {
        StringBuilder result = new StringBuilder();
        result.append("Got it Boss, removed this task:\n");
        result.append(task).append("\n");
        result.append("You now have ").append(taskCount).append(" tasks in your list.");

        return result.toString();
    }

    public String addTask(Task task, int taskCount) {
        StringBuilder result = new StringBuilder();
        result.append("Got it Boss, added this task:\n");
        result.append(task).append("\n");
        result.append("You now have ").append(taskCount).append(" tasks in your list.");

        return result.toString();
    }

    public String findTask(List<Task> tasks) {
        StringBuilder result = new StringBuilder();
        result.append("Boss, here are the matching tasks in your list:\n");

        for (int i = 0; i < tasks.size(); i++) {
            result.append(i + 1)
                .append(". ")
                .append(tasks.get(i))
                .append("\n");
        }

        return result.toString();
    }



}
