
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Extension command errors test
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExtensionCommandErrors
{
    // Tests for command errors extension
    Spreadsheet grid;

    /**
     * Extension test
     */
    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }

    /**
     * Extension test
     */
    @Test
    public void testInvalidCommand()
    {
        String before = grid.processCommand("A1 = \"thrang\"");
        String error = grid.processCommand("lesnerize");
        String after = grid.getGridText();
        assertTrue("error message starts with ERROR: ",
            error.startsWith("ERROR: "));
        assertEquals("grid contents unchanged", before, after);
    }

    /**
     * Extension test
     */
    @Test
    public void testInvalidCellAssignment()
    {
        String before = grid.processCommand("A1 = \"hello\"");
        String error1 = grid.processCommand("A37 = 5");
        String error2 = grid.processCommand("M1 = 3");
        String error3 = grid.processCommand("A-5 = 2");
        String error4 = grid.processCommand("A0 = 17");
        String after = grid.getGridText();
        assertTrue("error1 message starts with ERROR: ",
            error1.startsWith("ERROR: "));
        assertTrue("error2 message starts with ERROR: ",
            error2.startsWith("ERROR: "));
        assertTrue("error3 message starts with ERROR: ",
            error3.startsWith("ERROR: "));
        assertTrue("error4 message starts with ERROR: ",
            error4.startsWith("ERROR: "));
        assertEquals("grid contents unchanged", before, after);
    }

    /**
     * Extension test
     */
    @Test
    public void testInvalidConstants()
    {
        String before = grid.processCommand("A1 = \"hello\"");
        String error1 = grid.processCommand("A2 = 5...");
        String error2 = grid.processCommand("A3 = 4p");
        String error3 = grid.processCommand("A4 = \"he");
        String error4 = grid.processCommand("A5 = 1/2/aughtfour");
        String error5 = grid.processCommand("A6 = *9");
        String after = grid.getGridText();
        assertTrue("error1 message starts with ERROR: ",
            error1.startsWith("ERROR: "));
        assertTrue("error2 message starts with ERROR: ",
            error2.startsWith("ERROR: "));
        assertTrue("error3 message starts with ERROR: ",
            error3.startsWith("ERROR: "));
        assertTrue("error4 message starts with ERROR: ",
            error4.startsWith("ERROR: "));
        assertTrue("error5 message starts with ERROR: ",
            error5.startsWith("ERROR: "));
        assertEquals("grid contents unchanged", before, after);
    }

    /**
     * Extension test
     */
    @Test
    public void testInvalidFormulaAssignment()
    {
        grid.processCommand("A1 = 1");
        String before = grid.processCommand("A2 = 2");
        String error1 = grid.processCommand("A3 = 5 + 2");
        String error2 = grid.processCommand("A4 = ( avs A1-A2 )");
        String error3 = grid.processCommand("A5 = ( sum A0-A2 )");
        String error4 = grid.processCommand("A6 = ( 1 + 2");
        String error5 = grid.processCommand("A7 = ( avg A1-B )");
        String error6 = grid.processCommand("A8 = M80");
        String after = grid.getGridText();
        assertTrue("error1 message starts with ERROR: ",
            error1.startsWith("ERROR: "));
        assertTrue("error2 message starts with ERROR: ",
            error2.startsWith("ERROR: "));
        assertTrue("error3 message starts with ERROR: ",
            error3.startsWith("ERROR: "));
        assertTrue("error4 message starts with ERROR: ",
            error4.startsWith("ERROR: "));
        assertTrue("error5 message starts with ERROR: ",
            error5.startsWith("ERROR: "));
        assertTrue("error6 message starts with ERROR: ",
            error6.startsWith("ERROR: "));
        assertEquals("grid contents unchanged", before, after);
    }

    /**
     * Extension test
     */
    @Test
    public void testWhitespaceTolerance()
    {
        // OK to either treat as error or as valid, just don't crash
        String before = grid.getGridText();
        grid.processCommand("L20=5");
        grid.processCommand(" A1  =     -14 ");
        grid.processCommand("A1=-14");
        grid.processCommand("A1=(3+5*4/2)");
        grid.processCommand("A1=(sum L20-L20)");
        grid.processCommand("clear    A1");
        String after = grid.processCommand("clear");
        assertEquals("end with empty grid", before, after);
    }
}