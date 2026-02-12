import java.util.Scanner;

/**
 * The main class for the Tapu Chatbot.
 * Handles user input and manages the task list.
 */
public class Tapu {

    /**
     * Main entry-point for the Tapu application.
     */
    public static void main(String[] args) {
        System.out.println("Hello I'm Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");

        String line;
        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner in = new Scanner(System.in);

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
                    throw new TapuException("SORRY, but I don't know what that means bro");
                }
            } catch (TapuException e) {
                printDivider();
                System.out.println("??? " + e.getMessage());
                printDivider();
            } catch (NumberFormatException e) {
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

    private static void handleMark(String line, Task[] tasks, int taskCount) throws TapuException {
        String[] parts = line.split(" ");
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
            throw new TapuException("Task " + parts[1] + " does not exist.");
        }
    }

    private static void handleUnmark(String line, Task[] tasks, int taskCount) throws TapuException {
        String[] parts = line.split(" ");
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
            throw new TapuException("Task " + parts[1] + " does not exist.");
        }
    }

    private static int handleTodo(String line, Task[] tasks, int taskCount) throws TapuException {
        if (line.trim().length() <= 4) {
            throw new TapuException("The description of a todo cannot be empty.");
        }

        String description = line.substring(5).trim();
        if (description.isEmpty()) {
            throw new TapuException("The description of a todo cannot be empty.");
        }

        tasks[taskCount] = new Todo(description);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleDeadline(String line, Task[] tasks, int taskCount) throws TapuException {
        int byIndex = line.indexOf("/by");

        if (byIndex == -1) {
            throw new TapuException("Please include a deadline using '/by'.\n"
                    + "Usage: deadline <description> /by <date>");
        }

        String description = line.substring(8, byIndex).trim(); // "deadline" is 8 chars
        if (description.isEmpty()) {
            throw new TapuException("The description of a deadline cannot be empty.");
        }

        String by = line.substring(byIndex + 4).trim();
        if (by.isEmpty()) {
            throw new TapuException("The date/time cannot be empty.");
        }

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleEvent(String line, Task[] tasks, int taskCount) throws TapuException {
        int fromIndex = line.indexOf("/from");
        int toIndex = line.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            throw new TapuException("Please include both '/from' and '/to'.\n"
                    + "Usage: event <desc> /from <start> /to <end>");
        }

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

    private static void printTaskAdded(Task task, int count) {
        printDivider();
        System.out.println("Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + count + " tasks in the list.");
        printDivider();
    }

    private static void printDivider() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}