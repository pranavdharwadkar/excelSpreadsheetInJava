
import java.util.Scanner;

// Update this file with your own code.

/**
 * Text version of excel
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TextExcel
{

    /**
     * Runs the text excel.
     * 
     * @param args Not used
     */
    public static void main(String[] args)
    {
        Spreadsheet sheet = new Spreadsheet();

        System.out.println(sheet.getGridText());

        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        while (!inputLine.equalsIgnoreCase("quit"))
        {
            String outputLine = sheet.processCommand(inputLine);
            System.out.println(outputLine);
            inputLine = scanner.nextLine();
        }
        scanner.close();
    }
}
