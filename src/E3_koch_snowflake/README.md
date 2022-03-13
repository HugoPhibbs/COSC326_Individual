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

### Using an IDE
- If you are running from an IDE (completely normal) just click run on KochSnowFlake.java

## Using the program
- Follow the prompts to enter the order of Koch snowflake that you would like. A JFrame
window should then open up with the drawn Snowflake
- **Beware**: Program may fail with large inputs for the order of the snowflake, since this was
implemented using recursion, not iteration.

## Tests
- I ran a short number of tests with JUnit, these can be found bellow and in the KocSnowflakeTest class
- Due to the limited functionality of this program, I wasn't able to test all that much
```java
package E3_koch_snowflake;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KochSnowflakeTest {

    KochSnowflake kochSnowflake = new KochSnowflake();

    @Test
    public void orderIsValidTest() {
        assertTrue(kochSnowflake.orderIsValid("1"));
        assertTrue(kochSnowflake.orderIsValid("5"));
        assertFalse(kochSnowflake.orderIsValid("a string"));
        assertFalse(kochSnowflake.orderIsValid(null));
        assertFalse(kochSnowflake.orderIsValid("0"));
        assertFalse(kochSnowflake.orderIsValid("-1"));
        assertTrue(kochSnowflake.orderIsValid("12"));
    }
}
```

### Libraries used
- Apart from the regular java libraries that are used, of particular interest is java.awt.geom, this gave me support for geometric objects which were
useful for drawing the snowflake within a JPanel
- For testing, I used JUnit 5.7
- For graphical output I used Java Swing.