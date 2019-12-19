# Postfix expression evaluator for CSV files

## To run:

1. Clone this repository.

2. Use the following command at the commandline to enter the directory named "evaluator" and build the java file:

   ### \$ cd evaluator && find . -name ‘\*.java’ | xargs javac

3. Run the PostFix class on the default csv file provided (SampleCSVFile.csv):

   ### \$ java PostFix

4. Or, run the PostFix class on your own csv file:

   ### \$ java PostFix YourInputCSVFile.csv

5. Check the directory for an output csv file named OutputCSVFile.csv:

   ### \$ test -f OutputCSVFile.csv && echo "File found"
