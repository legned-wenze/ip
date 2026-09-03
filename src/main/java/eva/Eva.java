package eva;

import eva.task.Deadline;
import eva.task.Event;
import eva.task.Task;
import eva.task.Todo;

/**
 * Runs the Eva task management application.
 */
public class Eva {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates an Eva application that stores tasks at the specified path.
     *
     * @param filePath Path of the task data file.
     */
    public Eva(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.load());
        } catch (EvaException e) {
            ui.showResponse("OOPS!!! " + e.getMessage());
            this.tasks = new TaskList();
        }
    }

    /**
     * Starts the command-reading loop and processes commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            ui.showResponse(getResponse(input));
            isExit = input.equals("bye");
        }

        ui.close();
    }

    /**
     * Processes a command and returns Eva's response.
     *
     * @param input User command.
     * @return Eva's response to the command.
     */
    public String getResponse(String input) {
        try {
            return execute(Parser.parse(input));
        } catch (EvaException | IllegalArgumentException e) {
            return "OOPS!!! " + e.getMessage();
        }
    }

    private String execute(Parser.ParsedCommand command) throws EvaException {
        switch (command.getType()) {
            case BYE:
                storage.save(tasks);
                return ui.getByeMessage();

            case LIST:
                return ui.getTaskListMessage(tasks);

            case MARK:
                Task markedTask = tasks.mark(command.getTaskNumber());
                storage.save(tasks);
                return ui.getMarkedTaskMessage(markedTask);

            case UNMARK:
                Task unmarkedTask = tasks.unmark(command.getTaskNumber());
                storage.save(tasks);
                return ui.getUnmarkedTaskMessage(unmarkedTask);

            case DELETE:
                Task deletedTask = tasks.delete(command.getTaskNumber());
                storage.save(tasks);
                return ui.getDeletedTaskMessage(deletedTask, tasks.size());

            case TODO:
                Task todo = new Todo(command.getValue(0));
                tasks.add(todo);
                storage.save(tasks);
                return ui.getAddedTaskMessage(todo, tasks.size());

            case DEADLINE:
                Task deadline = new Deadline(
                        command.getValue(0), command.getValue(1));
                tasks.add(deadline);
                storage.save(tasks);
                return ui.getAddedTaskMessage(deadline, tasks.size());

            case EVENT:
                Task event = new Event(
                        command.getValue(0),
                        command.getValue(1),
                        command.getValue(2));
                tasks.add(event);
                storage.save(tasks);
                return ui.getAddedTaskMessage(event, tasks.size());

            case FIND:
                TaskList matchingTasks =
                        tasks.find(command.getValue(0));
                return ui.getMatchingTasksMessage(matchingTasks);

            default:
                throw new EvaException("Unknown command.");
        }
    }

    /**
     * Starts the Eva application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Eva("data/eva.txt").run();
    }
}
