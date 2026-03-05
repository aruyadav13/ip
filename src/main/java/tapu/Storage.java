package tapu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from the hard drive and saving tasks back to it.
 */
public class Storage {
    private String filePath;
    private String dirPath;

    /**
     * Constructs a Storage instance with the specified file path.
     * Automatically determines the directory path from the file path.
     *
     * @param filePath The relative or absolute path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        File f = new File(filePath);
        this.dirPath = f.getParent();
    }

    /**
     * Loads tasks from the save file into an ArrayList.
     *
     * @return An ArrayList containing the parsed Task objects from the file.
     * @throws TapuException If there is an issue locating or reading the file.
     */
    public ArrayList<Task> load() throws TapuException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return loadedTasks;
            }
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) continue;

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                Task task = null;
                if (type.equals("T")) {
                    task = new Todo(desc);
                } else if (type.equals("D") && parts.length >= 4) {
                    task = new Deadline(desc, parts[3]);
                } else if (type.equals("E") && parts.length >= 5) {
                    task = new Event(desc, parts[3], parts[4]);
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    loadedTasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new TapuException("Error loading file: " + e.getMessage());
        }
        return loadedTasks;
    }

    /**
     * Saves the current list of tasks to the hard drive.
     * Creates the necessary directories if they do not exist.
     *
     * @param tasks The ArrayList of Task objects to save.
     * @throws TapuException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws TapuException {
        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);
            for (Task t : tasks) {
                fw.write(taskToFileString(t) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            throw new TapuException("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object into a formatted string suitable for text file storage.
     *
     * @param task The Task object to convert.
     * @return A pipe-separated string representing the task's type, status, and details.
     */
    private String taskToFileString(Task task) {
        String str = task.toString();
        String status = str.substring(4, 5).equals("X") ? "1" : "0";
        String description = str.substring(7);

        if (task instanceof Deadline) {
            int byIndex = description.lastIndexOf(" (by: ");
            if (byIndex != -1) {
                String desc = description.substring(0, byIndex);
                String by = description.substring(byIndex + 6, description.length() - 1);
                return "D | " + status + " | " + desc + " | " + by;
            }
        } else if (task instanceof Event) {
            int fromIndex = description.lastIndexOf(" (from: ");
            int toIndex = description.lastIndexOf(" to: ");
            if (fromIndex != -1 && toIndex != -1) {
                String desc = description.substring(0, fromIndex);
                String from = description.substring(fromIndex + 8, toIndex);
                String to = description.substring(toIndex + 5, description.length() - 1);
                return "E | " + status + " | " + desc + " | " + from + " | " + to;
            }
        }
        return "T | " + status + " | " + description;
    }
}