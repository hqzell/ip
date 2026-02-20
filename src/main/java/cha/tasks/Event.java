package cha.tasks;

import cha.ChaException;

/**
 * Represents an Event task with a start time and end time.
 * An Event contains a description, a start time, and an end time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Creates an Event task with the given description,
     * start time, and end time.
     *
     * @param desc The description of the event.
     * @param from The start time of the event.
     * @param to   The end time of the event.
     */
    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    /**
     * Parses the user input string and creates an Event object.
     * The expected format is:
     * description /from <start> /to <end>
     *
     * @param input The raw input string containing the event details.
     * @return A new Event object constructed from the input.
     * @throws ChaException If the description, start time,
     *                      or end time is missing.
     */
    public static Event parse(String input) throws ChaException {
        String[] descAndTimes = input.split("/from");
        String desc = descAndTimes[0].trim();
        if (desc.isEmpty())
            throw new ChaException(
                    "CHA doesn't know what to do! (The description cannot be empty)");

        String times = descAndTimes.length > 1 ? descAndTimes[1].trim() : "";
        if (times.isEmpty())
            throw new ChaException(
                    "CHA doesn't know when it starts! (Use /from <start> /to <end>)");

        String[] timeParts = times.split("/to");
        String from = timeParts[0].trim();
        String to = (timeParts.length > 1 ? timeParts[1].trim() : "");
        if (to.isEmpty())
            throw new ChaException(
                    "CHA doesn't know when it ends! (Use /from <start> /to <end>)");

        return new Event(desc, from, to);
    }

    /**
     * Returns the type identifier for this task.
     *
     * @return The string "E".
     */
    @Override
    public String getType() {
        return "E";
    }

    /**
     * Returns the file format representation of this Event.
     *
     * @return A formatted string suitable for saving to file.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | "
                + desc + " | " + from + " - " + to;
    }

    /**
     * Returns a string representation of this Event
     * for display to the user.
     *
     * @return A formatted string containing the description
     *         and event timing.
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
