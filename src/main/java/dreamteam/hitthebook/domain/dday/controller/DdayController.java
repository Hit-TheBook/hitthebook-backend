package dreamteam.hitthebook.domain.dday.controller;

import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.dday.service.DdayService;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public CommonResponseDto ddayCreate(HttpServletRequest request, @Valid @RequestBody DdayRequestDto ddayRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        ddayService.createDday(ddayRequestDto, emailId);
        return CommonResponseDto.builder()
                .message("Successfully created D-Day")
                .build();
    }

    @PutMapping("/{ddayId}")
    @SwaggerToken
    @DdayUpdateDetail
    public CommonResponseDto ddayModify(@PathVariable(name = "ddayId") Long ddayId, HttpServletRequest request, @Valid @RequestBody DdayRequestDto ddayRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        ddayService.modifyDday(ddayRequestDto, emailId, ddayId);
        return CommonResponseDto.builder()
                .message("Successfully modified D-Day")
                .build();
    }

    @DeleteMapping("/{ddayId}")
    @SwaggerToken
    @DdayDeleteDetail
    public CommonResponseDto ddayDelete(@PathVariable(name = "ddayId") Long ddayId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        ddayService.deleteDday(emailId, ddayId);
        return CommonResponseDto.builder()
                .message("Successfully deleted D-Day")
                .build();
    }

    @PostMapping("primary/{ddayId}")
    @SwaggerToken
    @DdayMakePrimaryDetail
    public CommonResponseDto primaryDdaySet(@PathVariable(name = "ddayId") Long ddayId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        ddayService.setPrimaryDday(emailId, ddayId);
        return CommonResponseDto.builder()
                .message("Successfully created primary D-Day")
                .build();
    }

    @GetMapping("/primary")
    @SwaggerToken
    @DdayGetPrimaryDetail
    public PrimaryDdayDto primaryDdayFind(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return ddayService.findPrimaryDday(emailId);
    }

    @GetMapping("/list")
    @SwaggerToken
    @DdayGetListDetail
    public DdayListDto ddayListFind(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return ddayService.findDdayList(emailId);
    }
}
