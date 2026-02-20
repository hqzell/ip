package cha;

import java.util.Scanner;

/**
 * Handles all user interactions for the application.
 * Responsible for displaying messages and reading user input.
 */
public class Ui {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the welcome message and application logo.
     *
     * @param logo The ASCII logo to display.
     */
    public void showWelcome(String logo) {
        System.out.println("Hello! I'm\n" + logo
                + "\nWhat Cha can I get for you?\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    /**
     * Reads a command entered by the user.
     *
     * @return The full command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("CHA CHA CHA! See you again soon!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Displays a normal message followed by a separator line.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}
