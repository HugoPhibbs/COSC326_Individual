package E1_date_parser;

/**
 * Class to represent an error when a date uses too many occurrences of one type of separator
 */
public class SeparatorCountError extends SeparatorError{
    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    SeparatorCountError(String msg) {
        super(msg);
    }
}
