//*******************************************************
// DO NOT MODIFY THIS FILE!!!
//*******************************************************
/**
 * Cell interface
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class PercentCell extends ValueCell {
    public PercentCell(String value) {
        super(value.substring(0, value.length() - 1));
    }
    
    @Override
    public double getDoubleValue() {
        return super.getDoubleValue() / 100;
    }
    
    @Override
    public String fullCellText() {
        return String.valueOf(getDoubleValue());
    }
    
    @Override
    public String abbreviatedCellText() {
        return String.format("%-10.10s", (int) super.getDoubleValue() + "%");
    }
}