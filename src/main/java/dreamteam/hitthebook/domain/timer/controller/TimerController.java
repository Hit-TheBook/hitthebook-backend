package dreamteam.hitthebook.domain.timer.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.commonutil.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.timer.service.TimerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;
import static dreamteam.hitthebook.common.annotation.TimerSwaggerDetail.*;

@Slf4j
@RestController
@RequestMapping("/timer")
@RequiredArgsConstructor
public class TimerController {
    private final JwtTokenHelper jwtTokenHelper;
    private final TimerService timerService;

    @PostMapping("/{subjectName}")
    @SwaggerToken
    @AddTimerSubjectDetail
    public CommonResponseDto timerCreate(HttpServletRequest request, @PathVariable(name = "subjectName")String subjectName){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.createTimer(subjectName, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PostMapping("/history/{timerId}")
    @SwaggerToken
    @AddTimerHistoryDetail
    public CommonResponseDto timerUpdate(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request, @RequestBody TimerHistoryRequestDto timerHistoryRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.updateTimer(timerHistoryRequestDto,timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @DeleteMapping("/{timerId}")
    @SwaggerToken
    @DeleteTimerSubjectDetail
    public CommonResponseDto timerDelete(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.deleteTimer(timerId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PatchMapping("/{timerId}/{subjectName}")
    @SwaggerToken
    @ModifyTimerSubjectDetail
    public CommonResponseDto timerSubjectNameModify(@PathVariable(name = "timerId") Long timerId, HttpServletRequest request,
                                                    @PathVariable(name = "subjectName")String subjectName){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        timerService.modifyTimerName(subjectName, timerId, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/list")
    @SwaggerToken
    @FindTimerListDetail
    public TimerListDto timerListFind(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.findTimerList(emailId);
    }

    @GetMapping("/today/data")
    @SwaggerToken
    @GetTimerInfoDetail
    public TodayTimerDataDto todayTimerDataGet(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.getTodayTimerData(emailId);
    }

    @GetMapping("/Statistics/daily/total/{targetDate}")
    @SwaggerToken
    @GetTotalDailyTimerStatisticsDetail
    public TargetDateDailyStatistics totalDailyStatisticsGet(HttpServletRequest request, @PathVariable(name = "targetDate") LocalDate targetDate){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.getTotalDailyStatistics(emailId, targetDate);
    }

    @GetMapping("/Statistics/daily/subject/{targetDate}/{subjectName}")
    @SwaggerToken
    @GetSubjectDailyTimerStatisticsDetail
    public TargetDateDailyStatistics subjectDailyStatisticsGet(HttpServletRequest request, @PathVariable(name = "targetDate") LocalDate targetDate,
                                                       @PathVariable(name = "subjectName") String subjectName){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.getSubjectDailyStatistics(emailId, targetDate, subjectName);
    }

    @GetMapping("Statistics/weekly/total/{targetDate}")
    @SwaggerToken
    @GetTotalWeeklyTimerStatisticsDetail
    public TargetDateWeeklyStatistics totalWeeklyStatisticsGet(HttpServletRequest request, @PathVariable(name = "targetDate") LocalDate targetDate){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.getTotalWeeklyStatistics(emailId, targetDate);
    }

    @GetMapping("/Statistics/weekly/subject/{targetDate}/{subjectName}")
    @SwaggerToken
    @GetSubjectWeeklyTimerStatisticsDetail
    public TargetDateWeeklyStatistics subjectWeeklyStatisticsGet(HttpServletRequest request, @PathVariable(name = "targetDate") LocalDate targetDate,
                                                        @PathVariable(name = "subjectName") String subjectName){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return timerService.getSubjectWeeklyStatistics(emailId, targetDate, subjectName);
    }

}