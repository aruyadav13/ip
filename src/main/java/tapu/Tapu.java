package tapu;

/**
 * The main class for the Tapu Chatbot.
 * Ties together the Ui, Storage, TaskList, and Parser classes.
 */
public class Tapu {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes the Tapu chatbot with the specified file path for storage.
     */
    public Tapu(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (TapuException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop for the chatbot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (TapuException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid number for the task.");
            }
        }
    }

    private void printTaskAdded(Task t) {
        ui.showLine();
        System.out.println("Got it. I've added this task:\n  " + t.toString());
        System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
        ui.showLine();
    }

    public static void main(String[] args) {
        new Tapu("./data/tapu.txt").run();
    }
}