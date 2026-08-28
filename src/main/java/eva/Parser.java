package eva;

public class Parser {
    public enum CommandType {
        BYE,
        LIST,
        MARK,
        UNMARK,
        DELETE,
        TODO,
        DEADLINE,
        EVENT,
        FIND
    }

    public static ParsedCommand parse(String input) throws EvaException {
        if (input.equals("bye")) {
            return new ParsedCommand(CommandType.BYE);
        }

        if (input.equals("list")) {
            return new ParsedCommand(CommandType.LIST);
        }

        if (input.startsWith("mark")) {
            if (!input.matches("mark \\d+")) {
                throw new EvaException(
                        "Please specify a valid task number to mark.");
            }

            int taskNumber = Integer.parseInt(input.substring(5));
            return new ParsedCommand(CommandType.MARK, taskNumber);
        }

        if (input.startsWith("unmark")) {
            if (!input.matches("unmark \\d+")) {
                throw new EvaException(
                        "Please specify a valid task number to unmark.");
            }

            int taskNumber = Integer.parseInt(input.substring(7));
            return new ParsedCommand(CommandType.UNMARK, taskNumber);
        }

        if (input.startsWith("delete")) {
            if (!input.matches("delete \\d+")) {
                throw new EvaException(
                        "Please specify a valid task number to delete.");
            }

            int taskNumber = Integer.parseInt(input.substring(7));
            return new ParsedCommand(CommandType.DELETE, taskNumber);
        }

        if (input.startsWith("todo")) {
            if (!input.startsWith("todo ")
                    || input.substring(4).trim().isEmpty()) {
                throw new EvaException(
                        "The description of a todo cannot be empty.");
            }

            String description = input.substring(5).trim();
            return new ParsedCommand(CommandType.TODO, description);
        }

        if (input.startsWith("deadline")) {
            if (!input.startsWith("deadline ")
                    || input.substring(8).trim().isEmpty()) {
                throw new EvaException(
                        "The description of a deadline cannot be empty.");
            }

            String content = input.substring(9).trim();
            int byIndex = content.indexOf(" /by ");

            if (byIndex == -1) {
                throw new EvaException("A deadline must contain /by.");
            }

            String description = content.substring(0, byIndex).trim();
            String by = content.substring(byIndex + 5).trim();

            if (description.isEmpty() || by.isEmpty()) {
                throw new EvaException(
                        "A deadline needs both a description and /by time.");
            }

            return new ParsedCommand(
                    CommandType.DEADLINE, description, by);
        }

        if (input.startsWith("event")) {
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
                        "An event needs a description, /from time, "
                                + "and /to time.");
            }

            return new ParsedCommand(
                    CommandType.EVENT, description, from, to);
        }

        if (input.startsWith("find")) {
            if (!input.startsWith("find ")
                    || input.substring(4).trim().isEmpty()) {
                throw new EvaException(
                        "Please specify a keyword to find.");
            }

            String keyword = input.substring(5).trim();
            return new ParsedCommand(CommandType.FIND, keyword);
        }

        throw new EvaException(
                "I'm sorry, but I don't know what that means.");
    }

    public static class ParsedCommand {
        private final CommandType type;
        private final int taskNumber;
        private final String[] values;

        private ParsedCommand(CommandType type) {
            this(type, 0);
        }

        private ParsedCommand(CommandType type, int taskNumber) {
            this.type = type;
            this.taskNumber = taskNumber;
            this.values = new String[0];
        }

        private ParsedCommand(CommandType type, String... values) {
            this.type = type;
            this.taskNumber = 0;
            this.values = values;
        }

        public CommandType getType() {
            return type;
        }

        public int getTaskNumber() {
            return taskNumber;
        }

        public String getValue(int index) {
            return values[index];
        }
    }
}