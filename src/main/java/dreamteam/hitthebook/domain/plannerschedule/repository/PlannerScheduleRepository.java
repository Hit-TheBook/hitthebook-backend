package dreamteam.hitthebook.domain.plannerschedule.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlannerScheduleRepository extends JpaRepository<PlannerSchedule, Long> {
//    List<PlannerSchedule> findByMemberAndScheduleTypeAndScheduleAt(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt);

    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "p.scheduleType = :scheduleType AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate)")
    List<PlannerSchedule> findByMemberAndScheduleTypeAndScheduleAt(@Param("member") Member member,
                                                                   @Param("scheduleType") ScheduleTypeEnum scheduleType, @Param("scheduleDate") LocalDateTime scheduleDate);

    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate)")
    List<PlannerSchedule> findByMemberAndScheduleAt(@Param("member") Member member, @Param("scheduleDate") LocalDateTime scheduleDate);

    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate) AND " +
            "p.startAt < :startAt AND p.endAt >= :startAt")
    Optional<PlannerSchedule> findByMemberAndTimeRange(@Param("member") Member member,
                                                                      @Param("scheduleDate") LocalDateTime scheduleDate,
                                                                      @Param("startAt") LocalDateTime startAt);

    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate) AND " +
            "p.startAt >= :startAt AND p.startAt <= :endAt")
    Optional<PlannerSchedule> findByMemberAndTimeRange2(@Param("member") Member member,
                                                                      @Param("scheduleDate") LocalDateTime scheduleDate,
                                                                      @Param("startAt") LocalDateTime startAt,
                                                                      @Param("endAt") LocalDateTime endAt);


}
