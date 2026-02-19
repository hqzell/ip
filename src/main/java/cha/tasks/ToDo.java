package cha.tasks;

public class ToDo extends Task {
    public ToDo(String desc) {
        super(desc);
    }

    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + desc;
    }


    @Override
    public String getType() {
        return "T";
    }
}