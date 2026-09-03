package eva;

import java.util.Scanner;

import eva.task.Task;

/**
 * Handles input and output for Eva's text-based interface.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates a text-based user interface.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays Eva's welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Eva.");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return User command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a response in the text-based interface.
     *
     * @param response Response to display.
     */
    public void showResponse(String response) {
        System.out.println(response);
    }

    /**
     * Returns a message containing every task in the list.
     *
     * @param tasks Tasks to include.
     * @return Formatted task-list message.
     */
    public String getTaskListMessage(TaskList tasks) {
        return getTaskListing("Here are the tasks in your list:", tasks);
    }

    /**
     * Returns a message containing every matching task.
     *
     * @param tasks Matching tasks to include.
     * @return Formatted matching-task message.
     */
    public String getMatchingTasksMessage(TaskList tasks) {
        return getTaskListing(
                "Here are the matching tasks in your list:", tasks);
    }

    /**
     * Returns a message confirming a newly added task.
     *
     * @param task Added task.
     * @param taskCount Updated number of tasks.
     * @return Task-added confirmation.
     */
    public String getAddedTaskMessage(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task
                + getTaskCountMessage(taskCount);
    }

    /**
     * Returns a message confirming a deleted task.
     *
     * @param task Deleted task.
     * @param taskCount Updated number of tasks.
     * @return Task-deleted confirmation.
     */
    public String getDeletedTaskMessage(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task
                + getTaskCountMessage(taskCount);
    }

    /**
     * Returns a message confirming that a task was marked.
     *
     * @param task Marked task.
     * @return Task-marked confirmation.
     */
    public String getMarkedTaskMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns a message confirming that a task was unmarked.
     *
     * @param task Unmarked task.
     * @return Task-unmarked confirmation.
     */
    public String getUnmarkedTaskMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns Eva's farewell message.
     *
     * @return Farewell message.
     */
    public String getByeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }

    private String getTaskListing(String heading, TaskList tasks) {
        StringBuilder listing = new StringBuilder(heading);
        for (int i = 0; i < tasks.size(); i++) {
            listing.append(System.lineSeparator())
                    .append(i + 1)
                    .append(".")
                    .append(tasks.getTaskAt(i));
        }
        return listing.toString();
    }

    private String getTaskCountMessage(int taskCount) {
        return "\nNow you have " + taskCount + " tasks in the list.";
    }
}
