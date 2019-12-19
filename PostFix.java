import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.lang.Math;

/**
 * The Postfix program evaluates postfix expressions in a given CSV file and
 * outputs the results on the screen. Additionally, it saves a CSV file
 * containing the evaluated postfix expressions in the same dimensions of the
 * input CSV file.
 * 
 * @author Vyoma Patel
 * @version 1.0.1
 * @since 2019-12-19
 */
public class PostFix {
    public static void main(String[] args) {

        // Input file which needs to be parsed
        String fileToParse = "SampleCSVFile.csv";
        if (args.length > 0) {
            fileToParse = args[0];
        }

        // Delimiter used in CSV file
        final String DELIMITER = ",";

        // LinkedHashmap to store cell references similar to a spreadsheet
        LinkedHashMap<String, String> mapping = new LinkedHashMap<String, String>();

        // List to collect postfix expressions, which is saved to output csv file
        List<String> collectTokens = new ArrayList<String>();

        try {
            // Load the file for reading and parsing
            Scanner fileReader = new Scanner(new File(fileToParse));

            // Initialise rows in a csv file
            int Rows = 1;

            // Read the file line by line
            while (fileReader.hasNext()) {
                // Get all tokens available in line
                String[] tokens = fileReader.nextLine().split(DELIMITER);

                // Initialise columns in a csv file
                int Cols = 1;

                for (String token : tokens) {
                    // Add token to scanner
                    Scanner tokenScanner = new Scanner(token);
                    // Run postfix evaluation on token if it's valid
                    if (tokenScanner.hasNextInt()) {
                        int result = evaluatePostfix(token);
                        // Add postfix evaluation of token to a list
                        collectTokens.add(String.valueOf((int) result));
                        // Add cell reference and token to linkedhashmap
                        mapping.put(columnToLetter(Cols) + String.valueOf(Rows), String.valueOf(result));
                        // Print to STDOUT
                        System.out.println(result);
                    } else {
                        // The token cannot be evaluated as a postfix expression
                        collectTokens.add("#ERR");
                        // Add cell reference and token to linkedhashmap
                        mapping.put(columnToLetter(Cols) + String.valueOf(Rows), "#ERR");
                        // Print to STDOUT
                        System.out.println("#ERR");
                    }
                    tokenScanner.close();
                    // Keep track of columns
                    Cols++;
                }
                // Add a newline after each line is read for output csv file
                collectTokens.add(System.lineSeparator());
                // Keep track of rows
                Rows++;
            }
            // Close the file
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save output to csv file in current working directory
        try {
            outputCSVFile(collectTokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // @TODO: remove STDOUT
        System.out.println(mapping);
    }

    /**
     * Evaluate postfix expression
     * 
     * @param expression The postfix expression to evaluate in a given cell
     * @return The evaluated postfix expression of a given cell
     */
    public static int evaluatePostfix(String expression) {

        Scanner scanner = new Scanner(expression);
        Stack<Integer> stack = new Stack<Integer>();

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                stack.push(scanner.nextInt());
            } else {
                int b = stack.pop();
                int a = stack.pop();
                char operator = scanner.next().charAt(0);
                switch (operator) {
                case '+':
                    stack.push(a + b);
                    break;

                case '-':
                    stack.push(a - b);
                    break;

                case '*':
                    stack.push(a * b);
                    break;

                case '/':
                    stack.push(a / b);
                    break;

                case '%':
                    stack.push(a % b);
                    break;

                case '^':
                    stack.push((int) Math.pow(a, b));
                    break;
                }
            }
        }
        scanner.close();
        return stack.pop();
    }

    /**
     * Convert a column in a CSV file into Excel column
     * 
     * @param columnNumber The column's position in a CSV file
     * @return The letter corresponding to the column position
     */
    public static String columnToLetter(int columnNumber) {
        // Store result
        StringBuilder columnName = new StringBuilder();

        while (columnNumber > 0) {
            // Find remainder
            int remainder = columnNumber % 26;
            // If remainder is 0
            if (remainder == 0) {
                columnName.append("Z");
                columnNumber = (columnNumber / 26) - 1;
            } else {
                // If remainder is non-zero
                columnName.append((char) ((remainder - 1) + 'A'));
                columnNumber = columnNumber / 26;
            }
        }
        // Convert to string, make it lowercase and reverse it
        return columnName.reverse().toString().toLowerCase();
    }

    /**
     * Outputs a CSV file containing evaluated postfix expressions
     * 
     * @param collectList The list of evaluated postfix expressions
     * @throws IOException
     */
    public static void outputCSVFile(List<String> collectlist) throws IOException {
        FileWriter csvWriter = new FileWriter("OutputCSVFile.csv");
        for (String element : collectlist) {
            csvWriter.write(element + ",");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
