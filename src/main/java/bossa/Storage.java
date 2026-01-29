package bossa;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    
    private final Path filePath;

    public Storage(String path){
        this.filePath = Paths.get(path);
    }

    public List<Task> loadTasks() {

        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(filePath)) {
                try {
                    tasks.add(Task.fromStorageString(line));
                } catch (Exception ignored) {}
            }
        } catch (IOException ignored) {}

        return tasks;
    }

    /**
     * Saves current tasks to disk.
     * Creates data folder if it doesn't exist.
     */
    public void saveTasks(List<Task> tasks)  {
        try {
            Files.createDirectories(filePath.getParent());

            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toStorageString());
            }

            Files.write(filePath, lines);
        } catch (IOException ignored) {}
    }

}
