package dreamteam.hitthebook.domain.timer.controller;

import dreamteam.hitthebook.common.annotation.SwaggerDetail;
import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.dday.dto.DdayDto;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.service.TimerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public CommonResponseDto timerCreate(HttpServletRequest request, TimerRequestDto timerRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.createTimer(timerRequestDto, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PostMapping("/{timerId}")
    @SwaggerToken
    @TimerEndDetail
    public CommonResponseDto timerSet(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerRequestDto timerRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.setTimer(timerRequestDto,timerId,emailId);
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

    @PutMapping("/{timerId}")
    @SwaggerToken
    @TimerNameModifyDetail
    public CommonResponseDto timerModify(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerRequestDto timerRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.modifyTimerName(timerRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }
}
