# COSC326 Dates

# How to Use
- Ensure all classes contained in this package are in the same directory
- use javac App.java to compile 

# Test Cases
- I put all my tests into a JUnit test class that I have included bellow
- This uses JUnit 5.7

```java
package date_parser;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateParserTest {

    DateParser dateParser = new DateParser();

    @Test
    public void testIrrelevantInput() {
        assertThrows(AssertionError.class, () -> {
            dateParser.parseDate(null);
        });
        assertEquals(" - INVALID: Date does not follow a valid separator scheme!", dateParser.parseDate(""));
        assertEquals("-1 - INVALID: Date does not follow a valid separator scheme!", dateParser.parseDate("-1"));
    }


    @Test
    public void testSeparators() {
        assertEquals("30 Dec 2001", dateParser.parseDate("30/12/2001"));
        assertEquals("30 Dec 2001", dateParser.parseDate("30 12 2001"));
        assertEquals("30 Dec 2001", dateParser.parseDate("30-12-2001"));

        // Check with mixed
        assertEquals("30/12-2001 - INVALID: Date does not follow a valid separator scheme!", dateParser.parseDate("30/12-2001"));
    }

    @Test
    public void testSmallDays() {
        assertEquals("09 Dec 2001", dateParser.parseDate("9/12/2001"));
        assertEquals("09 Dec 2001", dateParser.parseDate("09/12/2001"));
        assertEquals("0/2/2001 - INVALID: Day cannot be less than or equal to 0!", dateParser.parseDate("0/2/2001"));
        assertEquals("01 May 2002", dateParser.parseDate("1/5/2002"));
    }

    @Test
    public void testDaysInMonth() {
        assertEquals("31 Dec 1999", dateParser.parseDate("31/12/99"));
        assertEquals("31/11/88 - INVALID: Day is too large for this month and year!", dateParser.parseDate("31/11/88"));
    }

    @Test
    public void testYearRange() {
        assertEquals("01 Jan 1753", dateParser.parseDate("1/1/1753"));
        assertEquals("31 Dec 1753", dateParser.parseDate("31/12/1753"));
        assertEquals("31 Dec 3000", dateParser.parseDate("31/12/3000"));
        assertEquals("31/12/1752 - INVALID: Year is not in range! Needs to be between 1753 and 3000!", dateParser.parseDate("31/12/1752"));
        assertEquals("31/12/3001 - INVALID: Year is not in range! Needs to be between 1753 and 3000!", dateParser.parseDate("31/12/3001"));
    }

    @Test
    public void testLeapYears() {
        assertEquals("29 Feb 2004", dateParser.parseDate("29/2/2004"));
        assertEquals("29/2/2003 - INVALID: Day is too large for this month and year!", dateParser.parseDate("29/2/2003"));
        assertEquals("29 Feb 2000", dateParser.parseDate("29/2/2000"));
        assertEquals("29/2/1990 - INVALID: Day is too large for this month and year!", dateParser.parseDate("29/2/1990"));
    }

    @Test
    public void testYy() {
        assertEquals("07 Oct 2001", dateParser.parseDate("7/10/01"));
        assertEquals("09 Apr 1999", dateParser.parseDate("9/4/99"));
        assertEquals("09 Apr 2000", dateParser.parseDate("9/4/00"));
        assertEquals("09 Apr 2022", dateParser.parseDate("9/4/22"));
        assertEquals("09 Apr 1923", dateParser.parseDate("9/4/23"));
    }

    @Test
    public void testMonthAbbrevs() {
        assertEquals("31 Jul 2001", dateParser.parseDate("31-Jul-2001"));
        assertEquals("06 Aug 2001", dateParser.parseDate("06 aug 2001"));
        assertEquals("02 Nov 2001", dateParser.parseDate("2 NOV 2001"));
        assertEquals("30/DeC/2001 - INVALID: Abbreviated month is not valid! Must either be a length of 3 with uniform case or have it's first letter capitalized!", dateParser.parseDate("30/DeC/2001"));
        assertEquals("30/deC/2001 - INVALID: Abbreviated month is not valid! Must either be a length of 3 with uniform case or have it's first letter capitalized!", dateParser.parseDate("30/deC/2001"));
    }

    @Test
    public void testMm() {
        assertEquals("04 Sep 1886", dateParser.parseDate("4/9/1886"));
        assertEquals("4/90/1886 - INVALID: Integer for month is not in range! Needs to be between 1 and 12 (inclusive)", dateParser.parseDate("4/90/1886"));
        assertEquals("4/13/1886 - INVALID: Integer for month is not in range! Needs to be between 1 and 12 (inclusive)", dateParser.parseDate("4/13/1886"));
        assertEquals("4/00/1886 - INVALID: Integer for month is not in range! Needs to be between 1 and 12 (inclusive)", dateParser.parseDate("4/00/1886"));
        assertEquals("4/-1/1886 - INVALID: Integer for month is not in range! Needs to be between 1 and 12 (inclusive)", dateParser.parseDate("4/-1/1886"));
    }
}
```