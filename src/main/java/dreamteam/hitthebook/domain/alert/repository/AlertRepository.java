package dreamteam.hitthebook.domain.alert.repository;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}