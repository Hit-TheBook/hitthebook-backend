package dreamteam.hitthebook.domain.login.controller;

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
        LoginRequestDto loginRequsetDto = new LoginRequestDto("test3@example.com","password3");
        return loginService.makeTokenService(loginRequsetDto);
    }

    @PostMapping("/join")
    @JoinDetail
    public CommonResponseDto memberJoin(@Valid @RequestBody JoinRequestDto joinRequestDto){
        return loginService.joinMember(joinRequestDto);
    }

    @PostMapping("/join/mail/authorization")
    @AuthenticateEmailDetail
    public CommonResponseDto emailAuthenticate(@Valid @RequestBody EmailRequestDto emailRequestDto){
        return loginService.authenticateEmail(emailRequestDto);
    }

    @PostMapping("/login/token/issue")
    @ReissueDetail
    public LoginTokenDto tokenValidationIssue(@Valid HttpServletRequest request){
        String refreshToken = (String) jwtTokenHelper.getJwtFromRequest(request);
        return loginService.issueTokenService(refreshToken); // jwt DB에 저장 및 갱신하는 과정 수정 필요함
    }

    @PostMapping("/join/mail/authorization/verify")
    @AuthenticateEmailCodeDetail
    public CommonResponseDto authenticationCodeVerify(@Valid @RequestBody AuthCodeRequestDto authCodeRequestDto){
        return loginService.verifyAuthenticationCode(authCodeRequestDto); // dto를 넘겨주는게 맞는듯
    }

    // 생각보다 로직이 복잡할 것으로 보여서 나중에 구현
    @PostMapping("/login/password/forgot")
    public CommonResponseDto temporaryPasswordIssue(){
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

}
