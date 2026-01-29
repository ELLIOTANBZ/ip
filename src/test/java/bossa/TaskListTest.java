package bossa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addTask_sizeIncreases() {
        TaskList taskList = new TaskList();

        taskList.add(new ToDo("read book"));
        taskList.add(new ToDo("write code"));

        assertEquals(2, taskList.size());
    }

    @Test
    public void getTask_correctTaskReturned() {
        TaskList taskList = new TaskList();
        Task task = new ToDo("exercise");

        taskList.add(task);

        assertEquals(task, taskList.get(0));
    }

    @Test
    public void removeTask_sizeDecreasesAndCorrectTaskRemoved() {
        TaskList taskList = new TaskList();
        Task task1 = new ToDo("task one");
        Task task2 = new ToDo("task two");

        taskList.add(task1);
        taskList.add(task2);

        Task removed = taskList.remove(0);

        assertEquals(task1, removed);
        assertEquals(1, taskList.size());
        assertEquals(task2, taskList.get(0));
    }
}
