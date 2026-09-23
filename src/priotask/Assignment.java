package priotask;
import java.time.LocalDate;

public class Assignment {
    private String title;
    private LocalDate dueDate;
    private Priority priority;

    public Assignment(String title, LocalDate dueDate, Priority priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }
}