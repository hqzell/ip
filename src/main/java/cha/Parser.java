package cha;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cha.tasks.Deadline;
import cha.tasks.Event;
import cha.tasks.Task;
import cha.tasks.ToDo;

/**
 * Parses user input commands and executes the corresponding actions
 * on the TaskList, Ui, and Storage components.
 */
public class Parser {

    /**
     * Parses the given user command and performs the corresponding action.
     *
     * This method handles different command types such as:
     * bye – exits the application
     * list – displays all tasks
     * mark – marks a task as completed
     * delete – removes a task
     * find – searches for tasks containing a keyword
     * todo – adds a new ToDo task
     * deadline – adds a new Deadline task
     * event – adds a new Event task
     *
     * @param command The full user input string.
     * @param tasks   The TaskList that stores all current tasks.
     * @param ui      The Ui object responsible for displaying messages to the user.
     * @param storage The Storage object responsible for saving tasks on data file.
     * @return true if the command is "bye" (indicating the program should exit),
     *         false otherwise.
     */
    public static String parse(String command, TaskList tasks, Storage storage) {
        String[] words = command.trim().split(" ", 2);
        String action = words[0];

        try {
            switch (action) {

                case "bye":
                    return "CHA CHA CHA! See you again soon!";

                case "list":
                    if (tasks.size() == 0) {
                        return "No Chas brewing yet!";
                    } else {
                        return "Here are your Chas:\n"
                                + tasks.listTasks();
                    }

                case "mark":
                    int markIdx = Integer.parseInt(words[1]) - 1;
                    Task marked = tasks.markTaskAsDone(markIdx);
                    storage.save(tasks.getAllTasks());

                    return "Great! Your Cha is completed:\n  "
                            + marked;

                case "delete":
                    int delIdx = Integer.parseInt(words[1]) - 1;
                    Task removed = tasks.deleteTask(delIdx);
                    storage.save(tasks.getAllTasks());

                    return "Bye bye Cha! I've removed this task:\n  "
                            + removed
                            + "\nNow you have " + tasks.size() + " Chas brewing.";

                case "find":
                    if (words.length < 2 || words[1].trim().isEmpty()) {
                        throw new ChaException("Please provide a keyword to search.");
                    }

                    String keyword = words[1].trim();
                    String results = tasks.findTask(keyword);

                    return "Here are the matching tasks in your list:\n"
                            + results;

                case "todo":
                    Task todo = new ToDo(words[1].trim());
                    tasks.addTask(todo);
                    storage.save(tasks.getAllTasks());

                    return "On it! One Cha coming right up :D\n  "
                            + todo
                            + "\nNow you have " + tasks.size() + " Chas brewing.";

                case "deadline":
                    Task deadline = Deadline.parse(words[1].trim());
                    tasks.addTask(deadline);
                    storage.save(tasks.getAllTasks());

                    return "We got you, your Cha is on the way!\n  "
                            + deadline
                            + "\nNow you have " + tasks.size() + " Chas brewing.";

                case "event":
                    Task event = Event.parse(words[1].trim());
                    tasks.addTask(event);
                    storage.save(tasks.getAllTasks());

                    return "Scheduled! We'll see you there~\n  "
                            + event
                            + "\nNow you have " + tasks.size() + " Chas brewing.";

                case "update":
                    return handleUpdate(words[1], tasks, storage);

                default:
                    return "Unknown command!";
            }

        } catch (IndexOutOfBoundsException e) {
            return "Invalid task number.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Handles the update command by modifying fields of an existing task.
     *
     * @param input   The full user input string.
     * @param tasks   The task list containing existing tasks.
     * @param storage The storage instance used for persistence.
     * @return A confirmation message describing the updated task.
     * @throws ChaException If the command format is invalid
     *                      or the index is out of bounds.
     */
    private static String handleUpdate(String input,
            TaskList tasks,
            Storage storage) throws ChaException {

        assert input != null : "Update input should not be null";

        String[] parts = input.trim().split(" ", 2);

        if (parts.length < 2) {
            throw new ChaException("Please use this format: \"update INDEX /field value\"");
        }

        int index;
        try {
            index = Integer.parseInt(parts[0]) - 1;
        } catch (NumberFormatException e) {
            throw new ChaException("Index must be a number.");
        }

        if (index < 0 || index >= tasks.size()) {
            throw new ChaException("Invalid task index.");
        }

        Task task = tasks.getTask(index);
        task.update(parts[1].trim());

        storage.save(tasks.getAllTasks());

        return "Got it! I've updated this task:\n  "
                + (index + 1) + "." + task;
    }
}
