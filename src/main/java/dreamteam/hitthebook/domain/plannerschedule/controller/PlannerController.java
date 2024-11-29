package dreamteam.hitthebook.domain.plannerschedule.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.service.PlannerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static dreamteam.hitthebook.common.annotation.PlannerSwaggerDetail.*;
import static dreamteam.hitthebook.domain.plannerschedule.dto.PlannerDto.*;

@Slf4j
@RestController
@RequestMapping("/planner")
@RequiredArgsConstructor
public class PlannerController {
    private final JwtTokenHelper jwtTokenHelper;
    private final PlannerService plannerService;

    @PostMapping("/schedule/{scheduleType}")
    @SwaggerToken
    @PlannerAddScheduleDetail
    public CommonResponseDto scheduleCreate(HttpServletRequest request,
                                            @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType, @Valid @RequestBody ScheduleRequestDto scheduleRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createSchedule(scheduleRequestDto, scheduleType, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/schdule/{scheduleType}/{scheduleDate}")
    @SwaggerToken
    @PlannerFindScheduleDetail
    public ScheduleListDto scheduleFind(HttpServletRequest request,
                                        @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType,
                                        @PathVariable(name = "scheduleDate") LocalDateTime scheduleDate){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return plannerService.findSchedule(emailId, scheduleType, scheduleDate);
    }

    @GetMapping("/schdule/{scheduleDate}")
    @SwaggerToken
    @PlannerAllFindScheduleDetail
    public ScheduleListDto scheduleAllFind(HttpServletRequest request,
                                        @PathVariable(name = "scheduleDate") LocalDateTime scheduleDate){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return plannerService.findAllSchedule(emailId, scheduleDate);
    }

    @PatchMapping("/schedule/{scheduleType}/{plannerScheduleId}/{result}")
    @SwaggerToken
    @PlannerAddFeedbackDetail
    public CommonResponseDto scheduleFeedback(HttpServletRequest request,
                                              @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType,
                                              @PathVariable(name = "plannerScheduleId") Long plannerScheduleId,
                                              @PathVariable(name = "result")FeedbackTypeEnum feedbackType){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.feedbackSchedule(scheduleType, emailId, plannerScheduleId, feedbackType);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PostMapping("/schedule/{scheduleType}/{plannerScheduleId}/postpone")
    @SwaggerToken
    @PlannerPostponeScheduleDetail
    public CommonResponseDto postponeSchedule(HttpServletRequest request,
                                              @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType,
                                              @PathVariable(name = "plannerScheduleId") Long plannerScheduleId,
                                              @Valid @RequestBody PostPoneDto postPoneDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createPostponeSchedule(scheduleType, emailId, plannerScheduleId, postPoneDto);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }


    // 오늘의 총평시나리오
    // 일반적인 경우
    // 겟으로 가져왔을 때 null 이라면, Post로 생성 -> 근데 예외케이스가 있을 수 있는 로직 같은데 나중에 클라이언트와 테스트 과정에서 다시 생각해보면 좋을 것 같음
    // 키입력시마다 0.3초마다 PUT
    @PostMapping("/daily/review/{reviewAt}")
    @SwaggerToken
    @PlannerAddDailyReviewDetail
    public CommonResponseDto dailyReviewCreate(HttpServletRequest request, @PathVariable(name = "reviewAt") LocalDateTime reviewAt){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createDayReview(emailId, reviewAt);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PutMapping("/daily/review/{reviewAt}")
    @SwaggerToken
    @PlannerModifyDailyReviewDetail
    public CommonResponseDto dailyReviewModify(HttpServletRequest request, @Valid @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto,
                                             @PathVariable(name = "reviewAt") LocalDateTime reviewAt){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.modifyDayReview(emailId, reviewUpdateRequestDto, reviewAt);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/daily/review/{reviewAt}")
    @SwaggerToken
    @PlannerGetDailyReviewDetail
    public ReviewDto dailyReviewGet(HttpServletRequest request, @PathVariable(name = "reviewAt") LocalDateTime reviewAt){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return plannerService.getDayReview(emailId, reviewAt);
    }
}
