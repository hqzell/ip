import java.util.Scanner;

public class Cha {    

    public static void main(String[] args) {
        String logo =         
          "  _____ _   _     _        ~~      \n"
        + " / ____| | | |   / \\   ___~_~~____\n"
        + "| |    | |_| |  / _ \\  |         | \n"
        + "| |    |  _  | / ___ \\ |_________|\n"
        + "| |____| | | |/ /   \\ \\ \\        /\n"
        + " \\_____|_| |_|_/     \\_\\ \\______/\n"
        ;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm\n" + logo
            + "\nWhat Cha can I get for you?\n" 
         + "____________________________________________________________\n");

        String user = scanner.nextLine();
        while (!user.equals("bye")) {
            System.out.println(user +
                "\n____________________________________________________________\n");
            user = scanner.nextLine();
        }
        scanner.close();
        System.out.println("CHA CHA! See you again soon!\n" 
            + "____________________________________________________________");
    }
}