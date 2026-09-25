package priotask;
 
import java.time.LocalDate;
import java.util.List;
 
public class TaskManagerTest extends student.TestCase {
    private TaskManager manager;
 
    public void setUp() {
        manager = new TaskManager();
    }
 
    // ---- addAssignment ----
    public void testAddAssignmentNormalCase() {
        manager.addAssignment("Essay", LocalDate.now().plusDays(5), Priority.HIGH);
        assertEquals(1, manager.getActiveTasks().size());
        assertEquals("Essay", manager.getActiveTasks().get(0).getTitle());
    }
 
    public void testAddAssignmentNullTitleThrowsException() {
        Exception thrown = null;
        try {
            manager.addAssignment(null, LocalDate.now().plusDays(5), Priority.HIGH);
        }
        catch (IllegalArgumentException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(0, manager.getActiveTasks().size());
    }
 
    // ---- getSortedTasks ----
    public void testGetSortedTasksOrdersByDateThenPriority() {
        manager.addAssignment("Later Task", LocalDate.now().plusDays(10), Priority.LOW);
        manager.addAssignment("Sooner Task", LocalDate.now().plusDays(1), Priority.HIGH);
        manager.addAssignment("Middle Task", LocalDate.now().plusDays(5), Priority.MEDIUM);
 
        List<Assignment> sorted = manager.getSortedTasks();
 
        assertEquals("Sooner Task", sorted.get(0).getTitle());
        assertEquals("Middle Task", sorted.get(1).getTitle());
        assertEquals("Later Task", sorted.get(2).getTitle());
    }
 
    public void testGetSortedTasksEmptyListReturnsEmptyCollection() {
        List<Assignment> sorted = manager.getSortedTasks();
        assertNotNull(sorted);
        assertTrue(sorted.isEmpty());
    }
 
    // ---- completeTask ----
    public void testCompleteTaskRemovesCorrectDisplayedTask() {
        manager.addAssignment("Zebra Task", LocalDate.now().plusDays(10), Priority.LOW);
        manager.addAssignment("Apple Task", LocalDate.now().plusDays(1), Priority.HIGH);
 
        boolean result = manager.completeTask(1);
 
        assertTrue(result);
        assertEquals(1, manager.getActiveTasks().size());
        assertEquals("Zebra Task", manager.getActiveTasks().get(0).getTitle());
        assertEquals(1, manager.getPastTasks().size());
        assertEquals("Apple Task", manager.getPastTasks().get(0).getTitle());
    }
 
    public void testCompleteTaskOutOfBoundsReturnsFalse() {
        manager.addAssignment("Only Task", LocalDate.now().plusDays(3), Priority.MEDIUM);
 
        boolean result = manager.completeTask(5);
 
        assertFalse(result);
        assertEquals(1, manager.getActiveTasks().size());
        assertEquals(0, manager.getPastTasks().size());
    }
 
    // ---- addEvent ----
    public void testAddEventNormalCase() {
        manager.addEvent("Chess Club", LocalDate.now().plusDays(5), "Club");
        assertEquals(1, manager.getEvents().size());
        assertEquals("Chess Club", manager.getEvents().get(0).getEventName());
    }
 
    public void testAddEventNullNameThrowsException() {
        Exception thrown = null;
        try {
            manager.addEvent(null, LocalDate.now().plusDays(5), "Club");
        }
        catch (IllegalArgumentException e) {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(0, manager.getEvents().size());
    }
 
    // ---- getSortedEvents ----
    public void testGetSortedEventsOrdersByDate() {
        manager.addEvent("Later Event", LocalDate.now().plusDays(10), "Social");
        manager.addEvent("Sooner Event", LocalDate.now().plusDays(1), "Club");
 
        List<SchoolEvent> sorted = manager.getSortedEvents();
 
        assertEquals("Sooner Event", sorted.get(0).getEventName());
        assertEquals("Later Event", sorted.get(1).getEventName());
    }
 
    public void testGetSortedEventsEmptyListReturnsEmptyCollection() {
        List<SchoolEvent> sorted = manager.getSortedEvents();
        assertNotNull(sorted);
        assertTrue(sorted.isEmpty());
    }
 
    // ---- removeEvent ----
    public void testRemoveEventRemovesCorrectDisplayedEvent() {
        manager.addEvent("Zebra Event", LocalDate.now().plusDays(10), "Social");
        manager.addEvent("Apple Event", LocalDate.now().plusDays(1), "Club");
 
        boolean result = manager.removeEvent(1);
 
        assertTrue(result);
        assertEquals(1, manager.getEvents().size());
        assertEquals("Zebra Event", manager.getEvents().get(0).getEventName());
    }
 
    public void testRemoveEventOutOfBoundsReturnsFalse() {
        manager.addEvent("Only Event", LocalDate.now().plusDays(3), "Other");
 
        boolean result = manager.removeEvent(5);
 
        assertFalse(result);
        assertEquals(1, manager.getEvents().size());
    }
}
 