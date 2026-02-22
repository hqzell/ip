package cha;

import java.util.ArrayList;

import cha.tasks.Task;

/**
 * Encapsulates the list of tasks and provides operations on them.
 * This class manages adding, removing, retrieving, and modifying tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates a TaskList with an existing list of tasks.
     * A defensive copy of the given list is created to prevent external
     * modification.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks); // defensive copy
    }

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index The index of the task to delete.
     * @return The removed task.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index The index of the task to mark as done.
     * @return The updated task.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public Task markTaskAsDone(int index) throws IndexOutOfBoundsException {
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the given index.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        return tasks.get(index);
    }

    /**
     * Returns a formatted string of tasks whose descriptions
     * contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A formatted string of matching tasks.
     */
    public String findTask(String keyword) {
        StringBuilder sb = new StringBuilder();
        int matchCount = 0;

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchCount++;
                sb.append(matchCount)
                        .append(".")
                        .append(tasks.get(i))
                        .append("\n");
            }
        }

        if (matchCount == 0) {
            return "No matching Chas found!";
        }

        return sb.toString().trim();
    }

    /**
     * Returns a formatted string of tasks whose descriptions
     * contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A formatted string of matching tasks.
     */
    public String findTask(String keyword) {
        StringBuilder sb = new StringBuilder();
        int matchCount = 0;

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchCount++;
                sb.append(matchCount)
                        .append(".")
                        .append(tasks.get(i))
                        .append("\n");
            }
        }

        if (matchCount == 0) {
            return "No matching Chas found!";
        }

        return sb.toString().trim();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The total number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a copy of the internal task list.
     * Modifications to the returned list will not affect the original list.
     *
     * @return A new ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns a formatted string representation of all tasks.
     * Each task is numbered starting from 1.
     *
     * @return A string listing all tasks, or a message if the list is empty.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "No tasks in your list!";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1))
                    .append(".")
                    .append(tasks.get(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }
}
