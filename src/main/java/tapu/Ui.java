package tapu;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all interactions with the user.
 * Responsible for reading user input and printing formatted messages to the console.
 */
public class Ui {
    private Scanner in;

    /**
     * Constructs a Ui instance and initializes the Scanner to read from standard input.
     */
    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Prints the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        System.out.println("Hello I'm Tapu\n"
                + "What can I do for you?\n"
                + "________________________________\n");
    }

    /**
     * Prints a divider line to format the console output.
     */
    public void showLine() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The string inputted by the user.
     */
    public String readCommand() {
        return in.nextLine();
    }

    /**
     * Prints the goodbye message when the chatbot exits.
     */
    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Prints a formatted error message.
     *
     * @param message The specific error text to display to the user.
     */
    public void showError(String message) {
        showLine();
        System.out.println("??? " + message);
        showLine();
    }

    /**
     * Prints a specific error message indicating that the save file could not be loaded.
     */
    public void showLoadingError() {
        showLine();
        System.out.println("??? Error loading file. Starting with an empty task list.");
        showLine();
    }

    /**
     * Prints the list of tasks that match the user's search query.
     *
     * @param matchingTasks An ArrayList of tasks that contain the search keyword.
     */
    public void showFoundTasks(ArrayList<Task> matchingTasks) {
        showLine();
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found bro.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println((i + 1) + "." + matchingTasks.get(i).toString());
            }
        }
        showLine();
    }
}