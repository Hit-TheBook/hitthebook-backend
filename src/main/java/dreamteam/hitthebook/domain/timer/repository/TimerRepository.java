package dreamteam.hitthebook.domain.timer.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {

    @Query("SELECT t FROM Timer t WHERE t.member = :member AND DATE(t.studyStartTime) = :studyDate")
    List<Timer> findByMemberAndStudyStartTime(@Param("member") Member member, @Param("studyDate") LocalDate studyDate);

}
