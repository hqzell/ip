import java.util.ArrayList;
import java.util.Scanner;

public class Cha {

    public static void main(String[] args) throws ChaException {
        String logo =
              "  _____ _   _     _        ~~      \n"
            + " / ____| | | |   / \\   ___~_~~____\n"
            + "| |    | |_| |  / _ \\  |         | \n"
            + "| |    |  _  | / ___ \\ |_________|\n"
            + "| |____| | | |/ /   \\ \\ \\        /\n"
            + " \\_____|_| |_|_/     \\_\\ \\______/\n";

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        int count = 0;

        System.out.println("Hello! I'm\n" + logo
            + "\nWhat Cha can I get for you?\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String response = scanner.nextLine();
        while (!response.equals("bye")) {

            if (response.equals("list")) {
                System.out.println("Here are your Chas:");
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                response = scanner.nextLine();
                continue;
            }

            if (response.matches("mark \\d+")) {
                int i = Integer.parseInt(response.replaceAll("\\D", "")) - 1;
                if (i >= 0 && i < count) {
                    tasks.get(i).markAsDone();
                    System.out.println("Great! Your Cha is completed:\n  " + tasks.get(i) 
                        + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                } else {
                    System.out.println("Invalid task number.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                }
                response = scanner.nextLine();
                continue;
            }

            if (response.matches("delete \\d+")) {
                int i = Integer.parseInt(response.replaceAll("\\D", "")) - 1;
                if (i >= 0 && i < count) {
                    Task t = tasks.get(i);
                    tasks.remove(i);
                    count--;
                    System.out.println("Bye bye Cha! I've removed this task:\n " + t
                        + "\nNow you have " + count + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                }
            }
            // Add tasks
            try {
                if (response.startsWith("todo ")) {
                    String desc = response.substring(5).trim();
                    if (desc.isEmpty()) {
                        throw new ChaException("CHA doesn't know what to do! (The description cannot be empty)");
                    }
                    tasks.add(new ToDo(desc));
                    System.out.println("On it! One Cha coming right up :D\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + (count + 1) + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    count++;
                } else if (response.startsWith("deadline ")) {
                    // Format: deadline <desc> /by <time>
                    String[] parts = response.substring(9).split("/by");
                    String desc = parts[0].trim();
                    if (desc.isEmpty()) {
                        throw new ChaException("CHA doesn't know what to do! (The description cannot be empty)");
                    }
                    String by = parts.length > 1 ? parts[1].trim() : "";
                    if (by.isEmpty()) {
                        throw new ChaException("CHA doesn't know when it's due! (Use /by <time> to let Cha know the deadline)");
                    }
                    tasks.add(new Deadline(desc, by));
                    System.out.println("We got you, your Cha is on the way! \n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + (count + 1) + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    count++;
                } else if (response.startsWith("event ")) {
                    // Format: event <desc> /from <start> /to <end>
                    String[] descAndTimes = response.substring(6).split("/from");
                    String desc = descAndTimes[0].trim();
                    if (desc.isEmpty()) {
                        throw new ChaException("CHA doesn't know what to do! (The description cannot be empty)");
                    }
                    String times = descAndTimes.length > 1 ? descAndTimes[1].trim() : "";
                    if (times.isEmpty()) {
                        throw new ChaException("CHA doesn't know when it starts! (Use /from <start> /to <end> to let Cha know the event time)");
                    }
                    String[] timeParts = times.split("/to");
                    String from = timeParts[0].trim();
                    String to = timeParts.length > 1 ? timeParts[1].trim() : "";
                    if (to.isEmpty()) {
                        throw new ChaException("CHA doesn't know when it ends! (Use /from <start> /to <end> to let Cha know the event time)");
                    }
                    tasks.add(new Event(desc, from, to));
                    System.out.println("Scheduled! We'll see you there~\n  " +  tasks.get(tasks.size() - 1)
                        + "\nNow you have " + (count + 1) + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    count++;
            } else {
                System.out.println("Unknown command!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        } catch (ChaException e) {
            System.out.println(e.getMessage() + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
            response = scanner.nextLine();
    }
        scanner.close();
        System.out.println("CHA CHA CHA! See you again soon!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
