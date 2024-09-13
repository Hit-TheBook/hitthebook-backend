package dreamteam.hitthebook.common.exception;

public class EmailSendFailureException extends RuntimeException{
    public static final String MESSAGE = "Unable to send the email. Please verify the recipient address and try again.";
    public EmailSendFailureException() {
        super(MESSAGE);
    }
}
