package dreamteam.hitthebook.domain.plannerschedule.helper;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.TargetPageTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerReview;
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
    private final AlertRepository alertRepository;

    // emailId를 기반으로 멤버 검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public void checkSameDateOfScheduleTime(LocalDateTime startAt, LocalDateTime endAt){
        if(startAt.getYear() != endAt.getYear() ||
        startAt.getMonthValue() != endAt.getMonthValue() ||
        startAt.getDayOfMonth() != endAt.getDayOfMonth()){
            throw new RuntimeException();
        }
    }

    public void createNewPlannerScheduleEvent(ScheduleRequestDto scheduleRequestDto, Member member){
        PlannerSchedule plannerSchedule = PlannerSchedule.createByRequestDto(scheduleRequestDto, ScheduleTypeEnum.EVENT, member);
        plannerScheduleRepository.save(plannerSchedule);
        createNewPlannerScheduleEventAlert(plannerSchedule, member);
    }

    public void createNewPlannerScheduleSubject(ScheduleRequestDto scheduleRequestDto, Member member){
        PlannerSchedule plannerSchedule = PlannerSchedule.createByRequestDto(scheduleRequestDto, ScheduleTypeEnum.SUBJECT, member);
        plannerScheduleRepository.save(plannerSchedule);
    }

    public void createNewPlannerScheduleEventAlert(PlannerSchedule plannerSchedule, Member member){
        Alert alert = new Alert(TargetPageTypeEnum.PLANNER, "등록하신 일정이 하루남았습니다.", member, plannerSchedule);
        alertRepository.save(alert);
    }

    public void checkInvalidStartTime(LocalDateTime startAt, LocalDateTime endAt){
        if (startAt.isAfter(endAt)) {throw new RuntimeException();}
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

    public PlannerReview findReviewByMemberAndDate(Member member, LocalDateTime reviewAt){
//        int year = reviewDate.getYear();
//        int month = reviewDate.getMonthValue();
//        int day = reviewDate.getDayOfMonth();
//        return plannerReviewRepository.findByMemberAndReviewAtYearAndReviewAtMonthAndReviewAtDay(member, year, month, day);
        return plannerReviewRepository.findByMemberAndReviewAt(member, reviewAt);
    }

    public void checkReviewPresentAtDate(Member member, LocalDateTime reviewAt){
        PlannerReview plannerReview = findReviewByMemberAndDate(member,reviewAt);
        if(plannerReview != null){throw new RuntimeException();}
    }

    public void reviewAutoSaveChange(PlannerReview plannerReview, ReviewUpdateRequestDto reviewUpdateRequestDto){
        plannerReview.setReviewContent(reviewUpdateRequestDto.getContent());
        plannerReviewRepository.save(plannerReview);
    }

    public ReviewDto toReviewDto(PlannerReview plannerReview){
        return new ReviewDto(plannerReview.getReviewContent());
    }




}
