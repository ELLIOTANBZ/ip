package bossa;

/**
 * Represents a general task with a description and completion status.
 *
 * <p>This is the base class for specific task types such as {@link ToDo},
 * {@link Deadline}, and {@link Event}. It provides common functionality
 * such as marking tasks as done/undone and converting tasks to and from
 * storage-friendly strings.</p>
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     * The task is initially not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        assert !description.trim().isEmpty()
            : "Task description must not be empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a one-character status icon for the task.
     *
     * @return "X" if the task is done, otherwise a space " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); 
    }

    public void markAsDone() {
        assert !isDone : "Task is already marked as done";
        isDone = true;
    }

    public void markAsNotDone() {
        assert isDone : "Task is already marked as not done";
        isDone = false;
    }

    /**
     * Returns a string representation of the task for display purposes.
     * Includes a status icon and the task description.
     *
     * @return a formatted string representing the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns a string suitable for storage in a text file.
     * The base implementation is for a {@link ToDo} task.
     * Subclasses should override this method to include additional data.
     *
     * @return a string representing the task for storage
     */
    public String toStorageString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Creates a {@link Task} object from a storage string.
     *
     * <p>The string should follow the format produced by {@link #toStorageString()},
     * e.g., "T | 1 | Description" for a ToDo, or similar for Deadline/Event.</p>
     *
     * @param line the storage string representing a task
     * @return a Task object corresponding to the storage string
     * @throws IllegalArgumentException if the task type is unknown
     */
    public static Task fromStorageString(String line) {
        assert line != null : "Storage line must not be null";

        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Invalid storage format";

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        Task task;

        switch (type) {
        case "T":
            task = new ToDo(parts[2]);
            break;
        case "D":
            assert parts.length == 4 : "Deadline format invalid";
            task = new Deadline(parts[2], parts[3]);
            break;
        case "E":
            assert parts.length == 5 : "Event format invalid";
            task = new Event(parts[2], parts[3], parts[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type");
        }

        if (isDone) task.markAsDone();
        return task;
    }
}
