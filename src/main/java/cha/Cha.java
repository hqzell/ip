package cha;

public class Cha {

    private Storage storage;
    private TaskList tasks;
    private String welcomeString;

    public Cha() {
        storage = new Storage();
        tasks = new TaskList(storage.load());
        welcomeString = "Hello! I'm Cha, your personal tea-making assistant.\nWhat do you want to make?";
    }

    public String getWelcomeMessage() {
        return welcomeString;
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
