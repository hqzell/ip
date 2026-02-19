package cha;

public class Cha {

    public static void main(String[] args) {
        String logo =
              "  _____ _   _     _        ~~      \n"
            + " / ____| | | |   / \\   ___~_~~____\n"
            + "| |    | |_| |  / _ \\  |         | \n"
            + "| |    |  _  | / ___ \\ |_________|\n"
            + "| |____| | | |/ /   \\ \\ \\        /\n"
            + " \\_____|_| |_|_/     \\_\\ \\______/\n";

        Ui ui = new Ui();
        ui.showWelcome(logo);

        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                isExit = Parser.parse(fullCommand, tasks, ui, storage);
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }
}