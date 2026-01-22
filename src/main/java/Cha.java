import java.util.ArrayList;
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
         + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        ArrayList<Task> list = new ArrayList<>();
        Integer count = 1;
        String response = scanner.nextLine();
        while (!response.equals("bye")) {

            // user wants list
            if (response.equals("list")) {
                list.forEach(System.out::println);
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                response = scanner.nextLine();
                continue;
            }

            // user marks a task
            if (response.matches("mark \\d+")) {
                String i = response.replaceAll("\\D", "");
                Task t = list.get(Integer.parseInt(i) - 1);
                t.markAsDone();
                System.out.println(
                    "Great! This Cha has been made.\n" 
                    + t.getTask()
                    + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                response = scanner.nextLine();
                continue;

            }
            
            // user gives task
            Task t = new Task(response, count);
            list.add(t);
            System.out.println("added: " + response +
                "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            count++;
            response = scanner.nextLine();
        }
        scanner.close();
        System.out.println("CHA CHA! See you again soon!\n" 
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}