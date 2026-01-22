import java.util.Scanner;

public class Cha {

    public static void main(String[] args) {
        String logo =
              "  _____ _   _     _        ~~      \n"
            + " / ____| | | |   / \\   ___~_~~____\n"
            + "| |    | |_| |  / _ \\  |         | \n"
            + "| |    |  _  | / ___ \\ |_________|\n"
            + "| |____| | | |/ /   \\ \\ \\        /\n"
            + " \\_____|_| |_|_/     \\_\\ \\______/\n";

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count = 0;

        System.out.println("Hello! I'm\n" + logo
            + "\nWhat Cha can I get for you?\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String response = scanner.nextLine();
        while (!response.equals("bye")) {

            if (response.equals("list")) {
                System.out.println("Here are your Chas:");
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                response = scanner.nextLine();
                continue;
            }

            if (response.matches("mark \\d+")) {
                int i = Integer.parseInt(response.replaceAll("\\D", "")) - 1;
                if (i >= 0 && i < count) {
                    tasks[i].markAsDone();
                    System.out.println("Great! Your Cha is completed:\n  " + tasks[i]
                        + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                } else {
                    System.out.println("Invalid task number.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                }
                response = scanner.nextLine();
                continue;
            }

            // Add tasks
            if (response.startsWith("todo ")) {
                String desc = response.substring(5).trim();
                tasks[count] = new ToDo(desc);
                System.out.println("On it! One Cha coming right up :D\n  " + tasks[count]
                    + "\nNow you have " + (count + 1) + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                count++;
            } else if (response.startsWith("deadline ")) {
                // Format: deadline <desc> /by <time>
                String[] parts = response.substring(9).split("/by");
                String desc = parts[0].trim();
                String by = parts.length > 1 ? parts[1].trim() : "";
                tasks[count] = new Deadline(desc, by);
                System.out.println("We got you, your Cha is on the way! \n  " + tasks[count]
                    + "\nNow you have " + (count + 1) + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                count++;
            } else if (response.startsWith("event ")) {
                // Format: event <desc> /from <start> /to <end>
                String[] descAndTimes = response.substring(6).split("/from");
                String desc = descAndTimes[0].trim();
                String[] times = descAndTimes[1].split("/to");
                String from = times[0].trim();
                String to = times[1].trim();
                tasks[count] = new Event(desc, from, to);
                System.out.println("Scheduled! We'll see you there~\n  " + tasks[count]
                    + "\nNow you have " + (count + 1) + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                count++;
            } else {
                System.out.println("Unknown command!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }

            response = scanner.nextLine();
        }

        scanner.close();
        System.out.println("CHA CHA CHA! See you again soon!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
