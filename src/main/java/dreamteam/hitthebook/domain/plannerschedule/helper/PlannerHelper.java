package dreamteam.hitthebook.domain.plannerschedule.helper;

import dreamteam.hitthebook.common.exception.InvalidTimeDataException;
import dreamteam.hitthebook.common.exception.ModifyAuthenticationException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
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
import java.util.Optional;

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
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new); //익셉션 추가예정
    }

    // startAt과 endAt의 날짜가 다르다면 익셉션을 터뜨림
    public void checkSameDateOfScheduleTime(LocalDateTime startAt, LocalDateTime endAt){
        if(startAt.getYear() != endAt.getYear() ||
        startAt.getMonthValue() != endAt.getMonthValue() ||
        startAt.getDayOfMonth() != endAt.getDayOfMonth()){
            throw new InvalidTimeDataException();
        }
    }

    // 해당 시간에 겹치는 플래그 스케쥴이 있는지 확인하고 수정
    public void checkSameTimeOfSchedule(Member member, ScheduleRequestDto scheduleRequestDto){
        Optional<PlannerSchedule> beforePlannerSchedule = plannerScheduleRepository.findByMemberAndTimeRange(member,
                scheduleRequestDto.getScheduleAt(), scheduleRequestDto.getStartAt());
        Optional<PlannerSchedule> afterPlannerSchedule = plannerScheduleRepository.findByMemberAndTimeRange2(member,
                scheduleRequestDto.getScheduleAt(), scheduleRequestDto.getStartAt(), scheduleRequestDto.getEndAt());
        if(beforePlannerSchedule.isPresent() || afterPlannerSchedule.isPresent()){
            throw new InvalidTimeDataException();
        }
    }

    // 새로운 플래너스케쥴(일정) 추가
    public void createNewPlannerScheduleEvent(ScheduleRequestDto scheduleRequestDto, Member member){
        PlannerSchedule plannerSchedule = PlannerSchedule.createByRequestDto(scheduleRequestDto, ScheduleTypeEnum.EVENT, member);
        plannerScheduleRepository.save(plannerSchedule);
//        createNewPlannerScheduleEventAlert(plannerSchedule, member);
    }

    // 새로운 플래너스케쥴(과목) 추가
    public void createNewPlannerScheduleSubject(ScheduleRequestDto scheduleRequestDto, Member member){
        PlannerSchedule plannerSchedule = PlannerSchedule.createByRequestDto(scheduleRequestDto, ScheduleTypeEnum.SUBJECT, member);
        plannerScheduleRepository.save(plannerSchedule);
    }

    //시작 시간이 잘못되ㅇ었는지 확인(시작시간이 종료시간보다 나중인지)
    public void checkInvalidStartTime(LocalDateTime startAt, LocalDateTime endAt){
        if (startAt.isAfter(endAt)) {throw new InvalidTimeDataException();}
    }

    // 플래너스케쥴 id로 플래너 스케쥴 검색
    public PlannerSchedule findPlannerScheduleBySchedulePlannerScheduleId(Long plannerScheduleId){
        return plannerScheduleRepository.findById(plannerScheduleId).orElseThrow(ResourceNotFoundException::new); // 예외처리 필요
    }

    // 플래너 스케쥴을 카테고리 구분하여 날짜로 검색(로컬데이트타임이 정확히 일치하는지가 아니라 날짜만 같으면 바로 찾을 수 있도록)
    public List<PlannerSchedule> getSchedulesByCriteria(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        return plannerScheduleRepository.findByMemberAndScheduleTypeAndScheduleAt(member, scheduleType, scheduleAt);
    }

    // 플래너 스케쥴을 카테고리 구분없이 모두 검색(로컬데이트타임이 정확히 일치하는지가 아니라 날짜만 같으면 바로 찾을 수 있도록)
    public List<PlannerSchedule> getAllSchedulesByCriteria(Member member, LocalDateTime scheduleAt){
        return plannerScheduleRepository.findByMemberAndScheduleAt(member, scheduleAt);
    }

    // 카테고리가 구분된 플래너 스케쥴을 리스트로 변환하기
    public ScheduleListDto toScheduleListDto(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt){
        return new ScheduleListDto("successful", getSchedulesByCriteria(member, scheduleType, scheduleAt));
    }

    // 모든 플래너 스케쥴을 리스트로 변환하기
    public ScheduleListDto toScheduleAllListDto(Member member, LocalDateTime scheduleAt){
        return new ScheduleListDto("successful", getAllSchedulesByCriteria(member, scheduleAt));
    }

    // 피드백 수정하기
    public void updateFeedbackStatus(PlannerSchedule plannerSchedule, FeedbackTypeEnum feedbackType){
        plannerSchedule.setScheduleFeedback(feedbackType);
        plannerScheduleRepository.save(plannerSchedule);
    }

    // 스케쥴 수정권한 검사하기
    public void checkScheduleEditPermission(PlannerSchedule plannerSchedule, Member member){
        if(!(plannerSchedule.getMember().equals(member))){throw new ModifyAuthenticationException();} // 예외처리 필요
    }

    // 스케쥴 타입이 유효한지 검사하기
    public void checkValidScheduleType(ScheduleTypeEnum scheduleType, PlannerSchedule plannerSchedule){
        if(!(plannerSchedule.getScheduleType().equals(scheduleType))){throw new ModifyAuthenticationException();}
    }

    // 날짜와 멤버로 리뷰찾기
    public PlannerReview findReviewByMemberAndDate(Member member, LocalDateTime reviewAt){
        return plannerReviewRepository.findByMemberAndReviewAt(member, reviewAt);
    }

    // 리뷰가 있는지 없는지 검사하기 없다면 익셉션
    public void checkReviewPresentAtDate(Member member, LocalDateTime reviewAt){
        PlannerReview plannerReview = findReviewByMemberAndDate(member,reviewAt);
        if(plannerReview != null){throw new ResourceNotFoundException();}
    }

    // 리뷰 저장하기(오늘의 총평)
    public void reviewAutoSaveChange(PlannerReview plannerReview, ReviewUpdateRequestDto reviewUpdateRequestDto){
        plannerReview.setReviewContent(reviewUpdateRequestDto.getContent());
        plannerReviewRepository.save(plannerReview);
    }

    // reviewDto로 전환하기
    public ReviewDto toReviewDto(PlannerReview plannerReview){
        return new ReviewDto(plannerReview.getReviewContent());
    }

}
