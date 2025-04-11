import java.util.ArrayList;
import java.util.List;

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
        if (tokens[0].equalsIgnoreCase("SUM") || tokens[0].equalsIgnoreCase("AVG")) {
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

        if (tokens[0].equalsIgnoreCase("SUM")) {
            return sum;
        } else { // AVG
            return count == 0 ? 0 : sum / count;
        }
    }

    private List<String> parseArithmeticTokens(String formula) {
        List<String> tokens = new ArrayList<>();
        String[] rawTokens = formula.trim().split(" ");
        String operator = "+"; // default starting operator
    
        System.out.println("Parsing formula: " + formula);
        for (int i = 0; i < rawTokens.length; i++) {
            String token = rawTokens[i];
            System.out.println("Processing token: " + token);
    
            if (token.equals("+") || token.equals("-")) {
                // We found an operator, decide if we need to toggle sign
                operator = token;
                System.out.println("Found operator: " + operator);
    
                // Look ahead for consecutive operators like - -2 or - +3
                while (i + 1 < rawTokens.length &&
                       (rawTokens[i + 1].equals("+") || rawTokens[i + 1].equals("-"))) {
                    i++; // Skip the current operator
                    token = rawTokens[i];
    
                    // Toggle sign based on the number of consecutive signs
                    operator = operator.equals(token) ? "+" : "-";
                    System.out.println("Toggling operator: " + operator);
                }
            }
    
            // Process the next token (either a number or cell reference)
            if (i + 1 < rawTokens.length) {
                String nextToken = rawTokens[++i];
                System.out.println("Next token: " + nextToken);
    
                // Handle number token
                if (isNumber(nextToken)) {
                    String processedToken = operator.equals("-") ? "-" + nextToken : nextToken;
                    tokens.add(processedToken);
                    System.out.println("Added number: " + processedToken);
                } else {
                    // It's a cell reference (not a number)
                    tokens.add(operator);  // add the current operator
                    tokens.add(nextToken); // add the cell reference
                    System.out.println("Added cell reference: " + nextToken);
                }
            }
    
            operator = "+"; // Reset operator after each token
        }
    
        System.out.println("Tokens parsed: " + tokens);
        return tokens;
    }
    /*
    private double computeArithmeticFormula(String[] originalTokens) {
        List<String> tokens = parseArithmeticTokens(String.join(" ", originalTokens));
        double result = 0;
        String operator = "+";
    
        for (String token : tokens) {
            if (isOperator(token)) {
                operator = token;
            } else {
                double value;
    
                if (isNumber(token)) {
                    value = Double.parseDouble(token);
                } else {
                    SpreadsheetLocation location = new SpreadsheetLocation(token);
                    Cell cell = spreadsheet.getCell(location);
    
                    if (cell instanceof RealCell realCell) {
                        value = realCell.getDoubleValue();
                    } else {
                        value = 0;
                    }
                }
    
                result = applyOperator(result, value, operator);
            }
        }
    
        return result;
    }
    */
    private double computeArithmeticFormula(String[] originalTokens) {
        // Remove parentheses and trim
        String formula = String.join(" ", originalTokens)
                            .replace("(", "")
                            .replace(")", "")
                            .trim();
        String[] tokens = formula.split(" ");
    
        if (tokens.length == 0) return 0;
    
        double result = getValue(tokens[0]);
    
        for (int i = 1; i < tokens.length - 1; i += 2) {
            String operator = tokens[i];
            double nextValue = getValue(tokens[i + 1]);
    
            result = applyOperator(result, nextValue, operator);
        }
    
        return result;
    }
    
    private double getValue(String token) {
        token = token.trim();
        if (isNumber(token)) {
            return Double.parseDouble(token);
        } else {
            SpreadsheetLocation location = new SpreadsheetLocation(token);
            Cell cell = spreadsheet.getCell(location);
            if (cell instanceof RealCell realCell) {
                return realCell.getDoubleValue();
            }
        }
        return 0; // fallback
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

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
