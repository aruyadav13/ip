import java.util.Scanner;


public class Tapu {
    public static void main(String[] args) {

        System.out.println("Hello I'm Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");

        String line;
        String[] list = new String[100];
        int taskCount = 0;

        Scanner in = new Scanner(System.in);
        while (true) {
            line = in.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                        + "Bye. Hope to see you again soon!\n"
                        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                break;
            }

            else if (line.equalsIgnoreCase("list")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + list[i]);
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }

            else {
                list[taskCount] = line;
                taskCount++;
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                        + "added: " + line + "\n"
                        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        }
    }
}