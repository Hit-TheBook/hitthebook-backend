package dreamteam.hitthebook.domain.timer.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimerHistoryRepository extends JpaRepository<TimerHistory, Long> {
    Optional<List<TimerHistory>> findByTimer(Timer timer); // 타이머로 타이머 히스토리 리스트 옵셔널 검색

    @Query("SELECT th FROM TimerHistory th " +
            "WHERE th.timer = :timer " +
            "AND DATE(th.studyTime) = CURRENT_DATE")
    List<TimerHistory> findByTimerAndToday(@Param("timer") Timer timer); // 타이머로 오늘짜 타이머 히스토리 검색

    @Query("SELECT th FROM TimerHistory th " +
            "JOIN th.timer t " +
            "WHERE t.member = :member " +
            "AND DATE(th.studyTime) = CURRENT_DATE")
    List<TimerHistory> findTodayTimerHistoryByMember(@Param("member") Member member); // 멤버로 오늘짜 타이머 히스토리 검색

    @Query("SELECT th.studyTimeLength FROM TimerHistory th " +
            "JOIN th.timer t " +
            "WHERE t.member = :member " +
            "AND FUNCTION('YEAR', th.studyTime) = :year " +
            "AND FUNCTION('MONTH', th.studyTime) = :month " +
            "AND FUNCTION('DAY', th.studyTime) = :day")
    List<Duration> findStudyTimeLengthsByMemberAndDate(
            @Param("member") Member member,
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );

    @Query("SELECT th.studyTimeLength FROM TimerHistory th " +
            "JOIN th.timer t " +
            "WHERE t.member = :member " +
            "AND t.subjectName = :subjectName " +
            "AND FUNCTION('YEAR', th.studyTime) = :year " +
            "AND FUNCTION('MONTH', th.studyTime) = :month " +
            "AND FUNCTION('DAY', th.studyTime) = :day")
    List<Duration> findStudyTimeLengthsByMemberAndDateAndSubjectName(
            @Param("member") Member member,
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day,
            @Param("subjectName") String subjectName
    );

    @Modifying
    @Query("DELETE FROM TimerHistory t WHERE t.timer = :timer")
    void deleteByTimerPhysically(@org.springframework.data.repository.query.Param("timer") Timer timer);

}