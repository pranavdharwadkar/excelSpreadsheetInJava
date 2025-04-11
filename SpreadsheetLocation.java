
//Update this file with your own code.
/**
 * Represents a location in the spreadsheet
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SpreadsheetLocation extends Location
{
    private final int rowNumber;
    private final int colNumber;

    @Override
    public int getRow()
    {
        return this.rowNumber;
    }

    @Override
    public int getCol()
    {
        return this.colNumber;
    }

    /**
     * Constructor
     * 
     * @param cellName Name of cell, like "A1"
     */
    public SpreadsheetLocation(String cellName) 
    {
        
        char columnLetter = cellName.charAt(0);
        this.colNumber = (Character.toUpperCase(columnLetter) - 'A');

        // The row number (e.g., "1" for row 0, "2" for row 1, ...)
        this.rowNumber = Integer.parseInt(cellName.substring(1)) -1;
    }

    public SpreadsheetLocation(int row, int col) {
        this.rowNumber = row;
        this.colNumber = col;
    }
    
}
