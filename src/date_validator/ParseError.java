package date_validator;

public class ParseError extends Error{

    ParseError(String msg) {
        super(msg);
    }

    @Override
    public String toString(){
        return " - INVALID: " + this.getMessage();
    }
}
