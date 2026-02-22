package cha;

public class Cha {

    private Storage storage;
    private TaskList tasks;

    public Cha() {
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    public String getWelcomeMessage() {
        return "Hello! I'm Cha, your personal tea-making assistant.\nWhat do you want to make?";
    }

    public String getResponse(String input) {
        try {
            return Parser.parse(input, tasks, storage);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public boolean isExit(String input) {
        return input.equals("bye");
    }
}
