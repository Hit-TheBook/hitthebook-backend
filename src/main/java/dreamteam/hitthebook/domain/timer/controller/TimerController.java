package dreamteam.hitthebook.domain.timer.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.timer.service.TimerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

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
    public CommonResponseDto timerCreate(HttpServletRequest request, TimerStartRequestDto timerStartRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.createTimer(timerStartRequestDto, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PostMapping("/{timerId}")
    @SwaggerToken
    public CommonResponseDto timerEndSet(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerEndRequestDto timerEndRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.setEndTimer(timerEndRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @DeleteMapping("/{timerId}")
    @SwaggerToken
    public CommonResponseDto timerDelete(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.deleteTimer(timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PatchMapping("/{timerId}")
    @SwaggerToken
    public CommonResponseDto timerModify(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, TimerStartRequestDto timerStartRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.modifyTimerName(timerStartRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/list")
    @SwaggerToken
    public TimerListDto timerListFind(HttpServletRequest request, TimerDateDto timerDateDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.findTimerList(emailId,timerDateDto);
    }

    @GetMapping("/totalResult")
    @SwaggerToken
    public TotalInfoDto totalTimerFind(HttpServletRequest request, TimerDateDto timerDateDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.getTotalTimer(emailId,timerDateDto);
    }

    @GetMapping("/dailyStatistics")
    @SwaggerToken
    public ResponseEntity<Map<LocalDate, Duration>> getDailyStatistics(
            HttpServletRequest request,
            @RequestParam(required = false) String subjectName) {

        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        Map<LocalDate, Duration> dailyStats = timerService.getDailyStatisticsForWeek(emailId, subjectName, LocalDate.now());
        return ResponseEntity.ok(dailyStats);
    }

    @GetMapping("/weeklyStatistics")
    @SwaggerToken
    public ResponseEntity<Map<LocalDate, Duration>> getWeeklyStatistics(
            HttpServletRequest request,
            @RequestParam(required = false) String subjectName) {
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        Map<LocalDate, Duration> weeklyStats = timerService.getWeeklyStatisticsForLastFourWeeks(emailId, subjectName, LocalDate.now());
        return ResponseEntity.ok(weeklyStats);
    }

//    public CommonResponseDto temp



    // 1. 과목추가, 과목만 추가해주면 됨
    // 2. 타이머 종료, 타이머 히스토리 추가 및 타이머로 획득한 점수 멤버에 추가
    // 3. 타이머 삭제, 그냥 삭제만 해주면 됨
    // 4. 과목명 수정, 그냥 수정만 해주면 됨
    // 5. 리스트 가져오기 , 리스트에 필요한 정보를 가져오기 , 이때 히스토리로부터 어떻게 끌어오는지 확인
    // 6. 날짜에 따른 총결과 가져오기, 필요한 정보를 잘 연산해서 가져오기만 하면 됨
    // 7. 일간 통계리스트 가져오기, 전반적인 수정 필요
    // 8. 주간 통계리스트 가져오기, 전반적인 수정 필요

}