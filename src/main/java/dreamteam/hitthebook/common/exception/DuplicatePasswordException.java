package dreamteam.hitthebook.common.exception;

public class DuplicatePasswordException extends RuntimeException{
    public static final String MESSAGE = "The new password cannot be the same as the previous password.";
    public DuplicatePasswordException() {
        super(MESSAGE);
    }
}