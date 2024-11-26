package dreamteam.hitthebook.common.exception;

public class DuplicateNicknameException extends  RuntimeException{
    public static final String MESSAGE = "The provided Nickname is already in use. Please choose a different Nickname.";
    public DuplicateNicknameException(){
        super(MESSAGE);
    }
}
