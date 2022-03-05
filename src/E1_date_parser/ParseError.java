package E1_date_parser;

/**
 * Represents an Error while parsing an inputted Date
 *
 * Use to control Exceptional flow (i.e. non-blue sky scenarios)
 */
public class ParseError extends Error{

    /**
     * Constructor for a parse error
     *
     * @param msg String for a message to be displayed alongside this error
     */
    ParseError(String msg) {
        super(msg);
    }

    @Override
    public String toString(){
        return " - INVALID: " + this.getMessage();
    }
}
