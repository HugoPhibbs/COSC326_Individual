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