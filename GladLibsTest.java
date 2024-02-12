import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The GladLibsTest class is a JUnit test suite 
 * designed to validate the functionality of the GladLibs class, 
 * ensuring that placeholders are correctly replaced 
 * and inputs without placeholders remain unchanged.
 * 
 * @author Vaishali Vyas
 * @version 2024-02-12
 */
public class GladLibsTest {
    private GladLibs gladLibs;

    @BeforeEach
    public void setUp() {
        // Initialize your GladLibs instance before each test
        gladLibs = new GladLibs();
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources, if necessary
    }

    @Test
    public void testProcessWordReplacesPlaceholder() {
        // Assuming processWord is now accessible
        String template = "The <adjective> brown fox";
        String result = gladLibs.processWord(template);
        
        // Verify the placeholder was replaced (basic check, not checking for specific word)
        assertFalse(result.equals(template), "Placeholder should be replaced");
    }

    @Test
    public void testProcessWordWithoutPlaceholderRemainsUnchanged() {
        // Test input without placeholders
        String input = "The quick brown fox";
        String result = gladLibs.processWord(input);
        
        // Verify the input remains unchanged
        assertEquals(input, result, "Input without placeholders should remain unchanged");
    }
}
