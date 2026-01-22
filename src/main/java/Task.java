
public class Task {
    protected String desc;
    protected boolean isDone;
    protected int index;

    public Task(String desc, int count) {
        this.desc = desc;
        this.isDone = false;
        this.index = count;
    }
    
    public void markAsDone() {
        this.isDone = true;
    }

    public String getStatus() {
        return (isDone ? "X" : " ");
    }

    public String getTask() {
        return String.format("[%s] %s", this.getStatus(), desc);
    }

    @Override
    public String toString() {
        return String.format("%d. [%s] %s", index, this.getStatus(), desc);
    }
}
