package tapu;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TapuException {
        Task removed = tasks.deleteTask(index);
        ui.showLine();
        System.out.println("Noted. I've removed this task:\n  " + removed.toString());
        System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
        ui.showLine();
        storage.save(tasks.getTasks());
    }
}