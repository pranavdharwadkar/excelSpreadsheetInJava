
//*******************************************************
// DO NOT MODIFY THIS FILE!!!
//*******************************************************
/**
 * Cell interface
 * 
 * @author (your name)
 * @version (a version number or a date)
 */

 public class ValueCell extends RealCell {
    public ValueCell(String value) {
        super(value);
    }
    
    @Override
    public double getDoubleValue() {
        return Double.parseDouble(originalValue);
    }
    
    @Override
    public String abbreviatedCellText() {
        return String.format("%-10.10s", getDoubleValue());
    }
}