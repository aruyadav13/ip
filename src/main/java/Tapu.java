import java.util.Scanner;

public class Tapu {
    public static void main(String[] args) {

        System.out.println("Hello I'm Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");

        String line;
        Scanner in = new Scanner(System.in);
        while (true) {
            line = in.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                        + "Bye. Hope to see you again soon!\n"
                        + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                break;
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                    + line + "\n"
                    + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
    }
}