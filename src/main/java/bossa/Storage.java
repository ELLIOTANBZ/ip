package bossa;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to disk.
 *
 * <p>The Storage class is responsible for reading tasks from a file
 * and writing tasks back to the file. If the file or its parent
 * directories do not exist, they are created automatically.</p>
 */
public class Storage {
    
    private final Path filePath;

    /**
     * Constructs a Storage object for the specified file path.
     *
     * @param path the file path where tasks will be stored
     */
    public Storage(String path){
        this.filePath = Paths.get(path);
    }

    /**
     * Loads tasks from the storage file.
     *
     * <p>If the file does not exist, returns an empty list. Lines
     * that cannot be parsed into Task objects are ignored.</p>
     *
     * @return a list of tasks loaded from the file
     */
    public List<Task> loadTasks() {

        List<Task> loadedTasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return loadedTasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                addTaskSafely(loadedTasks, line);
            }
        } catch (IOException e) {
            // Unable to read file; return tasks loaded so far
        }

        return loadedTasks;
    }

    private void addTaskSafely(List<Task> tasks, String line) {
        try {
            tasks.add(Task.fromStorageString(line));
        } catch (Exception e) {
            // Ignore malformed lines
        }
    }

    /**
     * Saves the given list of tasks to the storage file.
     *
     * <p>If the parent directories do not exist, they are created.
     * Each task is written as a single line using {@link Task#toStorageString()}.</p>
     *
     * @param tasks the list of tasks to save
     */
    public void saveTasks(List<Task> tasks) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toStorageString());
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            // Unable to save tasks
        }
    }

}
