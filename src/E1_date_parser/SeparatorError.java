package E1_date_parser;

/**
 * Class to represent an error to do with separators when parsing a date
 */
public class SeparatorError extends ParseError{

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    SeparatorError(String msg) {
        super(msg);
    }


}
