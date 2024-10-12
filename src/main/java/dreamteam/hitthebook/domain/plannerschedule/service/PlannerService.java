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
        plannerHelper.checkSameTimeOfSchedule(member, scheduleRequestDto);
        if(scheduleType == ScheduleTypeEnum.EVENT){
            plannerHelper.createNewPlannerScheduleEvent(scheduleRequestDto, member);
        }

        else{
            plannerHelper.createNewPlannerScheduleSubject(scheduleRequestDto, member);
        }
    }

    public ScheduleListDto findSchedule(String emailId, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        return plannerHelper.toScheduleListDto(member, scheduleType, scheduleAt);
    }

    public ScheduleListDto findAllSchedule(String emailId, LocalDateTime scheduleAt){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        return plannerHelper.toScheduleAllListDto(member, scheduleAt);
    }

    public void feedbackSchedule(ScheduleTypeEnum scheduleType, String emailId, Long plannerScheduleId, FeedbackTypeEnum feedbackType){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerSchedule plannerSchedule = plannerHelper.findPlannerScheduleBySchedulePlannerScheduleId(plannerScheduleId);
        plannerHelper.checkValidScheduleType(scheduleType, plannerSchedule);
        plannerHelper.checkScheduleEditPermission(plannerSchedule, member);
        plannerHelper.updateFeedbackStatus(plannerSchedule, feedbackType);
    }

    public void createPostponeSchedule(ScheduleTypeEnum scheduleType, String emailId, Long plannerScheduleId, PostPoneDto postPoneDto){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerSchedule plannerSchedule = plannerHelper.findPlannerScheduleBySchedulePlannerScheduleId(plannerScheduleId);
        plannerHelper.checkScheduleEditPermission(plannerSchedule, member);
        plannerHelper.checkValidScheduleType(scheduleType, plannerSchedule);
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
        plannerHelper.reviewAutoSaveChange(plannerReview, reviewUpdateRequestDto);
    }

    public ReviewDto getDayReview(String emailId, LocalDateTime reviewDate){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerReview plannerReview = plannerHelper.findReviewByMemberAndDate(member, reviewDate);
        return plannerHelper.toReviewDto(plannerReview);
    }




}
