package priotask;
 
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Scanner;
 
public class PriotaskAppTest extends student.TestCase {
    private PriotaskApp app;
 
    public void setUp() {
        app = new PriotaskApp();
    }
 
    private void feedInput(String simulatedInput) throws Exception {
        Field scannerField = PriotaskApp.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(app, new Scanner(new StringReader(simulatedInput)));
    }
 
    private Object invokePrivate(String methodName) throws Exception {
        Method method = PriotaskApp.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(app);
    }
 
    public void testPromptForTitleNormalCase() throws Exception {
        feedInput("Math Homework\n");
        String result = (String) invokePrivate("promptForTitle");
        assertEquals("Math Homework", result);
    }
 
    public void testPromptForTitleRecoversFromBlankInput() throws Exception {
        feedInput("   \nMath Homework\n");
        String result = (String) invokePrivate("promptForTitle");
        assertEquals("Math Homework", result);
    }
 
    public void testPromptForDateNormalCase() throws Exception {
        feedInput("2026-11-01\n");
        LocalDate result = (LocalDate) invokePrivate("promptForDate");
        assertEquals(LocalDate.of(2026, 11, 1), result);
    }
 
    public void testPromptForDateRecoversFromBadFormat() throws Exception {
        feedInput("02/31/2026\n2026-11-01\n");
        LocalDate result = (LocalDate) invokePrivate("promptForDate");
        assertEquals(LocalDate.of(2026, 11, 1), result);
    }
 
    public void testPromptForPriorityNormalCase() throws Exception {
        feedInput("Medium\n");
        Priority result = (Priority) invokePrivate("promptForPriority");
        assertEquals(Priority.MEDIUM, result);
    }
 
    public void testPromptForPriorityRecoversFromInvalidChoice() throws Exception {
        feedInput("Extreme\nHigh\n");
        Priority result = (Priority) invokePrivate("promptForPriority");
        assertEquals(Priority.HIGH, result);
    }
}
 