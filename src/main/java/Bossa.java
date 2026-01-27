import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Bossa chatbot for task management.
 * Supports ToDo, Deadline, and Event tasks.
 * Automatically saves and loads tasks from disk.
 */
public class Bossa {
    private static final Path DATA_PATH = Paths.get("data", "bossa.txt");
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Loads tasks from disk into the task list.
     * If file or folder doesn't exist, starts with empty list.
     * Skips corrupted lines.
     */
    private void loadTasks() {
        if (!Files.exists(DATA_PATH)) {
            System.out.println("Data file not found. Starting with an empty task list.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(DATA_PATH);
            for (String line : lines) {
                try {
                    tasks.add(Task.fromStorageString(line));
                } catch (Exception e) {
                    System.err.println("Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Saves current tasks to disk.
     * Creates data folder if it doesn't exist.
     */
    private void saveTasks() {
        try {
            if (!Files.exists(DATA_PATH.getParent())) {
                Files.createDirectories(DATA_PATH.getParent());
            }

            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toStorageString());
            }

            Files.write(DATA_PATH, lines);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Main method, runs the chatbot loop.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Bossa bossa = new Bossa();
        bossa.run();
    }

    /**
     * Runs the chatbot input loop.
     */
    private void run() {
        Scanner scanner = new Scanner(System.in);
        loadTasks();

        System.out.println("Good day Boss. I'm Bossa.");
        System.out.println("How may I be of service?");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Best regards,");
                System.out.println("Bossa.");
                System.out.println("____________________________________________________________");
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("The tasks in your list: ");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
                continue;
            }

            if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    tasks.get(index).markAsDone();
                    saveTasks();

                    System.out.println("____________________________________________________________");
                    System.out.println("Great job! Marked as Done!");
                    System.out.println(" " + tasks.get(index));
                    System.out.println("____________________________________________________________");
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
                continue;
            }

            if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    tasks.get(index).markAsNotDone();
                    saveTasks();

                    System.out.println("____________________________________________________________");
                    System.out.println("Unmarked");
                    System.out.println(" " + tasks.get(index));
                    System.out.println("____________________________________________________________");
                } catch (Exception e) {
                    System.out.println("Invalid task number.");
                }
                continue;
            }

            if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks.add(new ToDo(description));
                saveTasks();

                System.out.println("____________________________________________________________");
                System.out.println("Got it Boss, added this task:");
                System.out.println(" " + tasks.get(tasks.size() - 1));
                System.out.println("You now have " + tasks.size() + " tasks in your list.");
                System.out.println("____________________________________________________________");
                continue;
            }

            if (input.startsWith("deadline ")) {
                String[] description = input.substring(9).split(" /by ");
                if (description.length < 2) {
                    System.out.println("Invalid deadline format. Use: deadline <desc> /by <time>");
                    continue;
                }
                tasks.add(new Deadline(description[0], description[1]));
                saveTasks();

                System.out.println("____________________________________________________________");
                System.out.println("Got it Boss, added this task:");
                System.out.println(" " + tasks.get(tasks.size() - 1));
                System.out.println("You now have " + tasks.size() + " tasks in your list.");
                System.out.println("____________________________________________________________");
                continue;
            }

            if (input.startsWith("event ")) {
                // Splitting by " /from " and " /to " carefully
                String[] parts = input.substring(6).split(" /from | /to ");
                if (parts.length < 3) {
                    System.out.println("Invalid event format. Use: event <desc> /from <start> /to <end>");
                    continue;
                }
                tasks.add(new Event(parts[0], parts[1], parts[2]));
                saveTasks();

                System.out.println("____________________________________________________________");
                System.out.println("Got it Boss, added this task:");
                System.out.println(" " + tasks.get(tasks.size() - 1));
                System.out.println("You now have " + tasks.size() + " tasks in your list.");
                System.out.println("____________________________________________________________");
                continue;
            }

            // Unknown command fallback
            System.out.println("I don't understand that command, Boss.");
        }

        scanner.close();
    }
}
