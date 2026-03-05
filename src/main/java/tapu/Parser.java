package tapu;

/**
 * Deals with making sense of the user command.
 * Parses raw string input into executable Command objects or specific arguments.
 */
public class Parser {

    /**
     * Extracts the main command word from the user's full input.
     *
     * @param fullCommand The complete string typed by the user.
     * @return The first word of the command in lowercase.
     */
    public static String getCommandWord(String fullCommand) {
        return fullCommand.split(" ")[0].toLowerCase();
    }

    /**
     * Parses the task index number from commands like "mark 2" or "delete 3".
     *
     * @param fullCommand The complete string typed by the user.
     * @return The zero-based integer index of the task.
     * @throws TapuException If the user didn't specify a number.
     * @throws NumberFormatException If the user typed letters instead of a number.
     */
    public static int parseIndex(String fullCommand) throws TapuException, NumberFormatException {
        String[] parts = fullCommand.split(" ");
        if (parts.length < 2) {
            throw new TapuException("Please specify the task number.");
        }
        return Integer.parseInt(parts[1]) - 1;
    }

    /**
     * Parses the description of a todo task.
     *
     * @param fullCommand The complete string typed by the user.
     * @return The description of the todo.
     * @throws TapuException If the description is empty.
     */
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

    /**
     * Parses the description and date for a deadline task.
     *
     * @param fullCommand The complete string typed by the user.
     * @return A String array where index 0 is the description and index 1 is the date.
     * @throws TapuException If the formatting is incorrect or fields are missing.
     */
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
        return new String[]{desc, by};
    }

    /**
     * Parses the description, start time, and end time for an event task.
     *
     * @param fullCommand The complete string typed by the user.
     * @return A String array where index 0 is description, index 1 is start time, and index 2 is end time.
     * @throws TapuException If the formatting is incorrect or fields are missing.
     */
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
        return new String[]{desc, from, to};
    }

    /**
     * Interprets the user's input and returns the appropriate Command object to execute.
     *
     * @param fullCommand The complete string typed by the user.
     * @return A Command object representing the action to take.
     * @throws TapuException If the command is unrecognized or improperly formatted.
     */
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
        } else if (commandWord.equals("find")) {
            String keyword = fullCommand.substring(4).trim();
            if (keyword.isEmpty()) {
                throw new TapuException("The keyword for find cannot be empty.");
            }
            return new FindCommand(keyword);
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
}