package tapu;

import java.util.Scanner;

/**
 * The main class for the tapu.Tapu Chatbot.
 * Handles user input and manages the task list.
 */
public class Tapu {

    /**
     * Main entry-point for the tapu.Tapu application.
     * Initializes the chatbot and enters the command processing loop.
     */
    public static void main(String[] args) {
        System.out.println("Hello I'm tapu.Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");

        String line;
        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner in = new Scanner(System.in);

        // Main command loop
        while (true) {
            line = in.nextLine();

            try {
                if (line.equalsIgnoreCase("bye")) {
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "Bye. Hope to see you again soon!\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                    break;
                } else if (line.equalsIgnoreCase("list")) {
                    printList(tasks, taskCount);
                } else if (line.startsWith("mark")) {
                    handleMark(line, tasks, taskCount);
                } else if (line.startsWith("unmark")) {
                    handleUnmark(line, tasks, taskCount);
                } else if (line.startsWith("todo")) {
                    taskCount = handleTodo(line, tasks, taskCount);
                } else if (line.startsWith("deadline")) {
                    taskCount = handleDeadline(line, tasks, taskCount);
                } else if (line.startsWith("event")) {
                    taskCount = handleEvent(line, tasks, taskCount);
                } else {
                    // Throw exception if the command is not recognized
                    throw new TapuException("SORRY, but I don't know what that means bro");
                }
            } catch (TapuException e) {
                // Handle specific tapu.Tapu logic errors
                printDivider();
                System.out.println("??? " + e.getMessage());
                printDivider();
            } catch (NumberFormatException e) {
                // Handle invalid number formats (e.g., "mark xyz")
                printDivider();
                System.out.println("??? Please enter a valid number for the task.");
                printDivider();
            }
        }
    }

    /**
     * Prints all tasks currently in the list.
     */
    private static void printList(Task[] tasks, int taskCount) {
        printDivider();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i].toString());
        }
        printDivider();
    }

    /**
     * Marks a specific task as done based on the user's input.
     *
     * @throws TapuException If the command format is invalid or the task index is out of bounds.
     */
    private static void handleMark(String line, Task[] tasks, int taskCount) throws TapuException {
        String[] parts = line.split(" ");
        // Ensure there is an argument after "mark"
        if (parts.length < 2) {
            throw new TapuException("Please specify which task to mark.");
        }
        int taskIndex = Integer.parseInt(parts[1]) - 1;

        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsDone();
            printDivider();
            System.out.println("Nice! I've marked this task as done:\n"
                    + "  " + tasks[taskIndex].toString());
            printDivider();
        } else {
            throw new TapuException("tapu.Task " + parts[1] + " does not exist.");
        }
    }

    /**
     * Marks a specific task as not done based on the user's input.
     *
     * @throws TapuException If the command format is invalid or the task index is out of bounds.
     */
    private static void handleUnmark(String line, Task[] tasks, int taskCount) throws TapuException {
        String[] parts = line.split(" ");
        // Ensure there is an argument after "unmark"
        if (parts.length < 2) {
            throw new TapuException("Please specify which task to unmark.");
        }
        int taskIndex = Integer.parseInt(parts[1]) - 1;

        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsNotDone();
            printDivider();
            System.out.println("OK, I've marked this task as not done yet:\n"
                    + "  " + tasks[taskIndex].toString());
            printDivider();
        } else {
            throw new TapuException("tapu.Task " + parts[1] + " does not exist.");
        }
    }

    /**
     * Creates and adds a new tapu.Todo task.
     *
     * @return The updated task count.
     * @throws TapuException If the description is empty.
     */
    private static int handleTodo(String line, Task[] tasks, int taskCount) throws TapuException {
        // Check if the command is just "todo" or "todo " with spaces
        if (line.trim().length() <= 4) {
            throw new TapuException("The description of a todo cannot be empty.");
        }

        // Extract description starting after "todo "
        String description = line.substring(5).trim();
        if (description.isEmpty()) {
            throw new TapuException("The description of a todo cannot be empty bro.");
        }

        tasks[taskCount] = new Todo(description);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    /**
     * Creates and adds a new tapu.Deadline task.
     * Parses the description and the "/by" date.
     *
     * @return The updated task count.
     * @throws TapuException If formatting is incorrect or fields are empty.
     */
    private static int handleDeadline(String line, Task[] tasks, int taskCount) throws TapuException {
        int byIndex = line.indexOf("/by");

        // Validate that the /by flag exists
        if (byIndex == -1) {
            throw new TapuException("Please include a deadline using '/by'.\n"
                    + "Usage: deadline <description> /by <date>");
        }

        // Extract description (chars between "deadline" and "/by")
        String description = line.substring(8, byIndex).trim();
        if (description.isEmpty()) {
            throw new TapuException("The description of a deadline cannot be empty bro.");
        }

        // Extract the date/time after "/by"
        String by = line.substring(byIndex + 4).trim();
        if (by.isEmpty()) {
            throw new TapuException("The date/time cannot be empty.");
        }

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    /**
     * Creates and adds a new tapu.Event task.
     * Parses the description, start time, and end time.
     *
     * @return The updated task count.
     * @throws TapuException If formatting is incorrect or fields are empty.
     */
    private static int handleEvent(String line, Task[] tasks, int taskCount) throws TapuException {
        int fromIndex = line.indexOf("/from");
        int toIndex = line.indexOf("/to");

        // Validate that both flags exist
        if (fromIndex == -1 || toIndex == -1) {
            throw new TapuException("Please include both '/from' and '/to'.\n"
                    + "Usage: event <desc> /from <start> /to <end>");
        }

        // Extract description, start time, and end time
        String description = line.substring(5, fromIndex).trim(); // "event" is 5 chars
        if (description.isEmpty()) {
            throw new TapuException("The description of an event cannot be empty.");
        }

        String from = line.substring(fromIndex + 6, toIndex).trim();
        String to = line.substring(toIndex + 4).trim();

        if (from.isEmpty() || to.isEmpty()) {
            throw new TapuException("The start and end times cannot be empty.");
        }

        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    /**
     * Prints the success message when a task is successfully added.
     */
    private static void printTaskAdded(Task task, int count) {
        printDivider();
        System.out.println("Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + count + " tasks in the list.");
        printDivider();
    }

    /**
     * Prints a standard divider line to the console.
     */
    private static void printDivider() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}