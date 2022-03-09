package E1_date_parser;

/**
 * Class to represent an error where a string is not an integer
 */
public class NotIntegerError extends ParseError{

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    NotIntegerError(String msg) {
        super(msg);
    }

    /**
     * Creates an error message indicating that a String value isn't an integer
     *
     * i.e. that val cannot be parsed into an int
     *
     * @param name name of a String that is ot an integer
     * @return Formatted String as described
     */
    public static String errorMsg(String name, String val){
        return String.format("%s value %s is not an integer", name, val);
    }
}
