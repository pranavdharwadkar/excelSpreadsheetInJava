import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Extension command history
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExtensionEvaluationErrors
{
    // Tests for evaluation errors extension
    private Spreadsheet grid;
    private final String expectedError = "#ERROR    ";

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
    public void testSimpleError()
    {
        String formula = "( A2 )";
        grid.processCommand("A1 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 0));
        assertEquals("evaluation error", expectedError,
            cell.abbreviatedCellText());
        assertEquals("formula", formula, cell.fullCellText());
    }

    /**
     * Extension test
     */
    @Test
    public void testDivideByZero()
    {
        String formula = "( 1 / 0 )";
        grid.processCommand("A1 = " + formula);
        Cell cell = grid.getCell(new TestsALL.TestLocation(0, 0));
        assertEquals("evaluation error", expectedError,
            cell.abbreviatedCellText());
        assertEquals("formula", formula, cell.fullCellText());
    }

    /**
     * Extension test
     * @param row row
     * @param col col
     * @param formula formula
     * @param description description
     */
    private void assertEvalError(int row, int col, String formula,
        String description)
    {
        Cell cell = grid.getCell(new TestsALL.TestLocation(row, col));
        assertEquals(description, expectedError, cell.abbreviatedCellText());
        assertEquals("formula", formula, cell.fullCellText());
    }

    /**
     * Extension test
     * @param row row
     * @param col col
     * @param expected expected
     * @param formula formula
     * @param description description
     */
    private void assertEvalOK(int row, int col, String expected, String formula,
        String description)
    {
        Cell cell = grid.getCell(new TestsALL.TestLocation(row, col));
        assertEquals(description, expected, cell.abbreviatedCellText());
        assertEquals("formula", formula, cell.fullCellText());
    }

    /**
     * Extension test
     */
    @Test
    public void testSimpleTypeErrors()
    {
        String formula = "( avg A1-A1 )";
        grid.processCommand("A2 = " + formula);
        assertEvalError(1, 0, formula, "empty ref error");
        grid.processCommand("A1 = 1");
        assertEvalOK(1, 0, "1.0       ", formula, "valid ref");
        grid.processCommand("A1 = \"hello\"");
        assertEvalError(1, 0, formula, "string ref error");
        grid.processCommand("A1 = 2");
        assertEvalOK(1, 0, "2.0       ", formula, "valid ref");
        grid.processCommand("A1 = 3");
        assertEvalOK(1, 0, "3.0       ", formula, "valid ref");
    }

    /**
     * Extension test
     */
    @Test
    public void testErrorPropagation()
    {
        String formulaA2 = "( sum A1-A1 )";
        String formulaA3 = "( 1 / A2 )";
        String formulaA4 = "( A3 + A3 )";
        String formulaB3 = "( A2 / 1 )";
        String formulaB4 = "( B3 + B3 )";
        String formulaC3 = "( avg A2-A3 )";
        String formulaC4 = "( sum C3-C3 )";
        grid.processCommand("A2 = " + formulaA2);
        grid.processCommand("A3 = " + formulaA3);
        grid.processCommand("A4 = " + formulaA4);
        grid.processCommand("B3 = " + formulaB3);
        grid.processCommand("B4 = " + formulaB4);
        grid.processCommand("C3 = " + formulaC3);
        grid.processCommand("C4 = " + formulaC4);
        assertEvalError(1, 0, formulaA2, "direct");
        assertEvalError(2, 0, formulaA3, "indirect");
        assertEvalError(3, 0, formulaA4, "indirect");
        assertEvalError(2, 1, formulaB3, "indirect");
        assertEvalError(3, 1, formulaB4, "indirect");
        assertEvalError(2, 2, formulaC3, "indirect");
        assertEvalError(3, 2, formulaC4, "indirect");
        grid.processCommand("A1 = 1");
        assertEvalOK(1, 0, "1.0       ", formulaA2, "direct");
        assertEvalOK(2, 0, "1.0       ", formulaA3, "indirect");
        assertEvalOK(3, 0, "2.0       ", formulaA4, "indirect");
        assertEvalOK(2, 1, "1.0       ", formulaB3, "indirect");
        assertEvalOK(3, 1, "2.0       ", formulaB4, "indirect");
        assertEvalOK(2, 2, "1.0       ", formulaC3, "indirect");
        assertEvalOK(3, 2, "1.0       ", formulaC4, "indirect");
        grid.processCommand("A1 = 0");
        assertEvalOK(1, 0, "0.0       ", formulaA2, "direct");
        assertEvalError(2, 0, formulaA3, "direct");
        assertEvalError(3, 0, formulaA4, "indirect");
        assertEvalOK(2, 1, "0.0       ", formulaB3, "indirect");
        assertEvalOK(3, 1, "0.0       ", formulaB4, "indirect");
        assertEvalError(2, 2, formulaC3, "indirect");
        assertEvalError(3, 2, formulaC4, "indirect");
    }
}