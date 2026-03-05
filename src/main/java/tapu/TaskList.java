package tapu;

import java.util.ArrayList;

/**
 * Contains the task list and has operations to add/delete tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    // Constructor when loading from a file
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Constructor when starting fresh
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws TapuException {
        if (index < 0 || index >= tasks.size()) {
            throw new TapuException("Task does not exist.");
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws TapuException {
        if (index < 0 || index >= tasks.size()) {
            throw new TapuException("Task does not exist.");
        }
        return tasks.get(index);
    }

    public int getSize() {
        return tasks.size();
    }

    // Used by Storage to save the list
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            // Check if the task's text contains our keyword
            if (task.toString().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}