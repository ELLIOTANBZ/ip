package bossa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task: a type of Task with a 
 * specific due date
 * 
 * <p>A deadline stores a description and a due date. The due date
 * is parsed from a string in the format {@code yyyy-MM-dd} and 
 * can be displayed in a more readable format.</p>
 */
public class Deadline extends Task {
    protected LocalDate by;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs a Deadline task with the specified description and due date.
     *
     * @param description the description of the task
     * @param by the due date in {@code yyyy-MM-dd} format
     * @throws java.time.format.DateTimeParseException if the {@code by} string is not in the correct format
     */
    public Deadline(String description, String by) {
        super(description);
        assert by != null : "Deadline date string must not be null";

        this.by = LocalDate.parse(by, INPUT_FORMAT);
        assert this.by != null : "Parsed deadline date must not be null";
    }

    /**
     * Returns a string representation of the Deadline task,
     * including the type indicator [D], its completion status,
     * and the due date in readable format.
     *
     * @return a formatted string representing the Deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a string suitable for storage in a text file,
     * including type, completion status, description, and due date
     * in {@code yyyy-MM-dd} format.
     *
     * @return a string representing the Deadline for storage
     */
    @Override
    public String toStorageString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.toString();
    }
}
