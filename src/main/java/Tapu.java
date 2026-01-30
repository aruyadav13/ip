import java.util.Scanner;

public class Tapu {
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
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            } else if (line.startsWith("mark")) {
                String[] parts = line.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;

                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsDone();

                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "Nice! I've marked this task as done:\n"
                            + "  " + tasks[taskIndex].toString() + "\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                } else {
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "Error: Task " + parts[1] + " does not exist.\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                }
            } else if (line.startsWith("unmark")) {
                String[] parts = line.split(" ");
                int taskIndex = Integer.parseInt(parts[1]) - 1;

                if (taskIndex >= 0 && taskIndex < taskCount) {
                    tasks[taskIndex].markAsNotDone();

                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "OK, I've marked this task as not done yet:\n"
                            + "  " + tasks[taskIndex].toString() + "\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                } else {
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                            + "Error: Task " + parts[1] + " does not exist.\n"
                            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                }
            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                        + "added: " + line + "\n"
                        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        }
    }
}