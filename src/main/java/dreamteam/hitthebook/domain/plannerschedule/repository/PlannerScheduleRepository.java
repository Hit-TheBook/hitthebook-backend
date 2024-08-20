package dreamteam.hitthebook.domain.plannerschedule.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlannerScheduleRepository extends JpaRepository<PlannerSchedule, Long> {
    List<PlannerSchedule> findByMemberAndScheduleTypeAndScheduleAt(Member member, ScheduleTypeEnum scheduleType, LocalDateTime scheduleAt);
}
