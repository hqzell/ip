import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List; 

/**
 * Handles loading and saving tasks to disk.
 */
public class Storage {

    private static final String FILE_PATH = "data/cha.txt";

    /**
     * Loads tasks from disk.
     *
     * @return list of tasks loaded from file
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            Path path = Path.of(FILE_PATH);

            if (!Files.exists(path)) {
                createDataFile(path);
                return tasks;
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                try {
                    Task task = parseTask(line);
                    tasks.add(task);
                } catch (Exception e) {
                    // Stretch goal: skip corrupted lines safely
                    System.out.println("Warning: Skipping corrupted line: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
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

    private void createDataFile(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.createFile(path);
    }

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
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            throw new IllegalArgumentException("Invalid task type");
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
