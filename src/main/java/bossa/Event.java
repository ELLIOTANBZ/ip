package bossa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task, which is a type of Task that has
 * a start date and an end date.
 *
 * <p>An Event stores a description, a start date, and an end date.
 * Both dates are parsed from strings in the format {@code yyyy-MM-dd}
 * and can be displayed in a more readable format.</p>
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs an Event task with the specified description, start date, and end date.
     *
     * @param description the description of the event
     * @param from the start date in {@code yyyy-MM-dd} format
     * @param to the end date in {@code yyyy-MM-dd} format
     * @throws java.time.format.DateTimeParseException if either date string is not in the correct format
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from, INPUT_FORMAT);
        this.to = LocalDate.parse(to, INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the Event task,
     * including the type indicator [E], its completion status,
     * and the start and end dates in readable format.
     *
     * @return a formatted string representing the Event
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns a string suitable for storage in a text file,
     * including type, completion status, description, start date, and end date
     * in {@code yyyy-MM-dd} format.
     *
     * @return a string representing the Event for storage
     */
    @Override
    public String toStorageString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.toString() + " | " + to.toString();
    }
}
