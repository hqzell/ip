package cha.tasks;

/**
 * Represents a ToDo task.
 * A ToDo contains only a description and does not have
 * additional time-related attributes.
 */
public class ToDo extends Task {

    /**
     * Creates a ToDo task with the given description.
     *
     * @param desc The description of the task.
     */
    public ToDo(String desc) {
        super(desc);
    }

    /**
     * Returns the file format representation of this ToDo.
     *
     * @return A formatted string suitable for saving to file.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + desc;
    }

    /**
     * Returns the type identifier for this task.
     *
     * @return The string "T".
     */
    @Override
    public String getType() {
        return "T";
    }
}
