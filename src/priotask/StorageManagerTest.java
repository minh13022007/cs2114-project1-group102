package priotask;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
public class StorageManagerTest extends student.TestCase {
    private static final String ASSIGNMENTS_FILE = "assignments.json";
    private static final String EVENTS_FILE = "events.json";
 
    private StorageManager storageManager;
    private String backupAssignments;
    private String backupEvents;
 
    public void setUp() throws IOException {
        storageManager = new StorageManager();
        backupAssignments = readFileIfExists(ASSIGNMENTS_FILE);
        backupEvents = readFileIfExists(EVENTS_FILE);
    }
 
    public void tearDown() throws IOException {
        restoreOrDelete(ASSIGNMENTS_FILE, backupAssignments);
        restoreOrDelete(EVENTS_FILE, backupEvents);
    }
 
    private String readFileIfExists(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        return new String(Files.readAllBytes(file.toPath()));
    }
 
    private void restoreOrDelete(String path, String content) throws IOException {
        File file = new File(path);
        if (content == null) {
            if (file.exists()) {
                file.delete();
            }
        }
        else {
            try (Writer writer = new FileWriter(file)) {
                writer.write(content);
            }
        }
    }
 
    public void testSaveAndLoadTasksRoundTrip() {
        List<Assignment> tasks = new ArrayList<>();
        tasks.add(new Assignment("Essay", LocalDate.of(2026, 11, 1), Priority.HIGH));
        storageManager.saveTasks(tasks);
 
        List<Assignment> loaded = storageManager.loadTasks();
 
        assertEquals(1, loaded.size());
        assertEquals("Essay", loaded.get(0).getTitle());
        assertEquals(LocalDate.of(2026, 11, 1), loaded.get(0).getDueDate());
        assertEquals(Priority.HIGH, loaded.get(0).getPriority());
    }
 
    public void testLoadTasksReturnsEmptyListWhenFileMissing() {
        File file = new File(ASSIGNMENTS_FILE);
        if (file.exists()) {
            file.delete();
        }
 
        List<Assignment> loaded = storageManager.loadTasks();
 
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }
 
    public void testSaveAndLoadEventsRoundTrip() {
        List<SchoolEvent> events = new ArrayList<>();
        events.add(new SchoolEvent("Chess Club", LocalDate.of(2026, 10, 20), "Club"));
        storageManager.saveEvents(events);
 
        List<SchoolEvent> loaded = storageManager.loadEvents();
 
        assertEquals(1, loaded.size());
        assertEquals("Chess Club", loaded.get(0).getEventName());
        assertEquals(LocalDate.of(2026, 10, 20), loaded.get(0).getEventDate());
        assertEquals("Club", loaded.get(0).getEventType());
    }
 
    public void testLoadEventsReturnsEmptyListWhenFileMissing() {
        File file = new File(EVENTS_FILE);
        if (file.exists()) {
            file.delete();
        }
 
        List<SchoolEvent> loaded = storageManager.loadEvents();
 
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }
}
 
