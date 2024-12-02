package dreamteam.hitthebook.domain.alert.repository;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    // 최신순으로 멤버와 알림타입을 이용하여 검색
    List<Alert> findByMemberAndAlertTypeOrderByCreatedAtDesc(Member member, AlertTypeEnum alertTypeEnum);

}
