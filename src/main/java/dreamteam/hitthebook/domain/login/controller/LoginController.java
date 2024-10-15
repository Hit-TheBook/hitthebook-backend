package dreamteam.hitthebook.domain.login.controller;

import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static dreamteam.hitthebook.common.annotation.SwaggerDetail.*;
import static dreamteam.hitthebook.domain.login.dto.LoginDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final JwtTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    @LoginDetail
    public LoginTokenDto Login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return loginService.loginService(loginRequestDto);
    }

    @GetMapping("/temp/token")
    @TempTokenDetail
    public LoginTokenDto tempTokenMake(){
        LoginRequestDto loginRequestDto = new LoginRequestDto("test3@example.com","password3");
        return loginService.makeTokenService(loginRequestDto);
    }

    @PostMapping("/join")
    @JoinDetail
    public CommonResponseDto memberJoin(@Valid @RequestBody JoinRequestDto joinRequestDto){
        loginService.joinMember(joinRequestDto);
        return CommonResponseDto.builder()
                .message("Your registration was successful!")
                .build();
    }

    @PostMapping("/login/token/issue")
    @ReissueDetail
    @SwaggerToken
    public LoginTokenDto tokenValidationIssue(HttpServletRequest request){
        String refreshToken = (String) jwtTokenHelper.getJwtFromRequest(request);
        return loginService.issueTokenService(refreshToken);
    }

    @PostMapping("/mail/join/authorization")
    @AuthenticateEmailDetail
    public CommonResponseDto emailAuthenticate(@Valid @RequestBody EmailRequestDto emailRequestDto){
        loginService.authenticateEmail(emailRequestDto);
        return CommonResponseDto.builder()
                .message("The verification code has been successfully sent to your email.")
                .build();
    }

    @PostMapping("/mail/authorization/verify")
    @AuthenticateEmailCodeDetail
    public CommonResponseDto authenticationCodeVerify(@Valid @RequestBody AuthCodeRequestDto authCodeRequestDto){
        loginService.verifyAuthenticationCode(authCodeRequestDto);
        return CommonResponseDto.builder()
                .message("Email authentication was successful!")
                .build();
    }

    @PostMapping("/mail/forget/authorization")
    @AuthenticateEmailAtForgotDetail
    public CommonResponseDto emailAuthenticateAtForgetPassword(@Valid @RequestBody EmailRequestDto emailRequestDto){
        loginService.authenticateEmailAtForgetPassword(emailRequestDto);
        return CommonResponseDto.builder()
                .message("The verification code has been successfully sent to your email.")
                .build();
    }

    @PostMapping("/forget/password/current")
    @CheckPasswordMatchDetail
    public CommonResponseDto samePasswordIs(@Valid @RequestBody PasswordDto passwordDto){
        loginService.isSamePassword(passwordDto);
        return CommonResponseDto.builder()
                .message("Password is different to previous password")
                .build();
    }

    //post가 더 적절한 것으로 판단됨
    @PostMapping("/forget/password/reset")
    public CommonResponseDto passwordReset(@Valid @RequestBody PasswordDto passwordDto){
        loginService.resetPassword(passwordDto);
        return CommonResponseDto.builder()
                .message("Password changed successfully.")
                .build();
    }
}
