
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A checkpoint 1 test
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ACheckpoint1
{
    // Tests for checkpoint 1.
    // Pass them all, plus ensure main loop until quit works,
    // for full credit on checkpoint 1.
    // Note these must also pass for all subsequent checkpoints
    // including final project.
    Spreadsheet grid;
    
    /**
     * A test
     */
    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }

    /**
     * A test
     */
    @Test
    public void testGetRows()
    {
        assertEquals("getRows", 20, grid.getRows());
    }

    /**
     * A test
     */
    @Test
    public void testGetCols()
    {
        assertEquals("getCols", 12, grid.getCols());
    }

    /**
     * A test
     */
    @Test
    public void testProcessCommand()
    {
        String str = grid.processCommand("");
        assertEquals("output from empty command", "", str);
    }

    /**
     * A test
     */
    @Test
    public void testLongShortStringCell()
    {
        SpreadsheetLocation loc = new SpreadsheetLocation("L20");
        assertEquals("SpreadsheetLocation column", 11, loc.getCol());
        assertEquals("SpreadsheetLocation row", 19, loc.getRow());

        loc = new SpreadsheetLocation("D5");
        assertEquals("SpreadsheetLocation column", 3, loc.getCol());
        assertEquals("SpreadsheetLocation row", 4, loc.getRow());

        loc = new SpreadsheetLocation("A1");
        assertEquals("SpreadsheetLocation column", 0, loc.getCol());
        assertEquals("SpreadsheetLocation row", 0, loc.getRow());
    }

    /**
     * A test
     */
    @Test
    public void testProcessCommandNonliteralEmpty()
    {
        String input = " ".trim();
        String output = grid.processCommand(input);
        assertEquals("output from empty command", "", output);
    }
}
