package bossa.storage;

import bossa.Task; // Make sure Task.java is in src\main\java\bossa
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                return tasks;
            }
            for (String line : Files.readAllLines(filePath)) {
                if (line.trim().isEmpty()) continue;
                Task task = Task.fromString(line);
                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("Error loading data. Starting with empty task list.");
        }
        return tasks;
    }

    public void save(List<Task> tasks) {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) lines.add(task.toFileString());
        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}
