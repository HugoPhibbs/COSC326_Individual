package E1_date_parser;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateParserTest {

    DateParser dateParser = new DateParser();

    @Test
    public void testExamples() {
        assertEquals("04 Jun 1992", dateParser.parseDate("4-6-92"));
        assertEquals("04 Jun 1992", dateParser.parseDate("04/06/1992"));
        assertEquals("03 Aug 1997", dateParser.parseDate("3 Aug 97"));
        assertEquals("12 Sep 1955", dateParser.parseDate("12-Sep-1955"));
    }

    @Test
    public void testIrrelevantInput() {
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper(null));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper(""));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("-1"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("Hugo"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("Hugo Thomas Phibbs"));
    }

    @Test
    public void testMultipleSeparatorsTypes() {
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 30/12/2001 "));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 30/12/2001    "));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("30/ 12/2001"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("30/12 /2001"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("30/12/ 2001"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("30/12/2 01"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("30/1 2/2001"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("11 - 11 - 11"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("-/2 3 97"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("2-3 97"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("2-3/97"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("2 3/97"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("2-3/97/"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("11-11- 11"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("4/-1/1886"));
        assertThrows(SeparatorTypesError.class, () -> dateParser.parseDateHelper("-4/1/1886"));
    }

    @Test
    public void testSeparatorCount() {
        assertThrows(SeparatorCountError.class, () -> dateParser.parseDateHelper("2 3  97"));
        assertThrows(SeparatorCountError.class, () -> dateParser.parseDateHelper("2/3//96"));
    }


    @Test
    public void testWhiteSpace() {
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper("2 3 97 "));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 2 3 97"));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 30/12/2001 "));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 30/12/2001    "));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper("30/12/2001 "));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 30/12/2001"));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper("2 3 97 "));
        assertThrows(WhiteSpaceError.class, () -> dateParser.parseDateHelper(" 2 3 97"));
    }

    @Test
    public void testNumSeparators(){
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("/23/22/"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("23/22"));
    }

    @Test
    public void testSeparators() {
        assertEquals("30 Dec 2001", dateParser.parseDate("30/12/2001"));
        assertEquals("30 Dec 2001", dateParser.parseDate("30 12 2001"));
        assertEquals("30 Dec 2001", dateParser.parseDate("30-12-2001"));

        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("30//12/2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("30/12/2001--"));
        assertThrows(NotIntegerError.class, () -> dateParser.parseDateHelper("30/12/20--"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("1212//2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("30/12/2001-"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("30//12-2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("30//122001"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("30122001"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("///////"));
        assertThrows(SeparatorError.class,() ->  dateParser.parseDateHelper("30//12/2001//"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("/23/22//"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("/23/22/"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("23/22"));
        assertThrows(ArgumentCountError.class, () -> dateParser.parseDateHelper("/23/22"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("--/23/22"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("30/12-2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("20   2001"));

        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("11 11 11 11"));
        assertThrows(SeparatorError.class, () -> dateParser.parseDateHelper("11 11"));
    }

    @Test
    public void testArgumentCount() {
        assertThrows(ArgumentCountError.class, () -> dateParser.parseDateHelper("11//11"));
        assertThrows(ArgumentCountError.class, () -> dateParser.parseDateHelper("/12/11"));
        assertThrows(ArgumentCountError.class, () -> dateParser.parseDateHelper("12/11/"));
    }

    @Test
    public void testDays() {
        assertThrows(NotIntegerError.class, () -> dateParser.parseDateHelper("no/12/2001"));
        assertThrows(NotIntegerError.class, () -> dateParser.parseDateHelper("8a/12/2001"));
        assertEquals("31 Dec 2001", dateParser.parseDate("31 12 2001"));
    }

    @Test
    public void testSmallDays(){
        assertEquals("09 Dec 2001", dateParser.parseDate("9/12/2001"));
        assertEquals("09 Dec 2001", dateParser.parseDate("09/12/2001"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("0/2/2001"));
        assertEquals("01 May 2002", dateParser.parseDate("1/5/2002"));
    }

    @Test
    public void testLargeDays() {
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("200/12/2001"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("99/12/2001"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("32/12/2001"));
    }

    @Test
    public void testDaysInMonth(){
        assertEquals("31 Dec 1999", dateParser.parseDate("31/12/99"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("31/11/88"));
    }

    @Test
    public void testYearRange(){
        assertEquals("01 Jan 1753", dateParser.parseDate("1/1/1753"));
        assertEquals("31 Dec 1753", dateParser.parseDate("31/12/1753"));
        assertEquals("31 Dec 3000", dateParser.parseDate("31/12/3000"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("31/12/1752"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("31/12/3001"));
    }

    @Test
    public void testLeapYears() {
        assertEquals("29 Feb 2004", dateParser.parseDate("29/2/2004"));
        assertEquals("29 Feb 2000", dateParser.parseDate("29/2/2000"));

        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("29/2/2003"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("29/2/1990"));
    }

    @Test
    public void testYears() {
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("01 01 001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("12/1/109"));
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("01 01 009900"));
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("01 01 9"));

        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("01 01 0099"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("01 01 0001"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("01 01 0840"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("01 01 0099"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("1 1 0011"));
    }

    @Test
    public void testMonths() {
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("12/201/2001"));
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("01/001/2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("01/000/2001"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("01/0000/2001"));
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("01/00001/2001"));
        assertThrows(LengthError.class, () -> dateParser.parseDateHelper("01/0012/2001"));
    }

    @Test
    public void testYy(){
        assertEquals("07 Oct 2001", dateParser.parseDate("7/10/01"));
        assertEquals("09 Apr 1999", dateParser.parseDate("9/4/99"));
        assertEquals("09 Apr 2000", dateParser.parseDate("9/4/00"));
        assertEquals("09 Apr 2022", dateParser.parseDate("9/4/22"));
        assertEquals("09 Apr 1963", dateParser.parseDate("9/4/63"));
        assertEquals("09 Apr 2049", dateParser.parseDate("9/4/49"));
        assertEquals("09 Apr 1950", dateParser.parseDate("9/4/50"));
    }

    @Test
    public void testMonthAbbrevs() {
        assertEquals("31 Jul 2001", dateParser.parseDate("31-Jul-2001"));
        assertEquals("06 Aug 2001", dateParser.parseDate("06 aug 2001"));
        assertEquals("02 Nov 2001", dateParser.parseDate("2 NOV 2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("2 N 2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("2 No 2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("2 Now 2001"));
        assertThrows(ParseError.class, () -> dateParser.parseDateHelper("2 july 2001"));
        assertEquals("30/DeC/2001 - INVALID: Abbreviated month is not valid! Must either be a length of 3 with uniform case or have it's first letter capitalized!", dateParser.parseDate("30/DeC/2001"));
        assertEquals("30/deC/2001 - INVALID: Abbreviated month is not valid! Must either be a length of 3 with uniform case or have it's first letter capitalized!", dateParser.parseDate("30/deC/2001"));
    }

    @Test
    public void testMm(){
        assertEquals("04 Sep 1886", dateParser.parseDateHelper("4/9/1886"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("4/90/1886"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("4/13/1886"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("4/00/1886"));
        assertThrows(RangeError.class, () -> dateParser.parseDateHelper("4/0/1886"));
    }
}