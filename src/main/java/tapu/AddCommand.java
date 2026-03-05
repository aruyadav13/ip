package tapu;

public class AddCommand extends Command {
    private Task toAdd;

    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TapuException {
        tasks.addTask(toAdd);
        ui.showLine();
        System.out.println("Got it. I've added this task:\n  " + toAdd.toString());
        System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
        ui.showLine();
        storage.save(tasks.getTasks());
    }
}