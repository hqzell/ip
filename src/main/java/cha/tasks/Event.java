package cha.tasks;

import cha.ChaException;

/**
 * Represents an Event task with a start time and end time.
 * An Event contains a description, a start time, and an end time.
 */
public class Event extends Task {

    /** Delimiters used in parsing user input. */
    private static final String FROM_DELIMITER = " /from ";
    private static final String TO_DELIMITER = " /to ";

    /** Error messages. */
    private static final String ERROR_EMPTY_DESCRIPTION = "CHA doesn't know what to do! (The description cannot be empty)";

    private static final String ERROR_MISSING_FROM = "CHA doesn't know when it starts! (Use /from <start> /to <end>)";

    private static final String ERROR_MISSING_TO = "CHA doesn't know when it ends! (Use /from <start> /to <end>)";

    /** Start and end times of the event. */
    private final String startTime;
    private final String endTime;

    /**
     * Creates an Event task.
     *
     * @param description The description of the event.
     * @param startTime   The start time of the event.
     * @param endTime     The end time of the event.
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        assert startTime != null : "Start time must not be null";
        assert endTime != null : "End time must not be null";

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Parses user input and creates an Event object.
     *
     * Expected format:
     * <description> /from <start> /to <end>
     *
     * @param input Raw input string.
     * @return Parsed Event object.
     * @throws ChaException If any required component is missing.
     */
    public static Event parse(String input) throws ChaException {
        assert input != null : "Input to parse() must not be null";

        String[] descriptionSplit = input.split(FROM_DELIMITER, 2);
        String description = descriptionSplit[0].trim();

        if (description.isEmpty()) {
            throw new ChaException(ERROR_EMPTY_DESCRIPTION);
        }

        if (descriptionSplit.length < 2) {
            throw new ChaException(ERROR_MISSING_FROM);
        }

        String[] timeSplit = descriptionSplit[1].split(TO_DELIMITER, 2);
        String startTime = timeSplit[0].trim();

        if (startTime.isEmpty()) {
            throw new ChaException(ERROR_MISSING_FROM);
        }

        if (timeSplit.length < 2) {
            throw new ChaException(ERROR_MISSING_TO);
        }

        String endTime = timeSplit[1].trim();
        if (endTime.isEmpty()) {
            throw new ChaException(ERROR_MISSING_TO);
        }

        return new Event(description, startTime, endTime);
    }

    /**
     * Returns the type identifier for this task.
     *
     * @return "E" representing Event.
     */
    @Override
    public String getType() {
        return "E";
    }

    /**
     * Returns the start time.
     *
     * @return Start time as String.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time.
     *
     * @return End time as String.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Returns a file-storage format representation of this Event.
     *
     * @return Formatted string for saving to file.
     */
    @Override
    public String toFileFormat() {
        assert desc != null : "Description must not be null";
        assert startTime != null : "Start time must not be null";
        assert endTime != null : "End time must not be null";

        return "E | " + (isDone ? "1" : "0")
                + " | " + desc
                + " | " + startTime + " - " + endTime;
    }

    /**
     * Returns a user-friendly string representation.
     *
     * @return Display string of the Event.
     */
    @Override
    public String toString() {
        assert startTime != null : "Start time must not be null";
        assert endTime != null : "End time must not be null";

        return super.toString()
                + " (from: " + startTime + " to: " + endTime + ")";
    }
}
