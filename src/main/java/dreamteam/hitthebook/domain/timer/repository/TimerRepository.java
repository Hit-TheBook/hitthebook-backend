package dreamteam.hitthebook.domain.timer.repository;

import dreamteam.hitthebook.domain.timer.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {

}
