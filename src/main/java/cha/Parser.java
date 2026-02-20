package cha;

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
     * <p>This method handles different command types such as:
     * <ul>
     *     <li>bye – exits the application</li>
     *     <li>list – displays all tasks</li>
     *     <li>mark – marks a task as completed</li>
     *     <li>delete – removes a task</li>
     *     <li>todo – adds a new ToDo task</li>
     *     <li>deadline – adds a new Deadline task</li>
     *     <li>event – adds a new Event task</li>
     * </ul>
     *
     * @param command The full user input string.
     * @param tasks The TaskList that stores all current tasks.
     * @param ui The Ui object responsible for displaying messages to the user.
     * @param storage The Storage object responsible for saving tasks on data file.
     * @return true if the command is "bye" (indicating the program should exit),
     *         false otherwise.
     */
    public static boolean parse(String command, TaskList tasks, Ui ui, Storage storage) {
        String[] words = command.trim().split(" ", 2);
        String action = words[0];

        try {
            switch (action) {

            case "bye":
                ui.showMessage("CHA CHA CHA! See you again soon!");
                return true;

            case "list":
                if (tasks.size() == 0) {
                    ui.showMessage("No Chas brewing yet!");
                } else {
                    ui.showMessage("Here are your Chas:\n"
                        + tasks.listTasks());
                }
                break;

            case "mark":
                int markIdx = Integer.parseInt(words[1]) - 1;
                Task marked = tasks.markTaskAsDone(markIdx);
                storage.save(tasks.getAllTasks());

                ui.showMessage("Great! Your Cha is completed:\n  "
                        + marked);
                break;

            case "delete":
                int delIdx = Integer.parseInt(words[1]) - 1;
                Task removed = tasks.deleteTask(delIdx);
                storage.save(tasks.getAllTasks());

                ui.showMessage("Bye bye Cha! I've removed this task:\n  "
                        + removed
                        + "\nNow you have " + tasks.size() + " Chas brewing.");
                break;

            case "todo":
                Task todo = new ToDo(words[1].trim());
                tasks.addTask(todo);
                storage.save(tasks.getAllTasks());

                ui.showMessage("On it! One Cha coming right up :D\n  "
                        + todo
                        + "\nNow you have " + tasks.size() + " Chas brewing.");
                break;

            case "deadline":
                Task deadline = Deadline.parse(words[1].trim());
                tasks.addTask(deadline);
                storage.save(tasks.getAllTasks());

                ui.showMessage("We got you, your Cha is on the way!\n  "
                        + deadline
                        + "\nNow you have " + tasks.size() + " Chas brewing.");
                break;

            case "event":
                Task event = Event.parse(words[1].trim());
                tasks.addTask(event);
                storage.save(tasks.getAllTasks());

                ui.showMessage("Scheduled! We'll see you there~\n  "
                        + event
                        + "\nNow you have " + tasks.size() + " Chas brewing.");
                break;

            default:
                ui.showError("Unknown command!");
            }

        } catch (IndexOutOfBoundsException e) {
            ui.showError("Invalid task number.");
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }

        return false;
    }
}
