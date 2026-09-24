package priotask;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class PriotaskApp {
    private final Scanner scanner;
    private final TaskManager manager;
    private final StorageManager storageManager;

    public PriotaskApp() {
        this.scanner = new Scanner(System.in);
        this.manager = new TaskManager();
        this.storageManager = new StorageManager();
        
        // Phased JSON Rollout: Load on startup
        manager.setActiveTasks(storageManager.loadTasks());
        manager.setEvents(storageManager.loadEvents());
    }

    public static void main(String[] args) {
        PriotaskApp app = new PriotaskApp();
        app.run();
    }
   

    public void run() {
        System.out.println("Welcome to Priotask!");
        boolean running = true;

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View Calendar (Tasks & Events)");
            System.out.println("2. Add Task");
            System.out.println("3. Complete Task");
            System.out.println("4. View Completed Tasks");
            System.out.println("5. Add School Event");
            System.out.println("6. Exit");
            System.out.print("> ");
            
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    List<Assignment> tasksToView = manager.getSortedTasks();
                    if (tasksToView.isEmpty()) {
                        System.out.println("No active assignments to display. Add an assignment first!");
                    } else {
                        System.out.println("\n--- Active Tasks ---");
                        displayTasks(tasksToView);
                    }
                    displayEvents(manager.getSortedEvents());
                    break;
                case "2":
                    String title = promptForTitle();
                    LocalDate date = promptForDate();
                    Priority priority = promptForPriority();
                    manager.addAssignment(title, date, priority);
                    System.out.println("Task successfully added!");
                    break;
                case "3":
                    List<Assignment> tasksToComplete = manager.getSortedTasks();
                    if (tasksToComplete.isEmpty()) {
                        System.out.println("No active assignments to display. Add an assignment first!");
                        break;
                    }
                    displayTasks(tasksToComplete);
                    System.out.print("Enter task number to complete: ");
                    try {
                        int index = Integer.parseInt(scanner.nextLine().trim());
                        if (!manager.completeTask(index)) {
                            System.out.println("Task number not found. Enter a number between 1 and " + manager.getActiveTasks().size());
                        } else {
                            System.out.println("Task marked as complete!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                    break;
                case "4":
                    List<Assignment> completedTasks = manager.getPastTasks();
                    if (completedTasks.isEmpty()) {
                        System.out.println("No completed tasks yet. Go get some work done!");
                    } else {
                        System.out.println("\n--- Completed Tasks ---");
                        displayTasks(completedTasks);
                    }
                    break;
                case "5":
                    String eventName = promptForEventName();
                    LocalDate eventDate = promptForDate();
                    String eventType = promptForEventType();
                    manager.addEvent(eventName, eventDate, eventType);
                    System.out.println("School event successfully added!");
                    break;
                case "6":
                    System.out.println("Saving tasks and events...");
                    storageManager.saveTasks(manager.getActiveTasks());
                    storageManager.saveEvents(manager.getEvents());
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
        scanner.close();
    }

    private String promptForTitle() {
        while (true) {
            System.out.print("Enter assignment title: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty() || input.length() > 60) {
                System.out.println("Titles must be between 1 and 60 characters.");
            } else {
                return input;
            }
        }
    }

    private LocalDate promptForDate() {
        while (true) {
            System.out.print("Enter due date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                LocalDate parsedDate = LocalDate.parse(input);
                if (parsedDate.isBefore(LocalDate.now())) {
                    System.out.println("Due date cannot be in the past.");
                } else {
                    return parsedDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Expected format: YYYY-MM-DD");
            }
        }
    }

    private Priority promptForPriority() {
        while (true) {
            System.out.print("Enter priority (High, Medium, Low): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Priority.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice. Please enter High, Medium, or Low.");
            }
        }
    }

    // --- School Events (clubs, social events, etc.) ---
 
    private String promptForEventName() {
        while (true) {
            System.out.print("Enter event name: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty() || input.length() > 60) {
                System.out.println("Event names must be between 1 and 60 characters.");
            } else {
                return input;
            }
        }
    }
 
    private String promptForEventType() {
        while (true) {
            System.out.print("Enter event type (Club, Social, Other): ");
            String input = scanner.nextLine().trim();
            switch (input.toLowerCase()) {
                case "club":
                    return "Club";
                case "social":
                    return "Social";
                case "other":
                    return "Other";
                default:
                    System.out.println("Invalid choice. Please enter Club, Social, or Other.");
            }
        }
    }
 
    private void displayEvents(List<SchoolEvent> events) {
        System.out.println("\n--- Upcoming School Events ---");
        if (events.isEmpty()) {
            System.out.println("No upcoming school events.");
            return;
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-40s | %-12s | %-15s%n", "ID", "Event Name", "Date", "Type");
        System.out.println("--------------------------------------------------------------------------------");
 
        for (int i = 0; i < events.size(); i++) {
            SchoolEvent event = events.get(i);
            System.out.printf("%-5d | %-40s | %-12s | %-15s%n",
                    (i + 1),
                    event.getEventName(),
                    event.getEventDate().toString(),
                    event.getEventType());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
 
    private void displayTasks(List<Assignment> tasks) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-40s | %-12s | %-10s%n", "ID", "Title", "Due Date", "Priority");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < tasks.size(); i++) {
            Assignment task = tasks.get(i);
            System.out.printf("%-5d | %-40s | %-12s | %-10s%n", 
                    (i + 1), 
                    task.getTitle(), 
                    task.getDueDate().toString(), 
                    task.getPriority().name());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}