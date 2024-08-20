package dreamteam.hitthebook.domain.plannerschedule.helper;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerReviewRepository;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static dreamteam.hitthebook.domain.plannerschedule.dto.PlannerDto.*;

@Component
@RequiredArgsConstructor
public class PlannerHelper {
    private final MemberRepository memberRepository;
    private final PlannerReviewRepository plannerReviewRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;

    // emailId를 기반으로 멤버 검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public PlannerSchedule findPlannerScheduleBySchedulePlannerId(Long plannerScheduleId){
        return plannerScheduleRepository.findById(plannerScheduleId).orElseThrow(RuntimeException::new); // 예외처리 필요
    }

    public List<PlannerSchedule> getSchedulesByCriteria(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        return plannerScheduleRepository.findByMemberAndScheduleTypeAndScheduleAt(member, scheduleType, scheduleAt);
    }
    
    public ScheduleListDto toScheduleListDto(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        return new ScheduleListDto("successful", getSchedulesByCriteria(member, scheduleType, scheduleAt));
    }

    public void updateFeedbackStatus(PlannerSchedule plannerSchedule, FeedbackTypeEnum feedbackType){
        plannerSchedule.setScheduleFeedback(feedbackType);
        plannerScheduleRepository.save(plannerSchedule);
    }

    public void checkScheduleEditPermission(PlannerSchedule plannerSchedule, Member member){
        if(!(plannerSchedule.getMember().equals(member))){throw new RuntimeException();} // 예외처리 필요
    }


}
