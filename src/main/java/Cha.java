import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main driver class for the Cha task tracker.
 * Supports user commands to add todos, deadlines, and events,
 * list tasks, delete tasks, and mark tasks as completed.
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
        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());

        System.out.println("Hello! I'm\n" + logo
            + "\nWhat Cha can I get for you?\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String response;
        while (!(response = scanner.nextLine()).equals("bye")) {
            processCommand(response, tasks, storage);
        }

        scanner.close();
        System.out.println(
            "CHA CHA CHA! See you again soon!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    // Handles user input and routes to appropriate command
    private static void processCommand(String input, TaskList tasks, Storage storage) {
        try {
            if (input.equals("list")) {
                listTasks(tasks);
            } else if (input.matches("mark \\d+")) {
                markTask(input, tasks);
            } else if (input.matches("delete \\d+")) {
                deleteTask(input, tasks);
            } else if (input.startsWith("todo ")) {
                addTodo(input, tasks);
            } else if (input.startsWith("deadline ")) {
                addDeadline(input, tasks);
            } else if (input.startsWith("event ")) {
                addEvent(input, tasks);
            } else {
                System.out.println(
                    "Unknown command!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        } catch (ChaException e) {
            System.out.println(e.getMessage() + 
            "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        } finally {
            storage.save(tasks.getAllTasks());
        }
    }

    private static void listTasks(TaskList tasks) {
        ArrayList<Task> tasksList = tasks.getAllTasks();
        if (tasks.size() == 0) {
            System.out.println(
                "No Chas brewing yet!\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }
        System.out.println("Here are your Chas:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasksList.get(i));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void markTask(String input, TaskList tasks) {
        int index = Integer.parseInt(input.replaceAll("\\D", "")) - 1;
        if (index < 0 || index >= tasks.size()) {
            System.out.println(
                "Invalid task number.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }
        Task task = tasks.markAsDone(index);
        System.out.println("Great! Your Cha is completed:\n  " + task
            + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void deleteTask(String input, TaskList tasks) {
        int index = Integer.parseInt(input.replaceAll("\\D", "")) - 1;
        if (index < 0 || index >= tasks.size()) {
            System.out.println(
                "Invalid task number.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }
        Task removed = tasks.deleteTask(index);
        System.out.println("Bye bye Cha! I've removed this task:\n  " + removed
            + "\nNow you have " + tasks.size() 
            + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void addTodo(String input, TaskList tasks) throws ChaException {
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) throw new ChaException(
            "CHA doesn't know what to do! (The description cannot be empty)");
        tasks.addTask(new ToDo(desc));
        System.out.println("On it! One Cha coming right up :D\n  " 
            + tasks.getTask(tasks.size() - 1)
            + "\nNow you have " + tasks.size() + 
            " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void addDeadline(String input, TaskList tasks) throws ChaException {
        String[] parts = input.substring(9).split("/by");
        String desc = parts[0].trim();
        if (desc.isEmpty()) throw new ChaException(
            "CHA doesn't know what to do! (The description cannot be empty)");
        String by = (parts.length > 1 ? parts[1].trim() : "");
        if (by.isEmpty()) throw new ChaException(
            "CHA doesn't know when it's due! (Use /by <time>)");
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime byFormatted = LocalDateTime.parse(by, inputFormatter);
            tasks.addTask(new Deadline(desc, byFormatted));
        } catch (DateTimeParseException e) {
            throw new ChaException(
                "CHA can't understand that time! (Use format: yyyy-MM-dd HHmm)");
        }
        System.out.println("We got you, your Cha is on the way!\n  " 
            + tasks.getTask(tasks.size() - 1)
            + "\nNow you have " + tasks.size() 
            + " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void addEvent(String input, TaskList tasks) throws ChaException {
        String[] descAndTimes = input.substring(6).split("/from");
        String desc = descAndTimes[0].trim();
        if (desc.isEmpty()) throw new ChaException(
            "CHA doesn't know what to do! (The description cannot be empty)");

        String times = descAndTimes.length > 1 ? descAndTimes[1].trim() : "";
        if (times.isEmpty()) throw new ChaException(
            "CHA doesn't know when it starts! (Use /from <start> /to <end>)");

        String[] timeParts = times.split("/to");
        String from = timeParts[0].trim();
        String to = (timeParts.length > 1 ? timeParts[1].trim() : "");
        if (to.isEmpty()) throw new ChaException(
            "CHA doesn't know when it ends! (Use /from <start> /to <end>)");

        tasks.addTask(new Event(desc, from, to));
        System.out.println("Scheduled! We'll see you there~\n  " + tasks.getTask(tasks.size() - 1)
            + "\nNow you have " + tasks.size() + 
            " Chas brewing.\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}
