package dreamteam.hitthebook.common.exception;

public class InvalidTimeDataException extends RuntimeException {
    public static final String MESSAGE = "Invalid time setting";
    public InvalidTimeDataException() {
        super(MESSAGE);
    }
}
