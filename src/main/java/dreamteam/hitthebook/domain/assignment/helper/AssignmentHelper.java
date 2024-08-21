package dreamteam.hitthebook.domain.assignment.helper;

import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.assignment.enumulation.AssignmentDayOfWeekEnum;
import dreamteam.hitthebook.domain.assignment.repository.AssignmentRepository;
import dreamteam.hitthebook.domain.assignmentevent.entity.AssignmentEvent;
import dreamteam.hitthebook.domain.assignmentevent.repository.AssignmentEventRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static dreamteam.hitthebook.domain.assignment.dto.AssignmentDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssignmentHelper {
    private final MemberRepository memberRepository;
    private final AssignmentEventRepository assignmentEventRepository;
    private final AssignmentRepository assignmentRepository;

    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public Assignment findAssignmentByAssignmentId(Long assignmentId){
        return assignmentRepository.findById(assignmentId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public AssignmentEvent findAssignmentEventByAssignmentIdAndDate(AssignmentCompleteDto assignmentCompleteDto,Long assignmentId){
        LocalDate date = assignmentCompleteDto.getCompleteDate();
        return assignmentEventRepository.findByAssignment_AssignmentIdAndAssignmentDate(assignmentId,date)
                .orElseThrow(RuntimeException::new);
    }

    public void updateAssignmentComplete(AssignmentEvent assignmentEvent){
        assignmentEvent.setAssignmentIsComplete(true);
        assignmentEventRepository.save(assignmentEvent);
    }

    public void checkAssignmentEditPermission(Assignment assignment, Member member){
        if(!(assignment.getMember().equals(member))){throw new RuntimeException();}
    }

    @Async
    public void createAssignmentEvent(Assignment assignment, LocalDate startAt, LocalDate endAt, List<AssignmentDayOfWeekEnum> dayOfWeekEnumList) {
        LocalDate currentAt = startAt;

        while (!currentAt.isAfter(endAt)) {
            // 현재 날짜의 요일을 가져옴
            DayOfWeek currentDayOfWeek = currentAt.getDayOfWeek();

            // 요일이 enum 리스트에 포함되어 있는지 확인
            if (dayOfWeekEnumList.contains(AssignmentDayOfWeekEnum.valueOf(currentDayOfWeek.name()))) {
                AssignmentEvent assignmentEvent = new AssignmentEvent();
                assignmentEvent.setAssignment(assignment);
                assignmentEvent.setAssignmentDate(currentAt);
                assignmentEvent.setAssignmentIsComplete(false); // 기본값 설정

                assignmentEventRepository.save(assignmentEvent);
            }

            currentAt = currentAt.plusDays(1);
        }
    }
}
