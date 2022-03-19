package E1_date_parser;

/**
 * Class to represent error when an inputted date has too many arguments in it
 */
public class ArgumentCountError extends ParseError{

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    ArgumentCountError(String msg) {
        super(msg);
    }
}
