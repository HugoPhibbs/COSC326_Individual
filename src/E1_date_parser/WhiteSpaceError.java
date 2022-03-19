package E1_date_parser;

import java.util.ArrayList;

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

    /**
     * Constructs a message for a white space error
     *
     * Shares method name with errorMessage(String)
     *
     * @param names Arraylist containing names of values containing white space
     * @return String for a white space error as described
     */
    public static String errorMessage(ArrayList<String> names) {
        assert names.size() > 0: "Inputted names must have a length greater than 1!";
        if (names.size() == 1){
            return WhiteSpaceError.errorMessage(names.get(0));
        }
        else if (names.size() == 2){
            return WhiteSpaceError.errorMessage(String.join(" and ", names));
        }
        else {
            return WhiteSpaceError.errorMessage(String.format("%s and %s", String.join(", ", names.subList(0, names.size()-1)), names.get(names.size()-1)));
        }
    }
}
