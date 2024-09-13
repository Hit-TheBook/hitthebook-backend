package dreamteam.hitthebook.common.exception;

public class DuplicateIDException extends  RuntimeException{
    public static final String MESSAGE = "The provided ID is already in use. Please choose a different ID.";
    public DuplicateIDException(){
        super(MESSAGE);
    }
}
