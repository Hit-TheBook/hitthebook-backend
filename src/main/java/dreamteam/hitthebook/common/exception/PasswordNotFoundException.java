package dreamteam.hitthebook.common.exception;

public class PasswordNotFoundException extends RuntimeException {
    public static final String MESSAGE = "The password entered does not match our records.";
    public PasswordNotFoundException() {
        super(MESSAGE);
    }
}
