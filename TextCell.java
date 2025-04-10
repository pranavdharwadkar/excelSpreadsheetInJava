//*******************************************************
// DO NOT MODIFY THIS FILE!!!
//*******************************************************
/**
 * Cell interface
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextCell extends Cell
{
    private final String text;

    public TextCell(String text) {
        this.text = text;
    }
    @Override
    public String abbreviatedCellText() {
        if (text.length() > 10) {
            return text.substring(0, 10);
        } else {
            return String.format("%-10.10s", text);
        }
    }
    @Override
    public String fullCellText() {
        return "\"" + text + "\"";
    }

    public int compareTo(TextCell other) {
        return this.fullCellText().compareTo(other.fullCellText());
    }
    
}
