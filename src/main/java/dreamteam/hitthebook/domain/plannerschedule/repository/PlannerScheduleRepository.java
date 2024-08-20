package dreamteam.hitthebook.domain.plannerschedule.repository;

import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerScheduleRepository extends JpaRepository<PlannerSchedule, Long> {
}
