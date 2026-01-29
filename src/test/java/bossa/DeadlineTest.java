package bossa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void toString_validDate_correctFormat() {
        Deadline deadline = new Deadline("submit report", "2025-03-15");

        String expected = "[D][ ] submit report (by: Mar 15 2025)";
        assertEquals(expected, deadline.toString());
    }

    @Test
    public void toStorageString_notDone_correctFormat() {
        Deadline deadline = new Deadline("homework", "2024-12-01");

        String expected = "D | 0 | homework | 2024-12-01";
        assertEquals(expected, deadline.toStorageString());
    }

    @Test
    public void toStorageString_markedDone_correctFormat() {
        Deadline deadline = new Deadline("project", "2024-11-20");
        deadline.markAsDone();

        String expected = "D | 1 | project | 2024-11-20";
        assertEquals(expected, deadline.toStorageString());
    }
}
