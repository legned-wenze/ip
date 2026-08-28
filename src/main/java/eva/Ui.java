package eva;

import java.util.Scanner;

import eva.task.Task;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Eva.");
        System.out.println("What can I do for you?");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.getTaskAt(i));
        }
    }

    /**
     * Displays the tasks that match a search keyword.
     *
     * @param tasks Matching tasks to display.
     */
    public void showMatchingTasks(TaskList tasks) {
        System.out.println("Here are the matching tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.getTaskAt(i));
        }
    }

    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    public void showDeletedTask(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        showTaskCount(taskCount);
    }

    public void showMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void close() {
        scanner.close();
    }


    private void showTaskCount(int taskCount) {
        System.out.println(
                "Now you have " + taskCount + " tasks in the list.");
    }
}