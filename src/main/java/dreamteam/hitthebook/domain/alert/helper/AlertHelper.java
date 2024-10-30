package dreamteam.hitthebook.domain.alert.helper;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.TargetPageTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.assignment.repository.AssignmentRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static dreamteam.hitthebook.domain.alert.dto.AlertDto.*;


@Component
@RequiredArgsConstructor
public class AlertHelper {

    private final MemberRepository memberRepository;
    private final AssignmentRepository assignmentRepository;
    private final AlertRepository alertRepository;

    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public AlertListDto findAlertListByMember(Member member){
        List<Alert> alertList = alertRepository.findAlertByMember(member);
        return new AlertListDto(alertList);
    }

    public void setAssignmentEntity(Member member) {
        List<Assignment> assignmentList = assignmentRepository.findByMemberAndAssignmentEndAt(member, LocalDate.now());
        for (Assignment assignment : assignmentList) {
            String alertContent = "미션이 오늘까지 입니다!";
            Alert alert = new Alert(TargetPageTypeEnum.ASSIGNMENT,alertContent,assignment, member);
            alertRepository.save(alert);
        }
    }
}
