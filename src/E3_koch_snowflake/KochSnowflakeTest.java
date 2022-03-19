package E3_koch_snowflake;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KochSnowflakeTest {

    @Test
    public void orderIsValidTest() {
        assertTrue(KochSnowflake.orderIsValid("1"));
        assertTrue(KochSnowflake.orderIsValid("5"));
        assertFalse(KochSnowflake.orderIsValid("a string"));
        assertFalse(KochSnowflake.orderIsValid(null));
        assertFalse(KochSnowflake.orderIsValid("0"));
        assertFalse(KochSnowflake.orderIsValid("-1"));
        assertTrue(KochSnowflake.orderIsValid("12"));
    }

}