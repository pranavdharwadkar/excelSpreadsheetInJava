
//*******************************************************
// DO NOT MODIFY THIS FILE!!!
//*******************************************************
/**
 * Cell interface
 * 
 * @author (your name)
 * @version (a version number or a date)
 */

public abstract class RealCell extends Cell {
    protected String originalValue;
    
    public RealCell(String value) {
        this.originalValue = value;
    }
    
    public abstract double getDoubleValue();
    
    @Override
    public String fullCellText() {
        return originalValue;
    }
}