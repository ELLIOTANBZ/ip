/**
 * Represents a generic task.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts this task into a string for saving to file.
     *
     * @return Storage string representing this task.
     */
    public String toStorageString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Converts a storage string into a Task object.
     *
     * @param line Storage string read from file.
     * @return Task object represented by the string.
     * @throws IllegalArgumentException if the line format is invalid.
     */
    public static Task fromStorageString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task storage format: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid deadline storage format: " + line);
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Invalid event storage format: " + line);
                }
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
