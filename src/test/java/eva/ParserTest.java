package eva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    void parseTodo_validInput_returnsTodoCommand() throws EvaException {
        Parser.ParsedCommand command =
                Parser.parse("todo read book");

        assertEquals(Parser.CommandType.TODO, command.getType());
        assertEquals("read book", command.getValue(0));
    }

    @Test
    void parseMark_validInput_returnsTaskNumber() throws EvaException {
        Parser.ParsedCommand command = Parser.parse("mark 3");

        assertEquals(Parser.CommandType.MARK, command.getType());
        assertEquals(3, command.getTaskNumber());
    }

    @Test
    void parseDeadline_missingBy_throwsEvaException() {
        assertThrows(
                EvaException.class,
                () -> Parser.parse("deadline submit project"));
    }

    @Test
    void parseUnknownCommand_throwsEvaException() {
        assertThrows(
                EvaException.class,
                () -> Parser.parse("hello"));
    }
}