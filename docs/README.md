# Tapu User Guide

Tapu is a desktop application for managing tasks, optimized for use via a Command Line Interface (CLI). If you can type fast, Tapu can get your task management done faster than traditional GUI apps.

## Features

### Adding a Todo Task: `todo`
Adds a task without any date/time attached to it.
* **Format:** `todo <description>`
* **Example:** `todo read book`

### Adding a Deadline: `deadline`
Adds a task that needs to be done before a specific date/time.
* **Format:** `deadline <description> /by <date>`
* **Example:** `deadline return book /by 2026-10-15`

### Adding an Event: `event`
Adds a task that starts at a specific time and ends at a specific time.
* **Format:** `event <description> /from <start time> /to <end time>`
* **Example:** `event project meeting /from Mon 2pm /to 4pm`

### Listing all Tasks: `list`
Shows a list of all tasks in the task list.
* **Format:** `list`

### Marking a Task as Done: `mark`
Marks the specified task as completed.
* **Format:** `mark <task_number>`
* **Example:** `mark 2`

### Unmarking a Task: `unmark`
Marks the specified task as incomplete.
* **Format:** `unmark <task_number>`
* **Example:** `unmark 2`

### Deleting a Task: `delete`
Deletes the specified task from the list.
* **Format:** `delete <task_number>`
* **Example:** `delete 3`

### Finding a Task: `find`
Finds tasks whose descriptions contain the given keyword.
* **Format:** `find <keyword>`
* **Example:** `find book`

### Exiting the Program: `bye`
Exits the program and saves your task list automatically.
* **Format:** `bye`