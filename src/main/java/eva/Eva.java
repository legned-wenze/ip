package eva;

import eva.task.Deadline;
import eva.task.Event;
import eva.task.Task;
import eva.task.Todo;

public class Eva {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    public Eva(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.load());
        } catch (EvaException e) {
            ui.showError(e.getMessage());
            this.tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = ui.readCommand();
                Parser.ParsedCommand command = Parser.parse(input);
                isExit = execute(command);
            } catch (EvaException | IllegalArgumentException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }

    private boolean execute(Parser.ParsedCommand command)
            throws EvaException {
        switch (command.getType()) {
            case BYE:
                storage.save(tasks);
                ui.showBye();
                return true;

            case LIST:
                ui.showTaskList(tasks);
                return false;

            case MARK:
                Task markedTask = tasks.mark(command.getTaskNumber());
                storage.save(tasks);
                ui.showMarkedTask(markedTask);
                return false;

            case UNMARK:
                Task unmarkedTask = tasks.unmark(command.getTaskNumber());
                storage.save(tasks);
                ui.showUnmarkedTask(unmarkedTask);
                return false;

            case DELETE:
                Task deletedTask = tasks.delete(command.getTaskNumber());
                storage.save(tasks);
                ui.showDeletedTask(deletedTask, tasks.size());
                return false;

            case TODO:
                Task todo = new Todo(command.getValue(0));
                tasks.add(todo);
                storage.save(tasks);
                ui.showAddedTask(todo, tasks.size());
                return false;

            case DEADLINE:
                Task deadline = new Deadline(
                        command.getValue(0), command.getValue(1));
                tasks.add(deadline);
                storage.save(tasks);
                ui.showAddedTask(deadline, tasks.size());
                return false;

            case EVENT:
                Task event = new Event(
                        command.getValue(0),
                        command.getValue(1),
                        command.getValue(2));
                tasks.add(event);
                storage.save(tasks);
                ui.showAddedTask(event, tasks.size());
                return false;

            default:
                throw new EvaException("Unknown command.");
        }
    }

    public static void main(String[] args) {
        new Eva("data/eva.txt").run();
    }
}