package dreamteam.hitthebook.domain.assignment.repository;

import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

}
