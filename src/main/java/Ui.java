// Ui.java
import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome(String logo) {
        System.out.println("Hello! I'm\n" + logo + "\nWhat Cha can I get for you?\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showGoodbye() {
        System.out.println("CHA CHA CHA! See you again soon!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public void showMessage(String message) {
        System.out.println(message);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");  
    }
}