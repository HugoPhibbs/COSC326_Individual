package E1_date_parser;

public class WhiteSpaceError extends ParseError{

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    WhiteSpaceError(String msg) {
        super(msg);
    }

    /**
     * Constructs an message for a white space error
     *
     * @param name String for the name of a value that triggers a white space erro
     * @return String for a white space error
     */
    public static String errorMessage(String name) {
        return String.format("%s cannot contain white space!", name);
    }
}
