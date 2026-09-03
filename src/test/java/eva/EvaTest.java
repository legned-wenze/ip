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
                "Got it. I've added this task:\n"
                        + "  [T][ ] read book\n"
                        + "Now you have 1 tasks in the list.",
                response.replace(System.lineSeparator(), "\n"));
    }

    @Test
    void getResponse_unknownCommand_returnsError() {
        Eva eva = new Eva(tempDirectory.resolve("eva.txt").toString());

        assertEquals(
                "OOPS!!! I'm sorry, but I don't know what that means.",
                eva.getResponse("hello"));
    }
}
