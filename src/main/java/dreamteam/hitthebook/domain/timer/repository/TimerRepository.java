package dreamteam.hitthebook.domain.timer.repository;


import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    Optional<Timer> findBySubjectName(String subjectName);

    List<Timer> findByMember(Member member);

    @Query("SELECT t FROM Timer t WHERE t.isDeleted = true AND t.updatedAt < :cutoffDate")
    List<Timer> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM Timer t WHERE t.member = :member")
    void deleteByMemberPhysically(@Param("member") Member member);

    @Modifying
    @Query("DELETE FROM Timer t WHERE t.timerId = :id")
    void deletePhysicallyById(@Param("id") Long timerId);
}