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
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index the index of the task to remove
     * @return the removed task
     */
    public Task remove(int index){
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the task at the given index
     */
    public Task get(int index){
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


}
