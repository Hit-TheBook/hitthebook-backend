package dreamteam.hitthebook.domain.member.controller;

import dreamteam.hitthebook.common.annotation.SwaggerDetail;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.member.dto.LoginDto;
import dreamteam.hitthebook.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static dreamteam.hitthebook.domain.member.dto.MemberDto.*;
import static dreamteam.hitthebook.common.annotation.MemberSwaggerDetail.*;

@Slf4j
@RestController("/member")
@RequiredArgsConstructor
public class MemberController {
    private final JwtTokenHelper jwtTokenHelper;
    private final MemberService memberService;

    @GetMapping("/nickname")
    public NicknameDto nicknameGet(HttpServletRequest request) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return memberService.getNickname(emailId);
    }

    @PutMapping("/nickname/{nickname}")
    public CommonResponseDto nicknameModify(@PathVariable(name = "nickname") String nickname, HttpServletRequest request) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        memberService.modifyNickname(emailId, nickname);
        return CommonResponseDto.builder()
                .message("Nickname is successfully changed")
                .build();
    }

    @PostMapping("/nickname/check/{nickname}")
    @CheckNicknameMatchDetail
    public CommonResponseDto sameNicknameIs(@PathVariable(name = "nickname") String nickname){
        memberService.isSameNickname(nickname);
        return CommonResponseDto.builder()
                .message("Nickname is unique data")
                .build();
    }

    @GetMapping("/level")
    public LevelDto levelGet(HttpServletRequest request) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return memberService.getLevel(emailId);
    }

    @GetMapping("/emblem")
    public EmblemDto emblemGet(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return memberService.getEmblem(emailId);
    }

}
