package E1_date_parser;

/**
 * Class to represent an error when a value is not within a specified range
 */
public class RangeError extends ParseError {

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    RangeError(String msg) {
        super(msg);
    }

    /**
     * Creates an error message indicating that a value is not in a given range
     *
     * Intended to be used for values that are numbers
     *
     * @param name name of a value that is not in range
     * @param val int for the value that is not in range
     * @param explanation Any explanation describing why a value is not in range
     * @return Formatted String as described
     */
    public static String errorMsg(String name, int val, String explanation){
        return String.format("%s value %d is not in a valid range, %s", name, val, explanation);
    }
}
