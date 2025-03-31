 
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * C final test
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class CFinal
{
    private Spreadsheet grid;
    
    /**
     * C test
     */
    @Before
    public void initializeGrid()
    {
        grid = new Spreadsheet();
    }
    
    /**
     * C test
     */
    private static String getCellName(int row, int col)
    {
        return "" + (Character.toString((char) ('A' + col))) + (row + 1);
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaCol() 
    {
        grid.processCommand("A1 = 19.1");
        grid.processCommand("A2 = 2.1");
        grid.processCommand("A3 = 607.1");
        grid.processCommand("A4 = 0.01");

        grid.processCommand("soRTa A1-A4");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals("2.1",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
        assertEquals("19.1",
            grid.getCell(new TestsALL.TestLocation(2, 0)).fullCellText());
        assertEquals("607.1",
            grid.getCell(new TestsALL.TestLocation(3, 0)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaRow()
    {
        grid.processCommand("A1 = 19.2");
        grid.processCommand("B1 = 2.2");
        grid.processCommand("C1 = 607.2");
        grid.processCommand("D1 = 0.01");

        grid.processCommand("soRTa A1-D1");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals("2.2",
            grid.getCell(new TestsALL.TestLocation(0, 1)).fullCellText());
        assertEquals("19.2",
            grid.getCell(new TestsALL.TestLocation(0, 2)).fullCellText());
        assertEquals("607.2",
            grid.getCell(new TestsALL.TestLocation(0, 3)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaRowExtraValues() 
    {

        grid.processCommand("A1 = 19.4");
        grid.processCommand("B1 = 2.4");
        grid.processCommand("C1 = 607.4");
        grid.processCommand("D1 = 0.01");
        grid.processCommand("E1 = 17.4");
        grid.processCommand("A2 = 3.14159");
        grid.processCommand("B2 = \"extras!\"");

        grid.processCommand("soRTa A1-D1");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals( "2.4",
            grid.getCell(new TestsALL.TestLocation(0, 1)).fullCellText());
        assertEquals("19.4",
            grid.getCell(new TestsALL.TestLocation(0, 2)).fullCellText());
        assertEquals("607.4",
            grid.getCell(new TestsALL.TestLocation(0, 3)).fullCellText());

        assertEquals("17.4",
            grid.getCell(new TestsALL.TestLocation(0, 4)).fullCellText());
        assertEquals("3.14159",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
        assertEquals("\"extras!\"",
            grid.getCell(new TestsALL.TestLocation(1, 1)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaTwoDimensionalOffset() 
    {

        grid.processCommand("C3 = 19.5");
        grid.processCommand("C4 = 2.5");
        grid.processCommand("D3 = 607.5");
        grid.processCommand("D4 = 0.01");
        grid.processCommand("E1 = 17.5");
        grid.processCommand("A2 = 3.14159");

        grid.processCommand("soRTa C3-D4");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(2, 2)).fullCellText());
        assertEquals("2.5",
            grid.getCell(new TestsALL.TestLocation(2, 3)).fullCellText());
        assertEquals("19.5",
            grid.getCell(new TestsALL.TestLocation(3, 2)).fullCellText());
        assertEquals("607.5",
            grid.getCell(new TestsALL.TestLocation(3, 3)).fullCellText());

        assertEquals("17.5",
            grid.getCell(new TestsALL.TestLocation(0, 4)).fullCellText());
        assertEquals("3.14159",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
    }
    
    /**
     * C test
     */
    @Test
    public void testSortaTwoDimensional() 
    {

        grid.processCommand("A1 = 19.3");
        grid.processCommand("A2 = 2.3");
        grid.processCommand("B1 = 607.3");
        grid.processCommand("B2 = 0.01");

        grid.processCommand("SoRTa A1-B2");

        assertEquals("0.01",
            grid.getCell(new TestsALL.TestLocation(0, 0)).fullCellText());
        assertEquals("2.3",
            grid.getCell(new TestsALL.TestLocation(0, 1)).fullCellText());
        assertEquals("19.3",
            grid.getCell(new TestsALL.TestLocation(1, 0)).fullCellText());
        assertEquals("607.3",
            grid.getCell(new TestsALL.TestLocation(1, 1)).fullCellText());
    }   
    
    @Test
    public void testSortdCol() 
    {
        grid.processCommand("A1 = 19.2");
        grid.processCommand("A2 = 2.2");
        grid.processCommand("A3 = 607.2");
        grid.processCommand("A4 = 0.01");

        grid.processCommand("SoRTd A1-A4");

        assertEquals("0.01", grid.getCell(new TestsALL.TestLocation(3,0)).fullCellText());
        assertEquals("2.2", grid.getCell(new TestsALL.TestLocation(2,0)).fullCellText());
        assertEquals("19.2", grid.getCell(new TestsALL.TestLocation(1,0)).fullCellText());
        assertEquals("607.2", grid.getCell(new TestsALL.TestLocation(0,0)).fullCellText());
    }

    @Test
    public void testSortdMultidigit2DValue() 
    {
        grid.processCommand("A9 = -13.2");
        grid.processCommand("A10 = 19.2");
        grid.processCommand("A11 = 2.2");
        grid.processCommand("A12 = 607.1");
        grid.processCommand("A13 = 0.01");
        grid.processCommand("B9 = 88.2");
        grid.processCommand("B10 = -190.1");
        grid.processCommand("B11 = 1.2");
        grid.processCommand("B12 = 607.2");
        grid.processCommand("B13 = -0.02");

        grid.processCommand("SoRTd A9-B13");

        String[] sortedVals = { "-190.1", "-13.2", "-0.02", "0.01", "1.2", "2.2", "19.2", "88.2", "607.1", "607.2" };
        int iSortedVals = 0;

        for (int row = 12; row >= 8; row--)
        {
            for (int col = 1; col >= 0; col--)
            {
                assertEquals("Inspecting cell " + getCellName(row, col), sortedVals[iSortedVals++], grid.getCell(new TestsALL.TestLocation(row,col)).fullCellText());
            }
        }
    }  

    @Test
    public void testSortDMultiType2D()
    {
        grid.processCommand("A9 = -13.2");
        grid.processCommand("A10 = 19.2");
        grid.processCommand("A11 = 2.2");
        grid.processCommand("A12 = 60710%");
        grid.processCommand("A13 = 1%");
        grid.processCommand("B9 = 88.2");
        grid.processCommand("B10 = -190.1");
        grid.processCommand("B11 = 101%");
        grid.processCommand("B12 = 607.2");
        grid.processCommand("B13 = -0.02");

        grid.processCommand("SoRTd A9-B13");

        String[] sortedVals = { "-190.1", "-13.2", "-0.02", "0.01", "1.01", "2.2", "19.2", "88.2", "607.1", "607.2" };
        int iSortedVals = 0;

        for (int row = 12; row >= 8; row--)
        {
            for (int col = 1; col >= 0; col--)
            {
                assertEquals("Inspecting cell " + getCellName(row, col), sortedVals[iSortedVals++], grid.getCell(new TestsALL.TestLocation(row,col)).fullCellText());
            }
        }
    }

    @Test
    public void testSortMultiType2DMultipleTimes()
    {
        grid.processCommand("A9 = -13.2");
        grid.processCommand("A10 = 19.2");
        grid.processCommand("A11 = 2.2");
        grid.processCommand("A12 = 60710%");
        grid.processCommand("A13 = 1%");
        grid.processCommand("B9 = 88.2");
        grid.processCommand("B10 = -190.1");
        grid.processCommand("B11 = 101%");
        grid.processCommand("B12 = 607.2");
        grid.processCommand("B13 = -0.02");

        grid.processCommand("SoRTa A11-B12");
        grid.processCommand("SoRTd A12-B13");
        grid.processCommand("sorta A9-B10");
        grid.processCommand("SoRTd A9-B13");

        String[] sortedVals = { "-190.1", "-13.2", "-0.02", "0.01", "1.01", "2.2", "19.2", "88.2", "607.1", "607.2" };
        int iSortedVals = 0;

        for (int row = 12; row >= 8; row--)
        {
            for (int col = 1; col >= 0; col--)
            {
                assertEquals("Inspecting cell " + getCellName(row, col), sortedVals[iSortedVals++], grid.getCell(new TestsALL.TestLocation(row,col)).fullCellText());
            }
        }
    } 
}
