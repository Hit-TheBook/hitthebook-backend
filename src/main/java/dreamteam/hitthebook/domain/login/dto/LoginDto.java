package dreamteam.hitthebook.domain.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginDto {
    public static class LoginRequestDto {
        @NotBlank
        public String emailId;
        @NotBlank
        public String password;
        public LoginRequestDto(String emailId, String password) {
            this.emailId = emailId;
            this.password = password;
        }
    }

    public static class LoginTokenDto{
        public String message;
        public String accessToken;
        public String refreshToken;
        public LoginTokenDto(String message, String accessToken, String refreshToken){
            this.message = message;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    public static class ReissueTokenDto{
        @NotBlank
        public String refreshToken;
    }

    public static class JoinRequestDto{
        @NotBlank
        public String emailId;
        @NotBlank
        public String password;
        @NotBlank
        public String nickname;
    }
    // 현재 HTTP로 통신을 하게될 가능성이 높은데, HTTPS로 바로 진행을 안할 수 있으므로 암호화에 대한 고민이 필요

    public static class EmailRequestDto{
        @NotBlank
        public String emailId;
    }

    public static class AuthCodeRequestDto{
        @NotBlank
        public String emailId;
        @NotBlank
        public String authCode;
    }
}
