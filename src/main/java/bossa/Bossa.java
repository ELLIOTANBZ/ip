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

    private static final int TODO_CMD_LEN = 5;
    private static final int DEADLINE_CMD_LEN = 9;
    private static final int EVENT_CMD_LEN = 6;

    /**
     * Contructs a new Bossa chatbot instance
     *
     * @param filePath the file path to load and save tasks
     */
    public Bossa(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
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
            return handleMark(parseIndex(fullInput), true);
        }

        if (command.equalsIgnoreCase("unmark")) {
            return handleMark(parseIndex(fullInput), false);
        }

        if (command.equalsIgnoreCase("remove")) {
            return handleRemove(parseIndex(fullInput));
        }

        if (command.equalsIgnoreCase("todo")) {
            return handleTodo(fullInput);
        }

        if (command.equalsIgnoreCase("deadline")) {
            return handleDeadline(fullInput);
        }

        if (command.equalsIgnoreCase("event")) {
            return handleEvent(fullInput);
        }

        if (command.equalsIgnoreCase("find")) {
            return handleFind(fullInput);
        }

        return ui.showDontUnderstand();

    }

    private int parseIndex(String input) throws NumberFormatException {
        return Integer.parseInt(input.split(" ")[1]) - 1;
    }

    /**
     * Marks or unmarks the task at the specified index
     * Updates the task status, saves the updated task list to storage,
     * and returns the corresponding UI response message
     *
     * @param index zero-based index of the task in the task list
     * @param done true to mark the task as done, false to mark it as not done
     * @return the response message to be shown to the user
     */
    private String handleMark(int index, boolean done){
        try {
            Task task = tasks.get(index);
            if (done) {
                task.markAsDone();
            } else {
                task.markAsNotDone();
            }
            storage.saveTasks(tasks.getAll());
            return done ? ui.markAsDone(task) : ui.markAsUndone(task);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return ui.showMessage("Apologies Boss, that task number does not exist.");
        }
    }

    /**
     * Removes the task at the specified index from the task list.
     * Saves the updated task list to storage and returns the
     * corresponding UI response message.
     *
     * @param index zero-based index of the task to be removed
     * @return the response message to be shown to the user
     */
    private String handleRemove(int index){
        try {
            Task removed = tasks.remove(index);
            storage.saveTasks(tasks.getAll());
            return ui.removeTask(removed, tasks.size());
        } catch (Exception e) {
            return ui.showMessage("Apologies Boss, that task number does not exist.");
        }
    }


    /**
     * Parses the input string to create and add a new ToDo task.
     * Validates that the task description is not empty before adding it.
     *
     * @param input full user input containing the todo command and description
     * @return the response message to be shown to the user
     */
    private String handleTodo(String input){
        String[] parts = input.split(" ", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return ui.showMessage("Boss, the description of a todo cannot be empty.");
        }

        String description = parts[1].trim();
        Task task = new ToDo(description);
        return handleAddTask(task);
    }

    /**
     * Parses the input string to create and add a new Deadline task.
     * Expects the format: deadline &lt;description&gt; /by &lt;date&gt;.
     * Validates the format and date before adding the task.
     *
     * @param input full user input containing the deadline command
     * @return the response message to be shown to the user
     */
    private String handleDeadline(String input) {
        String[] parts = input.substring(DEADLINE_CMD_LEN).split(" /by ");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return ui.showMessage("Boss, Deadline format: deadline <description> /by <time>");
        }
        try {
            Task task = new Deadline(parts[0].trim(), parts[1].trim());
            return handleAddTask(task);
        } catch (DateTimeParseException e) {
            return ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for deadlines.");
        }
    }

    /**
     * Parses the input string to create and add a new Event task.
     * Expects the format: event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;.
     * Validates the format and date values before adding the task.
     *
     * @param input full user input containing the event command
     * @return the response message to be shown to the user
     */
    private String handleEvent(String input){
        String[] parts = input.substring(EVENT_CMD_LEN).split(" /from | /to ");
        if (parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
           return ui.showMessage("Boss, Event format: event <description> /from <start> /to <end>");
        }
        try {
            Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
            return handleAddTask(task);
        } catch (DateTimeParseException e) {
            return ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for events.");
        }
    }

    /**
     * Searches for tasks containing the specified keyword.
     * Extracts the keyword from the user input and returns
     * the matching tasks via the UI.
     *
     * @param input full user input containing the find command
     * @return the response message to be shown to the user
     */
    private String handleFind(String input){
        try {
            String keyword = input.substring(5).trim();
            if (keyword.isEmpty()) {
                return ui.showMessage("Boss, please provide a keyword to search for.");
            }

            List<Task> matches = tasks.find(keyword);
            return ui.findTask(matches);
        } catch (IndexOutOfBoundsException e) {
            return ui.showMessage("Boss, please provide a keyword to search for.");
        }
    }

    /**
     * Adds the specified task to the task list, saves the updated
     * list to storage, and returns the corresponding UI response message.
     *
     * @param task the task to be added
     * @return the response message to be shown to the user
     */
    private String handleAddTask(Task task){
        tasks.add(task);
        storage.saveTasks(tasks.getAll());
        return ui.addTask(task, tasks.size());
    }


    public static void main(String[] args) {
        // ..
    }

    public String getWelcome() {
        return ui.showWelcome();
    }


}
