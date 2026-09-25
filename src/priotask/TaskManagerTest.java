package priotask;
 
import java.time.LocalDate;
import java.util.List;
 
import student.TestCase;
 
/**
 * Tests for TaskManager's core logic: assignments (adding, sorting,
 * completing) and school events (adding, sorting, removing).
 *
 * Covers each key method with one normal case and one bad-input / edge
 * case.
 */
public class TaskManagerTest
    extends TestCase
{
    private TaskManager manager;
 
    /**
     * Sets up a fresh TaskManager before each test.
     */
    public void setUp()
    {
        manager = new TaskManager();
    }
 
    // ==================================================================
    // Assignments
    // ==================================================================
 
    // ---- addAssignment -----------------------------------------------
 
    /**
     * Normal case: adding a valid assignment increases the active list size
     * by one.
     */
    public void testAddAssignmentNormalCase()
    {
        manager.addAssignment("Essay", LocalDate.now().plusDays(5), Priority.HIGH);
        assertEquals(1, manager.getActiveTasks().size());
        assertEquals("Essay", manager.getActiveTasks().get(0).getTitle());
    }
 
    /**
     * Bad-input case: a null title must throw IllegalArgumentException and
     * must not add anything to the list.
     */
    public void testAddAssignmentNullTitleThrowsException()
    {
        Exception thrown = null;
        try
        {
            manager.addAssignment(null, LocalDate.now().plusDays(5), Priority.HIGH);
        }
        catch (IllegalArgumentException e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(0, manager.getActiveTasks().size());
    }
 
    // ---- getSortedTasks -------------------------------------------------
 
    /**
     * Normal case: an unsorted set of tasks comes back sorted by due date
     * ascending, then by priority.
     */
    public void testGetSortedTasksOrdersByDateThenPriority()
    {
        manager.addAssignment("Later Task", LocalDate.now().plusDays(10), Priority.LOW);
        manager.addAssignment("Sooner Task", LocalDate.now().plusDays(1), Priority.HIGH);
        manager.addAssignment("Middle Task", LocalDate.now().plusDays(5), Priority.MEDIUM);
 
        List<Assignment> sorted = manager.getSortedTasks();
 
        assertEquals("Sooner Task", sorted.get(0).getTitle());
        assertEquals("Middle Task", sorted.get(1).getTitle());
        assertEquals("Later Task", sorted.get(2).getTitle());
    }
 
    /**
     * Bad-input / edge case: an empty active list returns an empty
     * collection instead of throwing.
     */
    public void testGetSortedTasksEmptyListReturnsEmptyCollection()
    {
        List<Assignment> sorted = manager.getSortedTasks();
        assertNotNull(sorted);
        assertTrue(sorted.isEmpty());
    }
 
    // ---- completeTask -----------------------------------------------
 
    /**
     * Normal case: completing a task by its DISPLAYED index removes the
     * task the user actually sees at that position (not whatever happens
     * to sit at that index in insertion order).
     */
    public void testCompleteTaskRemovesCorrectDisplayedTask()
    {
        // Inserted out of date order on purpose: Zebra Task is added first
        // but is due later, so it should NOT be index 1 in the display.
        manager.addAssignment("Zebra Task", LocalDate.now().plusDays(10), Priority.LOW);
        manager.addAssignment("Apple Task", LocalDate.now().plusDays(1), Priority.HIGH);
 
        boolean result = manager.completeTask(1);
 
        assertTrue(result);
        assertEquals(1, manager.getActiveTasks().size());
        assertEquals("Zebra Task", manager.getActiveTasks().get(0).getTitle());
        assertEquals(1, manager.getPastTasks().size());
        assertEquals("Apple Task", manager.getPastTasks().get(0).getTitle());
    }
 
    /**
     * Bad-input case: an out-of-bounds index returns false and leaves the
     * active list unchanged.
     */
    public void testCompleteTaskOutOfBoundsReturnsFalse()
    {
        manager.addAssignment("Only Task", LocalDate.now().plusDays(3), Priority.MEDIUM);
 
        boolean result = manager.completeTask(5);
 
        assertFalse(result);
        assertEquals(1, manager.getActiveTasks().size());
        assertEquals(0, manager.getPastTasks().size());
    }
 
    // ==================================================================
    // School events
    // ==================================================================
 
    // ---- addEvent -----------------------------------------------
 
    /**
     * Normal case: adding a valid event increases the event list size by
     * one.
     */
    public void testAddEventNormalCase()
    {
        manager.addEvent("Chess Club", LocalDate.now().plusDays(5), "Club");
        assertEquals(1, manager.getEvents().size());
        assertEquals("Chess Club", manager.getEvents().get(0).getEventName());
    }
 
    /**
     * Bad-input case: a null event name must throw IllegalArgumentException
     * and must not add anything to the list.
     */
    public void testAddEventNullNameThrowsException()
    {
        Exception thrown = null;
        try
        {
            manager.addEvent(null, LocalDate.now().plusDays(5), "Club");
        }
        catch (IllegalArgumentException e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertEquals(0, manager.getEvents().size());
    }
 
    // ---- getSortedEvents -----------------------------------------------
 
    /**
     * Normal case: events come back sorted by date ascending.
     */
    public void testGetSortedEventsOrdersByDate()
    {
        manager.addEvent("Later Event", LocalDate.now().plusDays(10), "Social");
        manager.addEvent("Sooner Event", LocalDate.now().plusDays(1), "Club");
 
        List<SchoolEvent> sorted = manager.getSortedEvents();
 
        assertEquals("Sooner Event", sorted.get(0).getEventName());
        assertEquals("Later Event", sorted.get(1).getEventName());
    }
 
    /**
     * Bad-input / edge case: an empty event list returns an empty
     * collection instead of throwing.
     */
    public void testGetSortedEventsEmptyListReturnsEmptyCollection()
    {
        List<SchoolEvent> sorted = manager.getSortedEvents();
        assertNotNull(sorted);
        assertTrue(sorted.isEmpty());
    }
 
    // ---- removeEvent -----------------------------------------------
 
    /**
     * Normal case: removing an event by its DISPLAYED index removes the
     * event actually shown at that position, the same
     * displayed-vs-insertion-order guard as completeTask.
     */
    public void testRemoveEventRemovesCorrectDisplayedEvent()
    {
        manager.addEvent("Zebra Event", LocalDate.now().plusDays(10), "Social");
        manager.addEvent("Apple Event", LocalDate.now().plusDays(1), "Club");
 
        boolean result = manager.removeEvent(1);
 
        assertTrue(result);
        assertEquals(1, manager.getEvents().size());
        assertEquals("Zebra Event", manager.getEvents().get(0).getEventName());
    }
 
    /**
     * Bad-input case: an out-of-bounds index returns false and leaves the
     * event list unchanged.
     */
    public void testRemoveEventOutOfBoundsReturnsFalse()
    {
        manager.addEvent("Only Event", LocalDate.now().plusDays(3), "Other");
 
        boolean result = manager.removeEvent(5);
 
        assertFalse(result);
        assertEquals(1, manager.getEvents().size());
    }
}
 
