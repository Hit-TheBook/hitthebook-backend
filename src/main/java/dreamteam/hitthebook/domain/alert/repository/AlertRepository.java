package dreamteam.hitthebook.domain.alert.repository;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    // 최신순으로 멤버와 알림타입을 이용하여 검색
    List<Alert> findByMemberAndAlertTypeOrderByCreatedAtDesc(Member member, AlertTypeEnum alertTypeEnum);

    List<Alert> findByMember(Member member);

    @Query("SELECT a FROM Alert a WHERE a.isDeleted = true AND a.updatedAt < :cutoffDate")
    List<Alert> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM Alert a WHERE a.member = :member")
    void deleteByMemberPhysically(@Param("member") Member member);
}
