package dreamteam.hitthebook.domain.plannerschedule.service;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerReview;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.helper.PlannerHelper;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerReviewRepository;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static dreamteam.hitthebook.domain.plannerschedule.dto.PlannerDto.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlannerService {
    private final PlannerHelper plannerHelper;
    private final PlannerReviewRepository plannerReviewRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;

    public void createSchedule(ScheduleRequestDto scheduleRequestDto, ScheduleTypeEnum scheduleType, String emailId){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        plannerHelper.checkInvalidStartTime(scheduleRequestDto.getStartAt(), scheduleRequestDto.getEndAt());
        plannerHelper.checkSameDateOfScheduleTime(scheduleRequestDto.getStartAt(), scheduleRequestDto.getEndAt());

        PlannerSchedule plannerSchedule = PlannerSchedule.createByRequestDto(scheduleRequestDto, scheduleType, member);
        // 스케쥴타입에 따라서 일정이라면 알람을 만들어주는 aop와 스케쥴러 구현 필요
        plannerScheduleRepository.save(plannerSchedule);
    }

    public ScheduleListDto findSchedule(String emailId, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        return plannerHelper.toScheduleListDto(member, scheduleType, scheduleAt);
    }

    public void feedbackSchedule(String emailId, Long plannerScheduleId, FeedbackTypeEnum feedbackType){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerSchedule plannerSchedule = plannerHelper.findPlannerScheduleBySchedulePlannerId(plannerScheduleId);
        plannerHelper.checkScheduleEditPermission(plannerSchedule, member);
        plannerHelper.updateFeedbackStatus(plannerSchedule, feedbackType);
    }

    public void createPostponeSchedule(String emailId, Long plannerScheduleId, PostPoneDto postPoneDto){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerSchedule plannerSchedule = plannerHelper.findPlannerScheduleBySchedulePlannerId(plannerScheduleId);
        plannerHelper.checkInvalidStartTime(postPoneDto.getStartAt(), postPoneDto.getEndAt());
        plannerHelper.checkSameDateOfScheduleTime(postPoneDto.getStartAt(), postPoneDto.getEndAt());
        PlannerSchedule newPlannerSchedule = PlannerSchedule.createNewPostponeEntity(postPoneDto, plannerSchedule);
        plannerScheduleRepository.save(newPlannerSchedule);
    }

    public void createDayReview(String emailId, LocalDateTime reviewAt){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        plannerHelper.checkReviewPresentAtDate(member, reviewAt);// 있는지 없는지 검사하고 없을 경우에만 생성하고 있는 경우에는 예외처리
        PlannerReview plannerReview = PlannerReview.createByRequestDto(reviewAt, member);
        plannerReviewRepository.save(plannerReview);
    }

    public void modifyDayReview(String emailId, ReviewUpdateRequestDto reviewUpdateRequestDto, LocalDateTime reviewAt){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerReview plannerReview = plannerHelper.findReviewByMemberAndDate(member, reviewAt);
        //권한 다르면 예외처리 근데 애초에 위에서 걸림 Id로 찾는게 아니라서
        plannerHelper.reviewAutoSaveChange(plannerReview, reviewUpdateRequestDto);
    }

    public ReviewDto getDayReview(String emailId, LocalDateTime reviewDate){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerReview plannerReview = plannerHelper.findReviewByMemberAndDate(member, reviewDate);
        return plannerHelper.toReviewDto(plannerReview);
    }




}
