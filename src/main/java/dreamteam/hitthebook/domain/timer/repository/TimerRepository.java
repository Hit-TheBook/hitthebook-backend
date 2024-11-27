package dreamteam.hitthebook.domain.timer.repository;


import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {

    boolean existsBySubjectName(String subjectName);

    Timer findBySubjectName(String subjectName);

    @Query("SELECT th FROM Timer th WHERE th.member = :member AND FUNCTION('DATE', th.studyTime) = :studyDate")
    List<Timer> findByMemberAndStudyTime(
            @Param("member") Member member,
            @Param("studyDate") LocalDate studyDate
    );

    @Query("SELECT t FROM Timer t WHERE t.member = :member AND FUNCTION('DATE', t.studyTime) = :studyDate")
    List<Timer> findByMemberAndStudyTimeDate(@Param("member") Member member, @Param("studyDate") LocalDate studyDate);

    List<Timer> findByMemberAndStudyTimeBetween(
            Member member,
            LocalDateTime startDate,
            LocalDateTime endDat
    );
}