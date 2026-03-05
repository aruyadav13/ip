package tapu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected String fromString;
    protected String toString;
    protected LocalDate fromDate;
    protected LocalDate toDate;

    public Event(String description, String from, String to) {
        super(description);
        this.fromString = parseDate(from);
        this.toString = parseDate(to);
    }

    private String parseDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        } catch (DateTimeParseException e) {
            return dateStr;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromString + " to: " + toString + ")";
    }
}