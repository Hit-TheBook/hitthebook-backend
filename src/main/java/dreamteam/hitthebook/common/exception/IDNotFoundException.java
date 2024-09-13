package dreamteam.hitthebook.common.exception;

public class IDNotFoundException extends RuntimeException{
    public static final String MESSAGE = "No resource found for the provided ID.";
    public IDNotFoundException() {
        super(MESSAGE);
    }
}
