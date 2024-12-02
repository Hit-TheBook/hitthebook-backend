package dreamteam.hitthebook.domain.alert.helper;

import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static dreamteam.hitthebook.domain.alert.dto.AlertDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertHelper {
    private final AlertRepository alertRepository;
    private final MemberRepository memberRepository;

    // emailId로 멤버 검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    // 최신순으로 멤버와 알림타입으로 알림 검색
    public List<Alert> findAlertByMemberAndType(Member member, AlertTypeEnum alertTypeEnum){
        return alertRepository.findByMemberAndAlertTypeOrderByCreatedAtDesc(member, alertTypeEnum);
    }

    // 알림 리스트 dto 만들기
    public AlertResponseDto toAlertResponseDto(Member member){
        return new AlertResponseDto(findAlertByMemberAndType(member, AlertTypeEnum.NOTICE), findAlertByMemberAndType(member, AlertTypeEnum.LEVEL),
                findAlertByMemberAndType(member, AlertTypeEnum.EMBLEM), findAlertByMemberAndType(member, AlertTypeEnum.TIMER),
                findAlertByMemberAndType(member, AlertTypeEnum.PLANNER));
    }
}
