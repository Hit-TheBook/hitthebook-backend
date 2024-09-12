package dreamteam.hitthebook.common.exception;

public class ModifyAuthenticationException extends RuntimeException {
    public static final String MESSAGE = "Access denied: You are not authorized to modify this resource.";
    public ModifyAuthenticationException() {
        super(MESSAGE);
    }
}
