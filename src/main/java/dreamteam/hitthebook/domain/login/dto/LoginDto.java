package dreamteam.hitthebook.domain.login.dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginDto {
    public static class LoginRequsetDto{
        public String emailId;
        public String password;
        public LoginRequsetDto(String emailId, String password) {
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
}
