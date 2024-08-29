package dreamteam.hitthebook.domain.assignment.repository;

import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.assignment.entity.AssignmentEvent;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssignmentEventRepository extends JpaRepository<AssignmentEvent,Long> {
    Optional<AssignmentEvent> findByAssignment_AssignmentIdAndAssignmentDate(Long assignmentId, LocalDate assignmentDate);

    List<AssignmentEvent> findByAssignment(Assignment assignment);

    List<AssignmentEvent> findByAssignment_MemberAndAssignmentDate(Member member, LocalDate assignmentDate);

    List<AssignmentEvent> findByAssignment_MemberAndAssignment_AssignmentId(Member member, Long assignmentId);
}
