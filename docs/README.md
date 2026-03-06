# Tapu - User Guide

Tapu is a **desktop app for managing tasks, optimized for use via a Command Line Interface (CLI)**. If you can type fast, Tapu can get your task management done faster than traditional GUI apps.

---

## Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
    * [Adding a Todo: `todo`](#adding-a-todo--todo)
    * [Adding a Deadline: `deadline`](#adding-a-deadline--deadline)
    * [Adding an Event: `event`](#adding-an-event--event)
    * [Listing all Tasks: `list`](#listing-all-tasks--list)
    * [Marking a Task: `mark`](#marking-a-task--mark)
    * [Unmarking a Task: `unmark`](#unmarking-a-task--unmark)
    * [Deleting a Task: `delete`](#deleting-a-task--delete)
    * [Locating Tasks by Name: `find`](#locating-tasks-by-name--find)
    * [Exiting the Program: `bye`](#exiting-the-program--bye)
* [FAQ](#faq)
* [Command Summary](#command-summary)

---

## Quick Start

1. Ensure you have Java `11` or above installed in your Computer.
2. Download the latest `tapu.jar` from the releases page.
3. Copy the file to the folder you want to use as the home folder for your task list.
4. Open a command terminal, `cd` into the folder, and use the `java -jar tapu.jar` command to run the application.
5. Type the command in the command box and press Enter to execute it. Some example commands you can try:
    * `list` : Lists all tasks.
    * `todo read book` : Adds a new todo task to the list.
    * `delete 3` : Deletes the 3rd task in the current list.
    * `bye` : Exits the app.

---

## Features

> ℹ️ **Notes about the command format:**
> * Words in `<angle-brackets>` are the parameters to be supplied by the user.
> * Extraneous parameters for commands that do not take in parameters (such as `list` and `bye`) will be ignored.

### Adding a Todo : `todo`
Adds a standard task without any date or time attached to it.

**Format:** `todo <description>`

**Example:**
* `todo read book`

### Adding a Deadline : `deadline`
Adds a task that needs to be done before a specific date.

**Format:** `deadline <description> /by <date>`

> 💡 **Tip:** Tapu can understand dates in the `yyyy-mm-dd` format (e.g., `2026-10-15`) and will automatically reformat them for you!

**Example:**
* `deadline return library book /by 2026-10-15`
* `deadline submit assignment /by Sunday`

### Adding an Event : `event`
Adds a task that starts at a specific time and ends at a specific time.

**Format:** `event <description> /from <start time> /to <end time>`

**Example:**
* `event project meeting /from Mon 2pm /to 4pm`

### Listing all Tasks : `list`
Shows a complete list of all tasks currently in your task list.

**Format:** `list`

### Marking a Task : `mark`
Marks the specified task in the list as completed.

**Format:** `mark <index>`
* Marks the task at the specified `index`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** (1, 2, 3, ...).

**Example:**
* `list` followed by `mark 2` marks the 2nd task in the list as done.

### Unmarking a Task : `unmark`
Marks the specified completed task in the list as incomplete.

**Format:** `unmark <index>`

**Example:**
* `unmark 1` marks the 1st task in the list as not done yet.

### Deleting a Task : `delete`
Deletes the specified task from the list permanently.

**Format:** `delete <index>`

**Example:**
* `delete 3` removes the 3rd task in the task list.

### Locating Tasks by Name : `find`
Finds all tasks whose descriptions contain the specified keyword.

**Format:** `find <keyword>`
* The search is case-sensitive.
* Only the description is searched.

**Example:**
* `find book` returns `read book` and `return library book`.

### Exiting the Program : `bye`
Exits the program and automatically saves your task list to the hard disk.

**Format:** `bye`

---

## FAQ

**Q**: How is my data saved?
**A**: Tapu data is saved in the hard disk automatically after any command that changes the list. There is no need to save manually. It will be located in a `data/tapu.txt` file in the same folder as your `.jar` file.

---

## Command Summary

| Action | Format, Examples |
|--------|------------------|
| **Todo** | `todo <description>` <br> e.g., `todo read book` |
| **Deadline** | `deadline <description> /by <date>` <br> e.g., `deadline return book /by 2026-10-15` |
| **Event** | `event <description> /from <start> /to <end>` <br> e.g., `event meeting /from 2pm /to 4pm` |
| **List** | `list` |
| **Mark** | `mark <index>` <br> e.g., `mark 2` |
| **Unmark** | `unmark <index>` <br> e.g., `unmark 1` |
| **Delete** | `delete <index>` <br> e.g., `delete 3` |
| **Find** | `find <keyword>` <br> e.g., `find book` |
| **Bye** | `bye` |