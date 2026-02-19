import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public static Deadline parse(String input) throws ChaException {
        String[] parts = input.split(" /by ");
        String desc = parts[0].trim();
        if (desc.isEmpty()) throw new ChaException(
            "CHA doesn't know what to do! (The description cannot be empty)");
        String by = (parts.length > 1 ? parts[1].trim() : "");
        if (by.isEmpty()) throw new ChaException(
            "CHA doesn't know when it's due! (Use /by <time>)");
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime byFormatted = LocalDateTime.parse(by, inputFormatter);
            return new Deadline(desc, byFormatted);
        } catch (DateTimeParseException e) {
            throw new ChaException(
                "CHA can't understand that time! (Use format: yyyy-MM-dd HHmm)");
        }
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
