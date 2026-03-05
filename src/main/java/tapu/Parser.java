package tapu;

/**
 * Deals with making sense of the user command.
 */
public class Parser {

    public static String getCommandWord(String fullCommand) {
        return fullCommand.split(" ")[0].toLowerCase();
    }

    public static int parseIndex(String fullCommand) throws TapuException {
        String[] parts = fullCommand.split(" ");
        if (parts.length < 2) {
            throw new TapuException("Please specify the task number.");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new NumberFormatException(); // Caught by main class
        }
    }

    public static Command parse(String fullCommand) throws TapuException {
        String commandWord = getCommandWord(fullCommand);

        if (commandWord.equals("bye")) {
            return new ExitCommand();
        } else if (commandWord.equals("list")) {
            return new ListCommand();
        } else if (commandWord.equals("mark")) {
            return new MarkCommand(parseIndex(fullCommand), true);
        } else if (commandWord.equals("unmark")) {
            return new MarkCommand(parseIndex(fullCommand), false);
        } else if (commandWord.equals("delete")) {
            return new DeleteCommand(parseIndex(fullCommand));
        } else if (commandWord.equals("todo")) {
            return new AddCommand(new Todo(parseTodo(fullCommand)));
        } else if (commandWord.equals("deadline")) {
            String[] parts = parseDeadline(fullCommand);
            return new AddCommand(new Deadline(parts[0], parts[1]));
        } else if (commandWord.equals("event")) {
            String[] parts = parseEvent(fullCommand);
            return new AddCommand(new Event(parts[0], parts[1], parts[2]));
        } else {
            throw new TapuException("SORRY, but I don't know what that means bro");
        }
    }

    public static String parseTodo(String fullCommand) throws TapuException {
        if (fullCommand.trim().length() <= 4) {
            throw new TapuException("The description of a todo cannot be empty.");
        }
        String desc = fullCommand.substring(5).trim();
        if (desc.isEmpty()) {
            throw new TapuException("The description of a todo cannot be empty bro.");
        }
        return desc;
    }

    public static String[] parseDeadline(String fullCommand) throws TapuException {
        int byIndex = fullCommand.indexOf("/by");
        if (byIndex == -1) {
            throw new TapuException("Please include a deadline using '/by'.\nUsage: deadline <description> /by <date>");
        }
        String desc = fullCommand.substring(8, byIndex).trim();
        if (desc.isEmpty()) {
            throw new TapuException("The description cannot be empty bro.");
        }
        String by = fullCommand.substring(byIndex + 4).trim();
        if (by.isEmpty()) {
            throw new TapuException("The date/time cannot be empty.");
        }
        return new String[]{desc, by}; // Returns an array: [description, by]
    }

    public static String[] parseEvent(String fullCommand) throws TapuException {
        int fromIndex = fullCommand.indexOf("/from");
        int toIndex = fullCommand.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new TapuException("Please include both '/from' and '/to'.\nUsage: event <desc> /from <start> /to <end>");
        }
        String desc = fullCommand.substring(5, fromIndex).trim();
        if (desc.isEmpty()) {
            throw new TapuException("The description cannot be empty.");
        }
        String from = fullCommand.substring(fromIndex + 6, toIndex).trim();
        String to = fullCommand.substring(toIndex + 4).trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new TapuException("The start and end times cannot be empty.");
        }
        return new String[]{desc, from, to}; // Returns an array: [description, from, to]
    }
}