import java.util.ArrayList;

/**
 * Encapsulates the list of tasks and provides operations on them.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /** Creates a TaskList with an existing list of tasks */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks); // defensive copy
    }

    /** Creates an empty TaskList */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Adds a task to the list */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /** Deletes a task by index and returns it */
    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    /** Marks a task as done by index and returns it */
    public Task markAsDone(int index) throws IndexOutOfBoundsException {
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    /** Gets a task by index */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        return tasks.get(index);
    }

    /** Returns the number of tasks */
    public int size() {
        return tasks.size();
    }

    /** Returns an unmodifiable copy of the task list */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}
