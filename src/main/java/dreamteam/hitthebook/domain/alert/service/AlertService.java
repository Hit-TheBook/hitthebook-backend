package dreamteam.hitthebook.domain.alert.service;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.helper.AlertHelper;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dreamteam.hitthebook.domain.alert.dto.AlertDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlertService {
    public final AlertRepository alertRepository;
    public final AlertHelper alertHelper;

    public AlertResponseDto findAlertList(String emailId){
        Member member = alertHelper.findMemberByEmailId(emailId);
        return alertHelper.toAlertResponseDto(member);
    }
}
