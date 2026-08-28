import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    public ArrayList<Task> load() throws EvaException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            for (String line : Files.readAllLines(filePath)) {
                if (!line.isBlank()) {
                    tasks.add(parseStoredTask(line));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new EvaException(
                    "I could not load the saved tasks: " + e.getMessage());
        }

        return tasks;
    }

    public void save(TaskList tasks) throws EvaException {
        try {
            Files.createDirectories(filePath.getParent());

            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                lines.add(tasks.getTaskAt(i).toFileString());
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new EvaException(
                    "I could not save the tasks: " + e.getMessage());
        }
    }

    private Task parseStoredTask(String line) {
        String[] parts = line.split(" \\| ", -1);

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task data.");
        }

        Task task;

        switch (parts[0]) {
            case "T":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid todo data.");
                }
                task = new Todo(parts[2]);
                break;
            case "D":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Invalid deadline data.");
                }
                task = new Deadline(parts[2], parts[3]);
                break;
            case "E":
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Invalid event data.");
                }
                task = new Event(parts[2], parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type.");
        }

        if (parts[1].equals("1")) {
            task.markAsDone();
        } else if (!parts[1].equals("0")) {
            throw new IllegalArgumentException("Invalid task status.");
        }

        return task;
    }
}
