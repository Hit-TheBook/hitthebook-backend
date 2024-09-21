package dreamteam.hitthebook.common.exception;

public class InvalidScheduleTimeException extends RuntimeException {
    public static final String MESSAGE = "Invalid schedule time setting";
    public InvalidScheduleTimeException() {
        super(MESSAGE);
    }
}
