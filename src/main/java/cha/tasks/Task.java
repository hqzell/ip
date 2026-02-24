package cha.tasks;

import cha.ChaException;

/**
 * Represents a generic task.
 * A Task contains a description and a completion status.
 * Subclasses define specific task types.
 */
public abstract class Task {

    protected String desc;
    protected boolean isDone;

    /**
     * Creates a Task with the given description.
     * The task is initially marked as not done.
     *
     * @param desc The description of the task.
     */
    public Task(String desc) {
        assert desc != null : "Task description cannot be null";
        this.desc = desc;
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Updates the description of this task.
     *
     * @param description The new description of the task.
     * @throws AssertionError If the description is null or empty.
     */
    public void setDescription(String description) {
        assert description != null && !description.isEmpty()
                : "Description cannot be empty";
        this.desc = description;
    }

    /**
     * Returns the completion status of this task.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatus() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the type identifier of this task.
     * Implemented by subclasses.
     *
     * @return A single-character string representing the task type.
     */
    public abstract String getType();

    /**
     * Returns the file format representation of this task.
     * Implemented by subclasses.
     *
     * @return A formatted string suitable for saving to file.
     */
    public abstract String toFileFormat();

    /**
     * Returns a string representation of this task
     * for display to the user.
     *
     * @return A formatted string containing the task type,
     *         status, and description.
     */
    @Override
    public String toString() {
        return "[" + getType() + "][" + getStatus() + "] " + desc;
    }

    /**
     * Updates this task based on the given update arguments.
     *
     * @param args Update arguments (e.g. "/desc new description")
     * @throws ChaException If update format is invalid
     */
    public void update(String args) throws ChaException {
        if (args.contains("/desc ")) {
            String[] parts = args.split("/desc ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new ChaException("Description cannot be empty.");
            }
            setDescription(parts[1].trim());
        } else {
            throw new ChaException("Invalid update field for this task.");
        }
    }
}
