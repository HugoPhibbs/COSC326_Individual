# Koch Snowflake drawer

## Running the Program
- The entry point into this program is contained in KochSnowflake

### Using Command Line
- Navigate to the package containing via command line containing the code.
- To compile, type and enter:
```bash
javac KochSnowflake.java
```
- Then to run type and enter:
```bash
java KochSnowFlake
```

### Using JAR file
- Navigate to the package containing via command line containing the code.
- To run, enter:
```bash
java -jar KochSnowflake.jar
```

### Using an IDE
- If you are running from an IDE (completely normal) just click run on KochSnowFlake.java

## Using the program
- Follow the prompts to enter the order of Koch snowflake that you would like. A JFrame
window should then open up with the drawn Snowflake
- **Beware**: Program may fail with large inputs for the order of the snowflake, since this was
implemented using recursion, not iteration. 

## Tests
- I just did integration tests manually. Checking with a range of input, it only works for integers greater than 0, which is desired. 
- I don't think individual unit tests are necessary due limited behaviour of this program. 