package dreamteam.hitthebook.common.exception;

public class InvalidFormatException extends RuntimeException{
    public static final String MESSAGE = "The provided string format is invalid.";
    public InvalidFormatException() {
        super(MESSAGE);
    }
}
