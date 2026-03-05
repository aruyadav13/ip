package tapu;

import java.util.ArrayList;

/**
 * Represents the list of tasks.
 * Handles operations such as adding, deleting, and retrieving tasks from the list.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList using an existing list of tasks.
     *
     * @param tasks An ArrayList of Task objects loaded from storage.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The Task object to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns a task from the list based on its index.
     *
     * @param index The zero-based index of the task to be deleted.
     * @return The Task object that was removed.
     * @throws TapuException If the index is out of bounds.
     */
    public Task deleteTask(int index) throws TapuException {
        if (index < 0 || index >= tasks.size()) {
            throw new TapuException("Task does not exist.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list based on its index.
     *
     * @param index The zero-based index of the task to retrieve.
     * @return The requested Task object.
     * @throws TapuException If the index is out of bounds.
     */
    public Task getTask(int index) throws TapuException {
        if (index < 0 || index >= tasks.size()) {
            throw new TapuException("Task does not exist.");
        }
        return tasks.get(index);
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns the entire ArrayList of tasks.
     * Primarily used by the Storage class to save the tasks.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Finds and returns a list of tasks that contain the specified keyword.
     *
     * @param keyword The string to search for within the task descriptions.
     * @return An ArrayList of tasks matching the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}