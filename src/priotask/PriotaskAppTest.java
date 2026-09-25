package priotask;
 
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Scanner;
 
import student.TestCase;
 
/**
 * Tests for PriotaskApp's console input validation loops:
 * promptForTitle, promptForDate, promptForPriority.
 *
 * These methods are private and read from a hardcoded Scanner(System.in),
 * so each test uses reflection to swap in a Scanner over simulated typed
 * input, then invokes the private method directly. This lets the "loop
 * until valid" recovery behavior be tested without needing real console
 * input.
 */
public class PriotaskAppTest
    extends TestCase
{
    private PriotaskApp app;
 
    /**
     * Creates a fresh PriotaskApp before each test.
     */
    public void setUp()
    {
        app = new PriotaskApp();
    }
 
    /**
     * Replaces app's private scanner field with one reading the given
     * simulated console input.
     */
    private void feedInput(String simulatedInput)
        throws Exception
    {
        Field scannerField = PriotaskApp.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(app, new Scanner(new StringReader(simulatedInput)));
    }
 
    /**
     * Invokes a private no-argument method on app by name and returns its
     * result.
     */
    private Object invokePrivate(String methodName)
        throws Exception
    {
        Method method = PriotaskApp.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(app);
    }
 
    // ---- promptForTitle -----------------------------------------------
 
    /**
     * Normal case: a valid title is returned as-is.
     */
    public void testPromptForTitleNormalCase()
        throws Exception
    {
        feedInput("Math Homework\n");
        String result = (String) invokePrivate("promptForTitle");
        assertEquals("Math Homework", result);
    }
 
    /**
     * Bad-input case: blank input is rejected and the loop re-prompts until
     * a valid title is entered.
     */
    public void testPromptForTitleRecoversFromBlankInput()
        throws Exception
    {
        feedInput("   \nMath Homework\n");
        String result = (String) invokePrivate("promptForTitle");
        assertEquals("Math Homework", result);
    }
 
    // ---- promptForDate -----------------------------------------------
 
    /**
     * Normal case: a valid future date parses and returns correctly.
     */
    public void testPromptForDateNormalCase()
        throws Exception
    {
        feedInput("2026-11-01\n");
        LocalDate result = (LocalDate) invokePrivate("promptForDate");
        assertEquals(LocalDate.of(2026, 11, 1), result);
    }
 
    /**
     * Bad-input case: an invalid date format is rejected (matching the
     * spec's own Test Plan example, 02/31/2026) and the loop re-prompts
     * until a valid date is entered.
     */
    public void testPromptForDateRecoversFromBadFormat()
        throws Exception
    {
        feedInput("02/31/2026\n2026-11-01\n");
        LocalDate result = (LocalDate) invokePrivate("promptForDate");
        assertEquals(LocalDate.of(2026, 11, 1), result);
    }
 
    // ---- promptForPriority -----------------------------------------------
 
    /**
     * Normal case: a valid priority string returns the matching enum value.
     */
    public void testPromptForPriorityNormalCase()
        throws Exception
    {
        feedInput("Medium\n");
        Priority result = (Priority) invokePrivate("promptForPriority");
        assertEquals(Priority.MEDIUM, result);
    }
 
    /**
     * Bad-input case: an unrecognized priority string is rejected and the
     * loop re-prompts until a valid priority is entered.
     */
    public void testPromptForPriorityRecoversFromInvalidChoice()
        throws Exception
    {
        feedInput("Extreme\nHigh\n");
        Priority result = (Priority) invokePrivate("promptForPriority");
        assertEquals(Priority.HIGH, result);
    }
}
 