package dreamteam.hitthebook.common.exception;

public class InvalidTokenException extends RuntimeException{
    public static final String MESSAGE = "Authentication failed: Invalid token.";
    public InvalidTokenException() {
        super(MESSAGE);
    }
}
