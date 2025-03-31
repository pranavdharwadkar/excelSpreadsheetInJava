
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * B checkpoint 1 test
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class BCheckpoint1
{
    // Tests for Part B, Checkpoint 1
    // Note these must also pass for all subsequent checkpoints
    // including final project.
    private Spreadsheet grid;
    
    /**
     * B test
     */
    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }
    
    /**
     * B test
     */
    @Test
    public void testConstant()
    {
        grid.processCommand("A1 = ( -43.5 )");
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 0));
        assertEquals("constant formula value",
            TestsALL.Helper.format("-43.5"), cell.abbreviatedCellText());
        assertEquals("constant formula inspection",
            "( -43.5 )", cell.fullCellText());
    }
    
    /**
     * B test
     */
    @Test
    public void testMultiplicative()
    {
        String formula = "( 2 * 3 * 4 * 5 / 2 / 3 / 2 )";
        grid.processCommand("A1 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 0));
        assertEquals("multiplicative formula value",
            TestsALL.Helper.format("10.0"), cell.abbreviatedCellText());
        assertEquals("multiplicative formula inspection",
            formula, cell.fullCellText());
    }
    
    /**
     * B test
     */
    @Test
    public void testAdditive()
    {
        String formula = "( 1 + 3 + 5 - 2 - 4 - 6 )";
        grid.processCommand("L20 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(19, 11));
        assertEquals("additive formula value",
            TestsALL.Helper.format("-3.0"), cell.abbreviatedCellText());
        assertEquals("additive formula inspection",
            formula, cell.fullCellText());
    }  
    
    /**
     * B test
     */
    @Test
    public void testMixed()
    {
        String formula = "( 5.4 * 3.5 / -1.4 + 27.4 - 11.182 )";
        grid.processCommand("E1 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 4));
        assertEquals("mixed formula value length",
            10, cell.abbreviatedCellText().length());
        assertEquals("mixed formula value", 2.718,
            Double.parseDouble(cell.abbreviatedCellText()), 1e-6);
        assertEquals("mixed formula inspection",
            formula, cell.fullCellText());
    }

    
    /**
     * B test
     */
    @Test
    public void testMultiplicativeWithNegative()
    {
        String formula = "( -2 * -3.0 * 4 * 5 / -2 / 3 / 2 )";
        grid.processCommand("A1 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 0));
        assertEquals("multiplicative formula value",
            TestsALL.Helper.format("-10.0"), cell.abbreviatedCellText());
        assertEquals("multiplicative formula inspection",
            formula, cell.fullCellText());
    }
    
    /**
     * B test
     */
    @Test
    public void testAdditiveWithNegatives()
    {
        String formula = "( -1 + 3 + 5.0 - -2 - 4 - 6 + -2 )";
        grid.processCommand("L20 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(19, 11));
        assertEquals("additive formula value",
            TestsALL.Helper.format("-3.0"), cell.abbreviatedCellText());
        assertEquals("additive formula inspection",
            formula, cell.fullCellText());
    }
    
    /**
     * B test
     */
    @Test
    public void testSimpleMixedWithOrWithoutPrecedence()
    {
        // Accept either the left-to-right basic result 13 or
        // the precedence extra credit result 11
        String formula = "( 1 + 2 * 3 + 4 )";
        grid.processCommand("A20 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(19, 0));
        String text = cell.abbreviatedCellText();
        assertEquals("length", 10, text.length());
        String result = text.trim();
        assertTrue("result " + result +
            " should be either 13 (left-to-right) or 11 (with precedence)",
            result.equals("13.0") || result.equals("11.0"));
    }
    
    /**
     * B test
     */
    @Test
    public void testMixedWithOrWithoutPrecedence()
    { // Accept either the left-to-right basic result 24 or
        // the precedence extra credit result 20
        String formula = "( 1 + 2 * 3 + 4.5 - 5 * 6 - 3.0 / 2 )";
        grid.processCommand("E1 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 4));
        assertEquals("mixed formula value length",
            10, cell.abbreviatedCellText().length());
        double result = Double.parseDouble(cell.abbreviatedCellText());
        if (result > 0)
        {
            assertEquals("mixed formula value (without precedence)",
                24, result, 1e-6);
        }
        else
        {
            assertEquals("mixed formula value (with precedence)",
                -20, result, 1e-6);
        }
        assertEquals("mixed formula inspection", formula, cell.fullCellText());
    }
}
