package dreamteam.hitthebook.common.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class) // 해당 토큰이 유효하지 않을 때
    public ResponseEntity<String> handleEInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(499).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 해당 리소스가 db에 존재하지 않을 때
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(440).body(ex.getMessage());
    }

    @ExceptionHandler(ModifyAuthenticationException.class) // 해당 유저가 수정권한이 없을 때
    public ResponseEntity<String> handleEModifyAuthenticationException(ModifyAuthenticationException ex) {
        return ResponseEntity.status(441).body(ex.getMessage());
    }
}
