import java.util.ArrayList;
import java.util.List;

/**
 * Spreadsheet
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spreadsheet
{
    private final Cell[][] mySheet;
    public static int MAX_NUM_ROWS = 20;
    public static int MAX_NUM_COLUMNS = 12;

    public Spreadsheet()
    {
        mySheet = new Cell[MAX_NUM_ROWS][MAX_NUM_COLUMNS];
        for (int i = 0; i < MAX_NUM_ROWS; i++)
        {
            for (int j = 0; j < MAX_NUM_COLUMNS; j++)
            {
                mySheet[i][j] = new EmptyCell();
            }
        }
    }

    // Processes commands (inspects cells, assigns values, clears cells)
    public String processCommand(String command) 
    {
        String cellIdentifier;
        SpreadsheetLocation location;

        System.out.println("Processing command: " + command);

        String[] parts = command.split(" ", 3);

        if (parts.length == 0)
        {
            return "";
        }
        String cmd = parts[0];
        
        // Check if the command is a single word
        // Two choices when the command is a single word
        // 1. Inspect a cell
        // 2. Clear the entire sheet
        
        if (parts.length == 1) 
        {
            // Check clear command first
            if (command.equalsIgnoreCase("clear")) {
                // Clear the entire sheet
                for (int row = 0; row < MAX_NUM_ROWS; row++) {
                    for (int col = 0; col < MAX_NUM_COLUMNS; col++) {
                        mySheet[row][col] = new EmptyCell();
                    }
                }
                return getGridText();
            }

            if (command.equalsIgnoreCase("")) {
                return "";
            }
            // Now a single command probably means inspect a cell

            location = new SpreadsheetLocation(cmd);
            return getCell(location).fullCellText();
        }
        
        // Check if the command is a two-part command
        // If it is 2 part command, then the only possibility is to clear a specific cell
        if (parts.length == 2) 
        {
                
            // It is a 2 part command, so it must be a command to clear a specific cell
            // Double check if there is a clear command if not it is invalid command
            if (command.startsWith("clear")) {
                // It contains clear command, so clear the specific cell
                // Get the cell identifier
                cellIdentifier = command.split(" ")[1];
                location = new SpreadsheetLocation(cellIdentifier);
                mySheet[location.getRow()][location.getCol()] = new EmptyCell();
                return getGridText();
            } 
            
            if (cmd.equalsIgnoreCase("sorta") || cmd.equalsIgnoreCase("sortd")) {
                String range = parts[1];
                String[] corners = range.split("-");
                SpreadsheetLocation start = new SpreadsheetLocation(corners[0]);
                SpreadsheetLocation end = new SpreadsheetLocation(corners[1]);
            
                List<Cell> cells = getCellRange(start, end);
            
                // Determine if TextCell or RealCell sort
                boolean isText = cells.get(0) instanceof TextCell;
                boolean isReal = cells.get(0) instanceof RealCell;
            
                // You may assume they are all the same type per spec
                if (isText || isReal) {
                    bubbleSort(cells, cmd.equalsIgnoreCase("sorta"));
                    putCellsBack(cells, start, end);
                    return getGridText();
                } else {
                    return "Unsupported cell type for sorting.";
                }
            }

            return "Invalid command";
        }
        
        // Check if the command is a three-part command
        if (parts.length == 3) 
        {
            // It is a 3 part command, first possibility is it is an assignment to a cell
            // Check if it is a assignment indeed
            if (command.contains("=")) 
            {
                // It is an assign command. 
                // Check if it is a valid cell identifier
                cellIdentifier = parts[0];
                location = new SpreadsheetLocation(cellIdentifier);

                if (location.getRow() < 0 || location.getRow() >= MAX_NUM_ROWS || location.getCol() < 0 || location.getCol() >= MAX_NUM_COLUMNS) {
                    return "Invalid cell identifier";
                }

                String cellValue = parts[2].trim();
                // First check the type of cellValue, whether it is TextCell 
                // or Number Cell or Double Number cell or Percent Cell or 
                // Formula cell

                // Check if it is a formula cell by checking for starting and ending parentheses
                if (cellValue.startsWith("(") && cellValue.endsWith(")")) {
                    mySheet[location.getRow()][location.getCol()] = new FormulaCell(cellValue, this);
                    return getGridText();
                }

                // Check if it a percent cell by checking if it ends with a % sign
                if (cellValue.endsWith("%")) {
                    mySheet[location.getRow()][location.getCol()] = new PercentCell(cellValue);
                    return getGridText();
                }
                // Check if it is a Double Number cell
                // Try to parse as value
                try {
                    Double.valueOf(cellValue);
                    mySheet[location.getRow()][location.getCol()] = new ValueCell(cellValue);
                    return getGridText();
                } catch (NumberFormatException e) {
                    // It is not a double number cell
                    // Check if it a number cell
                    if (cellValue.matches("[0-9]+")) {
                        // It is a number cell
                        mySheet[location.getRow()][location.getCol()] = new ValueCell(cellValue);
                        return getGridText();
                    } else {
                    // Number format failed; try checking if it's text
                        if (cellValue.startsWith("\"") && cellValue.endsWith("\"")) 
                        {
                            cellValue = cellValue.substring(1, cellValue.length() - 1);
                            mySheet[location.getRow()][location.getCol()] = new TextCell(cellValue);
                            return getGridText();
                        }
                    }
                }
            }
        }
        // If it is not a valid command, return invalid command
        return "Invalid command";
    }

    public int getRows()
    {
        return MAX_NUM_ROWS;
    }

    public int getCols()
    {
        return MAX_NUM_COLUMNS;
    }

    public Cell getCell(Location loc)
    {
        return mySheet[loc.getRow()][loc.getCol()];
    }

    // Returns a formatted string of the entire spreadsheet
    public String getGridText() {
        StringBuilder sb = new StringBuilder();
        // Define a CellFormat for the cells, 10 characters wide, left aligned, and 
        //truncated to 10 characters if too long and padded with spaces if too short
        String CELLFORMAT = "%-10.10s";
        String FIRSTCOLUMNFORMAT = "%-3.3s";
        sb.append(String.format(FIRSTCOLUMNFORMAT," ")).append("|");
        
        // Add column headers (A-L)
        for (char c = 'A'; c <= 'L'; c++) {
            sb.append(String.format(CELLFORMAT,c)).append("|");
        }
        sb.append("\n");

        // Add rows 1 to 20
        for (int row = 0; row < MAX_NUM_ROWS; row++) {
            sb.append(String.format(FIRSTCOLUMNFORMAT,row + 1)).append("|");
            for (int col = 0; col < MAX_NUM_COLUMNS; col++) {
                //sb.append("");
                sb.append(mySheet[row][col].abbreviatedCellText());
                sb.append("|");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private List<Cell> getCellRange(SpreadsheetLocation start, SpreadsheetLocation end) 
    {
        List<Cell> cells = new ArrayList<>();
        for (int row = start.getRow(); row <= end.getRow(); row++) {
            for (int col = start.getCol(); col <= end.getCol(); col++) {
                cells.add(mySheet[row][col]);
            }
        }
        return cells;
    }

    private void bubbleSort(List<Cell> cells, boolean ascending) 
    {
        for (int i = 0; i < cells.size(); i++) {
            for (int j = 0; j < cells.size() - 1 - i; j++) {
                Cell a = cells.get(j);
                Cell b = cells.get(j + 1);
                int result = 0;
                if (a instanceof TextCell && b instanceof TextCell) {
                    result = ((TextCell) a).compareTo((TextCell) b);
                } else if (a instanceof RealCell && b instanceof RealCell) {
                    result = ((RealCell) a).compareTo((RealCell) b);
                }
                if ((ascending && result > 0) || (!ascending && result < 0)) {
                    cells.set(j, b);
                    cells.set(j + 1, a);
                }
            }
        }
    }
    
    private void putCellsBack(List<Cell> sorted, SpreadsheetLocation start, SpreadsheetLocation end) {
        int index = 0;
        for (int row = start.getRow(); row <= end.getRow(); row++) {
            for (int col = start.getCol(); col <= end.getCol(); col++) {
                mySheet[row][col] = sorted.get(index++);
            }
        }
    }
    

}
