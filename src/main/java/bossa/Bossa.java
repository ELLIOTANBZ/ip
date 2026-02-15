package bossa;

import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The main class of the Bossa chatbot application. Handles user interface, task
 * storage, and execution of commands
 */
public class Bossa {

    private final TaskList tasks;
    private final Ui ui;
    private final Storage storage;

    /**
     * Contructs a new Bossa chatbot instance
     *
     * @param filePath the file path to load and save tasks
     */
    public Bossa(String filePath) {
        ui = new Ui();
        storage = new Storage("data/bossa.txt");
        List<Task> loaded = storage.loadTasks();
        assert loaded != null : "Loaded task list should not be null";
        tasks = new TaskList(loaded);
    }

    /**
     * Runs the Bossa application, continuously reading user input and executing
     * commands until the user exits.
     */
    public String getResponse(String input) {

        String fullInput = input.trim();
        String command = Parser.getCommandWord(fullInput);
        assert command != null : "Parser should never return null command";

        if (command.isEmpty()) {
            return ui.showDontUnderstand();
        }

        if (command.equalsIgnoreCase("bye")) {
            return ui.showBye();
        }

        if (command.equalsIgnoreCase("list")) {
            return ui.showAllTasks(tasks);
        }

        if (command.equalsIgnoreCase("mark")) {
            try {
                int index = Integer.parseInt(fullInput.split(" ")[1]) - 1;
                Task task = tasks.get(index);
                task.markAsDone();
                storage.saveTasks(tasks.getAll());
                return ui.markAsDone(task);
            } catch (Exception e) {
                return ui.showMessage("Apologies Boss, that task number does not exist.");
            }
        }

        if (command.equalsIgnoreCase("unmark")) {
            try {
                int index = Integer.parseInt(fullInput.split(" ")[1]) - 1;
                assert index >= 0 : "Task index should be non-negative";
                Task task = tasks.get(index);
                task.markAsNotDone();
                storage.saveTasks(tasks.getAll());
                return ui.markAsUndone(task);
            } catch (Exception e) {
                return ui.showMessage("Apologies Boss, that task number does not exist.");
            }
        }

        if (command.equalsIgnoreCase("remove")) {
            try {
                int index = Integer.parseInt(fullInput.split(" ")[1]) - 1;
                Task removed = tasks.remove(index);
                storage.saveTasks(tasks.getAll());
                return ui.removeTask(removed, tasks.size());
            } catch (Exception e) {
                return ui.showMessage("Apologies Boss, that task number does not exist.");
            }
        }

        if (command.equalsIgnoreCase("todo")) {
            String description = fullInput.length() > 4 ? fullInput.substring(5).trim() : "";
            if (description.isEmpty()) {
                return ui.showMessage("Boss, the description of a todo cannot be empty.");
            }
            Task task = new ToDo(description);
            int before = tasks.size();
            tasks.add(task);
            assert tasks.size() == before + 1 : "Task list size should increase after add";
            storage.saveTasks(tasks.getAll());
            return ui.addTask(task, tasks.size());
        }

        if (command.equalsIgnoreCase("deadline")) {
            String[] parts = fullInput.substring(9).split(" /by ");
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                return ui.showMessage("Boss, Deadline format: deadline <description> /by <time>");
            }
            try {
                Task task = new Deadline(parts[0].trim(), parts[1].trim());
                tasks.add(task);
                storage.saveTasks(tasks.getAll());
                return ui.addTask(task, tasks.size());
            } catch (DateTimeParseException e) {
                return ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for deadlines.");
            }
        }

        if (command.equalsIgnoreCase("event")) {
            String[] parts = fullInput.substring(6).split(" /from | /to ");
            if (parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                return ui.showMessage("Boss, Event format: event <description> /from <start> /to <end>");
            }
            try {
                Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                tasks.add(task);
                storage.saveTasks(tasks.getAll());
                return ui.addTask(task, tasks.size());
            } catch (DateTimeParseException e) {
                return ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for events.");
            }
        }

        if (command.equalsIgnoreCase("find")) {
            String keyword = fullInput.substring(5).trim();
            if (keyword.isEmpty()) {
                return ui.showMessage("Boss, please provide a keyword to search for.");
            }

            List<Task> matches = tasks.find(keyword);
            return ui.findTask(matches);
        }

        return ui.showDontUnderstand();

    }

    public static void main(String[] args) {
        // ..
    }

    public String getWelcome() {
        return ui.showWelcome();
    }


}
