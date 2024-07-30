package dreamteam.hitthebook.domain.dday.controller;

import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.dday.service.DdayService;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.annotation.SwaggerToken;
import static dreamteam.hitthebook.common.annotation.SwaggerDetail.*;
import static dreamteam.hitthebook.domain.dday.dto.DdayDto.*;


@Slf4j
@RestController
@RequestMapping("/dday")
@RequiredArgsConstructor
public class DdayController {
    private final JwtTokenHelper jwtTokenHelper;
    private final DdayService ddayService;

    @PostMapping("")
    @SwaggerToken
    @DdayRegisterDetail
    public CommonResponseDto ddayCreate(HttpServletRequest request, DdayRequestDto ddayRequestDto){
        String EmailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        ddayService.createDday(ddayRequestDto, EmailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

}
