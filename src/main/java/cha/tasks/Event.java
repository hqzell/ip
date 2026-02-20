package cha.tasks;

import cha.ChaException;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

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

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | "
                + desc + " | " + from + " - " + to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
