package bossa;

import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The main class of the Bossa chatbot application.
 * Handles user input and executes commands accordingly.
 */
public class Bossa {

    private final TaskList tasks;
    private final Ui ui;
    private final Storage storage;
    private Runnable lastUndoAction = null;

    /**
     * Constructs a Bossa chatbot with the specified storage file.
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
     * Processes the user input and returns the chatbot's response.
     *
     * @param input the user input
     * @return the chatbot response for the given input
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
            return handleMark(fullInput, true);
        }

        if (command.equalsIgnoreCase("unmark")) {
            return handleMark(fullInput, false);
        }

        if (command.equalsIgnoreCase("remove")) {
            return handleRemove(fullInput);
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

        if (command.equalsIgnoreCase("undo")) {
            return handleUndo();
        }

        return ui.showDontUnderstand();
    }

    /**
     * Marks or unmarks the task at the specified index.
     *
     * @param input the full user input containing a command and index
     * @param isDone true to mark as done, false to unmark
     * @return the result message to be shown to the user
     */
    private String handleMark(String input, boolean isDone) {
        try {
            int index = Parser.parseIndex(input);
            Task task = tasks.get(index);

            if (isDone) {
                task.markAsDone();
            } else {
                task.markAsNotDone();
            }

            storage.saveTasks(tasks.getAll());

            lastUndoAction = () -> {
                if (isDone) {
                    task.markAsNotDone();
                } else {
                    task.markAsDone();
                }
                storage.saveTasks(tasks.getAll());
            };

            return isDone ? ui.markAsDone(task) : ui.markAsUndone(task);

        } catch (NumberFormatException e) {
            return ui.showMessage("Boss, please provide a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            return ui.showMessage("Apologies Boss, that task number does not exist.");
        }
    }

    /**
     * Removes the task at the specified index.
     *
     * @param input the full user input containing a command and index
     * @return the result message to be shown to the user
     */
    private String handleRemove(String input) {
        try {
            int index = Parser.parseIndex(input);
            Task removed = tasks.remove(index);

            storage.saveTasks(tasks.getAll());

            lastUndoAction = () -> {
                tasks.add(index, removed);
                storage.saveTasks(tasks.getAll());
            };

            return ui.removeTask(removed, tasks.size());

        } catch (NumberFormatException e) {
            return ui.showMessage("Boss, please provide a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            return ui.showMessage("Apologies Boss, that task number does not exist.");
        }
    }

    /**
     * Creates and adds a new ToDo task from user input.
     *
     * @param input the full user input
     * @return the result message to be shown to the user
     */
    private String handleTodo(String input) {
        try {
            String description = Parser.parseTodo(input);
            Task task = new ToDo(description);

            tasks.add(task);
            storage.saveTasks(tasks.getAll());

            lastUndoAction = () -> {
                tasks.remove(tasks.size() - 1);
                storage.saveTasks(tasks.getAll());
            };

            return ui.addTask(task, tasks.size());

        } catch (IllegalArgumentException e) {
            return ui.showMessage("Boss, the description of a todo cannot be empty.");
        }
    }

    /**
     * Creates and adds a new Deadline task from user input.
     *
     * @param input the full user input
     * @return the result message to be shown to the user
     */
    private String handleDeadline(String input) {
        try {
            String[] parts = Parser.parseDeadline(input);
            Task task = new Deadline(parts[0], parts[1]);

            tasks.add(task);
            storage.saveTasks(tasks.getAll());

            lastUndoAction = () -> {
                tasks.remove(tasks.size() - 1);
                storage.saveTasks(tasks.getAll());
            };

            return ui.addTask(task, tasks.size());

        } catch (IllegalArgumentException e) {
            return ui.showMessage("Boss, Deadline format: deadline <description> /by <time>");
        } catch (DateTimeParseException e) {
            return ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for deadlines.");
        }
    }

    /**
     * Creates and adds a new Event task from user input.
     *
     * @param input the full user input
     * @return the result message to be shown to the user
     */
    private String handleEvent(String input) {
        try {
            String[] parts = Parser.parseEvent(input);
            Task task = new Event(parts[0], parts[1], parts[2]);

            tasks.add(task);
            storage.saveTasks(tasks.getAll());

            lastUndoAction = () -> {
                tasks.remove(tasks.size() - 1);
                storage.saveTasks(tasks.getAll());
            };

            return ui.addTask(task, tasks.size());

        } catch (IllegalArgumentException e) {
            return ui.showMessage(
                "Boss, Event format: event <description> /from <start> /to <end>");
        } catch (DateTimeParseException e) {
            return ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for events.");
        }
    }

    /**
     * Finds tasks containing the given keyword.
     *
     * @param input the full user input containing the search keyword
     * @return the result message listing matched tasks
     */
    private String handleFind(String input) {
        try {
            String keyword = Parser.parseFind(input);
            List<Task> matches = tasks.find(keyword);
            return ui.findTask(matches);

        } catch (IllegalArgumentException e) {
            return ui.showMessage("Boss, please provide a keyword to search for.");
        }
    }

    /**
     * Undoes the last command if possible.
     *
     * @return the result message indicating undo status
     */
    private String handleUndo() {
        if (lastUndoAction == null) {
            return ui.showMessage("Boss, nothing to undo.");
        }

        lastUndoAction.run();
        lastUndoAction = null;

        return ui.showMessage("Undid the previous command.");
    }

    public static void main(String[] args) {
        new Bossa("data/bossa.txt");
    }

    /**
     * Returns the welcome message from the UI.
     *
     * @return the welcome message
     */
    public String getWelcome() {
        return ui.showWelcome();
    }
}
