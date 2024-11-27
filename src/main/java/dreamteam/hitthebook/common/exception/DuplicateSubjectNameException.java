package dreamteam.hitthebook.common.exception;

public class DuplicateSubjectNameException extends RuntimeException{
    public static final String MESSAGE = "The provided subjectName is already in use. Please choose a different subjectName.";
    public DuplicateSubjectNameException() {
        super(MESSAGE);
    }
}
