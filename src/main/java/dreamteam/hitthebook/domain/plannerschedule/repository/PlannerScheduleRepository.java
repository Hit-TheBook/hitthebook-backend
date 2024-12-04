package dreamteam.hitthebook.domain.plannerschedule.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlannerScheduleRepository extends JpaRepository<PlannerSchedule, Long> {
//    List<PlannerSchedule> findByMemberAndScheduleTypeAndScheduleAt(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt);

    // 멤버의 플래너스케쥴을 스케쥴타입과 날짜로 검색
    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "p.scheduleType = :scheduleType AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate)")
    List<PlannerSchedule> findByMemberAndScheduleTypeAndScheduleAt(@Param("member") Member member,
                                                                   @Param("scheduleType") ScheduleTypeEnum scheduleType, @Param("scheduleDate") LocalDateTime scheduleDate);

    // 멤버와 멤버의 플래너 스케쥴을 날짜로 검색
    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate)")
    List<PlannerSchedule> findByMemberAndScheduleAt(@Param("member") Member member, @Param("scheduleDate") LocalDateTime scheduleDate);

    // 스케쥴 안겹치게 검사 알고리즘1
    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate) AND " +
            "p.startAt < :startAt AND p.endAt >= :startAt")
    Optional<PlannerSchedule> findByMemberAndTimeRange(@Param("member") Member member,
                                                                      @Param("scheduleDate") LocalDateTime scheduleDate,
                                                                      @Param("startAt") LocalDateTime startAt);

    // 스케쥴 안겹치게 검사 알고리즘2 , 1 + 2 둘 다 필요
    @Query("SELECT p FROM PlannerSchedule p WHERE p.member = :member AND " +
            "YEAR(p.scheduleAt) = YEAR(:scheduleDate) AND " +
            "MONTH(p.scheduleAt) = MONTH(:scheduleDate) AND " +
            "DAY(p.scheduleAt) = DAY(:scheduleDate) AND " +
            "p.startAt >= :startAt AND p.startAt <= :endAt")
    Optional<PlannerSchedule> findByMemberAndTimeRange2(@Param("member") Member member,
                                                                      @Param("scheduleDate") LocalDateTime scheduleDate,
                                                                      @Param("startAt") LocalDateTime startAt,
                                                                      @Param("endAt") LocalDateTime endAt);

    // 멤버가 해당 날짜에 생성한 플래너 스케쥴이 있는지 검색
    @Query("SELECT COUNT(ps) > 0 FROM PlannerSchedule ps WHERE ps.member = :member AND " +
            "YEAR(ps.createdAt) = :year AND MONTH(ps.createdAt) = :month AND DAY(ps.createdAt) = :day")
    boolean existsByMemberAndCreatedAt(@Param("member") Member member,
                                       @Param("year") int year,
                                       @Param("month") int month,
                                       @Param("day") int day);

    // 해당하는 날짜에 플래너 스케쥴들 검색
    List<PlannerSchedule> findByScheduleAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<PlannerSchedule> findByMember(Member member);

    @Query("SELECT p FROM PlannerSchedule p WHERE p.isDeleted = true AND p.updatedAt < :cutoffDate")
    List<PlannerSchedule> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM PlannerSchedule p WHERE p.member = :member")
    void deleteByMemberPhysically(@Param("member") Member member);

    @Modifying
    @Query("DELETE FROM PlannerSchedule ps WHERE ps.plannerScheduleId = :id")
    void deletePhysicallyById(@Param("id") Long id);

}
