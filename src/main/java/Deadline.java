import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a deadline task that must be completed before a specific
 * date and time.
 */
public class Deadline extends Task {

    /** The date and time by which the task must be completed. */
    private LocalDateTime by;

    /**
     * Creates a Deadline task.
     *
     * @param description Description of the task.
     * @param by The deadline date and time.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the type of this task.
     *
     * @return "D" representing Deadline.
     */
    @Override
    public String getType() {
        return "D";
    }

    /**
     * Returns the deadline date and time.
     *
     * @return The deadline as a LocalDateTime.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a user-friendly string representation of the deadline.
     * The date is formatted as MMM dd yyyy HHmm.
     *
     * @return Formatted deadline string.
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormatter =
                DateTimeFormatter.ofPattern("dd MMM yyyy, ha", Locale.ENGLISH);

        return super.toString()
                + " (by: " + by.format(outputFormatter) + ")";
    }

    /**
     * Converts the task into a file-storage format.
     * The date is saved in yyyy-MM-dd HHmm format.
     *
     * @return Formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        DateTimeFormatter fileFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        return "D | " + (isDone ? "1" : "0")
                + " | " + super.toString()
                + " | " + by.format(fileFormatter);
    }
}
