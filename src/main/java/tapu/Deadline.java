package tapu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected String byString;
    protected LocalDate byDate;

    public Deadline(String description, String by) {
        super(description);
        try {
            // Try to parse the input as a Date (yyyy-mm-dd)
            this.byDate = LocalDate.parse(by);
            // If successful, format it to "MMM d yyyy" (e.g., Oct 15 2019)
            this.byString = byDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (DateTimeParseException e) {
            // If it's not a valid date format (e.g., "Sunday"), just save the string
            this.byString = by;
            this.byDate = null;
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byString + ")";
    }
}