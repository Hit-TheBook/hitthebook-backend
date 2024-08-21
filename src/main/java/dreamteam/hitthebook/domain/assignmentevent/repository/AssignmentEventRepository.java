package dreamteam.hitthebook.domain.assignmentevent.repository;

import dreamteam.hitthebook.domain.assignmentevent.entity.AssignmentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AssignmentEventRepository extends JpaRepository<AssignmentEvent,Long> {
    Optional<AssignmentEvent> findByAssignment_AssignmentIdAndAssignmentDate(Long assignmentId, LocalDate assignmentDate);
}
