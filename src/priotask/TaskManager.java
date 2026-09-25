package priotask;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
 
public class TaskManager {
    private List<Assignment> activeTasks;
    private List<Assignment> pastTasks;
    private List<SchoolEvent> events;
 
    public TaskManager() {
        this.activeTasks = new ArrayList<>();
        this.pastTasks = new ArrayList<>();
        this.events = new ArrayList<>();
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
        List<Assignment> sorted = getSortedTasks();
        if (displayIndex < 1 || displayIndex > sorted.size()) {
            return false;
        }
        Assignment completed = sorted.get(displayIndex - 1);
        activeTasks.remove(completed);
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
 
    // --- School Events (clubs, social events, etc.) ---
 
    public void addEvent(String eventName, LocalDate eventDate, String eventType) {
        if (eventName == null || eventDate == null || eventType == null) {
            throw new IllegalArgumentException("Event details cannot be null");
        }
        events.add(new SchoolEvent(eventName, eventDate, eventType));
    }
 
    public List<SchoolEvent> getSortedEvents() {
        if (events.isEmpty()) {
            return new ArrayList<>();
        }
 
        return events.stream()
                .sorted(Comparator.comparing(SchoolEvent::getEventDate))
                .collect(Collectors.toList());
    }
 
    public boolean removeEvent(int displayIndex) {
        List<SchoolEvent> sorted = getSortedEvents();
        if (displayIndex < 1 || displayIndex > sorted.size()) {
            return false;
        }
        return events.remove(sorted.get(displayIndex - 1));
    }
 
    public List<SchoolEvent> getEvents() {
        return events;
    }
 
    public void setEvents(List<SchoolEvent> events) {
        if (events != null) {
            this.events = events;
        }
    }
}
 