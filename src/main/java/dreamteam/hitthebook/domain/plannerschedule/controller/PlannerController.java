package dreamteam.hitthebook.domain.plannerschedule.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
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
                                            @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType, @RequestBody ScheduleRequestDto scheduleRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createSchedule(scheduleRequestDto, scheduleType, emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/schdule/{scheduleType}")
    @SwaggerToken
    public ScheduleListDto scheduleFind(HttpServletRequest request,
                                          @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType, @RequestBody DateDto dateDto){
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
        plannerService.feedbackSchedule(emailId, plannerScheduleId, feedbackType);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PostMapping("/schedule/{scheduleType}/{plannerScheduleId}/postpone")
    @SwaggerToken
    public CommonResponseDto postponeSchedule(HttpServletRequest request,
                                              @PathVariable(name = "scheduleType") ScheduleTypeEnum scheduleType,
                                              @PathVariable(name = "plannerScheduleId") Long plannerScheduleId,
                                              @RequestBody PostPoneDto postPoneDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createPostponeSchedule(emailId, plannerScheduleId, postPoneDto);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }


    // 오늘의 총평시나리오
    // 일반적인 경우
    // 겟으로 가져왔을 때 null 이라면, Post로 생성 -> 근데 예외케이스가 있을 수 있는 로직 같은데 나중에 클라이언트와 테스트 과정에서 다시 생각해보면 좋을 것 같음
    // 키입력시마다 0.3초마다 PUT
    @PostMapping("/today/review")
    @SwaggerToken
    public CommonResponseDto dayReviewCreate(HttpServletRequest request, @RequestBody ReviewRequestDto reviewRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.createDayReview(emailId, reviewRequestDto);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @PutMapping("/today/review")
    @SwaggerToken
    public CommonResponseDto dayReviewModify(HttpServletRequest request, @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        plannerService.modifyDayReview(emailId, reviewUpdateRequestDto);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/today/review")
    @SwaggerToken
    public ReviewDto dayReviewGet(HttpServletRequest request, @RequestBody ReviewRequestDto reviewRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return plannerService.getDayReview(emailId, reviewRequestDto);
    }

}


//1. 오늘의 총평 추가 api -> ㅇㅋ
//2. 오늘의 총평 수정 api -> ㅇㅋ
//3. 오늘의 총평 조회 api -> ㅇㅋ
//ㅇㅇㅇ. 오늘의 총평 삭제는 없음 굳이 만들이유가 없음
//4. 스터디 추가 api -> 했음
//5. 스터디 수정 api -> 기획 문서상 수정 불가능한 파트라고 함
//ㅇㅇㅇ. 스터디 삭제 api필요 -> 필요한지 아닌지 애매함 지금
//6. 스터디 달성여부 수정 api
//7. 스터디 조회 api -> 했ㅅ음
//8. 달성여부 수정시 미루기 api필요함
//9. 다 개발하고 타이머쪽 코드도 한번 확인하기
