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

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;

            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }

            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskNumber - 1]);

            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                tasks[taskNumber - 1].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskNumber - 1]);

            } else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");

            } else if (input.startsWith("deadline ")) {
                String details = input.substring(9);
                String[] parts = details.split(" /by ", 2);

                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");

            } else if (input.startsWith("event ")) {
                String details = input.substring(6);
                String[] fromParts = details.split(" /from ", 2);
                String[] toParts = fromParts[1].split(" /to ", 2);

                tasks[taskCount] =
                        new Event(fromParts[0], toParts[0], toParts[1]);

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount]);
                taskCount++;
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            }
        }

        scanner.close();
    }
}
