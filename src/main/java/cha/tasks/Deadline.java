package cha.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import cha.ChaException;

/**
 * Represents a deadline task that must be completed before a specific date and
 * time.
 */
public class Deadline extends Task {

    /** Delimiter used to separate description and deadline. */
    private static final String BY_DELIMITER = " /by ";

    /** Expected input date-time format. */
    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd HHmm";

    /** Format used for file storage. */
    private static final String FILE_DATE_PATTERN = "yyyy-MM-dd HHmm";

    /** Format used for user display. */
    private static final String OUTPUT_DATE_PATTERN = "dd MMM yyyy, ha";

    /** Pre-created formatters (avoid recreating repeatedly). */
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern(INPUT_DATE_PATTERN);

    private static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern(FILE_DATE_PATTERN);

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern(OUTPUT_DATE_PATTERN,
            Locale.ENGLISH);

    /** The date and time by which the task must be completed. */
    private LocalDateTime deadline;

    /**
     * Creates a Deadline task.
     *
     * @param description Description of the task.
     * @param deadline    The deadline date and time.
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        assert deadline != null : "Deadline date must not be null";
        this.deadline = deadline;
    }

    /**
     * Parses a string into a Deadline task.
     *
     * Expected format:
     * <description> /by yyyy-MM-dd HHmm
     *
     * @param input User input string.
     * @return Parsed Deadline object.
     * @throws ChaException If input format is invalid.
     */
    public static Deadline parse(String input) throws ChaException {
        assert input != null : "Input to parse() must not be null";

        String[] parts = input.split(BY_DELIMITER, 2);
        String description = parts[0].trim();

        if (description.isEmpty()) {
            throw new ChaException(
                    "CHA doesn't know what to do! (The description cannot be empty)");
        }

        if (parts.length < 2) {
            throw new ChaException(
                    "CHA doesn't know when it's due! (Use /by <time>)");
        }

        String deadlineText = parts[1].trim();
        if (deadlineText.isEmpty()) {
            throw new ChaException(
                    "CHA doesn't know when it's due! (Use /by <time>)");
        }

        try {
            LocalDateTime parsedDeadline = LocalDateTime.parse(deadlineText, INPUT_FORMATTER);
            return new Deadline(description, parsedDeadline);
        } catch (DateTimeParseException e) {
            throw new ChaException(
                    "CHA can't understand that time! (Use format: "
                            + INPUT_DATE_PATTERN + ")");
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
     * @return Deadline as LocalDateTime.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Updates the deadline date and time of this task.
     *
     * @param by The new deadline date and time.
     * @throws AssertionError If {@code by} is null.
     */
    public void setBy(LocalDateTime by) {
        assert by != null : "Deadline time cannot be null";
        this.deadline = by;
    }

    /**
     * Returns a user-friendly string representation of the deadline.
     *
     * @return Formatted deadline string.
     */
    @Override
    public String toString() {
        assert desc != null : "Deadline date must not be null";

        return super.toString()
                + " (by: " + deadline.format(OUTPUT_FORMATTER) + ")";
    }

    /**
     * Converts the task into a file-storage format.
     *
     * @return Formatted string for file storage.
     */
    @Override
    public String toFileFormat() {
        assert desc != null : "Description should not be null";
        assert deadline != null : "Deadline date should not be null";

        return "D | " + (isDone ? "1" : "0")
                + " | " + desc
                + " | " + deadline.format(FILE_FORMATTER);
    }

    @Override
    public void update(String args) throws ChaException {
        assert args != null : "Update arguments should not be null";

        boolean updated = false;

        // Allow description update
        if (args.contains("/desc ")) {
            super.update(args);
            updated = true;
        }

        if (args.contains("/by ")) {
            String[] parts = args.split("/by ", 2);
            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                throw new ChaException("Deadline time cannot be empty.");
            }

            try {
                LocalDateTime parsed = LocalDateTime.parse(
                        parts[1].trim(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
                setBy(parsed);
                updated = true;
            } catch (Exception e) {
                throw new ChaException(
                        "Use format: yyyy-MM-dd HHmm (e.g. 2025-10-15 1800)");
            }
        }

        if (!updated) {
            throw new ChaException("Valid fields: /desc or /by");
        }
    }
}
