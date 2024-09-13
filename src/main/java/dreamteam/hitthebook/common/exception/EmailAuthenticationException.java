package dreamteam.hitthebook.common.exception;

public class EmailAuthenticationException extends RuntimeException {
    public static final String MESSAGE = "The email verification is invalid or has expired.";
    public EmailAuthenticationException() {
        super(MESSAGE);
    }
}
