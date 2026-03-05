package tapu;

public class MarkCommand extends Command {
    private int index;
    private boolean isMark;

    public MarkCommand(int index, boolean isMark) {
        this.index = index;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TapuException {
        Task t = tasks.getTask(index);
        if (isMark) {
            t.markAsDone();
            ui.showLine();
            System.out.println("Nice! I've marked this task as done:\n  " + t.toString());
        } else {
            t.markAsNotDone();
            ui.showLine();
            System.out.println("OK, I've marked this task as not done yet:\n  " + t.toString());
        }
        ui.showLine();
        storage.save(tasks.getTasks());
    }
}