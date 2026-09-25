package priotask;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
import student.TestCase;
 
/**
 * Tests for StorageManager's JSON persistence: saving/loading assignments
 * and saving/loading events.
 *
 * These tests write to the real assignments.json / events.json files (the
 * paths StorageManager uses), so setUp/tearDown back up and restore
 * whatever was already there, to avoid clobbering real saved data when the
 * suite is run.
 */
public class StorageManagerTest
    extends TestCase
{
    private static final String ASSIGNMENTS_FILE = "assignments.json";
    private static final String EVENTS_FILE = "events.json";
 
    private StorageManager storageManager;
    private String backupAssignments;
    private String backupEvents;
 
    /**
     * Sets up a fresh StorageManager and backs up any existing data files
     * before each test.
     */
    public void setUp() throws IOException
    {
        storageManager = new StorageManager();
        backupAssignments = readFileIfExists(ASSIGNMENTS_FILE);
        backupEvents = readFileIfExists(EVENTS_FILE);
    }
 
    /**
     * Restores whatever was in the data files before the test ran (or
     * deletes the test-created file if there was nothing before).
     */
    public void tearDown() throws IOException
    {
        restoreOrDelete(ASSIGNMENTS_FILE, backupAssignments);
        restoreOrDelete(EVENTS_FILE, backupEvents);
    }
 
    private String readFileIfExists(String path) throws IOException
    {
        File file = new File(path);
        if (!file.exists())
        {
            return null;
        }
        return new String(Files.readAllBytes(file.toPath()));
    }
 
    private void restoreOrDelete(String path, String content) throws IOException
    {
        File file = new File(path);
        if (content == null)
        {
            if (file.exists())
            {
                file.delete();
            }
        }
        else
        {
            try (Writer writer = new FileWriter(file))
            {
                writer.write(content);
            }
        }
    }
 
    // ---- saveTasks / loadTasks -----------------------------------------
 
    /**
     * Normal case: saving a list of tasks and loading it back reconstructs
     * the same data.
     */
    public void testSaveAndLoadTasksRoundTrip()
    {
        List<Assignment> tasks = new ArrayList<>();
        tasks.add(new Assignment("Essay", LocalDate.of(2026, 11, 1), Priority.HIGH));
        storageManager.saveTasks(tasks);
 
        List<Assignment> loaded = storageManager.loadTasks();
 
        assertEquals(1, loaded.size());
        assertEquals("Essay", loaded.get(0).getTitle());
        assertEquals(LocalDate.of(2026, 11, 1), loaded.get(0).getDueDate());
        assertEquals(Priority.HIGH, loaded.get(0).getPriority());
    }
 
    /**
     * Bad-input / edge case: loading when the file doesn't exist on disk
     * returns an empty list instead of throwing.
     */
    public void testLoadTasksReturnsEmptyListWhenFileMissing()
    {
        File file = new File(ASSIGNMENTS_FILE);
        if (file.exists())
        {
            file.delete();
        }
 
        List<Assignment> loaded = storageManager.loadTasks();
 
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }
 
    // ---- saveEvents / loadEvents -----------------------------------------
 
    /**
     * Normal case: saving a list of events and loading it back reconstructs
     * the same data.
     */
    public void testSaveAndLoadEventsRoundTrip()
    {
        List<SchoolEvent> events = new ArrayList<>();
        events.add(new SchoolEvent("Chess Club", LocalDate.of(2026, 10, 20), "Club"));
        storageManager.saveEvents(events);
 
        List<SchoolEvent> loaded = storageManager.loadEvents();
 
        assertEquals(1, loaded.size());
        assertEquals("Chess Club", loaded.get(0).getEventName());
        assertEquals(LocalDate.of(2026, 10, 20), loaded.get(0).getEventDate());
        assertEquals("Club", loaded.get(0).getEventType());
    }
 
    /**
     * Bad-input / edge case: loading when the events file doesn't exist on
     * disk returns an empty list instead of throwing.
     */
    public void testLoadEventsReturnsEmptyListWhenFileMissing()
    {
        File file = new File(EVENTS_FILE);
        if (file.exists())
        {
            file.delete();
        }
 
        List<SchoolEvent> loaded = storageManager.loadEvents();
 
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }
}
 