package dreamteam.hitthebook.domain.timer.repository;

import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimerHistoryRepository extends JpaRepository<TimerHistory, Long> {

    void deleteByTimer_TimerId(Long timerId);

}