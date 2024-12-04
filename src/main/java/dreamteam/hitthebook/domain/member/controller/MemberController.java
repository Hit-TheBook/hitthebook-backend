package dreamteam.hitthebook.domain.member.controller;

import dreamteam.hitthebook.common.commonutil.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static dreamteam.hitthebook.domain.member.dto.MemberDto.*;
import static dreamteam.hitthebook.common.annotation.MemberSwaggerDetail.*;

@Slf4j
@RestController
@RequestMapping(("/member"))
@RequiredArgsConstructor
public class MemberController {
    private final JwtTokenHelper jwtTokenHelper;
    private final MemberService memberService;

    @GetMapping("/nickname")
    @GetNicknameDetail
    public NicknameDto nicknameGet(HttpServletRequest request) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return memberService.getNickname(emailId);
    }

    @PutMapping("/nickname/{nickname}")
    @ModifyNicknameDetail
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
    @GetLevelDetail
    public LevelDto levelGet(HttpServletRequest request) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return memberService.getLevel(emailId);
    }

    @GetMapping("/emblem")
    @GetEmblemDetail
    public EmblemDto emblemGet(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return memberService.getEmblem(emailId);
    }

    @DeleteMapping()
    public CommonResponseDto memberDelete(HttpServletRequest request) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        memberService.deleteMember(emailId);
        return CommonResponseDto.builder()
                .message("successful withdraw")
                .build();
    }

}
