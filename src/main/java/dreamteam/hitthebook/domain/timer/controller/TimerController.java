package dreamteam.hitthebook.domain.timer.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.timer.service.TimerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static dreamteam.hitthebook.common.annotation.SwaggerDetail.*;
import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;

@Slf4j
@RestController
@RequestMapping("/timer")
@RequiredArgsConstructor
public class TimerController {
    private final JwtTokenHelper jwtTokenHelper;
    private final TimerService timerService;

    @PostMapping("")
    @SwaggerToken
    @TimerStartDetail
    public TimerContents timerCreate(HttpServletRequest request, TimerStartRequestDto timerStartRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.createTimer(timerStartRequestDto, emailId);
    }

    @PutMapping("/{timerId}/start")
    @SwaggerToken
    @TimerPlayDetail
    public CommonResponseDto timerStartSet(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerPlayRequestDto timerPlayRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.setStartTimer(timerPlayRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PutMapping("/{timerId}/end")
    @SwaggerToken
    @TimerEndDetail
    public CommonResponseDto timerEndSet(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerEndRequestDto timerEndRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.setEndTimer(timerEndRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @DeleteMapping("/{timerId}")
    @SwaggerToken
    @TimerDeleteDetail
    public CommonResponseDto timerDelete(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.deleteTimer(timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PatchMapping("/{timerId}")
    @SwaggerToken
    @TimerNameModifyDetail
    public CommonResponseDto timerModify(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerStartRequestDto timerStartRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.modifyTimerName(timerStartRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/list")
    @SwaggerToken
    @TimerListWithDateDetail
    public TimerListDto timerListFind(HttpServletRequest request, TimerDateDto timerDateDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.findTimerList(emailId,timerDateDto);
    }
}
