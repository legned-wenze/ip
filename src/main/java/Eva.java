import java.util.Scanner;

public class Eva {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm Eva.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;

                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }

                } else if (input.startsWith("mark")) {
                    if (!input.matches("mark \\d+")) {
                        throw new EvaException(
                                "Please specify a valid task number to mark.");
                    }

                    int taskNumber = Integer.parseInt(input.substring(5));

                    if (taskNumber < 1 || taskNumber > taskCount) {
                        throw new EvaException(
                                "That task number does not exist.");
                    }

                    tasks[taskNumber - 1].markAsDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskNumber - 1]);

                } else if (input.startsWith("unmark")) {
                    if (!input.matches("unmark \\d+")) {
                        throw new EvaException(
                                "Please specify a valid task number to unmark.");
                    }

                    int taskNumber = Integer.parseInt(input.substring(7));

                    if (taskNumber < 1 || taskNumber > taskCount) {
                        throw new EvaException(
                                "That task number does not exist.");
                    }

                    tasks[taskNumber - 1].markAsNotDone();

                    System.out.println(
                            "OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskNumber - 1]);

                } else if (input.startsWith("todo")) {
                    if (!input.startsWith("todo ")
                            || input.substring(4).trim().isEmpty()) {
                        throw new EvaException(
                                "The description of a todo cannot be empty.");
                    }

                    String description = input.substring(5).trim();

                    tasks[taskCount] = new Todo(description);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);

                    taskCount++;

                    System.out.println(
                            "Now you have " + taskCount + " tasks in the list.");

                } else if (input.startsWith("deadline")) {
                    if (!input.startsWith("deadline ")
                            || input.substring(8).trim().isEmpty()) {
                        throw new EvaException(
                                "The description of a deadline cannot be empty.");
                    }

                    String content = input.substring(9).trim();
                    int byIndex = content.indexOf(" /by ");

                    if (byIndex == -1) {
                        throw new EvaException(
                                "A deadline must contain /by.");
                    }

                    String description =
                            content.substring(0, byIndex).trim();
                    String by =
                            content.substring(byIndex + 5).trim();

                    if (description.isEmpty() || by.isEmpty()) {
                        throw new EvaException(
                                "A deadline needs both a description and /by time.");
                    }

                    tasks[taskCount] = new Deadline(description, by);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);

                    taskCount++;

                    System.out.println(
                            "Now you have " + taskCount + " tasks in the list.");

                } else if (input.startsWith("event")) {
                    if (!input.startsWith("event ")
                            || input.substring(5).trim().isEmpty()) {
                        throw new EvaException(
                                "The description of an event cannot be empty.");
                    }

                    String content = input.substring(6).trim();

                    int fromIndex = content.indexOf(" /from ");
                    int toIndex = content.indexOf(" /to ");

                    if (fromIndex == -1
                            || toIndex == -1
                            || toIndex <= fromIndex) {
                        throw new EvaException(
                                "An event must contain /from and /to.");
                    }

                    String description =
                            content.substring(0, fromIndex).trim();
                    String from =
                            content.substring(fromIndex + 7, toIndex).trim();
                    String to =
                            content.substring(toIndex + 5).trim();

                    if (description.isEmpty()
                            || from.isEmpty()
                            || to.isEmpty()) {
                        throw new EvaException(
                                "An event needs a description, /from time, and /to time.");
                    }

                    tasks[taskCount] =
                            new Event(description, from, to);

                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount]);

                    taskCount++;

                    System.out.println(
                            "Now you have " + taskCount + " tasks in the list.");

                } else {
                    throw new EvaException(
                            "I'm sorry, but I don't know what that means.");
                }

            } catch (EvaException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            }
        }

        scanner.close();
    }
}