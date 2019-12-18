import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Stack;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class PostFix {
    public static void main(String[] args) {
        // Input file which needs to be parsed
        String fileToParse = "SampleCSVFile.csv";
        if (args.length > 0) {
            fileToParse = args[0];
        }
        BufferedReader fileReader = null;

        // Delimiter used in CSV file
        final String DELIMITER = ",";

        // List to collect postfix expressions, which is saved to output csv file
        List<String> collectTokens = new ArrayList<String>();

        try {
            String line = "";
            // Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            // Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                // Get all tokens available in line
                String[] tokens = line.split(DELIMITER);

                for (String token : tokens) {
                    // Add token to scanner
                    Scanner tokenScanner = new Scanner(token);
                        // Run postfix evaluation on token if it's valid 
                        if (tokenScanner.hasNextDouble()) {
                            double result = evaluatePostfix(token);
                            // Print to STDOUT
                            System.out.println(result);
                            // Add postfix evaluation of token to a list
                            collectTokens.add(String.valueOf(result));
                        } else {
                            // The token cannot be evaluated as a postfix expression
                            collectTokens.add("#ERR");
                        }
                    tokenScanner.close();
                }
                // Add a newline after each line is read for output csv file
                collectTokens.add(System.lineSeparator());
            }
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
    }

    // Evaluate postfix expression
    static double evaluatePostfix(String expression) {

        Scanner scanner = new Scanner(expression);
        Stack<Double> stack = new Stack<Double>();

        while (scanner.hasNext()) {

            if (scanner.hasNextDouble()) {
                stack.push(scanner.nextDouble());
            } else {
                double b = stack.pop();
                double a = stack.pop();
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
                    stack.push(Math.pow(a, b));
                    break;
                }
            }
        }
        scanner.close();
        return stack.pop();
    }

    // Output csv file
    public static void outputCSVFile(List<String> collectlist) throws IOException {
        FileWriter csvWriter = new FileWriter("OutputCSVFile.csv");
        for (String element : collectlist) {
            csvWriter.write(element);
            csvWriter.write(", ");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
