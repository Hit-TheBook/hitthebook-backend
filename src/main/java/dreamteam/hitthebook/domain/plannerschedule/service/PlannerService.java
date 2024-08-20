package dreamteam.hitthebook.domain.plannerschedule.service;

import dreamteam.hitthebook.domain.member.entity.Member;
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
        PlannerSchedule plannerSchedule = PlannerSchedule.createByRequestDto(scheduleRequestDto, scheduleType, member);
        // 스케쥴타입에 따라서 일정이라면 알람을 만들어주는 aop와 스케쥴러 구현 필요
        plannerScheduleRepository.save(plannerSchedule);
    }

    public ScheduleListDto findSchedule(String emailId, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        return plannerHelper.toScheduleListDto(member, scheduleType, scheduleAt);
    }

    public void feedbackSchedule(String emailId, ScheduleTypeEnum scheduleType, Long plannerScheduleId, FeedbackTypeEnum feedbackType){
        Member member = plannerHelper.findMemberByEmailId(emailId);
        PlannerSchedule plannerSchedule = plannerHelper.findPlannerScheduleBySchedulePlannerId(plannerScheduleId);
        plannerHelper.checkScheduleEditPermission(plannerSchedule, member);
        plannerHelper.updateFeedbackStatus(plannerSchedule, feedbackType);
    }


}
