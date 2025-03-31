
//*******************************************************
// DO NOT MODIFY THIS FILE!!!
//*******************************************************
/**
 * Cell interface
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EmptyCell extends Cell
{
    String CELLFORMAT = "%-10.10s";

    /**
     * onstructor for EmptyCell, calling the constructor of the superclass Cell
     * 
     */
    public EmptyCell() {
        super();
    }

    @Override
    /**
     * EmptyCell represents a cell that is empty, so it does not hold any value
     * and its display text is empty.
     * @return string
     */
    public String abbreviatedCellText() {
        return String.format(CELLFORMAT, ""); // Empty cells display the empty string
    }

    @Override
    public String fullCellText() {
        return String.format(CELLFORMAT, ""); // Empty cells also show nothing in full cell text
    }
}
