package priotask;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Assignment> activeTasks;
    private List<Assignment> pastTasks;

    public TaskManager() {
        this.activeTasks = new ArrayList<>();
        this.pastTasks = new ArrayList<>();
    }

    public void addAssignment(String title, LocalDate dueDate, Priority priority) {
        if (title == null || dueDate == null || priority == null) {
            throw new IllegalArgumentException("Assignment details cannot be null");
        }
        activeTasks.add(new Assignment(title, dueDate, priority));
    }

    public List<Assignment> getSortedTasks() {
        if (activeTasks.isEmpty()) {
            return new ArrayList<>();
        }
        
        return activeTasks.stream()
                .sorted(Comparator.comparing(Assignment::getDueDate)
                .thenComparing(Assignment::getPriority))
                .collect(Collectors.toList());
    }

    public boolean completeTask(int displayIndex) {
        if (displayIndex < 1 || displayIndex > activeTasks.size()) {
            return false;
        }
        
        // Convert 1-based display index to 0-based array index
        Assignment completed = activeTasks.remove(displayIndex - 1);
        pastTasks.add(completed);
        return true;
    }

    public List<Assignment> getActiveTasks() {
        return activeTasks;
    }

    public void setActiveTasks(List<Assignment> tasks) {
        if (tasks != null) {
            this.activeTasks = tasks;
        }
    }
    public List<Assignment> getPastTasks() {
        return pastTasks;
    }
    
}