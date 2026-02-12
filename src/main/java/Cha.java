import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main driver class for the Cha task tracker.
 * Supports user commands to add todos, deadlines, and events, to list tasks,
 * to delete tasks, and to mark tasks as completed.
 */

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
<<<<<<< HEAD
        Storage storage = new Storage();
        ArrayList<Task> tasks = storage.load();
        int count = 0;
=======
        ArrayList<Task> tasks = new ArrayList<>();
>>>>>>> master

        System.out.println("Hello! I'm\n" + logo
                + "\nWhat Cha can I get for you?\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String response = scanner.nextLine();

        while (!response.equals("bye")) {

<<<<<<< HEAD
            if (response.equals("list")) {
                System.out.println("Here are your Chas:");
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                response = scanner.nextLine();
                storage.save(tasks);
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
                storage.save(tasks);
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
                response = scanner.nextLine();
                storage.save(tasks);
                continue;
            }
            // Add tasks
=======
>>>>>>> master
            try {
                if (response.equals("list")) {
                    printTaskList(tasks);

                } else if (response.matches("mark \\d+")) {
                    markTask(tasks, response);

                } else if (response.matches("delete \\d+")) {
                    deleteTask(tasks, response);

                } else if (response.startsWith("todo ")) {
                    addToDo(tasks, response);

                } else if (response.startsWith("deadline ")) {
                    addDeadline(tasks, response);

                } else if (response.startsWith("event ")) {
                    addEvent(tasks, response);

                } else {
                    System.out.println("Unknown command!");
                    printDivider();
                }
            } catch (ChaException e) {
                System.out.println(e.getMessage());
                printDivider();
            }
<<<<<<< HEAD
        } catch (ChaException e) {
            System.out.println(e.getMessage() + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
        response = scanner.nextLine();
        storage.save(tasks);
        }
=======

            response = scanner.nextLine();
        }

>>>>>>> master
        scanner.close();
        System.out.println("CHA CHA CHA! See you again soon!");
        printDivider();
    }

    /**
     * Uses the "list" command to print out and index all current tasks.
     *
     * @param tasks list of current tasks
     */
    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are your Chas:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        printDivider();
    }

    /**
     * Uses the "mark" command to mark the specified task as done.
     *
     * @param tasks list of current tasks
     * @param response user input response from the command
     * @throws ChaException if index is invalid
     */
    private static void markTask(ArrayList<Task> tasks, String response) throws ChaException {
        int index = Integer.parseInt(response.replaceAll("\\D", "")) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new ChaException("Invalid task number.");
        }

        tasks.get(index).markAsDone();
        System.out.println("Great! Your Cha is completed:\n  " + tasks.get(index));
        printDivider();
    }

    /**
     * Uses the "delete" command to remove the specified task.
     *
     * @param tasks list of current tasks
     * @param response user input response from the command
     * @throws ChaException if index is invalid
     */
    private static void deleteTask(ArrayList<Task> tasks, String response) throws ChaException {
        int index = Integer.parseInt(response.replaceAll("\\D", "")) - 1;

        if (index < 0 || index >= tasks.size()) {
            throw new ChaException("Invalid task number.");
        }

        Task removed = tasks.remove(index);
        System.out.println("Bye bye Cha! I've removed this task:\n  " + removed);
        System.out.println("Now you have " + tasks.size() + " Chas brewing.");
        printDivider();
    }

    /**
     * Uses the "todo" command to add a ToDo task.
     *
     * @param tasks list of current tasks
     * @param response user input response from the command
     * @throws ChaException if description is empty
     */
    private static void addToDo(ArrayList<Task> tasks, String response) throws ChaException {
        String description = response.substring(5).trim();

        if (description.isEmpty()) {
            throw new ChaException("CHA doesn't know what to do! (The description cannot be empty)");
        }

        tasks.add(new ToDo(description));
        printTaskAdded(tasks, "On it! One Cha coming right up :D");
    }

    /**
     * Uses the "deadline" command to add a Deadline task.
     *
     * @param tasks list of current tasks
     * @param response user input response from the command
     * @throws ChaException if description or by time is invalid
     */
    private static void addDeadline(ArrayList<Task> tasks, String response) throws ChaException {
        String[] parts = response.substring(9).split("/by");
        String description = parts[0].trim();

        if (description.isEmpty()) {
            throw new ChaException("CHA doesn't know what to do! (The description cannot be empty)");
        }

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ChaException(
                    "CHA doesn't know when it's due! (Use /by <time> to let Cha know the deadline)");
        }

        tasks.add(new Deadline(description, parts[1].trim()));
        printTaskAdded(tasks, "We got you, your Cha is on the way!");
    }

    /**
     * Uses the "event" command to add an Event task.
     *
     * @param tasks list of current tasks
     * @param response user input response from the command
     * @throws ChaException if description/start/end times are invalid
     */
    private static void addEvent(ArrayList<Task> tasks, String response) throws ChaException {
        String[] descAndTimes = response.substring(6).split("/from");
        String description = descAndTimes[0].trim();

        if (description.isEmpty()) {
            throw new ChaException("CHA doesn't know what to do! (The description cannot be empty)");
        }

        if (descAndTimes.length < 2) {
            throw new ChaException(
                    "CHA doesn't know when it starts! (Use /from <start> /to <end>)");
        }

        String[] times = descAndTimes[1].split("/to");
        if (times.length < 2 || times[1].trim().isEmpty()) {
            throw new ChaException(
                    "CHA doesn't know when it ends! (Use /from <start> /to <end>)");
        }

        tasks.add(new Event(description, times[0].trim(), times[1].trim()));
        printTaskAdded(tasks, "Scheduled! We'll see you there~");
    }

    /**
     * Prints confirmation that the most recently added task was added.
     *
     * @param tasks list of current tasks
     * @param message confirmation message to print
     */
    private static void printTaskAdded(ArrayList<Task> tasks, String message) {
        System.out.println(message + "\n  "
                + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " Chas brewing.");
        printDivider();
    }

    private static void printDivider() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}
