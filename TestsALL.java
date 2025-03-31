
//*******************************************************
//DO NOT MODIFY THIS FILE!!!
//*******************************************************

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ACheckpoint1.class,
    ACheckpoint2.class,
    ACheckpoint3.class,
    BCheckpoint1.class,
    BFinal.class,
    CCheckpoint1.class,
    CFinal.class
})

public class TestsALL
{
    /**
     * Test Location
     */
    public static class TestLocation extends Location
    {
        // Simple implementation of Location interface for use only by tests.
        private int row;
        private int col;

        /**
         * Contruct location
         * 
         * @param row row
         * @param col col
         */
        public TestLocation(int row, int col)
        {
            this.row = row;
            this.col = col;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getCol() {
            return col;
        }
    }

    /**
     * Helper
     */
    public static class Helper
    {
        // For use only by test code, which uses it carefully.
        private String[][] items;

        /**
         * Constructor
         */
        public Helper()
        {
            items = new String[20][12];
            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 12; j++)
                    items[i][j] = format("");
        }

        /**
         * Format string
         * 
         * @param s string
         * @return formatted string
         */
        public static String format(String s)
        {
            return String.format(String.format("%%-%d.%ds", 10, 10),  s);
        }

        /**
         * Set item
         * 
         * @param row row
         * @param col col
         * @param text text
         */
        public void setItem(int row, int col, String text)
        {
            items[row][col] = format(text);
        }

        
        /**
         * Get text
         * 
         * @return text
         */
        public String getText()
        {
            String ret = "   |";
            for (int j = 0; j < 12; j++)
                ret = ret + format(Character.toString((char)('A' + j))) + "|";
            ret = ret + "\n";
            for (int i = 0; i < 20; i++)
            {
                ret += String.format("%-3d|", i + 1);
                for (int j = 0; j < 12; j++)
                {
                    ret += items[i][j] + "|";
                }
                ret += "\n";
            }
            return ret;
        }
    }
}
