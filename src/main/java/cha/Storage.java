package cha;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cha.tasks.Task;
import cha.tasks.ToDo;
import cha.tasks.Deadline;
import cha.tasks.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to disk.
 * Tasks are stored in a text file in a predefined format and
 * reconstructed into Task objects when loaded.
 */
public class Storage {

    private static final String FILE_PATH = "data/cha.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Loads tasks from disk.
     *
     * @return list of tasks loaded from file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        assert FILE_PATH != null : "File path must be defined";
        Path path = Path.of(FILE_PATH);

        if (!Files.exists(path)) {
            createDataFile(path);
            return tasks;
        }

        List<String> lines = readLines(path);
        for (String line : lines) {
            Task task = safeParseTask(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to disk.
     * Each task is written in its file format representation.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        assert FILE_PATH != null : "File path must be defined";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                assert task != null : "Task in list should not be null";
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Creates the data file and its parent directories if they do not exist.
     *
     * @param path The file path to create.
     */
    private void createDataFile(Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Error creating data file: " + e.getMessage());
        }
    }

    /**
     * Reads all lines from the specified file path.
     *
     * @param path The path of the file to read.
     * @return A list of strings representing each line in the file.
     *         Returns an empty list if reading fails.
     */
    private List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Error reading tasks: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Safely parses a task from a line of text.
     * If parsing fails due to corrupted format, the line is skipped.
     *
     * @param line A line from the data file.
     * @return A parsed Task, or null if parsing fails.
     */
    private Task safeParseTask(String line) {
        try {
            return parseTask(line);
        } catch (Exception e) {
            System.out.println("Warning: Skipping corrupted line: " + line);
            return null;
        }
    }

    /**
     * Parses a line from the data file into a Task.
     *
     * @param line A properly formatted line representing a task.
     * @return The reconstructed Task.
     * @throws IllegalArgumentException If the task type is invalid.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Invalid task: " + line;
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;

        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                assert parts.length == 4 : "Deadline must have 4 fields";
                task = parseDeadline(description, parts[3]);
                break;

            case "E":
                assert parts.length == 5 : "Event must have 5 fields";
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Invalid task type: " + type);
        }

        if (isDone)
            task.markAsDone();

        return task;
    }

    /**
     * Parses a deadline date-time string and creates a Deadline task.
     *
     * @param description The task description.
     * @param byString    The deadline date-time string in yyyy-MM-dd HHmm format.
     * @return the created Deadline object.
     */
    private Deadline parseDeadline(String description, String byString) {
        assert byString != null && !byString.isEmpty()
                : "Deadline date string should not be empty";
        LocalDateTime by = LocalDateTime.parse(byString, DATE_FORMAT);
        return new Deadline(description, by);
    }
}
