package E1_date_parser;

/**
 * Class to represent an error when a data uses more than one type of separator
 */
public class SeparatorTypesError extends SeparatorError {
    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    SeparatorTypesError(String msg) {
        super(msg);
    }
}
