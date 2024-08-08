package dreamteam.hitthebook.domain.login.controller;

import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.domain.login.service.LoginService;
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
    @PostMapping("/login")
    @LoginDetail
    public LoginTokenDto Login(@RequestBody LoginRequestDto loginRequestDto){
        return loginService.makeTokenService(loginRequestDto);
    }

    @GetMapping("/temp/token")
    @TempTokenDetail
    public LoginTokenDto tempTokenMake(){
        LoginRequestDto loginRequsetDto = new LoginRequestDto("test3@example.com","password3");
        return loginService.makeTokenService(loginRequsetDto);
    }

    @PostMapping("/join")
    public CommonResponseDto memberJoin(@RequestBody JoinRequestDto joinRequestDto){
        return loginService.joinMember(joinRequestDto);
    }

    @PostMapping("/join/mail/authorization")
    @AuthenticateEmailDetail
    public CommonResponseDto emailAuthenticate(@RequestBody EmailRequestDto emailRequestDto){
        return loginService.authenticateEmail(emailRequestDto);
    }

    @PostMapping("/join/mail/authorization/verify")
    public CommonResponseDto authenticationCodeVerify(@RequestBody AuthCodeRequestDto authCodeRequestDto){
        return loginService.verifyAuthenticationCode(authCodeRequestDto); // dto를 넘겨주는게 맞는듯
    }

    // 해야할 일 정리
    // 1. 레디스를 통해 인증번호 기능 구현 : OK
    // 2. 지금 메세지로 보내고 있는데, 이것을 HTML 템플릿을 전송하는 방식으로 수정 : 아직 미완료
    // 3. JWT기능 구현
    // 3-1. 로그인 메소드 수정
    // 3-2. 리프레시 토큰 검증 API구현
    // 3-3. 리프레시토큰 데이터베이스에 저장하고, 새로 갱신하는 로직 구현(이부분은 나중에 AOP로 수정해도 좋을 것으로 보임)
    // 4. 비밀번호 암호화 구현 ... 하 이거를 해야해? 개귀찮다...

}
