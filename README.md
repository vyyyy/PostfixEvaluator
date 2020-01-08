# Postfix expression evaluator for CSV files

## To run:

1. Clone this repository.

2. Navigate to the directory named "PostfixEvaluator":

   #### \$ cd PostfixEvaluator

3. Navigate to the directory named "src" and build the java class:

   #### \$ cd src && find . -name ‘\*.java’ | xargs javac

4. Run the Main class on the default csv file provided (SampleCSVFile.csv):

   #### \$ java postfixeval.Main

5. Or, run the Main class on your own csv file:

   #### \$ java postfixeval.Main YourInputCSVFile.csv

6. Check the directory for an output csv file named OutputCSVFile.csv:

   #### \$ test -f OutputCSVFile.csv && echo "File found"
