public abstract class Task {
    protected String desc;
    protected boolean isDone;

    public Task(String desc) {
        this.desc = desc;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String getStatus() {
        return isDone ? "X" : " ";
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + getType() + "][" + getStatus() + "] " + desc;
    }
}