package E1_date_parser;

/**
 * Class to represent an error where a string value is too long or short
 */
public class LengthError extends ParseError{

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    LengthError(String msg) {
        super(msg);
    }
}
