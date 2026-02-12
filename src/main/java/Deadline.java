public class Deadline extends Task {
    protected String by;

    public Deadline(String desc, String by) {
        super(desc);
        this.by = by;
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | "
                + desc + " | " + by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
