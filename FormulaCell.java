public class FormulaCell extends RealCell {
    private final String formula;
    private final Spreadsheet spreadsheet;



    public FormulaCell(String formula, Spreadsheet spreadsheet) {
        super(formula); // Call the RealCell constructor with the formula
        this.formula = formula.trim();
        this.spreadsheet = spreadsheet;
    }

    @Override
    public String abbreviatedCellText() {
        double value = getDoubleValue();
        String text = value + "                    "; // Add extra spaces
        return text.substring(0, 10); // Truncate
    }

    @Override
    public String fullCellText() {
        return "(" + formula + ")";
    }

    @Override
    public double getDoubleValue() {
        String expr = formula.substring(1, formula.length() - 1).trim(); // remove outer parens
        String[] tokens = expr.split(" ");

        // Handle Method Formulas: SUM or AVG
        if (tokens[0].equals("SUM") || tokens[0].equals("AVG")) {
            return computeMethodFormula(tokens);
        } else {
            return computeArithmeticFormula(tokens);
        }
    }
    private double computeMethodFormula(String[] tokens) {
        double sum = 0;
        int count = 0;

        for (int i = 1; i < tokens.length; i++) {
            String cellIdentifier = tokens[i];
    
            SpreadsheetLocation location = new SpreadsheetLocation(cellIdentifier);
            Cell cell = spreadsheet.getCell(location);
            if (cell != null) {
                double value = 0;
                if (cell instanceof RealCell realCell) {
                    value = realCell.getDoubleValue();
                }
                sum += value;
                count++;
            }
        }

        if (tokens[0].equals("SUM")) {
            return sum;
        } else { // AVG
            return count == 0 ? 0 : sum / count;
        }
    }

    private double computeArithmeticFormula(String[] tokens) {
        double result = 0;
        String operator = "+";

        for (String token : tokens) {
            if (isOperator(token)) {
                operator = token;
            } else {
                SpreadsheetLocation location = new SpreadsheetLocation(token);
                Cell cell = spreadsheet.getCell(location);
                // Check if the cell is not null and is a RealCell
                if (cell != null) {
                    double value = 0;
                    if (cell instanceof RealCell realCell) {
                        value = realCell.getDoubleValue();
                    }
                    result = applyOperator(result, value, operator);
                }
            }
        }

        return result;
    }
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
    private double applyOperator(double result, double value, String operator) {
        return switch (operator) {
            case "+" -> result + value;
            case "-" -> result - value;
            case "*" -> result * value;
            case "/" -> result / value;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
    @Override
    public String toString() {
        return "FormulaCell{" +
                "formula='" + formula + '\'' +
                ", spreadsheet=" + spreadsheet +
                '}';
    }
    /*
    public String getText() {
        return formula;
    }
    */
    
}
