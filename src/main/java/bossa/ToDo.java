package bossa;

/**
 * Represents a ToDo task, a type of Task without a specific date or time.
 *
 * <p>A ToDo stores only a description and its completion status.</p>
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the specified description.
     *
     * @param description the description of the task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task,
     * including the type indicator [T] and its completion status.
     *
     * @return a formatted string representing the ToDo
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string suitable for storage in a text file,
     * including type, completion status, and description.
     *
     * @return a string representing the ToDo for storage
     */
    @Override
    public String toStorageString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
