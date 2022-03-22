package E1_date_parser;

import java.lang.reflect.Array;
import java.time.YearMonth;
import java.util.*;

/**
 * Class to parse inputted Dates from a user
 *
 * @author Hugo Phibbs
 * @since 4/3/22
 */
public class DateParser {

    // CLASS ATTRIBUTES //

    /** ArrayList of Strings containing 3 letter abbreviations for each month of the year
     * Credit to https://stackoverflow.com/questions/1005073/initialization-of-an-arraylist-in-one-line for this
     */
    private final ArrayList<String> months = new ArrayList<String>(
            Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));

    /**
     * String array containing the possible separators contained in a date
     */
    private final ArrayList<String> separators = new ArrayList<String>(Arrays.asList("/", "-", " "));

    // METHODS //

    // Main parse methods //

    public static void main(String args[]) {
        new DateParser().start();
    }

    /**
     * Starts the date parser program
     */
    public void start() {
        ArrayList<String> dates = getInput();
        printParsedDates(dates);
    }

    /**
     * Parses each date string and prints the result of each parsing on a new line
     *
     * @param dates ArrayList containing Strings for entered dates
     */
    private void printParsedDates(ArrayList<String> dates) {
        System.out.println("Output:");
        for (String date : dates) {
            System.out.println(parseDate(date));
        }
    }

    /**
     * Gets an input of a dates to be parsed from a user
     *
     * @return ArrayList containing Strings for the dates that a user has entered
     */
    private ArrayList<String> getInput() {
        System.out.println("Welcome to date parser\n\nInstructions:\nEnter one date per line\nPress enter on an empty line to submit");
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> dates = new ArrayList<String>();
        String date = scanner.nextLine();
        while (!date.equals("")) {
            dates.add(date);
            date = scanner.nextLine();
        }
        return dates;
    }

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
        try {
            return parseDateHelper(date);
        }
        catch (ParseError pe) {
            return String.format("%s%s", date, pe);
        }
    }

    /**
     * Helper method for parseDate
     *
     * Does the actual parsing part
     *
     * Extracted into its own method for easy testing, so errors are thrown up to the test level
     *
     * @param date String for a date entered
     * @throws ParseError if a date cannot be parsed, useful for testing
     * @return String for a parsed date
     */
    public String parseDateHelper(String date) throws ParseError{
        preParseDateChecks(date);
        HashMap<String, String> splitDateMap = this.splitDate(date);
        checkWhiteSpace(splitDateMap);
        String year = this.parseYear(splitDateMap.get("Year"));
        String month = this.parseMonth(splitDateMap.get("Month"));
        String day = this.parseDay(splitDateMap.get("Day"), month, Integer.parseInt(year));
        return String.format("%s %s %s", day, month, year);
    }

    /**
     * Performs checks on an inputted date value before any actual parsing can happen
     *
     * @param date String for a date that is entered by a user
     * @throws ParseError if an inputted date does not pass pre parse checks
     */
    private void preParseDateChecks(String date) throws ParseError{
        if (date == null) {
            throw new ParseError("Date cannot be null!");
        }
        if (date.equals("")) {
            throw new ParseError("Date cannot be an empty string");
        }
        checkWhiteSpace(date);
    }

    /**
     * Checks if an inputted date has any trailing or leading white space
     *
     * NB: Not to be confused with checkWhiteSpace(HashMap), this checks for white space within individual components
     * of the date.
     *
     * This method is a compromised solution on a limitation of my algorithm, that is any internal white space for a date will be
     * treated as a Separator (assuming that this method is called before checkTypesOfSeparators(String)).
     *
     *
     * @throws WhiteSpaceError if the inputted date has any trailing or leading white space
     * @param date String for an inputted date from a user
     */
    private void checkWhiteSpace(String date) {
        assert date != null & !Objects.equals(date, "") : "Inputted date cannot be empty or null!";
        if (date.length() >= 1){
            if (date.charAt(0) == ' ' || date.charAt(date.length() - 1) == ' ') {
                throw new WhiteSpaceError("Date cannot contain leading or trailing whitespace!");
            }
        }
    }

    // Splitting Dates //

    /**
     * Splits a date according to a separator, and returns a HashMap containing the parts of the date
     *
     * @param date String for a date
     * @return HashMap containing the parts of the date, keys being parts of the date
     */
    private HashMap<String, String> splitDate(String date) {
        ArrayList<String> splitDateRes = checkTypesOfSeparators(date);
        ArrayList<String> splitDate = new ArrayList<>(splitDateRes.subList(0, splitDateRes.size()-1));
        String sep = splitDateRes.get(splitDateRes.size() - 1);
        checkCorrectNumSeparators(date, sep);
        checkCorrectNumArguments(splitDate); // In-case wasn't triggered above
        HashMap<String, String> dateMap = dateArrayListToDict(splitDate);
        checkWhiteSpace(dateMap);
        return dateMap;
    }

    /**
     * Checks that a date uses only one type of Separator
     *
     * NB: For any Date that contains white space: Any white space character in a date will be treated as a separator.
     * Thus, whitespace in between arguments in a date may not trigger a WhiteSpaceError, But instead a SeparatorTypesError
     *
     * @param date String for an inputted date
     * @throws SeparatorTypesError if the date doesn't use only one type of separator
     * @return ArrayList containing Strings, the first elements being the date split by a separator, and the last element being the separator that
     * splits this date.
     */
    private ArrayList<String> checkTypesOfSeparators(String date) throws SeparatorTypesError {
        ArrayList<ArrayList<String>> splitDates = checkTypesOfSeparatorsHelper(date);
        if (splitDates.size() > 1) {
            throw new SeparatorTypesError("Inputted date must use only one type of separator");
        }
        else if (splitDates.size() < 1){
            throw new SeparatorTypesError("Inputted date does not use any valid separators");
        }
        return splitDates.get(0);
    }

    /**
     * Helper method for checkTypesOfSeparators(String)
     *
     * @param date String for an inputted date from a user
     * @return ArrayList containing ArrayLists containing Strings for a date split according to each possible separator
     */
    private ArrayList<ArrayList<String>> checkTypesOfSeparatorsHelper(String date) {
        ArrayList<ArrayList<String>> splitDates = new ArrayList<>();
        for (String sep : separators) {
            ArrayList<String> splitDate = separateDate(date, sep);
            if (splitDate.size() > 1) {
                splitDate.add(sep);
                splitDates.add(splitDate);
            }
        }
        return splitDates;
    }


    /**
     * Separates a date according to a separator
     *
     * @param date String for a date
     * @param sep String for a separator
     * @return ArrayList containing Strings for a split date as described
     */
    private ArrayList<String> separateDate(String date, String sep) {
        return new ArrayList<>(Arrays.asList(date.split(sep)));
    }

    /**
     * Checks that a String contains the correct number of occurrences for a given separator
     *
     * @throws  SeparatorCountError if the number of separators used in this date is invalid
     *  AssertionError if the date cannot be split according to the inputted separator.
     * @param date String for an inputted date from a user
     * @param sep String for the separator that splits this date
     *
     */
    private void checkCorrectNumSeparators(String date, String sep) throws SeparatorCountError, AssertionError{
        long count = date.chars().filter(c -> c == sep.charAt(0)).count();
        assert count > 0: "Date cannot be split according to this separator!";
        if (count > 2) {
            handleTooManySeparators(date, sep);
        }
        else if (count < 2) {
            handleTooFewSeparators(date, sep);
        }
    }

    /**
     * Handles the case where a date has too many separators
     *
     * @param date String
     * @param sep String for the Separator used to split the inputted date
     */
    private void handleTooManySeparators(String date, String sep) {
        int argCount = countArguments(separateDate(date, sep));
        String msg = "Date has too many occurrences of it's separator";
        if (argumentCountTooLarge(argCount)) { // Issue comes hand in hand
            msg = msg.concat(" and has too many arguments");
        }
        throw new SeparatorCountError(msg);
    }

    /**
     * Handles the case where a date has too many separators
     *
     * @param date String for a date
     * @param sep String for a separator
     */
    private void handleTooFewSeparators(String date, String sep) {
        int argCount = countArguments(separateDate(date, sep));
        String msg = "Date has too few occurrences of it's separator";
        if (argumentCountTooSmall(argCount)) { // Issue comes hand in hand
            msg = msg.concat(" and has too few arguments");
        }
        throw new SeparatorCountError(msg);
    }

    /**
     * Checks that a date contains the correct number of arguments
     *
     * @throws ArgumentCountError if the date does not contain the correct number of arguments\
     *
     *
     * @param splitDate ArrayList containing Strings for a date split according to its separator. Dates may contain whitespace.
     */
    private void checkCorrectNumArguments(ArrayList<String> splitDate) throws ArgumentCountError {
        int argumentCount = countArguments(splitDate);
        if (argumentCountTooLarge(argumentCount)){
            throw new ArgumentCountError("Date has too few arguments, please note empty characters are not treated as arguments");
        }
        else if (argumentCountTooSmall(argumentCount)) {
            throw new ArgumentCountError("Date has too many arguments, please note empty characters are not treated as arguments");
        }
    }

    /**
     * Determines if a count for the number of arguments in a date is too large or not
     *
     * @param argumentCount int for number of arguments
     * @return boolean as described
     */
    private boolean argumentCountTooLarge(int argumentCount) {
        return argumentCount > 3;
    }

    /**
     * Determines if a count for the number of arguments in a date is too small or not
     *
     * @param argumentCount int for number of arguments
     * @return boolean as described
     */
    private boolean argumentCountTooSmall(int argumentCount) {
        return argumentCount < 3;
    }

    /**
     * Counts the number of arguments in a split date
     *
     * Empty characters are not counted as arguments
     *
     * @param splitDate ArrayList containing Strings for a split date
     * @return an int for the number of arguments as described
     */
    private int countArguments(ArrayList<String> splitDate) {
        int argumentCount = 0;
        for (String part : splitDate){
            if (!part.equals("")){
                argumentCount ++;
            }
        }
        return argumentCount;
    }

    /***
     * Converts a date array to a date dictionary
     *
     * Helper method for splitDate(date)
     *
     * @param dateArrayList ArrayList containing Strings for the parts of the date.
     * @return HashMap for the parts of the date
     */
    private HashMap<String, String> dateArrayListToDict(ArrayList<String> dateArrayList) {
        HashMap<String, String> dateDict = new HashMap<String, String>();
        dateDict.put("Day", dateArrayList.get(0));
        dateDict.put("Month", dateArrayList.get(1));
        dateDict.put("Year", dateArrayList.get(2));
        return dateDict;
    }

    /**
     * Finds out if any of the values for Month, day or year have white space
     *
     * Used code from https://stackabuse.com/java-how-to-get-keys-and-values-from-a-map/
     *
     * @throws ParseError if any of the values for Month, Day or Year contained in dateDict have whitespce
     * @param dateMap HashMap<String, String> containing parts of a date
     */
    private void checkWhiteSpace(HashMap<String, String> dateMap){
        ArrayList<String> valuesContainingWhiteSpace = new ArrayList<>();
        for (Map.Entry<String, String> valuePair : dateMap.entrySet()) {
            String dateKey = valuePair.getKey();
            if (containsWhiteSpace(dateMap.get(dateKey))) {
                valuesContainingWhiteSpace.add(dateKey);
            }
        }
        if (valuesContainingWhiteSpace.size() > 0) {
            throw new WhiteSpaceError(WhiteSpaceError.errorMessage(valuesContainingWhiteSpace));
        }
    }

    // Parsing Days //

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
        int dayInt = parseDayChecks(day, month, year);
        return this.formatDayToStr(dayInt);
    }

    /**
     * Does check on an inputted day value.
     *
     * Helper method for parseDay(String, String, int). Not meant to be called outside this method.
     *
     * Doesn't return anything, simply raises an error of a day doesn't past checks
     *
     * @param day String for a day
     * @param month String for a month. A 3 letter capitalized abbreviation
     * @return int for a day if it has passed checks
     * @throws ParseError if the inputted day value is not valid
     */
    private int parseDayChecks(String day, String month, int year) throws ParseError {
        if (!isInt(day)){
            throw new NotIntegerError(NotIntegerError.errorMsg("day", day));
        }
        if (!dayLengthIsValid(day)){
            throw new LengthError("Length of day must be either 1 or 2!");
        }
        int dayInt = Integer.parseInt(day);
        if (!dayInRange(dayInt, month, year)) {
            throw new RangeError("Value for day is not in range, must be above 0 and be within the number of days for the particular month given (including leap years)");
        }
        return dayInt;
    }

    /**
     * Finds out if an integer value for day is in range or not
     *
     * @param dayInt int for a day
     * @param month String for the month that this day is int
     * @param year int for the year that this day is in
     * @return boolean as described
     */
    private boolean dayInRange(int dayInt, String month, int year) {
        return (0 < dayInt & !dayTooLarge(dayInt, month, year));
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
        assert this.months.contains(month): "Inputted month is not valid!";
        int monthInt = this.months.indexOf(month) + 1;
        YearMonth yearMonth = YearMonth.of(year, monthInt);
        return yearMonth.lengthOfMonth();
    }

    // Parsing Months //

    /**
     * Parses a string for a month into a three letter abbreviation.
     *
     * @param month String for month in a year that was has been inputted by a user
     * @return String for the month put into a 3 letter format
     */
    private String parseMonth(String month) {
        if (this.isInt(month)) {
            return this.parseMonthInt(month);
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

    /**
     * Tries to convert a string for a month that can be interpreted as an integer to a 3-letter string abbreviation.
     *
     * Throws a ParseError if this could not be done
     *
     * @param month string for month
     * @throws AssertionError if the inputted month cannot be interpretted as an integer!
     * @return 3 Letter string abbreviation for a month as described
     */
    private String parseMonthInt(String month) {
        assert isInt(month): "Month is not an integer!";
        int monthInt = Integer.parseInt(month);
        if (!this.monthIntInRange(monthInt)) {
            throw new RangeError(RangeError.errorMsg("Month", monthInt, "Needs to be between 1 and 12 (inclusive)"));
        }
        if (!monthIntLengthIsValid(month)) {
            throw new LengthError("Inputted length of a month integer is not valid!");
        }
        return this.monthIntToAbbreviation(monthInt);
    }

    /**
     * Checks if a string for a month that can be interprtted as an integer has a valid length
     *
     * @param month String for an inputted month
     * @throws AssertionError if the inputted month cannot be interpreted as an integer
     * @return boolean as described
     */
    private boolean monthIntLengthIsValid(String month) throws AssertionError {
        assert isInt(month): "Inputted month cannot be interpreted as an integer!";
        return month.length() == 1 || month.length() == 2;
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

    // Parsing Years //

    /**
     * Parses an inputted year string into another String that can be displayed back
     * to a user
     *
     * @param year String for a year
     * @throws ParseError if the inputted year could not be parsed
     * @return String for year as described
     */
    private String parseYear(String year) throws ParseError {
        if (year.length() == 4){
            return parseYyyy(year);
        }
        else if (year.length() == 2) {
            return parseYy(year);
        }
        else {
            throw new LengthError("Inputted year is not in a yyyy or yy format");
        }
    }

    /**
     * Attempts to parse a string value for a year into a yyyy format
     *
     * @param year String for a year
     * @throws ParseError if inputted year cannot be parsed into a valid yyyy string
     * @return yyyy String if one can be created from the inputted year value
     */
    private String parseYyyy(String year) throws ParseError {
        if (!isInt(year)){
            throw new NotIntegerError(NotIntegerError.errorMsg("Year", year));
        }
        if (!(yyyyLengthIsValid(year))){
            throw new ParseError("Year is not in a YYYY format");
        }
        int yearInt = Integer.parseInt(year);
        if (!yyyyInRange(yearInt)) {
            throw new RangeError(RangeError.errorMsg("Year", yearInt, "Needs to be between 1753 and 3000 (inclusive)"));
        }
        return year;
    }

    /**
     * Checks if an inputted integer yyyy int value is within a correct range or not
     *
     * @param yyyy int for a yyyy value
     * @return boolean as described
     */
    private boolean yyyyInRange(int yyyy) {
        return ((1753 <= yyyy & yyyy <= 3000));
    }

    /**
     * Checks if the length of a yyyy value is valid or not
     *
     * @param year String for an inputted year
     * @return boolean as described
     */
    private boolean yyyyLengthIsValid(String year){
        assert isInt(year): "Inputted yyyy value must be an integer!";
        return year.length() == 4;
    }

    /**
     * Parses a value for yy
     *
     * @param year String for an inputted year
     * @return String of the inputted yy value converted into the appropriate yyyy value
     * @throws ParseError if the yy value cannot be parsed
     */
    private String parseYy(String year) throws ParseError {
        if (!(year.length() == 2)){
            throw new ParseError("Year is not in a yy format!");
        }
        if (!isInt(year)){
            throw new NotIntegerError(NotIntegerError.errorMsg("Year", year));
        }
        int yearInt = Integer.parseInt(year);
        if (!yyInRange(yearInt)) {
            throw new RangeError(RangeError.errorMsg("Year", yearInt, "Needs to be between 0 and 99 (Inclusive)"));
        }
        return Integer.toString(yyToYyyy(yearInt));
    }

    /**
     * Checks if an inputted yy is in a valid range or not
     *
     * @param yy int for a yy value
     * @return boolean if inputted yy is valid or not
     */
    private boolean yyInRange(int yy) {
        return (0 <= yy & yy <= 99);
    }

    /**
     * Converts a year in yy to yyyy format
     *
     * Assumes that if a year is given as yy, then it is a year in the 21st century
     *
     * @param yy int for year
     * @return int for inputted yy converted to yyyy
     */
    private int yyToYyyy(int yy) throws AssertionError{
        if (yy < 50) {
            return 2000 + yy;
        }
        return 1900 + yy;
    }

    // Utility methods //

    /**
     * Checks a String has any white space contained within it.
     *
     * For a year, month or day value to be valid, it cannot have any white space
     *
     * @param str String to be checked
     * @return boolean as described
     */
    private boolean containsWhiteSpace(String str){
        String[] splitStr = str.split("");
        for (String s : splitStr) {
            if (s.equals(" ")){
                return true;
            }
        }
        return false;
    }

    /**
     * Capitalizes a String, all characters following the first are put into lower case
     *
     * @param str String to be capitalized
     * @return A capitalized String
     */
    private String capitalizeString(String str) {
        if (str.length() == 0){
            return str;
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1).toLowerCase(Locale.ROOT);
    }

    /**
     * Checks if a string is capitalized or not
     *
     * Check capitalizeString(String) for my definition for a capitalized String
     *
     * @param str String as described
     * @return boolean as described
     */
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
}
