public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
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
