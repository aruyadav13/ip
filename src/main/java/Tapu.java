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
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                        + "I'm sorry, I don't understand that command.\n"
                        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        }
    }

    /**
     * Prints all tasks currently in the list.
     *
     * @param tasks     The array containing tasks.
     * @param taskCount The number of tasks currently stored.
     */
    private static void printList(Task[] tasks, int taskCount) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i].toString());
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void handleMark(String line, Task[] tasks, int taskCount) {
        String[] parts = line.split(" ");
        int taskIndex = Integer.parseInt(parts[1]) - 1;

        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsDone();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "Nice! I've marked this task as done:\n"
                    + "  " + tasks[taskIndex].toString() + "\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        } else {
            printError(parts[1]);
        }
    }

    private static void handleUnmark(String line, Task[] tasks, int taskCount) {
        String[] parts = line.split(" ");
        int taskIndex = Integer.parseInt(parts[1]) - 1;

        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsNotDone();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "OK, I've marked this task as not done yet:\n"
                    + "  " + tasks[taskIndex].toString() + "\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        } else {
            printError(parts[1]);
        }
    }

    private static int handleTodo(String line, Task[] tasks, int taskCount) {
        if (line.trim().length() <= 4) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "Error: The description of a todo cannot be empty.\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return taskCount;
        }

        String description = line.substring(5).trim();
        tasks[taskCount] = new Todo(description);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleDeadline(String line, Task[] tasks, int taskCount) {
        int byIndex = line.indexOf("/by");

        if (byIndex == -1) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "Error: Please include a deadline using '/by'.\n"
                    + "Usage: deadline <description> /by <date>\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return taskCount;
        }

        String description = line.substring(9, byIndex).trim();

        if (description.isEmpty()) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "Error: The description of a deadline cannot be empty.\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return taskCount;
        }

        String by = line.substring(byIndex + 4).trim();

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleEvent(String line, Task[] tasks, int taskCount) {
        int fromIndex = line.indexOf("/from");
        int toIndex = line.indexOf("/to");

        if (fromIndex == -1 || toIndex == -1) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "Error: Please include both '/from' and '/to'.\n"
                    + "Usage: event <desc> /from <start> /to <end>\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return taskCount;
        }

        String description = line.substring(6, fromIndex).trim();

        if (description.isEmpty()) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + "Error: The description of an event cannot be empty.\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return taskCount;
        }

        String from = line.substring(fromIndex + 6, toIndex).trim();
        String to = line.substring(toIndex + 4).trim();

        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
        return taskCount;
    }

    /**
     * Prints the success message when a task is added.
     *
     * @param task  The task that was added.
     * @param count The new total number of tasks.
     */
    private static void printTaskAdded(Task task, int count) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + count + " tasks in the list.\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static void printError(String index) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "Error: Task " + index + " does not exist.\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}