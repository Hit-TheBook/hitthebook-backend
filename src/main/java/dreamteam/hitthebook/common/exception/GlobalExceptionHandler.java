package dreamteam.hitthebook.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 애초에 서블렛으로 차단해서 컨트롤러어드바이스로 컨트롤을 못함
    @ExceptionHandler(InvalidTokenException.class) // 해당 토큰이 유효하지 않을 때
    public ResponseEntity<String> handleEInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(499).body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 해당 리소스가 db에 존재하지 않을 때
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(440).body(ex.getMessage());
    }

    @ExceptionHandler(ModifyAuthenticationException.class) // 해당 유저가 수정권한이 없을 때
    public ResponseEntity<String> handleModifyAuthenticationException(ModifyAuthenticationException ex) {
        return ResponseEntity.status(441).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class) // 해당 문자열이 원하는 형식이 아닌 경우
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity.status(442).body(ex.getMessage());
    }

    @ExceptionHandler(IDNotFoundException.class) // 존재하는 ID가 아닌 경우
    public ResponseEntity<String> handleIDNotFoundException(IDNotFoundException ex) {
        return ResponseEntity.status(443).body(ex.getMessage());
    }

    @ExceptionHandler(PasswordNotFoundException.class) // 패스워드가 틀렸을 경우
    public ResponseEntity<String> handlePasswordNotFoundException(PasswordNotFoundException ex) {
        return ResponseEntity.status(444).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateIDException.class) // 중복된 ID로 회원가입을 시도하는 경우
    public ResponseEntity<String> handleDuplicateIDException(DuplicateIDException ex) {
        return ResponseEntity.status(445).body(ex.getMessage());
    }

    @ExceptionHandler(EmailSendFailureException.class) // 이메일 발송 실패 관련 예외처리
    public ResponseEntity<String> handleEmailSendFailureException(EmailSendFailureException ex) {
        return ResponseEntity.status(446).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAuthenticationException.class)
    public ResponseEntity<String> handleEmailAutheticationException(EmailAuthenticationException ex) {
        return ResponseEntity.status(447).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTimeDataException.class)
    public ResponseEntity<String> handleInvalidTimeDataException(InvalidTimeDataException ex) {
        return ResponseEntity.status(448).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicatePasswordException.class)
    public ResponseEntity<String> handleDuplicatePasswordException(DuplicatePasswordException ex) {
        return ResponseEntity.status(449).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<String> handleDuplicateNicknameException(DuplicateNicknameException ex){
        return ResponseEntity.status(450).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateSubjectNameException.class)
    public ResponseEntity<String> handleDuplicateSubjectNameException(DuplicateSubjectNameException ex){
        return ResponseEntity.status(451).body(ex.getMessage());
    }
}
