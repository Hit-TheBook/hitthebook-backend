package dreamteam.hitthebook.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Resource not found";
    public ResourceNotFoundException() {
        super(MESSAGE);
    }
}
