package eva;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class EvaTest {
    @TempDir
    private Path tempDirectory;

    @Test
    void getResponse_addTodo_returnsConfirmation() {
        Eva eva = new Eva(tempDirectory.resolve("eva.txt").toString());

        String response = eva.getResponse("todo read book");

        assertEquals(
                "All set! I've added this task:\n"
                        + "  [T][ ] read book\n"
                        + "You have 1 task on your list.",
                response.replace(System.lineSeparator(), "\n"));
    }

    @Test
    void getResponse_unknownCommand_returnsError() {
        Eva eva = new Eva(tempDirectory.resolve("eva.txt").toString());

        assertEquals(
                "OOPS!!! I'm sorry, but I don't know what that means. :(",
                eva.getResponse("hello"));
    }

    @Test
    void getResponse_sort_ordersDeadlinesChronologically() {
        Eva eva = new Eva(tempDirectory.resolve("eva.txt").toString());
        eva.getResponse("deadline submit report /by 2026-09-30");
        eva.getResponse("deadline attend meeting /by 2026-09-15");

        String response = eva.getResponse("sort");

        assertEquals(
                "Here's your task list:\n"
                        + "1.[D][ ] attend meeting (by: Sep 15 2026)\n"
                        + "2.[D][ ] submit report (by: Sep 30 2026)",
                response.replace(System.lineSeparator(), "\n"));
    }
}
