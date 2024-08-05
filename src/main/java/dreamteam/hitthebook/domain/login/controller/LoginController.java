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
    public LoginTokenDto Login(@RequestBody LoginRequsetDto loginRequsetDto){
        log.info("***********************************************{}", loginRequsetDto);
        return loginService.makeTokenService(loginRequsetDto);
    }

    @GetMapping("/temp/token")
    public LoginTokenDto getTempToken(){
        LoginRequsetDto loginRequsetDto = new LoginRequsetDto("test3@example.com","password3");
        return loginService.makeTokenService(loginRequsetDto);
    }

    @PostMapping("/mail/authorization")
    public CommonResponseDto authenticateEmail(@RequestBody EmailRequestDto emailRequestDto){
        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★{}",emailRequestDto);
        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★{}", emailRequestDto.emailId);
        return loginService.emailAuthenticate(emailRequestDto.emailId);
    }
}
