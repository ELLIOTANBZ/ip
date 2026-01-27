import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from, INPUT_FORMAT);
        this.to = LocalDate.parse(to, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toStorageString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.toString() + " | " + to.toString();
    }
}
