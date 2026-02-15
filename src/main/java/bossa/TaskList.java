package bossa;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of tasks.
 *
 * <p>This class wraps a {@link List} of {@link Task} objects and provides
 * methods to add, remove, and access tasks. It serves as the main container
 * for tasks in the Bossa application.</p>
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList(){
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        assert tasks != null : "Task list provided to constructor must not be null";
        this.tasks = tasks;
    }

    /**
     * Returns the list of all tasks.
     *
     * @return a list of tasks
     */
    public List<Task> getAll() {
        return tasks;
    }

    /**
     * Adds a task to the TaskList.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        assert task != null : "Cannot add null task to TaskList";
        int before = tasks.size();
        tasks.add(task);
        assert tasks.size() == before + 1
            : "Task list size should increase after adding a task";
    }

    public void add(int index, Task task) {
        tasks.add(index, task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index the index of the task to remove
     * @return the removed task
     */
    public Task remove(int index){
        assert index >= 0 && index < tasks.size() 
            : "Remove index out of bounds";

        int before = tasks.size();
        Task removed = tasks.remove(index);
        assert tasks.size() == before - 1
            : "Task list size should decrease after removal";
        return removed;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the task at the given index
     */
    public Task get(int index){
        assert index >= 0 && index < tasks.size()
            : "Get index out of bounds";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the TaskList.
     *
     * @return the size of the task list
     */
    public int size(){
        return tasks.size();
    }

    public List<Task> find(String keyword) {
        assert keyword != null : "Keyword for find must not be null";

        List<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            assert task != null : "Task in list should not be null";

            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }

        return matches;
    }


}
