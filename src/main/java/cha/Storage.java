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
     * Saves tasks to disk.
     *
     * @param tasks list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /** Creates the data file and its directories if missing */
    private void createDataFile(Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Error creating data file: " + e.getMessage());
        }
    }

    /** Reads all lines from the file, safely */
    private List<String> readLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Error reading tasks: " + e.getMessage());
            return List.of();
        }
    }

    /** Parses a task from a line, returns null if parsing fails */
    private Task safeParseTask(String line) {
        try {
            return parseTask(line);
        } catch (Exception e) {
            System.out.println("Warning: Skipping corrupted line: " + line);
            return null;
        }
    }

    /** Parses a line into a Task */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;

        switch (type) {
            case "T":
                task = new ToDo(description);
                break;
            case "D":
                task = parseDeadline(description, parts[3]);
                break;
            case "E":
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Invalid task type: " + type);
        }

        if (isDone) task.markAsDone();

        return task;
    }

    /** Converts a deadline string into a Deadline task */
    private Deadline parseDeadline(String description, String byString) {
        LocalDateTime by = LocalDateTime.parse(byString, DATE_FORMAT);
        return new Deadline(description, by);
    }
}
