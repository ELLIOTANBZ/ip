import java.time.format.DateTimeParseException;
import java.util.List;

public class Bossa {

    private final TaskList tasks;
    private final Ui ui;
    private final Storage storage;

    public Bossa(String filePath) {
        ui = new Ui();
        storage = new Storage("data/bossa.txt");
        List<Task> loaded = storage.loadTasks();
        tasks = new TaskList(loaded);
    }

    public void run() {

        ui.showWelcome();

        while (true) {
            String fullInput = ui.readCommand().trim();
            String input = Parser.getCommandWord(fullInput);

            if (input.isEmpty()) {
                ui.showDontUnderstand();
                continue;
            }

            if (input.equalsIgnoreCase("bye")) {
                ui.showBye();
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                ui.showAllTasks(tasks);
                continue;
            }

            if (input.equalsIgnoreCase("mark")) {
                try {
                    int index = Integer.parseInt(fullInput.split(" ")[1]) - 1;
                    Task task = tasks.get(index);
                    task.markAsDone();
                    storage.saveTasks(tasks.getAll());
                    ui.markAsDone(task);
                } catch (Exception e) {
                    ui.showMessage("Apologies Boss, that task number does not exist.");
                }
                continue;
            }

            if (input.equalsIgnoreCase("unmark")) {
                try {
                    int index = Integer.parseInt(fullInput.split(" ")[1]) - 1;
                    Task task = tasks.get(index);
                    task.markAsNotDone();
                    storage.saveTasks(tasks.getAll());
                    ui.markAsUndone(task);
                } catch (Exception e) {
                    ui.showMessage("Apologies Boss, that task number does not exist.");
                }
                continue;
            }

            if (input.equalsIgnoreCase("remove")) {
                try {
                    int index = Integer.parseInt(fullInput.split(" ")[1]) - 1;
                    Task removed = tasks.remove(index);
                    storage.saveTasks(tasks.getAll());
                    ui.removeTask(removed, tasks.size());
                } catch (Exception e) {
                    ui.showMessage("Apologies Boss, that task number does not exist.");
                }
                continue;
            }

            if (input.equalsIgnoreCase("todo")) {
                String description = fullInput.length() > 4 ? fullInput.substring(5).trim() : "";
                if (description.isEmpty()) {
                    ui.showMessage("Boss, the description of a todo cannot be empty.");
                    continue;
                }
                Task task = new ToDo(description);
                tasks.add(task);
                storage.saveTasks(tasks.getAll());
                ui.addTask(task, tasks.size());
                continue;
            }

            if (input.equalsIgnoreCase("deadline")) {
                String[] parts = fullInput.substring(9).split(" /by ");
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    ui.showMessage("Boss, Deadline format: deadline <description> /by <time>");
                    continue;
                }
                try {
                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks.add(task);
                    storage.saveTasks(tasks.getAll());
                    ui.addTask(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for deadlines.");
                }
                continue;
            }

            if (input.equalsIgnoreCase("event")) {
                String[] parts = fullInput.substring(6).split(" /from | /to ");
                if (parts.length < 3 || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                    ui.showMessage("Boss, Event format: event <description> /from <start> /to <end>");
                    continue;
                }
                try {
                    Task task = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    tasks.add(task);
                    storage.saveTasks(tasks.getAll());
                    ui.addTask(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showMessage("Boss, invalid date format. Use yyyy-MM-dd for events.");
                }
                continue;
            }

            ui.showDontUnderstand();
        }
    }

    public static void main(String[] args) {
        Bossa bossa = new Bossa("data/tasks.txt");
        bossa.run();
    }

}
