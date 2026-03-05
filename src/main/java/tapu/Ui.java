package tapu;

import java.util.Scanner;

/**
 * Handles all interactions with the user.
 */
public class Ui {
    private Scanner in;

    public Ui() {
        in = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello I'm Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");
    }

    public void showLine() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public String readCommand() {
        return in.nextLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println("??? " + message);
        showLine();
    }

    public void showLoadingError() {
        showLine();
        System.out.println("??? Error loading file. Starting with an empty task list.");
        showLine();
    }
}