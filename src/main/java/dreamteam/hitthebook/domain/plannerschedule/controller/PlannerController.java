package dreamteam.hitthebook.domain.plannerschedule.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.dday.dto.DdayDto;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.service.PlannerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public CommonResponseDto scheduleCreate(HttpServletRequest request,
                                            @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType, ScheduleRequestDto scheduleRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createSchedule(scheduleRequestDto, scheduleType, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

//    @DeleteMapping("schedule/{scheduleType}/{scheduleId}")
//    @SwaggerToken
//    public CommonResponseDto schduleDelete(HttpServletRequest request,
//                                           @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType,
//                                           @PathVariable(name = "scheduleId") Long scheduleId, ScheduleRequestDto scheduleRequestDto){
//        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
//        return CommonResponseDto.builder()
//                .message("successful")
//                .build();
//    }

    @GetMapping("/schdule/{scheduleType}")
    @SwaggerToken
    public ScheduleListDto scheduleFind(HttpServletRequest request,
                                          @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType, DateDto dateDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return plannerService.findSchedule(emailId, scheduleType, dateDto.getScheduleDate());
    }

    @PatchMapping("/schedule/{scheduleType}/{plannerScheduleId}/{result}")
    @SwaggerToken
    public CommonResponseDto scheduleFeedback(HttpServletRequest request,
                                              @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType,
                                              @PathVariable(name = "plannerScheduleId") Long plannerScheduleId,
                                              @PathVariable(name = "result")FeedbackTypeEnum feedbackType){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.feedbackSchedule(emailId, scheduleType, plannerScheduleId, feedbackType);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PostMapping("/today/review")
    @SwaggerToken
    public CommonResponseDto todayReviewCreate(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }



}


//1. 오늘의 총평 추가 api
//2. 오늘의 총평 수정 api
//3. 오늘의 총평 조회 api
//ㅇㅇㅇ. 오늘의 총평 삭제는 없음 굳이 만들이유가 없음
//4. 스터디 추가 api -> 했음
//5. 스터디 수정 api -> 기획 문서상 수정 불가능한 파트라고 함
//ㅇㅇㅇ. 스터디 삭제 api필요 -> 필요한지 아닌지 애매함 지금
//6. 스터디 달성여부 수정 api
//7. 스터디 조회 api -> 했ㅅ음
//8. 달성여부 수정시 미루기 api필요함
//9. 다 개발하고 타이머쪽 코드도 한번 확인하기
