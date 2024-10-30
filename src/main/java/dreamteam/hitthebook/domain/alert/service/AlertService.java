package dreamteam.hitthebook.domain.alert.service;

import dreamteam.hitthebook.domain.alert.dto.AlertDto;
import dreamteam.hitthebook.domain.alert.helper.AlertHelper;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dreamteam.hitthebook.domain.alert.dto.AlertDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AlertService {

    private final AlertHelper alertHelper;

    public AlertListDto findAlertList(String emailId){
        Member member = alertHelper.findMemberByEmailId(emailId);
        return alertHelper.findAlertListByMember(member);
    }

    public void setAssignmentAlarm(String emailId){
        Member member = alertHelper.findMemberByEmailId(emailId);
        alertHelper.setAssignmentEntity(member);
    }
}
