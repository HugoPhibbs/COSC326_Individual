package date_parser;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

/**
 * Class to parse inputted Dates from a user
 *
 * @author Hugo Phibbs
 * @since 4/3/22
 */
public class DateParser {

    /** ArrayList of Strings containing 3 letter abbreviations for each month of the
     * Credit to https://stackoverflow.com/questions/1005073/initialization-of-an-arraylist-in-one-line for this
     */
    private final ArrayList<String> months = new ArrayList<String>(
            Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));

    /**
     * Parses an inputted date from a user
     *
     * If the date is valid, then it prints it out in a readable format. Otherwise, it prints the inputted date
     * along with a why the inputted date is invalid
     *
     * @param date String for a date
     * @return String for the inputted date converted into a readable format
     */
    public String parseDate(String date) {
        assert date != null: "Date cannot be null!";
        try {
            HashMap<String, String> splitDateMap = this.splitDate(date);
            String year = this.parseYear(splitDateMap.get("Year"));
            String month = this.parseMonth(splitDateMap.get("Month"));
            String day = this.parseDay(splitDateMap.get("Day"), month, Integer.parseInt(year));
            return String.format("%s %s %s", day, month, year);
        }
        catch (ParseError pe) {
            return String.format("%s%s", date, pe);
        }
    }

    /**
     * Finds if a split up date's length is valid or not.
     *
     * @param splitDate array of a split date
     * @return boolean as described
     */
    private boolean splitDateLengthIsValid(String[] splitDate) {
        return splitDate.length == 3;
    }

    /**
     * Splits a date according to a separator, and returns a HashMap containing the parts of the date
     *
     * @param date String for a date
     * @return HashMap containing the parts of the date, keys being parts of the date
     */
    private HashMap<String, String> splitDate(String date) {
        // Splits up a date according to a separator
        String [] separators = {"/", "-", " "};
        ArrayList<String[]> splitDates = new ArrayList<String[]>();
        for (String sep : separators) {
            String[] splitDate = date.split(sep);
            if (this.splitDateLengthIsValid(splitDate)) {
                splitDates.add(splitDate);
            }
        }
        if (splitDates.size() == 0){
            throw new ParseError("Date does not follow a valid separator scheme!");
        }
        assert splitDates.size() == 1: "Date cannot use two different separators at once!";
        return this.dateArrayToDict(splitDates.get(0));
    }

    /***
     * Converts a date array to a date dictionary
     *
     * Helper method for splitDate(date)
     *
     * @param dateArray String array containing the parts of the date.
     * @return HashMap for the parts of the date
     */
    private HashMap<String, String> dateArrayToDict(String[] dateArray) {
        HashMap<String, String> dateDict = new HashMap<String, String>();
        dateDict.put("Day", dateArray[0]);
        dateDict.put("Month", dateArray[1]);
        dateDict.put("Year", dateArray[2]);
        return dateDict;
    }

    /**
     * Parses a day value into a dd format. Checking that is valid while doing so
     *
     * Throws a ParseError if day isn't a valid dd, d or 0d value
     *
     * @param day String for a day in a month
     * @param month String for the month that this day belongs to, the first 3 letters of a capitalized month
     * @param year int for the year that this day belongs to
     * @return String as described
     */
    private String parseDay(String day, String month, int year) {
        assert this.months.contains(month): "Inserted month value is not a valid";
        if (this.isInt(day)){
            if (!this.dayLengthIsValid(day)){
                throw new ParseError("Length of day must be either 1 or 2!");
            }
            int dayInt = Integer.parseInt(day);
            if (dayInt <= 0){
                throw new ParseError("Day cannot be less than or equal to 0!");
            }
            if (this.dayTooLarge(dayInt, month, year)){
                throw new ParseError("Day is too large for this month and year!");
            }
            return this.formatDayToStr(dayInt);
        }
        else {
            throw new ParseError("Day is not an integer!");
        }
    }

    /**
     * Formats an integer for a day back into a string, formatted as dd, or 0d
     *
     * @param day int for day in a month
     * @return String for the formatted string
     */
    private String formatDayToStr(int day) {
        if (day > 9) {
            return Integer.toString(day);
        }
        else {
            return "0"+day;
        }
    }

    /**
     * Finds out if an inputted day value is too large for a given month and year
     *
     * @param day int for a day in a month
     * @param month String for a month
     * @param year int for a year
     * @return boolean if inputted day value is too large
     */
    private boolean dayTooLarge(int day, String month, int year){
        return day > this.daysInMonth(month, year);
    }

    /**
     * Finds if the inputted string for day is too long or not
     *
     * @param day String for a day
     * @return boolean as described
     */
    private boolean dayLengthIsValid(String day){
        return (day.length() == 1 || day.length() == 2);
    }

    /**
     * Finds the number of days in a month for a given year
     *
     * @param month int for the month in a year
     * @param year int for the year as described. Used for leap years
     * @return int for the number of days as described
     */
    private int daysInMonth(String month, int year) {
        // Account for leap years
        assert this.months.contains(month): "Inputted month is not valid!";
        int monthInt = this.months.indexOf(month) + 1;
        YearMonth yearMonth = YearMonth.of(year, monthInt);
        return yearMonth.lengthOfMonth();
    }

    /**
     * Parses an inputted year string into another String that can be displayed back
     * to a user
     *
     * @param year String for a year
     * @return String for year as described
     */
    private String parseYear(String year) {
        if (this.isInt(year)) {
            int yearInt = Integer.parseInt(year);
            if (this.isValidYy(yearInt)){
                return Integer.toString(this.yyToYyyy(yearInt));
            }
            else if (this.isValidYyyy(yearInt)){
                return year;
            }
            else {
                throw new ParseError("Year is not in range! Needs to be between 1753 and 3000!");
            }
        }
        else {
            throw new ParseError("Year is not an integer!");
        }
    }

    /**
     * Converts a year in yy to yyyy format
     *
     * Assumes that if a year is given as yy, then it is not in the future. Makes things easy
     *
     * @param yy int for year
     * @return int for inputted yy converted to yyyy
     */
    private int yyToYyyy(int yy){
        int currYear = LocalDateTime.now().getYear() - 2000; // Should work for next 78 years!, hopefully a new language has come out by then
        assert this.isValidYy(yy): "Inputted yy is not a valid yy value!";
        if (currYear < yy & yy <= 99){
            return 1900 + yy;
        }
        return 2000 + yy;
    }

    /**
     * Checks if an inputted yy value is valid or not
     *
     * @param yy int for a yy value
     * @return boolean if inputted yy is valid or not
     */
    private boolean isValidYy(int yy) {
        return (0 <= yy & yy <= 99);
    }

    /**
     * Checks if an inputted yyyy value is valid or not
     *
     * @param yyyy int for a yyyy value
     *
     * @return boolean as described
     */
    private boolean isValidYyyy(int yyyy) {
        return ((1753 <= yyyy & yyyy <= 3000));
    }

    /**
     * Checks if an inputted string is an integer or not
     *
     * @param str String to be checked
     * @return boolean as described
     */
    private boolean isInt(String str) {
        float num;
        try {
            num = Float.parseFloat(str);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return (num == (int) num);
    }

    /**
     * Parses a string for a month into a three letter abbreviation.
     *
     * @param month String for month in a year that was has been inputted by a user
     * @return String for the month put into a 3 letter format
     */
    private String parseMonth(String month) {
        if (this.isInt(month)) {
            return this.parseMonthInt(Integer.parseInt(month));
        }
        return this.parseMonthAbbrev(month);
    }

    /**
     *  Parses a String abbreviation for a Month to a form that is usable form
     *
     *  Throws a ParseError if this cannot be done
     *
     * @param monthAbbrev String for the abbreviation of a month
     * @return String for the abbreviation of the month in a usable form
     */
    private String parseMonthAbbrev(String monthAbbrev) {
        if (!this.monthAbbreviationIsValid(monthAbbrev)) {
            throw new ParseError("Abbreviated month is not valid! Must either be a length of 3 with uniform case or have it's first letter capitalized!");
        }
        return this.capitalizeString(monthAbbrev);
    }

    /**
     * Capitalizes a String, all characters following the first are put into lower case
     *
     * @param str String to be capitalized
     * @return A capitalized String
     */
    private String capitalizeString(String str) {
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1).toLowerCase(Locale.ROOT);
    }

    /**
     * Finds out if the abbreviation for a month is invalid or not\
     *
     * @param monthAbbreviation String for the abbreviation of a month, may be valid or not
     * @return boolean as described
     */
    private boolean monthAbbreviationIsValid(String monthAbbreviation) {
        if (this.stringIsUpperCase(monthAbbreviation) || this.stringIsLowerCase(monthAbbreviation) || this.stringIsCapitalized(monthAbbreviation)) {
            monthAbbreviation = this.capitalizeString(monthAbbreviation);
            return this.months.contains(monthAbbreviation);
        }
        return false;
    }

    private boolean stringIsCapitalized(String str) {
        return (str.equals(this.capitalizeString(str)));
    }

    /**
     * Finds out if a string is upper case or not
     *
     * @param str String to be checked
     * @return boolean if a string is upper case or not
     */
    private boolean stringIsUpperCase(String str) {
        return str.toUpperCase(Locale.ROOT).equals(str);
    }

    /**
     * Finds out if a String is all lower case or not
     *
     * @param str String to be checked
     * @return boolean if a string is lower case or not
     */
    private boolean stringIsLowerCase(String str) {
        return str.toLowerCase(Locale.ROOT).equals(str);
    }

    /**
     * Tries to convert an integer for a month to a 3-letter string abbreviation.
     *
     * Throws a ParseError if this could not be done
     *
     * @param month int for month
     * @return 3 Letter string abbreviation for a month as described
     */
    private String parseMonthInt(int month) {
        if (!this.monthIntInRange(month)) {
            throw new ParseError("Integer for month is not in range! Needs to be between 1 and 12 (inclusive)");
        }
        return this.monthIntToAbbreviation(month);
    }
    /**
     * Converts an inputted int for a month to a 3-letter string abbreviation
     *
     * @param month int for cardinal value of month
     * @return String as described
     */
    private String monthIntToAbbreviation(int month) {
        assert this.monthIntInRange(month): "Month is not in range!";
        return this.months.get(month-1);
    }
    /**
     * Finds if an integer value for a month is valid or not
     *
     * @param month int for month
     * @return boolean as described
     */
    private boolean monthIntInRange(int month) {
        return (1 <= month & month <= 12);
    }

    /**
     * Starts the date parser program
     */
    public static void start() {
        System.out.println("Welcome to date parser, please enter a date!");
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        DateParser dateParser = new DateParser();
        System.out.println(dateParser.parseDate(date));
    }

    public static void main(String args[]) {
        DateParser.start();
    }
}
