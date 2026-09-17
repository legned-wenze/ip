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
    void parseMark_extraSpaces_returnsTaskNumber() throws EvaException {
        Parser.ParsedCommand command = Parser.parse("  mark   3  ");

        assertEquals(Parser.CommandType.MARK, command.getType());
        assertEquals(3, command.getTaskNumber());
    }

    @Test
    void parseDeadline_extraSpaces_returnsDeadlineCommand()
            throws EvaException {
        Parser.ParsedCommand command = Parser.parse(
                " deadline  submit report  /by  2026-09-30 ");

        assertEquals(Parser.CommandType.DEADLINE, command.getType());
        assertEquals("submit report", command.getValue(0));
        assertEquals("2026-09-30", command.getValue(1));
    }

    @Test
    void parseSort_validInput_returnsSortCommand() throws EvaException {
        Parser.ParsedCommand command = Parser.parse("sort");

        assertEquals(Parser.CommandType.SORT, command.getType());
    }

    @Test
    void parseDeadline_missingBy_throwsEvaException() {
        assertThrows(EvaException.class, () -> Parser.parse("deadline submit project"));
    }

    @Test
    void parseUnknownCommand_throwsEvaException() {
        assertThrows(EvaException.class, () -> Parser.parse("hello"));
    }

    @Test
    void parseEmptyCommand_returnsHelpfulError() {
        EvaException error = assertThrows(
                EvaException.class, () -> Parser.parse("   "));

        assertEquals("Please enter a command.", error.getMessage());
    }

    @Test
    void parseMark_overflowReturnsHelpfulError() {
        String input = "mark 999999999999999999999";
        EvaException error = assertThrows(
                EvaException.class, () -> Parser.parse(input));

        assertEquals("Please specify a valid task number to mark.",
                error.getMessage());
    }
}
