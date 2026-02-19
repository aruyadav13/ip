package tapu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The main class for the Tapu Chatbot.
 * Handles user input, manages the task list, and handles file storage.
 */
public class Tapu {

    private static final String FILE_PATH = "./data/tapu.txt";
    private static final String DIR_PATH = "./data";

    /**
     * Main entry-point for the Tapu application.
     * Initializes the chatbot and enters the command processing loop.
     */
    public static void main(String[] args) {
        System.out.println("Hello I'm Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");

        String line;
        Task[] tasks = new Task[100];

        // Load data from hard disk on startup
        int taskCount = loadData(tasks);

        Scanner in = new Scanner(System.in);

        // Main command loop
        while (true) {
            line = in.nextLine();
            boolean isChanged = false; // Tracks if we need to save to disk

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
                    isChanged = true;
                } else if (line.startsWith("unmark")) {
                    handleUnmark(line, tasks, taskCount);
                    isChanged = true;
                } else if (line.startsWith("todo")) {
                    taskCount = handleTodo(line, tasks, taskCount);
                    isChanged = true;
                } else if (line.startsWith("deadline")) {
                    taskCount = handleDeadline(line, tasks, taskCount);
                    isChanged = true;
                } else if (line.startsWith("event")) {
                    taskCount = handleEvent(line, tasks, taskCount);
                    isChanged = true;
                } else {
                    throw new TapuException("SORRY, but I don't know what that means bro");
                }

                // Save automatically if the list was modified
                if (isChanged) {
                    saveData(tasks, taskCount);
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

    // ==========================================
    // FILE I/O METHODS (LEVEL 7)
    // ==========================================

    /**
     * Loads tasks from the data file on startup.
     */
    private static int loadData(Task[] tasks) {
        int count = 0;
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) {
                return 0; // File doesn't exist yet, nothing to load
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) continue;

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                Task task = null;
                if (type.equals("T")) {
                    task = new Todo(desc);
                } else if (type.equals("D") && parts.length >= 4) {
                    task = new Deadline(desc, parts[3]);
                } else if (type.equals("E") && parts.length >= 5) {
                    task = new Event(desc, parts[3], parts[4]);
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks[count] = task;
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return count;
    }

    /**
     * Saves the current task list to the data file.
     */
    private static void saveData(Task[] tasks, int taskCount) {
        try {
            // Create directory if it doesn't exist
            File dir = new File(DIR_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                fw.write(taskToFileString(tasks[i]) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object into a formatted string for file storage.
     */
    private static String taskToFileString(Task task) {
        String str = task.toString();
        // Parse the toString output: e.g., "[D][X] return book (by: June 6th)"
        String status = str.substring(4, 5).equals("X") ? "1" : "0";
        String description = str.substring(7);

        if (task instanceof Deadline) {
            int byIndex = description.lastIndexOf(" (by: ");
            if (byIndex != -1) {
                String desc = description.substring(0, byIndex);
                String by = description.substring(byIndex + 6, description.length() - 1);
                return "D | " + status + " | " + desc + " | " + by;
            }
        } else if (task instanceof Event) {
            int fromIndex = description.lastIndexOf(" (from: ");
            int toIndex = description.lastIndexOf(" to: ");
            if (fromIndex != -1 && toIndex != -1) {
                String desc = description.substring(0, fromIndex);
                String from = description.substring(fromIndex + 8, toIndex);
                String to = description.substring(toIndex + 5, description.length() - 1);
                return "E | " + status + " | " + desc + " | " + from + " | " + to;
            }
        }
        // Default to Todo format
        return "T | " + status + " | " + description;
    }

    // ==========================================
    // BOT LOGIC METHODS
    // ==========================================

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
            throw new TapuException("The description of a todo cannot be empty bro.");
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

        String description = line.substring(8, byIndex).trim();
        if (description.isEmpty()) {
            throw new TapuException("The description of a deadline cannot be empty bro.");
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

        String description = line.substring(5, fromIndex).trim();
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