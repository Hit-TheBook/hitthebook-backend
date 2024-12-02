package dreamteam.hitthebook.domain.alert.repository;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByMemberAndAlertTypeOrderByCreatedAtDesc(Member member, AlertTypeEnum alertTypeEnum);

}
